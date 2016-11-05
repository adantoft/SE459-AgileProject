package vacuum;

import floor.Tile;
import floor.Tile.Direction;
import general.DataValidationException;

import java.util.ArrayList;
import java.util.Stack;

import static floor.Tile.Direction.*;


public class CleanSweep {

	private static final double MAX_CHARGE = 100;
	private static final int MAX_DIRT = 50;
	private static CleanSweep instance;
	private Tile currentTile;
	private ArrayList<Tile> visited;	// Visited tiles
	private ArrayList<Tile> unvisited;	// Tiles seen but not visited
	private Stack<Tile> visitHistory;
	private double charge;
	private int dirtBag;
	private double returnChargeLow;
    private Tile chargingStationTile;
    private boolean needsCharge;

	private CleanSweep() {}

	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
			instance.visited = new ArrayList<>();  // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.unvisited = new ArrayList<>(); // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.visitHistory = new Stack<>();
			instance.charge = MAX_CHARGE;
			instance.dirtBag = 0;
			instance.returnChargeLow = 50;
            instance.chargingStationTile = null;
            instance.needsCharge = false;
		}
		return instance;
	}


	/**
	 * Moves the Clean Sweep to a specified adjacent tile, if possible.
	 * Returns true if the tile exists and false if not.
	 * @param direction
	 * @return boolean
	 * @throws DataValidationException
	 */
	public boolean move(Direction direction) throws DataValidationException {

		if (currentTile.getAdjacent(direction) == null) {
			System.err.println("ERROR: Move failed.");
			return false;
		}

		if (charge <= 0) {
			return false;
		}

		// Previous tile
		double previousFloorCost = currentTile.getFloor().getFloorCost();

		switch (direction) {
			case NORTH:
				visitHistory.add(currentTile); //moving away from the current tile so adding it to the visited stack before moving
				currentTile = currentTile.getAdjacent(NORTH);
				break;

			case SOUTH:
				visitHistory.add(currentTile); //moving away from the current tile so adding it to the visited stack before moving
				currentTile = currentTile.getAdjacent(SOUTH);
				break;

			case EAST:
				visitHistory.add(currentTile); //moving away from the current tile so adding it to the visited stack before moving
				currentTile = currentTile.getAdjacent(EAST);
				break;

			case WEST:
				visitHistory.add(currentTile); //moving away from the current tile so adding it to the visited stack before moving
				currentTile = currentTile.getAdjacent(WEST);
				break;

			default:
				throw new DataValidationException("ERROR: Invalid direction");
		}

		System.err.println("Moved to: " + currentTile.getCoordinates());

		// Next tile
		double nextFloorCost = currentTile.getFloor().getFloorCost();


		// Depletes charge
		depleteCharge(previousFloorCost, nextFloorCost);

        checkCharge();

		// Checks for dirt and cleans the Tile
		cleanTile();

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



		return true;
	}

	public void depleteCharge() {
		charge -= currentTile.getFloor().getFloorCode();
	}


	public void depleteCharge(double previous, double next) {
		double chargeUsed = (previous + next) / 2;
		charge -= chargeUsed;
	}

	public void recharge() {
		charge = MAX_CHARGE;
	}

	private boolean checkCharge() {
        if (getCharge() == returnChargeLow && !needsCharge) {
            System.out.print("Charge");
            Navigation.getInstance().clearShortestPath();
            needsCharge = true;
            ArrayList<Tile.Direction> successPath = Navigation.calculatePath(getTile(), chargingStationTile);
            followPath(successPath);
            recharge();
            return true;
        } else if (dirtBag == MAX_DIRT && !needsCharge) {
            System.out.print("Dirt");
            Navigation.getInstance().clearShortestPath();
            needsCharge = true;
            ArrayList<Tile.Direction> successPath = Navigation.calculatePath(getTile(), chargingStationTile);
            followPath(successPath);
            return true;
        }

        return true;
    }

	public void cleanTile() {

		while (currentTile.hasDirt()) {

			// Stops cleaning when the bag is full
			if (dirtBag >= 50) {
				return;
			}
//
//			if (charge < 40) {
//				return;
//			}
//
			currentTile.clean();
			dirtBag ++;
			depleteCharge();
		}
	}

	/**
	 * Moves CS to the tile it visited before the current tile.
	 * @return true if move back success, fail if move back failed (CS has to previous tile)
	 */
	public boolean moveBack() {
		if (!visitHistory.empty()) { //prevents popping and empty stack
			try {
				move(currentTile.getDirectionTo(visitHistory.pop()));
				visitHistory.pop(); //removes original tile (before move) from stack as move puts it on the stack
			} catch (DataValidationException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public void followPath(ArrayList<Direction> path) throws DataValidationException {
		for (Direction d : path) {
			move(d);
			// TODO: Vacuum when necessary (?)
		}
	}

	public void followPathReturnAndGo(ArrayList<Direction> path) throws DataValidationException {
		for (Direction d : path) {
			move(d);
		}
		recharge();
		Navigation.getInstance().clearShortestPath();
	}

	public void followPathReturnAndStop(ArrayList<Direction> path) throws DataValidationException {
		for (Direction d : path) {
			move(d);
		}
		Navigation.getInstance().clearShortestPath();
	}

	public Tile getTile() {
		return currentTile;
	}

	public double getCharge() {
		return charge;
	}

	public int getTileFloorCost() {
        return currentTile.getFloor().getFloorCost();
	}

	public int getDirtBag() {
		return dirtBag;
	}

	public void setTile(Tile tile) {
		currentTile = tile;
		currentTile.visit();
        checkChargingStationTile();
	}

	public void checkChargingStationTile() {
        if (currentTile.isChargingStation()) {
            chargingStationTile = currentTile;
        }
    }

	public Tile getChargingStationTile() {
        return chargingStationTile;
    }

	/**
	 * Adds a tile to Clean Sweep's visit history
	 * @param tile
	 */
	void addToHistory(Tile tile){
		visitHistory.add(tile);
	}

	/**
	 * Returns most recently visited tile without removing it from visit history
	 * @return previous visited tile
	 */
	Tile visitHistoryPeek() {
		return visitHistory.peek();
	}

	/**
	 * Returns most recently visited tile and removes it from visit history
	 * @return previous visited tile
	 */
	Tile visitHistoryPop() {
		return visitHistory.pop();
	}
	boolean isVisitHistoryEmpty(){
		return visitHistory.empty();
	}

	/**
	 * Resets all relevant values of the CleanSweep singleton.
	 */
	public void reset() {
		instance = null;
	}


}