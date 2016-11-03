package vacuum;

import floor.Tile;
import map.Map;
import map.Point;
import map.Space;
import map.TestMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static floor.Tile.Direction.*;
import static floor.Tile.Floor.*;
import static org.junit.Assert.*;

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
        map.setSpace(0, testRoomBare, BARE);

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

    @Test (timeout = 150000)
    public void runVacuumBigSpaceTest() throws Exception {

        Map map = new Map(50, 50);
        Space testRoomBare = new Space(new Point(0, 0), new Point(49,49));
        map.setSpace(0, testRoomBare, BARE);

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
        map.setSpace(0, hallway, BARE);

        Space guestBedroomA = new Space(new Point(0, 0), new Point(3, 4));
        map.setSpace(0, guestBedroomA, LOW);
        map.getTile(3,3).attachTile(map.getTile(4,3), EAST);

        Space guestBedroomACloset = new Space(new Point(0, 5), new Point(3, 5));
        map.setSpace(0, guestBedroomACloset, BARE);
        map.getTile(1,4).attachTile(map.getTile(1,5), NORTH);

        Space guestBedroomB = new Space(new Point(0, 7), new Point(3, 9));
        map.setSpace(0, guestBedroomB, LOW);
        map.getTile(3,7).attachTile(map.getTile(4,7), EAST);

        Space guestBedroomBCloset = new Space(new Point(0, 6), new Point(1, 6));
        map.setSpace(0, guestBedroomBCloset, BARE);
        map.getTile(0,7).attachTile(map.getTile(0,6), SOUTH);

        Space hallwayCloset = new Space(new Point(2, 6), new Point(3, 6));
        map.setSpace(0, hallwayCloset, BARE);
        map.getTile(3,6).attachTile(map.getTile(4,6), EAST);


        Space hallwayBathroom = new Space(new Point(4, 8), new Point(5, 8));
        map.setSpace(0, hallwayBathroom, BARE);
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
    public void shortestDistanceSingleRoomTest() throws Exception {
    	System.err.println("\nshortestDistanceSingleRoomTest()");

        int xSize = 50;
        int ySize = xSize;
        int lowerLeft = 0;
        int upperRight = xSize - 1;
        int endTileX = 49;
        int endTileY = 49;

        System.err.println("End Tile is coordinates: X: " + endTileX + " Y: " +endTileY);

        Map map = new Map(xSize, ySize);
        Space testRoomBare = new Space(new Point(lowerLeft, lowerLeft), new Point(upperRight,upperRight));
        map.setSpace(0, testRoomBare, BARE);

        Tile startTile = map.getTile(0,0);
        Tile endTile = map.getTile(endTileX,endTileY);

        // Tests that no tile is visited
        for (Tile tile : map.getTiles()) {
            assertEquals(tile.getVisited(), 0);
        }
        
        // Tests that all tiles have adjacent tiles
        for (Tile tile : map.getTiles()) {
        	assertFalse(tile.getAdjacentTiles().isEmpty());
        }


        cs.setTile(startTile);
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
        cs.followPath(successPath);


        // Prints the path
        for (Tile.Direction dir : successPath) {
        	System.err.println(dir);
        }
        
        // Prints path info
        System.err.print("Counts: ");
        for (Tile.Direction dir : Tile.Direction.values()){
            System.err.print(dir + ": " + Collections.frequency(successPath, dir) + " ");
        }
        System.err.println();

        assertEquals(cs.getTile(), endTile);

    }


    @Test
    public void shortestDistanceExampleMapTest() throws Exception {
    	System.err.println("\nshortestDistanceExampleMapTest()");
        Map map = TestMap.buildExampleMap();

        int startTileX = 0;
        int startTileY = 6;
        int endTileX = 9;
        int endTileY = 0;

        Tile startTile = map.getTile(startTileX,startTileY);
        Tile endTile = map.getTile(endTileX,endTileY);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(startTile);
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
        cs.followPath(successPath);
        System.err.print(successPath);
        assertTrue(cs.getTile()==endTile);

    }

    @Test
    public void cleanSweepReturnLowPowerTest() throws Exception {
        System.err.println("\ncleanSweepReturnLowPowerTest()");


        int xSize = 40;
        int ySize = xSize;
        int lowerLeft = 0;
        int upperRight = xSize - 1;
        int endTileX = ThreadLocalRandom.current().nextInt(39, xSize);
        int endTileY = ThreadLocalRandom.current().nextInt(39, xSize);

        //System.err.println("End Tile is coordinates: X: " + endTileX + " Y: " +endTileY);

        Map map = new Map(xSize, ySize);
        Space testRoomBare = new Space(new Point(lowerLeft, lowerLeft), new Point(upperRight,upperRight));
        map.setSpace(0, testRoomBare, BARE);

        Tile startTile = map.getTile(0,0);
        Tile endTile = map.getTile(endTileX,endTileY);

        // Tests that no tile is visited
        for (Tile tile : map.getTiles()) {
            assertEquals(tile.getVisited(), 0);
        }

        // Tests that all tiles have adjacent tiles
        for (Tile tile : map.getTiles()) {
            assertFalse(tile.getAdjacentTiles().isEmpty());
        }
        //shortest path forward
        cs.setTile(startTile);
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
        cs.followPath(successPath);

        assertFalse(cs.getTile() == startTile);
        assertFalse(cs.getCharge() == 100);

        //return to chargiassertTrueng station
        cs.setTile(cs.getTile());
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath1 = Navigation.calculatePath(cs.getTile(), startTile);
        cs.followPathReturnAndGo(successPath1);

        // Prints the path
        for (Tile.Direction dir : successPath) {
            System.err.println(dir);
        }

        // Prints path info
        System.err.print("Counts: ");
        for (Tile.Direction dir : Tile.Direction.values()){
            System.err.print(dir + ": " + Collections.frequency(successPath, dir) + " ");
        }
        System.err.println();

        assertTrue(cs.getTile() == startTile);
        assertTrue(cs.getCharge() == 100);
    }

    @Test
    public void cleanSweepReturnFullDirtTest() throws Exception {
        System.err.println("\ncleanSweepReturnLowPowerTest()");


        int xSize = 40;
        int ySize = xSize;
        int lowerLeft = 0;
        int upperRight = xSize - 1;
        int endTileX = ThreadLocalRandom.current().nextInt(39, xSize);
        int endTileY = ThreadLocalRandom.current().nextInt(39, xSize);

        //System.err.println("End Tile is coordinates: X: " + endTileX + " Y: " +endTileY);

        Map map = new Map(xSize, ySize);
        Space testRoomBare = new Space(new Point(lowerLeft, lowerLeft), new Point(upperRight,upperRight));

        //map.Map testMapNewTest = new map.Map(2,2);

        //Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));

        map.setFloor(5, testRoomBare, HIGH);
        map.setSpace(5, testRoomBare, BARE);


        Tile startTile = map.getTile(0,0);
        Tile endTile = map.getTile(endTileX,endTileY);

        // Tests that no tile is visited
        for (Tile tile : map.getTiles()) {
            assertEquals(tile.getVisited(), 0);
        }

        // Tests that all tiles have adjacent tiles
        for (Tile tile : map.getTiles()) {
            assertFalse(tile.getAdjacentTiles().isEmpty());
        }
        //shortest path forward
        cs.setTile(startTile);
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
        cs.followPath(successPath);

        assertFalse(cs.getTile() == startTile);
        //System.out.println(cs.getDirtBag());


        cs.setTile(cs.getTile());
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
        ArrayList<Tile.Direction> successPath1 = Navigation.calculatePath(cs.getTile(), startTile);
        cs.followPathReturnAndStop(successPath1);

        // Prints the path
        for (Tile.Direction dir : successPath) {
            System.err.println(dir);
        }

        // Prints path info
        System.err.print("Counts: ");
        for (Tile.Direction dir : Tile.Direction.values()){
            System.err.print(dir + ": " + Collections.frequency(successPath, dir) + " ");
        }
        System.err.println();

        assertTrue(cs.getTile() == startTile);
        System.out.println(cs.getDirtBag());
//        assertFalse(cs.getDirtBag() == 50);


    }


}