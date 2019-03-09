package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.ValueGrid;

/**
 * Grid data analysis
 * @author sneuroh
 *
 */
public class GridAnalysis {
	
	Logger logger = LoggerFactory.getLogger(GridAnalysis.class);
	private int type = ValueGrid.BASE_GRID;
	
	private ValueGrid grid = null;
	
	/**
	 * Initialisation
	 * @param _type
	 */
	public GridAnalysis(int _type) {
		type = _type;
		logger.info("Init: Grid Analysis - " + type);
		grid = new ValueGrid(type);
	}
	
	/**
	 * Value for the dimension based on lat lon
	 * @param lat
	 * @param lon
	 * @return
	 */
	public Double getValue(int lat, int lon) {
		Double result = grid.getGridValue(lat, lon);
		logger.info(String.format("Grid Analysis %s %s %s: %s", type, lat, lon, result));
		return result;
	}

}
