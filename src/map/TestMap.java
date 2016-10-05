package map;

import vacuum.CleanSweep;
import floor.Tile;
import floor.Tile.Floor;
import general.DataValidationException;

public class TestMap {
	
	public static void main(String[] args) {
		CleanSweep cs = CleanSweep.getInstance();
		
		Tile t1 = new Tile(2, Floor.BARE);
		Tile t2 = new Tile(1, Floor.LOW);
		Tile t3 = new Tile(3, Floor.HIGH);
		
		try {
			t1.attachTile(t2, 'n');
			t2.attachTile(t3, 'e');
			cs.setTile(t1);
			System.out.println(cs.getTile().toString());
			cs.move('n');
			System.out.println(cs.getTile().toString());
			cs.move('e');
			System.out.println(cs.getTile().toString());
			
			
		} catch (DataValidationException e) {
			e.printStackTrace();
		}
	}
}
