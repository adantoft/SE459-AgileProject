package vacuum;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import floor.Tile;
import general.DataValidationException;

public class CleanSweep {
	
	private static CleanSweep instance;
	
	private Tile currentTile;
	private ArrayList<Tile> visited;	// Visited tiles
	private ArrayList<Tile> unvisited;	// Tiles seen but not visited
	private Stack<Tile> visitHistory;
	
	private CleanSweep() {}
	
	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
			instance.visited = new ArrayList<>();  // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.unvisited = new ArrayList<>(); // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.visitHistory = new Stack<>();
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
		currentTile.visit();
		visited.add(currentTile); // TODO don't think we need this since visit is tracked in tile [Alex]
		unvisited.remove(currentTile); // TODO don't think we need this since visit is tracked in tile [Alex]

		// Marks the new tile's adjacent tiles to unvisited
		for (Tile tile : currentTile.getAdjacentTiles()) {
			if (!visited.contains(tile) && !unvisited.contains(tile)) {
				unvisited.add(tile);
			}
		}

		//Add to visit history of tiles visited
		visitHistory.add(currentTile);
	}

	/**
	 * Moves CS to the tile it visited before the current tile.
	 */
	public void moveBack() {
		currentTile = visitHistory.pop(); // TODO this should use the move method instead to limit code redundancy
		// TODO need to handle if there are no items on stack
	}

	public void runVacuum() {
		visitHistory.add(currentTile); //once we reach power management, this should not be allowed if there is not enough power to move

		while (!visitHistory.empty()) {
			// TODO call clean code here

			List<Tile> successorTiles = new ArrayList<>();
			for (Tile x : currentTile.getAdjacentTiles()) {
				if (x.getVisited() == 0){
					successorTiles.add(x);
				}
			}
			if (!successorTiles.isEmpty()) {
				try {
					move(currentTile.getDirectionTo(successorTiles.get(0)));
				} catch (DataValidationException e) {
					e.printStackTrace();
				}
			} else {
				moveBack();
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
