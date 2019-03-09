#!/usr/bin/env python3

import os
import pygame

import requests
import json

import matplotlib
import matplotlib.pyplot as plt

import cartopy.crs as ccrs
import cartopy.feature as cfeature

import cartopy.io.img_tiles as cimgt
import cartopy.io.shapereader as shpreader
from shapely.geometry import Point

matplotlib.use("Agg")

country_colors = dict()

EXTENT = (-25, 47, 30, 68)
SCREENSIZE = (1920, 1000)
API_HOST = "localhost"

countries = list()
blocked_areas = list()

pygame.init()

basicfont = pygame.font.Font("data/DejaVuSans.ttf", 14)
largefont = pygame.font.Font("data/DejaVuSans.ttf", 34)

spritelist = []


def gis_from_pixels(pos):
    gis_x = EXTENT[0] + (pos[0] * abs(EXTENT[0] - EXTENT[1]) / SCREENSIZE[0])
    gis_y = EXTENT[3] - (pos[1] * abs(EXTENT[2] - EXTENT[3]) / SCREENSIZE[1])

    return (gis_x, gis_y)


def country_from_gis(pos):
    punkt = Point(*pos)
    for country in countries:
        if country.geometry.contains(punkt):
            return country


def country_from_pixels(pos):
    return country_from_gis(gis_from_pixels(pos))


def render_map():
    stamen_terrain = cimgt.Stamen("terrain-background")
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1, projection=ccrs.PlateCarree())
    ax.set_extent(EXTENT, crs=ccrs.PlateCarree())

    ax.add_image(stamen_terrain, 4)
    # ax.stock_img()
    ax.coastlines(resolution="50m")
    ax.add_feature(cfeature.BORDERS)
    ax.add_feature(cfeature.OCEAN)

    plt.subplots_adjust(left=0.0, right=1.0, bottom=0.0, top=1.0)
    fig.set_size_inches(19.2, 10)

    shpfilename = shpreader.natural_earth(
        resolution="110m", category="cultural", name="admin_0_countries"
    )
    reader = shpreader.Reader(shpfilename)
    country_list = list(reader.records())

    for country in country_list:
        if country.attributes["CONTINENT"] == "Europe":
            countries.append(country)

    shpfilename = shpreader.natural_earth(
        resolution="10m", category="physical", name="lakes"
    )
    reader = shpreader.Reader(shpfilename)
    for lake in reader.records():
        blocked_areas.append(lake.geometry)
        ax.add_geometries(lake.geometry, ccrs.PlateCarree())

    print(len(blocked_areas))

    plt.draw()
    return fig.canvas.tostring_rgb()


def load_image(name, colorkey=None):
    fullname = os.path.join("data", name)
    try:
        image = pygame.image.load(fullname)
    except pygame.error as e:
        print("Cannot load image:", name)
        raise SystemExit(e)
    image = image.convert()
    if colorkey is not None:
        if colorkey is -1:
            colorkey = image.get_at((0, 0))
        image.set_colorkey(colorkey, pygame.RLEACCEL)
    return image, image.get_rect()


class TPLabel(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image = basicfont.render("Test", True, (0, 0, 0))
        self.rect = self.image.get_rect()
        self.held = False
        self.quality = [0, 0, 0, 0]


class Teleport(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image, self.rect = load_image("teleport.png", -1)
        self.orgimg = self.image.copy()
        self.image.convert_alpha()
        self.held = False
        self.session = requests.Session()
        self.label = TPLabel()
        self.quality = [0, 0, 0, 0]

    def is_blocked(self, pos):
        punkt = Point(pos)
        for area in blocked_areas:
            if area.contains(punkt):
                return True
        return False

    def update(self):
        if self.held:
            pos = pygame.mouse.get_pos()
            self.rect.midbottom = pos
            self.country = country_from_pixels(pos)
            lat, lon = gis_from_pixels(pos)
            if self.country and not self.is_blocked((lat, lon)):
                self.compute()
                caption = "Country: {} lat {:3.2f} lon {:3.2f} {}".format(
                    self.country.attributes["NAME"], lat, lon, self.quality
                )
                pygame.display.set_caption(caption)
                self.label.rect.midbottom = self.rect.midtop
                self.label.image = basicfont.render(
                    caption, True, (0, 0, 0), ([x * 255 for x in self.quality[:3]])
                )
                self.image = self.orgimg.copy()
            else:
                self.label.image = pygame.Surface((1, 1))
                self.quality = [0, 0, 0, 0]
                pygame.draw.line(self.image, (255, 0, 0), (0, 0), (29, 29), 2)
                pygame.draw.line(self.image, (255, 0, 0), (0, 29), (29, 0), 2)

    def compute(self):
        lon, lat = gis_from_pixels(self.rect.midbottom)
        country = country_from_pixels(self.rect.midbottom).attributes["ADM0_A3"]
        data = {
            "teleportCountry": country,
            "teleportLat": str(lat),
            "teleportLon": str(lon),
            "satellite": "23.5",
            "transmitBand": "C",
            "receiveBand": "Ka",
        }
        headers = {"content-type": "application/json"}
        r = self.session.post(
            f"http://{API_HOST}:8080/compute", data=json.dumps(data), headers=headers
        )
        self.quality = r.json()


def mouseclick(teleports):
    pos = pygame.mouse.get_pos()
    for teleport in teleports:
        if teleport.held:
            teleport.update()
            if teleport.country:
                teleport.held = False
                pygame.mouse.set_visible(True)
        if teleport.rect.collidepoint(pos):
            teleport.held = True
            pygame.mouse.set_visible(False)


def main():

    screen = pygame.display.set_mode(SCREENSIZE)

    bg = pygame.image.fromstring(render_map(), SCREENSIZE, "RGB")

    screen.blit(bg, (0, 0))

    clock = pygame.time.Clock()

    allsprites = pygame.sprite.RenderPlain(spritelist)

    while True:
        clock.tick(60)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return
            elif event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE:
                return
            elif event.type == pygame.KEYDOWN and event.key == pygame.K_SPACE:
                tp = Teleport()
                tp.rect.midbottom = pygame.mouse.get_pos()
                spritelist.append(tp)
                spritelist.append(tp.label)
                allsprites = pygame.sprite.RenderPlain(spritelist)

            elif event.type == pygame.MOUSEBUTTONDOWN:
                mouseclick(allsprites)
        screen.blit(bg, (0, 0))
        allsprites.update()
        allsprites.draw(screen)
        total_quality = sum([x.quality[3] for x in spritelist])
        tq_str = "{:3.2f}".format(total_quality)
        qual_label = largefont.render(tq_str, False, (0, 0, 0), (0, 255, 0))
        screen.blit(qual_label, (20, 900))
        pygame.display.update()


if __name__ == "__main__":
    main()
