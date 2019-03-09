package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.SatelliteBeamCountries;

/**
 * Check, if the Teleport is in the Beam of the Satellite
 * @author sneuroh
 *
 */
public class TeleportInBeam {
	
	Logger logger = LoggerFactory.getLogger(TeleportInBeam.class);
	//Data
	private Map<String, SatelliteBeamCountries> satInBeamCountries = new HashMap<String, SatelliteBeamCountries>();
	
	/**
	 * CTOR - Create data
	 */
	public TeleportInBeam(){
		
		logger.info("Init: TeleportInBeam");
		
		SatelliteBeamCountries sbc = new SatelliteBeamCountries();
		sbc.setSatellite(Constants.ASTRA_235);
		Set<String> inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_235, sbc);
		//----------
		sbc.setSatellite(Constants.ASTRA_500);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_500, sbc);
		
		//----------
		sbc.setSatellite(Constants.ASTRA_192);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_192, sbc);
		
		//----------
		sbc.setSatellite(Constants.GOVSAT);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.GOVSAT, sbc);
		//----------
		sbc.setSatellite(Constants.ASTRA_235);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_235, sbc);
		//----------
		sbc.setSatellite(Constants.ASTRA_282);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_282, sbc);
		
		//----------
		sbc.setSatellite(Constants.ASTRA_315);
		inBeamCountries = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			inBeamCountries.add(Constants.countries[i]);
		}
		sbc.setInBeamCountries(inBeamCountries);
		
		satInBeamCountries.put(Constants.ASTRA_315, sbc);
		
	}
	/**
	 * Check if the TEleport is in the Beam
	 * @param teleportCountry
	 * @param satellite
	 * @return 1.0 if the Teleport is in the Beam, -1.0 else
	 */
	public Double isTeleportInBeam(String teleportCountry, String satellite) {
		Double result = 1.0;
		
		SatelliteBeamCountries inBeamCountries = satInBeamCountries.get(satellite);
		if (inBeamCountries == null) {
			result = -1.0;
		}else {
			if (!inBeamCountries.getInBeamCountries().contains(teleportCountry)) {
				result = -1.0;
			}
		}
		logger.info(String.format("isTeleportInBeam %s %s: %s", satellite, teleportCountry, result));
		return result;
	}
	
	/**
	 * Basic Test
	 * @param args
	 */
	public static void main(String[] args) {
		TeleportInBeam tip = new TeleportInBeam();
		tip.isTeleportInBeam(Constants.GERMANY, Constants.ASTRA_235);
		tip.isTeleportInBeam(Constants.MAROC, Constants.ASTRA_235);
		
	}

}
