package vacuum;

import floor.Tile;

public class CleanSweep {
	
	private static CleanSweep instance;
	
	private Tile currentTile;
	
	private CleanSweep() {}
	
	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
		}
		
		return instance;
	}
}
