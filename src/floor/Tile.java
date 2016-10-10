package floor;

import general.DataValidationException;

public class Tile {
	
	private Tile north, south, east, west;
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
	
	public Tile[] getAdjacentTiles() {
		Tile[] adjacents = { north, south, east, west };
		return adjacents;
	}
	
	public void visit() {
		visited ++;
	}
	
}
