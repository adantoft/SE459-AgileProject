package vacuum;

import floor.Tile;
import floor.Tile.Role;
import map.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static floor.Tile.Floor.BARE;
import static org.junit.Assert.*;

public class NavigationTest {
	CleanSweep cs = CleanSweep.getInstance();

	@Before
	public void setUp() throws Exception {
		cs.reset();
		cs = CleanSweep.getInstance();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getInstance() throws Exception {

	}

	// Fails when running NavigationTest
	// Passes when running individual test

	@Test (timeout = 10000)
	public void runVacuumSmallSpaceTest() throws Exception {

		FloorPlan map = new FloorPlan(2, 2);
		Space testRoomBare = new Space(new Point(0, 0), new Point(1,1));
		map.setSpace(0, testRoomBare, BARE);

		for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
			assertEquals(tile.getVisited(), 0);
		}

		cs.setTile(map.getTile(0,0));

		assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);

		// TODO: Fix this.
		Navigation.getInstance().traverseWholeFloor();

		for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
			assertTrue(tile.getVisited() != 0);
		}

	}

    // Fails when running NavigationTest
    // Passes when running individual test

    @Test (timeout = 10000)
    public void runVacuumMediumSpaceTest() throws Exception {

        FloorPlan map = new FloorPlan(25, 25);
        Space testRoomBare = new Space(new Point(0, 0), new Point(24,24));
        map.setSpace(0, testRoomBare, BARE);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(map.getTile(0,0));
        cs.getTile().getCoordinates();
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);

        Navigation.getInstance().traverseWholeFloor();

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertTrue(tile.getVisited()!=0);
        }

    }

	// Fails when running NavigationTest
	// Passes when running individual test

	@Test (timeout = 10000)
	public void runVacuumBigSpaceTest() throws Exception {

		FloorPlan map = new FloorPlan(50, 50);
		Space testRoomBare = new Space(new Point(0, 0), new Point(49,49));
		map.setSpace(0, testRoomBare, BARE);

		for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
			assertEquals(tile.getVisited(), 0);
		}

		cs.setTile(map.getTile(0,0));
        cs.getTile().getCoordinates();
		assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);

		Navigation.getInstance().traverseWholeFloor();

		for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
			assertTrue(tile.getVisited()!=0);
		}

	}

	// Fails when running NavigationTest
	// Passes when running individual test

	@Test (timeout = 30000)
	public void runVacuumExampleFloorTest() throws Exception {
//		FloorPlan map = TestMap.buildExampleFloorPlan();

        UploadFloorPlan floorPlans = new UploadFloorPlan();
        floorPlans.loadFloorPlans();

        FloorPlan map = floorPlans.getFloorPlan("Sample Floor Plan");

		for (Tile tile : map.getActiveTiles()) { // tests that all tiles are not visited
			assertEquals(tile.getVisited(), 0);
		}

		cs.setTile(map.getTile(0,0));
        cs.getTile().setChargingStation();

		Navigation.getInstance().traverseWholeFloor();


		for (Tile tile : map.getActiveTiles()) { // tests that all tiles are not visited
			assertTrue(tile.getVisited()!=0);
		}

	}

	// Fails when running NavigationTest
	// Passes when running individual test
	@Test (timeout = 10000)
	public void shortestDistanceSingleRoomTest() throws Exception {
		System.err.println("\nshortestDistanceSingleRoomTest()");

		int xSize = 10;
		int ySize = xSize;
		int lowerLeft = 0;
		int upperRight = xSize - 1;
		int endTileX = 9;
		int endTileY = 9;

		System.err.println("End tile coordinates: X: " + endTileX + " Y: " +endTileY);

		FloorPlan map = new FloorPlan(xSize, ySize);
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


	// Fails when running NavigationTest
	// Fails when running individual test

	@Test (timeout = 10000)
	public void shortestDistanceExampleFloorPlanTest() throws Exception {
		System.err.println("\nshortestDistanceExampleFloorPlanTest()");
		FloorPlan map = TestMap.buildExampleFloorPlan();

		// Starting coordinates
		int startTileX = 1;
		int startTileY = 6;

		// Ending coordinates
		int endTileX = 3;
		int endTileY = 5;

		// Fewest amount of moves to get from start to end
		int shortestPathDistance = 18;

		Tile startTile = map.getTile(startTileX,startTileY);
		Tile endTile = map.getTile(endTileX,endTileY);

		for (Tile tile : map.getTiles()) { // Tests that all tiles are not visited
			assertEquals(tile.getVisited(), 0);
		}

		cs.setTile(startTile);
		assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
		ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
		cs.followPath(successPath);
		assertTrue(cs.getTile() == endTile);

		int counter = 0;
		for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
			counter += tile.getVisited();
		}
		assertEquals(counter, shortestPathDistance);
	}


    // Fails when running NavigationTest
    // Fails when running individual test

	@Test (timeout = 10000)
	public void cleanSweepReturnLowPowerTest() throws Exception {
		System.err.println("\ncleanSweepReturnLowPowerTest()");

		int xSize = 40;
		int ySize = xSize;
		int lowerLeft = 0;
		int upperRight = xSize - 1;
		int endTileX = ThreadLocalRandom.current().nextInt(39, xSize);
		int endTileY = ThreadLocalRandom.current().nextInt(39, xSize);

		//System.err.println("End Tile is coordinates: X: " + endTileX + " Y: " +endTileY);

		FloorPlan map = new FloorPlan(xSize, ySize);
		Space testRoomBare = new Space(new Point(lowerLeft, lowerLeft), new Point(upperRight,upperRight));
		map.setSpace(0, testRoomBare, BARE);

		Tile startTile = map.getTile(0,0);
		map.getTile(0,0).setChargingStation();
		assertTrue(map.getTile(0,0).isChargingStation());

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
		assertTrue(cs.getChargingStationTile().isChargingStation());

		assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
		ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
		cs.followPath(successPath);

		assertTrue(cs.getTile() == startTile);
		assertTrue(cs.getCharge() == 100);

		// Prints the path
		for (Tile.Direction dir : successPath) {
			System.err.println(dir);
		}

		// Prints path info
		System.err.print("Counts: ");
		for (Tile.Direction dir : Tile.Direction.values()){
			System.err.print(dir + ": " + Collections.frequency(successPath, dir) + " ");
		}
		//
		//        assertTrue(cs.getTile() == startTile);
		//        assertTrue(cs.getCharge() == 100);
	}

    // Fails when running NavigationTest
    // Fails when running individual test

	@Test (timeout = 10000)
	public void cleanSweepReturnFullDirtTest() throws Exception {
		System.err.println("\ncleanSweepReturnFullDirtTest()");

		int xSize = 40;
		int ySize = xSize;
		int lowerLeft = 0;
		int upperRight = xSize - 1;
		int endTileX = (xSize - 1);
		int endTileY = (ySize - 1);

		FloorPlan map = new FloorPlan(xSize, ySize);
		Space testRoomBare = new Space(new Point(lowerLeft, lowerLeft), new Point(upperRight,upperRight));

		map.setFloor(20, testRoomBare, BARE);
		map.setSpace(20, testRoomBare, BARE);

		Tile startTile = map.getTile(0,0);
		Tile endTile = map.getTile(endTileX, endTileY);

		map.getTile(0,0).setChargingStation();
		assertTrue(map.getTile(0,0).isChargingStation());

		// Tests that no tile is visited
		for (Tile tile : map.getTiles()) {
			assertEquals(tile.getVisited(), 0);
		}

		// Tests that all tiles have adjacent tiles
		for (Tile tile : map.getTiles()) {
			assertFalse(tile.getAdjacentTiles().isEmpty());
		}

		// Shortest path forward
		cs.setTile(startTile);
		assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0);
		ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), endTile);
		cs.followPath(successPath);

		assertTrue(cs.getTile() == startTile);

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

		System.out.println(cs.getDirtBag());
		assertNotEquals(cs.getDirtBag(), 0);
	}

	// Fails when running NavigationTest
	// Passes when running individual test

	@Test (timeout = 10000)
	public void getNearestChargingStationTest() throws Exception {
		FloorPlan map = new FloorPlan(8, 8);
		map.setSpace(0, new Space(new Point(0,0), new Point(3, 7)), BARE);
		map.setSpace(0, new Space(new Point(4,0), new Point(7, 7)), BARE);
		map.getTile(0, 0).setRole(Role.BASE);
		Tile chargingStationTile = map.getTile(4, 7);
		chargingStationTile.setChargingStation();

		cs.setTile(map.getTile(0, 0));
		assertEquals(cs.getTile(), cs.getNearestChargingStation());
		assertNotEquals(chargingStationTile, cs.getNearestChargingStation());

		ArrayList<Tile.Direction> successPath = Navigation.calculatePath(cs.getTile(), map.getTile(3, 7));
		cs.followPath(successPath);
		assertEquals(chargingStationTile, cs.getNearestChargingStation());
		
		Point p = cs.getNearestChargingStation().getCoordinates();
		System.out.println("Nearest charging station: " + p);
	}
}