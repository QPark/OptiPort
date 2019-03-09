package org.optiport.rest;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.optiport.rest.beans.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Rest Endpoints
 * @author sneuroh
 *
 */
@RestController
public class OptiPortController {
	
		Logger logger = LoggerFactory.getLogger(OptiPortController.class);
		
		//The objective function
		private ObjectiveFunction of = new ObjectiveFunction();
	
		//Index
	    @RequestMapping("/")
	    public String index() {
	        return "Greetings from OptiPort!";
	    }
	    
	    //Computation of the objective function
	    @PostMapping("/compute")
	    public Double[] computeObjectiveFunction(@RequestBody Map<String, Object> payload) {
	    	logger.info("Compute objective function");
	    	Input input = new Input();
	    	input.setTeleportCountry((String)payload.get("teleportCountry"));
	    	input.setTransmitBand((String)payload.get("transmitBand"));
	    	input.setReceiveBand((String)payload.get("receiveBand"));
	    	input.setTeleportLat(Double.valueOf((String) payload.get("teleportLat")));
	    	input.setTeleportLon(Double.valueOf((String) payload.get("teleportLon")));
	    	input.setSatellite((String) payload.get("satellite"));
	    	input.print();
	    	Double[] result = of.computeObjectiveFunction(input);
	    	
	    	logger.info(String.format("COL %s QOC %s ATT %s  OF VAL %s", result[0], result[1], result[2],result[3]));
	     	return result;
	    }
	    

	}

