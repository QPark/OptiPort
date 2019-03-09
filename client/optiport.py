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

pygame.init()

basicfont = pygame.font.Font("data/DejaVuSans.ttf", 14)


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

    # ax.add_image(stamen_terrain, 4)
    ax.stock_img()
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
            # ax.add_geometries(
            # country.geometry,
            # ccrs.PlateCarree(),
            # facecolor=(0, 0, (1 / color)),
            # label=country.attributes["ADM0 A3"],
            # )

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


class Teleport(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image, self.rect = load_image("teleport.png", -1)
        self.image.convert_alpha()
        self.held = False
        self.session = requests.Session()

    def update(self):
        if self.held:
            pos = pygame.mouse.get_pos()
            self.rect.midbottom = pos
            self.country = country_from_pixels(pos)
            lat, lon = gis_from_pixels(pos)
            if self.country:
                self.compute()
                caption = "{} / {:3.2f}:{:3.2f} / {}".format(
                    self.country.attributes["NAME"], lat, lon, self.quality
                )
                pygame.display.set_caption(caption)

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

    teleport = Teleport()
    teleport.rect.topleft = (100, 100)
    teleport2 = Teleport()
    teleport2.rect.topleft = (200, 200)

    spritelist = []
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
                allsprites = pygame.sprite.RenderPlain(spritelist)

            elif event.type == pygame.MOUSEBUTTONDOWN:
                mouseclick(allsprites)
        screen.blit(bg, (0, 0))
        allsprites.update()
        allsprites.draw(screen)
        pygame.display.update()


if __name__ == "__main__":
    main()
