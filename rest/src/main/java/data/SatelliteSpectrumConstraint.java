package data;

import java.util.Set;

/**
 * Data Container
 * @author sneuroh
 *
 */
public class SatelliteSpectrumConstraint {
	public String getSatellite() {
		return satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	public Set<String> getCountryBands() {
		return countryBand;
	}
	public void setCountryBands(Set<String> countryBand) {
		this.countryBand = countryBand;
	}
	private String satellite;
	private Set<String> countryBand;
	
}
