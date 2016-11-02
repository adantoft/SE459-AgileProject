package vacuum;

import floor.Tile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static floor.Tile.Direction.*;
import static floor.Tile.Floor.*;
import static org.junit.Assert.*;

public class CleanSweepTest {
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

    @Test
    public void moveTest() throws Exception {
        Tile upLeft = new Tile(0, BARE);
        Tile upRight = new Tile(0, BARE);
        Tile downLeft = new Tile(0, BARE);
        Tile downRight = new Tile(0, BARE);

        downLeft.attachTile(upLeft, NORTH);
        downRight.attachTile(upRight, NORTH);
        downLeft.attachTile(downRight, EAST);
        upLeft.attachTile(upRight, EAST);

        assertNotEquals(downLeft, downRight);
        assertNotEquals(downLeft, upLeft);
        assertNotEquals(downLeft, upRight);

        cs.setTile(downLeft);
        cs.move(NORTH);
        assertEquals(upLeft, cs.getTile());
        cs.move(EAST);
        assertEquals(upRight, cs.getTile());
        cs.move(SOUTH);
        assertEquals(downRight, cs.getTile());
        cs.move(WEST);
        assertEquals(downLeft, cs.getTile());

    }
    
    @Test
    public void depleteChargeTest() throws Exception {
    	Tile tileBare = new Tile(BARE);
    	Tile tileLow = new Tile(LOW);
    	Tile tileHigh = new Tile(HIGH);
    	Tile tileBare2 = new Tile(BARE);
    	Tile tileLow2 = new Tile(LOW);
    	Tile tileHigh2 = new Tile(HIGH);
    	
    	tileBare.attachTile(tileLow, EAST);
    	tileLow.attachTile(tileHigh, EAST);
    	
    	tileBare.attachTile(tileBare2, NORTH);
    	tileLow.attachTile(tileLow2, NORTH);
    	tileHigh.attachTile(tileHigh2, NORTH);
    	
    	tileHigh2.attachTile(tileBare2, NORTH);
    	
    	cs.setTile(tileBare);
    	assertTrue(cs.getCharge() == 100);
    	cs.move(EAST);
    	assertTrue(cs.getCharge() == 98.5);
    	cs.move(EAST);
    	assertTrue(cs.getCharge() == 96);
    	
    	cs.setTile(tileBare);
    	cs.move(NORTH);
    	assertTrue(cs.getCharge() == 95);
    	
    	cs.setTile(tileLow);
    	cs.move(NORTH);
    	assertTrue(cs.getCharge() == 93);
    	
    	cs.setTile(tileHigh);
    	cs.move(NORTH);
    	assertTrue(cs.getCharge() == 90);
    	
    	cs.move(NORTH);
    	assertTrue(cs.getCharge() == 88);
    }
    
    @Test
    public void rechargeTest() throws Exception {
    	cs.recharge();
    	assertTrue(cs.getCharge() == 100);
    	Tile tile1 = new Tile(BARE);
    	Tile tile2 = new Tile(BARE);
    	tile1.attachTile(tile2, NORTH);
    	
    	cs.setTile(tile1);
    	cs.move(NORTH);
    	assertTrue(cs.getCharge() < 100);
    }
    @Test
    public void cleanTileTest() throws Exception {
        Tile tile1 = new Tile(1, BARE);
        cs.setTile(tile1);
        assertTrue(tile1.hasDirt() == true);
        tile1.clean();
        assertFalse(tile1.hasDirt() == true);


    }
/*
    @Test
    public void cleanTest() throws Exception {
    	Tile tile1 = new Tile(0, BARE);
    	Tile tile2 = new Tile(32, BARE);
    	Tile tile3 = new Tile(32, BARE);
    	tile1.attachTile(tile2, NORTH);
    	tile2.attachTile(tile3, EAST);
    	cs.setTile(tile1);
    	
    	cs.move(NORTH);
    	assertFalse(cs.getTile().hasDirt());
    	assertTrue(cs.getDirtBag() == 32);
    	
    	cs.move(EAST);
    	assertTrue(cs.getTile().hasDirt());
    	assertTrue(cs.getDirtBag() == 50);
    }
    */

    @Test
    public void obstacleTest() throws Exception {
    	
    	// Connect two tiles.
    	Tile tile1 = new Tile(0, BARE);
    	Tile tile2 = new Tile(0, BARE);
    	tile1.attachTile(tile2, NORTH);
    	
    	// Verify that the CS has moved to the adjacent tile.
    	cs.setTile(tile1);
    	cs.move(NORTH);
    	assertEquals(cs.getTile(), tile2);
    	
    	// Introduce a wall or edge by detaching the tiles.
    	tile1.detachTile(NORTH);
    	
    	// Verify that the CS cannot return to the previous tile.
    	assertNull(cs.getTile().getAdjacent(SOUTH));	// Detecting a nearby wall/edge
    	assertFalse(cs.move(SOUTH));
    	assertNotEquals(cs.getTile(), tile1);
    }

    @Test
    public void moveBackTest() throws Exception {
        Tile t1 = new Tile(0, BARE);
        Tile t2 = new Tile(0, BARE);

        t1.attachTile(t2, NORTH);

        assertNull(cs.getTile());

        //set to t1, try to move back
        cs.setTile(t1);
        assertNotEquals(t2, cs.getTile());
        assertEquals(t1, cs.getTile());
        assertFalse(cs.moveBack());  //CS should remain on same tile since it cannot move back
        assertNotEquals(t2, cs.getTile());
        assertEquals(t1, cs.getTile());

        //set to t1, move north, then move back
        cs.move(NORTH);
        assertNotEquals(t1, cs.getTile());
        assertEquals(t2, cs.getTile());
        assertTrue(cs.moveBack());
        assertEquals(t1, cs.getTile());
        assertNotEquals(t2, cs.getTile());

        // the below is testing if the visit history stack is properly implemented
        assertFalse(cs.moveBack());  //CS should remain on same tile since it cannot move back

        // try moving north, south, and north again
        cs.move(NORTH);
        cs.move(SOUTH);
        cs.move(NORTH);
        assertNotEquals(t1, cs.getTile());
        assertEquals(t2, cs.getTile());
        assertTrue(cs.moveBack()); //move back south
        assertEquals(t1, cs.getTile());
        assertNotEquals(t2, cs.getTile());
        assertTrue(cs.moveBack()); //move back north
        assertEquals(t2, cs.getTile());
        assertNotEquals(t1, cs.getTile());
        assertTrue(cs.moveBack()); //move back south
        assertEquals(t1, cs.getTile());
        assertNotEquals(t2, cs.getTile());
    }



    @Test
    public void followPath() throws Exception {

    }

    @Test
    public void getAndSetTilesTest() throws Exception {
        Tile t1 = new Tile(0, BARE);
        Tile t2 = new Tile(0, BARE);

        assertEquals(t1.getVisited(), 0);
        assertEquals(t2.getVisited(), 0);
        assertNull(cs.getTile());

        //set to t1 and test
        cs.setTile(t1);
        assertEquals(t1, cs.getTile());
        assertEquals(t1.getVisited(), 1);

        //set to t2 and test
        assertNotEquals(t1, t2);
        cs.setTile(t2);
        assertNotEquals(t1, cs.getTile());
        assertEquals(t2, cs.getTile());
        assertEquals(t2.getVisited(), 1);

        //set back to t1 and test
        cs.setTile(t1);
        assertNotEquals(t2, cs.getTile());
        assertEquals(t1, cs.getTile());
        assertEquals(t1.getVisited(), 2);
    }

}