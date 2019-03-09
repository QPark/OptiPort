package org.optiport.rest;

import org.optiport.rest.beans.Input;

import data.ValueGrid;
import model.Constants;
import model.GridAnalysis;
import model.TeleportInBeam;
import model.TransmissionRights;

/**
 * Objective function for the teleport optimisation problem
 * @author sneuroh
 *
 */
public class ObjectiveFunction { 
	
	//Constituents 
	private static TransmissionRights transmissionRights = new TransmissionRights();
	private static TeleportInBeam teleportInBeam = new TeleportInBeam();
	private static GridAnalysis  costOfLivingAnalysis = new GridAnalysis(ValueGrid.COST_OF_LIVING_GRID);
	private static GridAnalysis  qualityOfConnectivityAnalysis = new GridAnalysis(ValueGrid.QUALITY_OF_CONNECTIVITY_GRID);
	private static GridAnalysis  kaBandAnalysis = new GridAnalysis(ValueGrid.KA_BAND_GRID);
	
	
	/**
	 * Compute the objective function
	 * @param in
	 * @return value of the objective function
	 */
	public Double computeObjectiveFunction(Input in) {
		
		Double result = 1.0;
		
		Double transmissionRightsOf = transmissionRights.hasTransmissionRights(in.getSatellite(), in.getTransmitBand(), in.getTeleportCountry());
		Double teleportInBeamOf = teleportInBeam.isTeleportInBeam(in.getTeleportCountry(), in.getSatellite());
		Double costOfLivingGridOf = costOfLivingAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		Double qualityOfConnectivityGridOf = qualityOfConnectivityAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		Double kaBandGridOf = kaBandAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		
		//Function - no weights for the moment
		result = result * transmissionRightsOf * teleportInBeamOf * costOfLivingGridOf * qualityOfConnectivityGridOf;
		//Ka BAnd
		if (in.getTransmitBand().equals(Constants.KA_BAND)) {
			result = result * kaBandGridOf;
		}
		
		return result;
	}
	
;
}
