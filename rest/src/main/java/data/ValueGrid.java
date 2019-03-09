package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Value Grid for the persistence of data on the view.
 * Initialised with different data
 * @author sneuroh
 *
 */
public class ValueGrid {
	
	public static final int BASE_GRID = 1;
	public static final int COST_OF_LIVING_GRID = 2;
	public static final int QUALITY_OF_CONNECTIVITY_GRID = 3;
	public static final int KA_BAND_GRID = 4;
	public static final int KU_BAND_GRID = 5;
	public static final int C_BAND_GRID = 6;
	
	Logger logger = LoggerFactory.getLogger(ValueGrid.class);
	
	public int getLatNum() {
		return latNum;
	}

	public int getLonNum() {
		return lonNum;
	}

	private Double[][] valueGrid;
	private int latNum;
	private int lonNum;
	
	/**
	 * CTOR
	 * @param type
	 */
	public ValueGrid(int type) {
		latNum = 8;
		lonNum = 16;
		valueGrid = new Double[latNum][lonNum];
		switch (type) {
			case COST_OF_LIVING_GRID: 
				initCostOfLivingGrid();
				break;
			case QUALITY_OF_CONNECTIVITY_GRID: 
				initQualityOfConnectivityGrid();
				break;
			case KA_BAND_GRID: 
				initKaBandGrid();
				break;
			case KU_BAND_GRID: 
				initKuBandGrid();
				break;
			case C_BAND_GRID: 
				initCBandGrid();
				break;
			case BASE_GRID: 
				initBaseGrid();
				break;
			default: initValueGrid();	
		}
	}
	/**
	 * C Band Grid, a high value indicates that there are no attenuation problems
	 * 0.0 < x <=1.0
	 */
	private void initCBandGrid() {
		Double[][] cBandGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.8,0.8,0.5,0.5,0.5,0.8,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.4,0.4,0.4,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}	
		};
		valueGrid = cBandGrid;
	}
	
	/**
	 * Ku Band Grid, a high value indicates that there are no attenuation problems
	 * 0.0 < x <=1.0
	 */
	private void initKuBandGrid() {
		Double[][] kuBandGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.8,0.8,0.5,0.5,0.5,0.8,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.4,0.4,0.4,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}	
		};
		valueGrid = kuBandGrid;
	}
	
	/**
	 * Ka Band Grid, a high value indicates that there are no attenuation problems
	 * 0.0 < x <=1.0
	 */
	private void initKaBandGrid() {
		Double[][] kaBandGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.8,0.8,0.5,0.5,0.5,0.8,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.4,0.4,0.4,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.9,0.9,0.8,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}	
		};
		valueGrid = kaBandGrid;
	}
	
	/**
	 * Test Grid
	 * 0.0 < x <=1.0
	 */
	private void initBaseGrid() {
		Double[][] baseGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}	
		};
		valueGrid = baseGrid;
	}
	
	/**
	 * Grid representing the quality of the Internet connectivity
	 * A high value indicates a good connectivity 0.0 < x <=1.0 
	 * Central Europe is better connected
	 */
	private void initQualityOfConnectivityGrid() {
		Double[][] qualityOfConnectivityGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.9,0.9,0.9,0.9,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.8,0.5,0.5},
				{0.5,0.5,0.1,0.5,0.5,0.5,0.9,0.5,0.1,0.5,0.5,0.1,0.5,0.8,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.5,0.9,0.9,0.5,0.1,0.5,0.5,0.5,0.7,0.2,0.5},
				{0.5,0.5,0.5,0.5,0.5,0.1,0.9,0.9,0.5,0.5,0.5,0.5,0.6,0.7,0.5,0.5},
				{0.5,0.5,0.5,1.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.4,0.5,0.3,0.5},
				{0.5,0.5,0.5,0.1,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.3,0.5,0.5}
		};
		valueGrid = qualityOfConnectivityGrid;
	}
	
	/**
	 * Grid representing the cost of living - indicating higher costs to operate the teleport
	 *  A high value indicates a low cost of living 0.0 < x <=1.0 
	 *  Central Europe is less ideal
	 */
	private void initCostOfLivingGrid() {
		Double[][] costOfLivingGrid = {
				{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.1,0.1,0.1,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.5,0.3,0.4,0.5,0.5,0.5,0.5,0.1,0.5,0.1,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.7,0.5,0.7,0.5,0.5,0.5,0.5,0.5,0.2,0.1,0.5,0.5,0.5},
				{0.5,0.5,0.5,0.6,0.5,0.8,0.1,0.1,0.5,0.5,0.3,0.5,0.8,0.9,0.9,0.5},
				{0.9,0.5,0.2,0.4,0.5,0.5,0.1,0.1,0.5,0.3,0.5,0.5,0.8,0.8,0.9,0.5},
				{0.9,0.8,0.5,0.5,0.5,0.5,0.5,0.1,0.5,0.5,0.5,0.3,0.6,0.7,0.9,0.5},
				{0.9,0.8,0.5,0.5,0.5,0.5,0.5,0.2,0.5,0.6,0.5,0.2,0.8,0.5,0.9,0.5},
				{0.9,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.6,0.7,0.8,0.9,0.9,0.5}	
		};
		valueGrid = costOfLivingGrid;
	}
	
	/**
	 * Simple initialiser
	 */
	private void initValueGrid() {
		for(int i = 0; i<latNum; i++) {
			for(int k = 0; k<lonNum; k++) {
				valueGrid[i][k] = 0.0;
			}
		}
	}
	/**
	 * Scaling of the lat lon coordinates to the grid
	 * @param lat 30 .. 68
	 * @param lon -25 .. +47
	 * @return gris value
	 */
	public Double getGridValue(int lat, int lon) {
		if (lat < 30 ) return 0.0;
		if (lat > 68 ) return 0.0;
		if (lon < -25) return 0.0;
		if (lon > 47) return 0.0;
		int latN = (lat-30)/5;
		int lonN = (lon+25)/5;
		//System.out.println(String.format("%s %s", latN,lonN));
		return getGridValueN(latN, lonN);
	}
	
	/**
	 * Grid access with index
	 * @param latN
	 * @param lonN
	 * @return
	 */
	private Double getGridValueN(int latN, int lonN) {
		if (latN >= latNum) return 0.0;
		if (lonN >= lonNum) return 0.0;
		return valueGrid[latN][lonN];
	}
	
	/**
	 * Test
	 * @param args
	 */
	public static void main (String[] args) {
		ValueGrid vg = new ValueGrid(COST_OF_LIVING_GRID);
		System.out.println("N " + vg.getGridValueN(0, 0));
		System.out.println("N " + vg.getGridValueN(9, 18));
		System.out.println("N " + vg.getGridValueN(10, 19));
		System.out.println("N " + vg.getGridValueN(10, 19));
		
		for (int i= 30;i<=68;i=i+5) {
			for (int k= -25;k<=47;k=k+5) {
				System.out.print(vg.getGridValue(i, k) + " ");
			}
			System.out.println();
		}	
	}	
}
