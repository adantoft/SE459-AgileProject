package floor;

import general.DataValidationException;

import java.util.ArrayList;
import java.util.List;

public class Tile {
	
	private Tile north, south, east, west;
	private Tile[] tiles = {north, south, east, west};
	private int visited;
	
	public enum Floor {
		BARE(1),
		LOW(2),
		HIGH(3);
		
		private final int floorCode;
		
		Floor(int floorCodeIn) {
			floorCode = floorCodeIn;
		}
		
		public int getFloorCode() {
			return floorCode;
		}
		
		public String getFloorCodeAsString() {
			switch (floorCode) {
			case 1:
				return "Bare floor";
				
			case 2:
				return "Low pile";
				
			case 3:
				return "High pile";
				
			default:
				return null;	// TODO: Throw exception?
			}
		}
	}
	
	private int dirt;
	Floor floor;
	
	public Tile(int dirtIn, Floor floorIn) {
		dirt = dirtIn;
		floor = floorIn;
		visited = 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dirt: " + dirt);
		sb.append("\nFloor type: " + floor.getFloorCodeAsString());
		
		return sb.toString();
	}
	
	public void attachTile(Tile tile, char direction) throws DataValidationException {
		switch (direction) {
		case 'n':
			this.north = tile;
			tile.south = this;
			break;
			
		case 's':
			this.south = tile;
			tile.north = this;
			break;
			
		case 'e':
			this.east = tile;
			tile.west = this;
			break;
			
		case 'w':
			this.west = tile;
			tile.west = this;
			break;
			
		default:
			throw new DataValidationException("ERROR: Invalid direction");
		}
	}
	
	public int getVisited() {
		return visited;
	}
	
	public int getDirt() {
		return dirt;
	}
	
	public Tile getAdjacent(char direction) throws DataValidationException {
		switch (direction) {
		case 'n':
			return north;
		
		case 's':
			return south;
			
		case 'e':
			return east;
			
		case 'w':
			return west;
			
		default:
			throw new DataValidationException("ERROR: Invalid direction");
		}
	}

	/**
	 * Gets list of adjacent tiles that exist.
	 *
	 * @return List of tiles.
	 */
	public List<Tile> getAdjacentTiles() {
		List<Tile> adjacents = new ArrayList<>();

		for (Tile x : tiles) {
			if (x != null) {
				adjacents.add(x);
			}
		}

		return adjacents;
	}
	
	public void visit() {
		visited ++;
	}

	/**
	 * Provides char direction to get to an adjacent tile.
	 * @param that Tile in which the direction to should be retrieved.
	 * @return Char of direction.
	 * @throws DataValidationException
	 */
	public char getDirectionTo(Tile that) throws DataValidationException  {
		if (this.getAdjacent('n') == that) {
			return 'n';
		} else if (this.getAdjacent('e') == that) {
			return 'e';
		} else if (this.getAdjacent('s') == that) {
			return 's';
		} else if (this.getAdjacent('w') == that) {
			return 'w';
		} else {
			throw new DataValidationException("ERROR: Direction unknown");
		}
	}

}
