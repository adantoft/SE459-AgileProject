package vacuum;

import floor.Tile;
import map.Map;
import map.Point;
import map.Space;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static floor.Tile.Direction.*;
import static floor.Tile.Floor.*;
import static org.junit.Assert.*;

/**
 * Created by Alex on 10/16/2016.
 */
public class NavigationTest {
    CleanSweep cs = CleanSweep.getInstance();

    @Before
    public void setUp() throws Exception {
        cs.reset();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void runVacuumSmallSpaceTest() throws Exception {

        Map map = new Map(2, 2);
        Space testRoomBare = new Space(new Point(0, 0), new Point(1,1));
        map.setSpace(testRoomBare, BARE);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(map.getTile(0,0));
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);

        Navigation.getInstance().traverseWholeFloor();

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertTrue(tile.getVisited()!=0);
        }

    }

    @Test
    public void runVacuumBigSpaceTest() throws Exception {

        Map map = new Map(50, 50);
        Space testRoomBare = new Space(new Point(0, 0), new Point(49,49));
        map.setSpace(testRoomBare, BARE);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(map.getTile(0,0));
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);

        Navigation.getInstance().traverseWholeFloor();

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertTrue(tile.getVisited()!=0);
        }

    }

    @Test
    public void runVacuumExampleFloorTest() throws Exception {

        Map map = new Map(10, 10);

        Space hallway = new Space(new Point(4, 0), new Point(5, 7));
        map.setSpace(hallway, BARE);

        Space guestBedroomA = new Space(new Point(0, 0), new Point(3, 4));
        map.setSpace(guestBedroomA, LOW);
        map.getTile(3,3).attachTile(map.getTile(4,3), EAST);

        Space guestBedroomACloset = new Space(new Point(0, 5), new Point(3, 5));
        map.setSpace(guestBedroomACloset, BARE);
        map.getTile(1,4).attachTile(map.getTile(1,5), NORTH);

        Space guestBedroomB = new Space(new Point(0, 7), new Point(3, 9));
        map.setSpace(guestBedroomB, LOW);
        map.getTile(3,7).attachTile(map.getTile(4,7), EAST);

        Space guestBedroomBCloset = new Space(new Point(0, 6), new Point(1, 6));
        map.setSpace(guestBedroomBCloset, BARE);
        map.getTile(0,7).attachTile(map.getTile(0,6), SOUTH);

        Space hallwayCloset = new Space(new Point(2, 6), new Point(3, 6));
        map.setSpace(hallwayCloset, BARE);
        map.getTile(3,6).attachTile(map.getTile(4,6), EAST);


        Space hallwayBathroom = new Space(new Point(4, 8), new Point(5, 8));
        map.setSpace(hallwayBathroom, BARE);
        map.getTile(4,7).attachTile(map.getTile(4,8), NORTH);

        //Below is for the Master bedroom - currently not tested as it's not linked together
//        Space masterBedroomA = new Space(new Point(6, 0), new Point(9, 6));
//        map.setSpace(masterBedroomA, BARE);
//        Space masterBedroomB = new Space(new Point(6, 7), new Point(7, 8));
//        map.setSpace(masterBedroomB, BARE);
//        Space combineMasterAB = new Space(new Point(6, 6), new Point(7, 7));
//        map.attachTiles(combineMasterAB);
//        Space masterHighPile = new Space(new Point(7, 1), new Point(8, 5));
//        map.setFloor(masterHighPile, HIGH);
//
//        Space masterBedroomBathroom = new Space(new Point(4, 9), new Point(7, 9));
//        map.setSpace(masterBedroomBathroom, BARE);
//
//        Space masterBedroomCloset = new Space(new Point(8, 7), new Point(9, 9));
//        map.setSpace(masterBedroomCloset, BARE);

        for (Tile tile : map.getActiveTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(map.getTile(0,0));
        Navigation.getInstance().traverseWholeFloor();

        for (Tile tile : map.getActiveTiles()) { // tests that all tiles are not visited
            assertTrue(tile.getVisited()!=0);
        }

    }

    @Test
    public void calculatePath() throws Exception {

        Map map = new Map(3, 3);
        Space testRoomBare = new Space(new Point(0, 0), new Point(2,2));
        map.setSpace(testRoomBare, BARE);

        Tile startTile = map.getTile(0,0);
        Tile endTile = map.getTile(2,0);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(startTile);
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        cs.followPath(Navigation.calculatePath(cs.getTile(), endTile));

        assertTrue(cs.getTile()==endTile);

    }
}