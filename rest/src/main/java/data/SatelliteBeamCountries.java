package data;

import java.util.HashSet;
import java.util.Set;

public class SatelliteBeamCountries {
	
	public Set<String> getInBeamCountries() {
		return inBeamCountries;
	}
	public void setInBeamCountries(Set<String> inBeamCountries) {
		this.inBeamCountries = inBeamCountries;
	}
	public String getSatellite() {
		return satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	private Set<String> inBeamCountries = new HashSet<String>();
	private String satellite;
	
	

}
