package vacuum;

import floor.Tile;
import general.DataValidationException;

public class PathCalculator {

	/*
	 * 1. Come up with a "path" for general movement + vacuuming.
	 * 	- Simple
	 * 	- Check for nearby unvisited tiles
	 *	- Don't worry about battery usage or floor type
	 * 	- Less structured
	 * 	- wander() (?)
	 * 
	 * 2. Come up with a path to return to a charging station.
	 *	- Shortest (or most efficient) path
	 *	- Don't check for nearby tiles
	 *	- More structured
	 *
	 */

	private static PathCalculator instance;

	private PathCalculator() {}

	public static PathCalculator getInstance() {
		if (instance == null) {
			instance = new PathCalculator();
		}

		return instance;
	}

	// TODO: Come up with an algorithm to find closest tile with dirt
	public static char[] calculatePath(Tile t1, Tile t2) {
		return null;
	}

	// TODO: Come up with an algorithm.
	public static char[] getEfficientPath(Tile t1, Tile t2) {
		return null;

	}


}

