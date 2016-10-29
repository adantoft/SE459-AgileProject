package map;

import vacuum.CleanSweep;
import map.Point;
import map.Map;
import map.Space;
import floor.Tile.Floor;
import floor.Tile.Direction;

import general.DataValidationException;

public class TestMap {

    private static TestMap instance;

    private TestMap() {}

    public static TestMap getInstance() {
        if (instance == null) {
            instance = new TestMap();
        }
        return instance;
    }

    public static Map buildExampleMap(){
        Map map = new Map(10, 10);

        Space guestBedroomA = new Space(new Point(0, 0), new Point(3, 4));
        map.setSpace(guestBedroomA, Floor.LOW);

        Space guestBedroomACloset = new Space(new Point(0, 5), new Point(3, 5));
        map.setSpace(guestBedroomACloset, Floor.BARE);

        Space guestBedroomB = new Space(new Point(0, 7), new Point(3, 9));
        map.setSpace(guestBedroomB, Floor.LOW);

        Space guestBedroomBCloset = new Space(new Point(0, 6), new Point(1, 6));
        map.setSpace(guestBedroomBCloset, Floor.BARE);

        Space hallway = new Space(new Point(4, 0), new Point(5, 7));
        map.setSpace(hallway, Floor.BARE);

        Space hallwayCloset = new Space(new Point(2, 6), new Point(3, 6));
        map.setSpace(hallwayCloset, Floor.BARE);

        Space hallwayBathroom = new Space(new Point(4, 8), new Point(5, 8));
        map.setSpace(hallwayBathroom, Floor.BARE);

        Space masterBedroomA = new Space(new Point(6, 0), new Point(9, 6));
        map.setSpace(masterBedroomA, Floor.BARE);
        Space masterBedroomB = new Space(new Point(6, 7), new Point(7, 8));
        map.setSpace(masterBedroomB, Floor.BARE);
        Space combineMasterAB = new Space(new Point(6, 6), new Point(7, 7));
        map.attachTiles(combineMasterAB);
        Space masterHighPile = new Space(new Point(7, 1), new Point(8, 5));
        map.setFloor(masterHighPile, Floor.HIGH);

        Space masterBedroomBathroom = new Space(new Point(4, 9), new Point(7, 9));
        map.setSpace(masterBedroomBathroom, Floor.BARE);

        Space masterBedroomCloset = new Space(new Point(8, 7), new Point(9, 9));
        map.setSpace(masterBedroomCloset, Floor.BARE);

        // Adding Doorways
        // map.getTile(1, 4).attachTile(map.getTile(1, 5), NORTH)

        map.setDoorway(new Point(1, 4), new Point(1, 5));

        map.setDoorway(new Point(0, 6), new Point(0, 7));

        map.setDoorway(new Point(3, 3), new Point(4, 3));

        map.setDoorway(new Point(3, 6), new Point(4, 6));

        map.setDoorway(new Point(3, 7), new Point(4, 7));

        map.setDoorway(new Point(4, 7), new Point(4, 8));

        map.setDoorway(new Point(6, 7), new Point(5, 7));

        map.setDoorway(new Point(6, 7), new Point(5, 7));

        map.setDoorway(new Point(6, 8), new Point(6, 9));

        return map;
    }

	public static void main(String[] args) {
		CleanSweep cs = CleanSweep.getInstance();
        Map map = getInstance().buildExampleMap();
        map.printMap();

        System.out.println(map.getTile(1, 1).getCoordinates());


	}
}
