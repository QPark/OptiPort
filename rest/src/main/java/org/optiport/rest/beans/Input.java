package org.optiport.rest.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input value object 
 * @author sneuroh
 *
 */
public class Input {
	
	public Logger getLogger() {
		return logger;
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}



	public String getTeleportCountry() {
		return teleportCountry;
	}



	public void setTeleportCountry(String country) {
		this.teleportCountry = country;
	}



	public Double getTeleportLat() {
		return teleportLat;
	}



	public void setTeleportLat(Double teleportLat) {
		this.teleportLat = teleportLat;
	}



	public Double getTeleportLon() {
		return teleportLon;
	}



	public void setTeleportLon(Double teleportLon) {
		this.teleportLon = teleportLon;
	}



	public String getSatellite() {
		return satellite;
	}



	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}



	public String getTransmitBand() {
		return transmitBand;
	}



	public void setTransmitBand(String transmitBand) {
		this.transmitBand = transmitBand;
	}



	public String getReceiveBand() {
		return receiveBand;
	}



	public void setReceiveBand(String receiveBand) {
		this.receiveBand = receiveBand;
	}



	Logger logger = LoggerFactory.getLogger(Input.class);
	
	private String teleportCountry;
	private Double teleportLat;
	private Double teleportLon;
	private String satellite;
	private String transmitBand;
	private String receiveBand;

	
	
	public void print() {
		logger.info("Country: " + teleportCountry);
		logger.info("Lat,Lon: " + teleportLat + " " + teleportLon);
		logger.info("Satellite: " + satellite);
		logger.info("Tx Band: " + transmitBand);
		logger.info("Rx Band: " + receiveBand);
	}

	

}
