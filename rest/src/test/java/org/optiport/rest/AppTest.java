package org.optiport.rest;

import org.optiport.rest.beans.Input;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.Constants;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	ObjectiveFunction of = new ObjectiveFunction();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	Input in = new Input();
    	for (int b =0; b< Constants.bands.length;b++) {
	    	in.setReceiveBand(Constants.bands[b]);
	    	in.setTransmitBand(Constants.C_BAND);
	    	for (int s = 0;s< 1;s++) {
		    	in.setSatellite(Constants.satellites[s]);
		    	for (int c=0; c<1;c++) {
			    	in.setTeleportCountry(Constants.countries[c]);
			    	for (int i= 30;i<=68;i=i+5) 
						for (int k= -25;k<=47;k=k+5) {
							in.setTeleportLat(Double.valueOf(i));
					    	in.setTeleportLon(Double.valueOf(k));
					    	System.out.println(of.computeObjectiveFunction(in) + " ");
						}	
					}
	    		}
    		}
    	}
    }

