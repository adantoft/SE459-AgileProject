package floor;

public class Tile {
	
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
	}
	
	private int dirt;
	Floor floor;
	
	public Tile(int dirtIn, Floor floorIn) {
		dirt = dirtIn;
		floor = floorIn;
	}
	
	public int getDirt() {
		return dirt;
	}
	
}
