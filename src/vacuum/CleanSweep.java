package vacuum;

import java.util.ArrayList;

import floor.Tile;
import general.DataValidationException;

public class CleanSweep {
	
	private static CleanSweep instance;
	
	private Tile currentTile;
	private ArrayList<Tile> visited;	// Visited tiles
	private ArrayList<Tile> unvisited;	// Tiles seen but not visited
	
	private CleanSweep() {}
	
	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
			instance.visited = new ArrayList<Tile>();
			instance.unvisited = new ArrayList<Tile>();
		}
		
		return instance;
	}
	
	public void move(char direction) throws DataValidationException {
		switch (direction) {
		case 'n':
			currentTile = currentTile.getAdjacent('n');
			break;
			
		case 's':
			currentTile = currentTile.getAdjacent('s');
			break;
			
		case 'e':
			currentTile = currentTile.getAdjacent('e');
			break;
			
		case 'w':
			currentTile = currentTile.getAdjacent('w');
			break;
			
		default:
			throw new DataValidationException("ERROR: Invalid direction");
		}
		
		// Re-categorizes the current tile from unvisited to visited
		visited.add(currentTile);
		unvisited.remove(currentTile);
		
		// Marks the new tile's adjacent tiles to unvisited
		for (Tile tile : currentTile.getAdjacentTiles()) {
			if (!visited.contains(tile) && !unvisited.contains(tile)) {
				unvisited.add(tile);
			}
		}
	}
	
	public void followPath(char[] path) throws DataValidationException {
		for (char c : path) {
			move(c);
			// TODO: Vacuum when necessary (?)
		}
	}
	
	public Tile getTile() {
		return currentTile;
	}
	
	public void setTile(Tile tile) {
		currentTile = tile;
	}
}
