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
    private Stack<Tile> visitHistoryToStation;	// TODO: What is this?
	private double charge;
	private int dirtBag;
	private double returnChargeLow;
    private Tile chargingStationTile;
    private Tile baseTile;
    private boolean isReturningToStation;
	private boolean isReturningToLastTile;
	private Tile lastTile;
    public boolean emptyMeIndicator;

	private CleanSweep() {}

	public static CleanSweep getInstance() {
		if (instance == null) {
			instance = new CleanSweep();
			instance.currentTile = null;
			instance.visited = new ArrayList<>();  // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.unvisited = new ArrayList<>(); // TODO don't think we need this since visit is tracked in tile [Alex]
			instance.visitHistory = new Stack<Tile>();
            instance.visitHistoryToStation = new Stack<Tile>();
			instance.charge = MAX_CHARGE;
			instance.dirtBag = 0;
			instance.returnChargeLow = 50;
			instance.chargingStationTile = null;
			instance.baseTile = null;
            instance.isReturningToStation = false;
			instance.isReturningToLastTile = false;
			instance.lastTile = null;
            instance.emptyMeIndicator = false;
		}
		return instance;
	}

	/**
	 * Puts Clean Sweep into Charge debug mode setting charge capacity to 1 million
	 */
	public void enableChargeDebugMode(){
		instance.charge = 100000;
	}

	public void enableDirtDebugMode(){
		instance.dirtBag = -100000;
	}


	/**
	 * Moves the Clean Sweep to a specified adjacent tile, if possible.
	 * Returns true if the tile exists and false if not.
	 * @param direction
	 * @return boolean
	 * @throws DataValidationException
	 */
	public boolean move(Direction direction) throws DataValidationException {
		
        if (direction == null) {
            return false;
        }

		if (currentTile.getAdjacent(direction) == null) {
			System.err.println("ERROR: Move failed.");
			return false;
		}

		if (charge <= 0) {
			return false;
		}

		if (emptyMeIndicator) {
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

		System.out.println("Moved " + direction.toString().toLowerCase() + " to: " + currentTile.getCoordinates());

		// Next tile
		double nextFloorCost = currentTile.getFloor().getFloorCost();


		// Depletes charge
		depleteCharge(previousFloorCost, nextFloorCost);

		// Checks to see if CS needs to return to station
		chargingStationTile = getNearestChargingStation();
        if (getCharge() <= returnChargeLow && !isReturningToStation) {
        	returnToStation();
            returnToLastTile();
        }

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
        System.out.println("Clean Sweep recharged! \t Current charge: " + charge);
        isReturningToStation = false;
	}
	
	private void returnToStation() throws DataValidationException {
		if (!isReturningToStation) {
            visitHistoryToStation = visitHistory;
            lastTile = currentTile;
			System.out.println("Returning to charging station...");
			Navigation.clearShortestPath();
			isReturningToStation = true;
			ArrayList<Tile.Direction> successPath = Navigation.calculatePath(getTile(), chargingStationTile);
			if (successPath == null) {
                System.out.println("PATH TO CHARGING STATION NOT FOUND");
				successPath = Navigation.calculatePath(currentTile, baseTile);
			}
			followPath(successPath);
			recharge();
		}
	}

	private void returnToLastTile() throws DataValidationException {
		if (!isReturningToLastTile) {
			System.out.println("Returning to last tile...");
			Navigation.clearShortestPath();
			isReturningToLastTile = true;
			ArrayList<Tile.Direction> successPath = Navigation.calculatePath(getTile(), lastTile);
			followPath(successPath);
			isReturningToLastTile = false;
		}
	}

	public void cleanTile() throws DataValidationException {
		if (isReturningToStation){
            return;
		}

        while (currentTile.hasDirt()) {

			// Stops cleaning when the bag is full
			if (dirtBag == MAX_DIRT) {
				returnToStation();
                setEmptyMeIndicator(true);
				return;
			}
			
			if (getCharge() <= returnChargeLow) {
				returnToStation();
                returnToLastTile();
				break;
			}

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
        if (path != null) {
            for (Direction d : path) {
                move(d);
            }
        }
	}
	
	public void clean() {
		Navigation.traverseWholeFloor();
	}

	public Tile getlastTile() {
		return lastTile;
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
		baseTile = currentTile;
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
	
	public Tile getNearestChargingStation() {
		ArrayList<Tile> nextTiles = currentTile.getNextTiles();
		ArrayList<Tile> nearbyTiles = new ArrayList<Tile>();
		
		for (Tile tile : nextTiles) {
			nearbyTiles.addAll(tile.getNextTiles());
		}
		
		for (Tile tile : nearbyTiles) {
			if (tile.isChargingStation()) {
				return tile;
			}
		}
		
		return chargingStationTile;
	}

	public void setEmptyMeIndicator(boolean state) {
        emptyMeIndicator = state;
        if (emptyMeIndicator) {
            System.out.println("---- EMPTY ME ----");
            System.out.println("Clean Sweep powering off...");
            // System.exit(0);
        }

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