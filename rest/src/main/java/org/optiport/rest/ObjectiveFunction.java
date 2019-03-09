package org.optiport.rest;

import java.math.BigDecimal;

import org.optiport.rest.beans.Input;

import ch.qos.logback.classic.Logger;
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
	private static GridAnalysis  kuBandAnalysis = new GridAnalysis(ValueGrid.KU_BAND_GRID);
	private static GridAnalysis  cBandAnalysis = new GridAnalysis(ValueGrid.C_BAND_GRID);
	
	private int weightCOL = 3;
	private int weightQOC = 8;
	private int weightATT = 5;
	
	/**
	 * Compute the objective function
	 * @param in
	 * @return value of the objective function
	 */
	public Double[] computeObjectiveFunction(Input in) {
		
		Double[] result = {1.0, 1.0, 1.0, 1.0};
		
		Double transmissionRightsOf = transmissionRights.hasTransmissionRights(in.getSatellite(), in.getTransmitBand(), in.getTeleportCountry());
		Double teleportInBeamOf = teleportInBeam.isTeleportInBeam(in.getTeleportCountry(), in.getSatellite());
		Double costOfLivingGridOf = costOfLivingAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		Double qualityOfConnectivityGridOf = qualityOfConnectivityAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		//attenuation
		Double attenuationOF = 0.0;
		if (in.getTransmitBand().equals(Constants.C_BAND)) {
			attenuationOF = cBandAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		}
		if (in.getTransmitBand().equals(Constants.KA_BAND)) {
			attenuationOF = kaBandAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		}
		if (in.getTransmitBand().equals(Constants.KU_BAND)) {
			attenuationOF = kuBandAnalysis.getValue(in.getTeleportLat().intValue(), in.getTeleportLon().intValue());
		}
		
		
		result[0] = costOfLivingGridOf;
		result[1] = qualityOfConnectivityGridOf;
		result[2] = attenuationOF;
		
		/**
		 * Objective Function:
		 * 
		 * Max(
		 *  (Cost of Living * weight +
		 *  Quality of Connectivity * weight +
		 *  Attenuation * weight)
		 *  * Transmission Right Flag * Teleport In Beam Flag
		 *  )
		 *  
		 *  Hard constraints: Transmission rights and Teleport in Beam - if violated the objective function value is multiplied with -1
		 *  
		 *  A negative value for the objective function invalidates the solution. 
		 * 
		 */
		
		Double weightedOf = (costOfLivingGridOf*weightCOL) + (qualityOfConnectivityGridOf * weightQOC) + (attenuationOF * weightATT);
		
		
		result[3] = weightedOf * transmissionRightsOf * teleportInBeamOf;
		BigDecimal weightedOfRounded = new BigDecimal(result[3]);
		result[3] = weightedOfRounded.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return result;
	}
	
;
}
