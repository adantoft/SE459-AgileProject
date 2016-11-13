package map;

import floor.Tile;
import org.junit.Test;

import static floor.Tile.Floor.BARE;
import static org.junit.Assert.*;


public class FloorPlanTest {


    FloorPlan testMap = new FloorPlan(10, 10);

    @Test
    public void fillingInMap() throws Exception {

        assertNotNull(testMap);

    }

    @Test(timeout = 10000)
    public void printMapTest() throws Exception {

        FloorPlan testMapNew = new FloorPlan(2, 2);
        testMapNew.printMap();

    }


    @Test(timeout = 10000)
    public void setFloorTest() throws Exception {

        FloorPlan testMapNew = new FloorPlan(2, 2);
        FloorPlan testMapNewCompare = new FloorPlan(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);
        testMapNew.printMap();
        assertNotNull(testMapNew);
        assertNotEquals(testMapNewCompare, testMapNew.toString());

    }


    @Test(timeout = 10000)
    public void attachTilesTest() throws Exception {

        FloorPlan testMapNew = new FloorPlan(2, 2);
        FloorPlan testMapNewCompare = new FloorPlan(2, 2);
        FloorPlan testMapNewCompare2 = new FloorPlan(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(0, 1));
        Space testRoomLowCarpNew = new Space(new Point(0, 0), new Point(0, 1));
        testMapNew.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);
        testMapNewCompare.setFloor(0, testRoomLowCarpNew, Tile.Floor.LOW);
        testMapNewCompare2.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);

        testMapNew.attachTiles(testRoom1HighCarpNew);
        assertNotEquals(testMapNew.getTiles().toString(), testMapNewCompare.getTiles().toString());

        testMapNewCompare.attachTiles(testRoomLowCarpNew);
        assertNotEquals(testMapNew.getTiles().toString(), testMapNewCompare.getTiles().toString());

        testMapNewCompare2.attachTiles(testRoom1HighCarpNew);
        assertEquals(testMapNew.getTiles().toString(), testMapNewCompare2.getTiles().toString());

    }


    @Test
    public void setSpaceTest() throws Exception {

        FloorPlan testMapNew = new FloorPlan(2, 2);
        FloorPlan testMapNewCompare = new FloorPlan(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        Space testRoom1HighCarpNew1 = new Space(new Point(0, 0), new Point(1, 1));

        testMapNew.setSpace(0, testRoom1HighCarpNew, Tile.Floor.BARE);
        testMapNewCompare.setSpace(0, testRoom1HighCarpNew, Tile.Floor.LOW);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(0, testRoom1HighCarpNew, Tile.Floor.HIGH);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(0, testRoom1HighCarpNew, Tile.Floor.BARE);
        assertEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

    }

    @Test(timeout = 10000)
    public void getTilesTest() throws Exception {

        FloorPlan testMapNew = new FloorPlan(2, 2);
        FloorPlan testMapCompare = new FloorPlan(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);

        System.out.println(testMapNew.getTiles());


        assertNotEquals(testMapNew.getTiles().toString(), testMapCompare.getTiles().toString());

        testMapCompare.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);
        assertEquals(testMapNew.getTiles().toString(), testMapCompare.getTiles().toString());

    }

    @Test
    public void getTileTest() throws Exception {

        FloorPlan testMapNewTest = new FloorPlan(2,2);

        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));

        testMapNewTest.setFloor(0, testRoom1HighCarpNew, Tile.Floor.HIGH);



        assertNotNull(testMapNewTest.getTile(0,0));


        //This assertEquals is failing as Dirt: 4 not 0 , not sure why this
        //is happening now.
        assertEquals("Dirt: 0\n" + "Floor type: High pile", testMapNewTest.getTile(0,0).toString());

        assertNotEquals("Dirt: 1\n" + "Floor type: High pile", testMapNewTest.getTile(0,0).toString());

    }
    @Test
    public void tileLinkingTest() throws Exception {

        //create 2x2 grid
        FloorPlan map = new FloorPlan(2, 2);
        Space testRoomBare = new Space(new Point(0, 0), new Point(1,1));
        map.setSpace(0, testRoomBare, BARE);

        // since this is 2x2, all tile should have 2 adjacent tiles
        for (Tile tile : map.getTiles()) {
            assertTrue(tile.getAdjacentTiles().size() == 2);
        }

    }

}
