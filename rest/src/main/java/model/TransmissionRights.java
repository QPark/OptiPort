package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.SatelliteSpectrumConstraint;

/**
 * https://www.nationsonline.org/oneworld/country_code_list.htm
 * @author sneuroh
 *
 */


public class TransmissionRights {
	
	Logger logger = LoggerFactory.getLogger(TransmissionRights.class);
	
	
	/**
	 * 
	 */
	public TransmissionRights(){
		
		logger.info("Init: TransmissionRights");
		
		SatelliteSpectrumConstraint c = new SatelliteSpectrumConstraint();
		c.setSatellite(Constants.ASTRA_235);
		Set<String> countryBand = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			countryBand.add(Constants.countries[i] + "_"+Constants.C_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KA_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KU_BAND);
		}
		
		c.setCountryBands(countryBand);
		rights.put(Constants.ASTRA_235,c);
		
		c = new SatelliteSpectrumConstraint();
		c.setSatellite(Constants.ASTRA_500);
		countryBand = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			countryBand.add(Constants.countries[i] + "_"+Constants.C_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KA_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KU_BAND);
		}
		
		c.setCountryBands(countryBand);
		rights.put(Constants.ASTRA_500,c);
		
		c = new SatelliteSpectrumConstraint();
		c.setSatellite(Constants.ASTRA_282);
		countryBand = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			countryBand.add(Constants.countries[i] + "_"+Constants.C_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KA_BAND);
			//countryBand.add(Constants.countries[i] + "_"+Constants.KU_BAND);
		}
		
		c.setCountryBands(countryBand);
		rights.put(Constants.ASTRA_282,c);
		
		c = new SatelliteSpectrumConstraint();
		c.setSatellite(Constants.ASTRA_235);
		countryBand = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			countryBand.add(Constants.countries[i] + "_"+Constants.C_BAND);
			//countryBand.add(Constants.countries[i] + "_"+Constants.KA_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KU_BAND);
		}
		
		c.setCountryBands(countryBand);
		rights.put(Constants.ASTRA_235,c);
		
		c = new SatelliteSpectrumConstraint();
		c.setSatellite(Constants.GOVSAT);
		countryBand = new HashSet<String>();
		
		for (int i=0;i<Constants.countries.length;i++){
			//countryBand.add(Constants.countries[i] + "_"+Constants.C_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KA_BAND);
			countryBand.add(Constants.countries[i] + "_"+Constants.KU_BAND);
		}
		
		c.setCountryBands(countryBand);
		rights.put(Constants.GOVSAT,c);
		
		
		
	}
	
	/**¨
	 * 
	 * @param satellite
	 * @param band
	 * @param country
	 * @return 1, if satellite has transmissionRights in the country for the band
	 */
	public Double hasTransmissionRights(String satellite, String band, String country) {
		
		Double result = 1.0;
		SatelliteSpectrumConstraint c = rights.get(satellite);	
		
		if (c == null) {
			result = -1.0;
		}else {
			String key = country + "_" + band;	
			if (!c.getCountryBands().contains(key)){
				result = -1.0;
			}
		}
		logger.info(String.format("hasTransmissionRights %s %s %s: %s", satellite, band, country, result));
		return result;
	}
	
	
	//Data
	private Map<String, SatelliteSpectrumConstraint> rights = new HashMap<String,SatelliteSpectrumConstraint>();

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		TransmissionRights rights = new TransmissionRights();
		rights.hasTransmissionRights(Constants.ASTRA_235, Constants.C_BAND, Constants.FRANCE);
		rights.hasTransmissionRights(Constants.ASTRA_235, Constants.KA_BAND, Constants.GREAT_BRITAIN);
		rights.hasTransmissionRights(Constants.ASTRA_235, Constants.KA_BAND, Constants.GREAT_BRITAIN);
		
	}
	
	
}
