package vacuum;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import floor.Tile;
import floor.Tile.Direction;
import general.DataValidationException;

import static floor.Tile.Direction.*;

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

	public void move(Direction direction) throws DataValidationException {
		switch (direction) {
			case NORTH:
				currentTile = currentTile.getAdjacent(NORTH);
				break;

			case SOUTH:
				currentTile = currentTile.getAdjacent(SOUTH);
				break;

			case EAST:
				currentTile = currentTile.getAdjacent(EAST);
				break;

			case WEST:
				currentTile = currentTile.getAdjacent(WEST);
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
			for (Tile tile : currentTile.getAdjacentTiles()) {
				if (tile.getVisited() == 0){
					successorTiles.add(tile);
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

	public void followPath(Direction[] path) throws DataValidationException {
		for (Direction d : path) {
			move(d);
			// TODO: Vacuum when necessary (?)
		}
	}

	public Tile getTile() {
		return currentTile;
	}

	public void setTile(Tile tile) {
		currentTile = tile;
		currentTile.visit();
	}
	
	/**
	 * Resets all relevant values of the CleanSweep singleton.
	 */
	public void reset() {
		currentTile = null;
		visited.clear();
		unvisited.clear();
		visitHistory.clear();
		
	}
}
