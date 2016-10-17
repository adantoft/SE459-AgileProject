package map;

import floor.Tile;
import org.junit.Test;
import static org.junit.Assert.*;


public class MapTest {


    map.Map testMap = new map.Map(10, 10);

    @Test
    public void fillingInMap() throws Exception {

        assertNotNull(testMap);

    }

    @Test(timeout = 10000)
    public void printMapTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        testMapNew.printMap();

    }


    @Test(timeout = 10000)
    public void setFloorTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapNewCompare = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);
        testMapNew.printMap();
        assertNotNull(testMapNew);
        assertNotEquals(testMapNewCompare, testMapNew.toString());

    }


    @Test(timeout = 10000)
    public void attachTilesTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapNewCompare = new map.Map(2, 2);
        map.Map testMapNewCompare2 = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(0, 1));
        Space testRoomLowCarpNew = new Space(new Point(0, 0), new Point(0, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);
        testMapNewCompare.setFloor(testRoomLowCarpNew, Tile.Floor.LOW);
        testMapNewCompare2.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);

        testMapNew.attachTiles(testRoom1HighCarpNew);
        assertNotEquals(testMapNew.getTiles().toString(), testMapNewCompare.getTiles().toString());

        testMapNewCompare.attachTiles(testRoomLowCarpNew);
        assertNotEquals(testMapNew.getTiles().toString(), testMapNewCompare.getTiles().toString());

        testMapNewCompare2.attachTiles(testRoom1HighCarpNew);
        assertEquals(testMapNew.getTiles().toString(), testMapNewCompare2.getTiles().toString());

    }


    @Test
    public void setSpaceTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapNewCompare = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        Space testRoom1HighCarpNew1 = new Space(new Point(0, 0), new Point(1, 1));

        testMapNew.setSpace(testRoom1HighCarpNew, Tile.Floor.BARE);
        testMapNewCompare.setSpace(testRoom1HighCarpNew, Tile.Floor.LOW);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(testRoom1HighCarpNew, Tile.Floor.HIGH);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(testRoom1HighCarpNew, Tile.Floor.BARE);
        assertEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

    }

    @Test(timeout = 10000)
    public void getTilesTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapCompare = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);

        System.out.println(testMapNew.getTiles());


        assertNotEquals(testMapNew.getTiles().toString(), testMapCompare.getTiles().toString());

        testMapCompare.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);
        assertEquals(testMapNew.getTiles().toString(), testMapCompare.getTiles().toString());

    }

    @Test
    public void getTileTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);


        assertNotNull(testMapNew.getTile(0,0));
        assertEquals("Dirt: 0\n" + "Floor type: High pile", testMapNew.getTile(0,0).toString());
        assertNotEquals("Dirt: 1\n" + "Floor type: High pile", testMapNew.getTile(0,0).toString());

    }
}
