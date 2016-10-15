package vacuum;

import floor.Tile;
import map.Point;
import map.Space;

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
    public void moveBack() throws Exception {

    }

    @Test
    public void runVacuum() throws Exception {

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

        //go to t1 and test
        cs.setTile(t1);
        assertEquals(t1, cs.getTile());
        assertEquals(t1.getVisited(), 1);

        //go to t2 and test
        assertNotEquals(t1, t2);
        cs.setTile(t2);
        assertNotEquals(t1, cs.getTile());
        assertEquals(t2, cs.getTile());
        assertEquals(t2.getVisited(), 1);

        //go back to t1 and test
        cs.setTile(t1);
        assertNotEquals(t2, cs.getTile());
        assertEquals(t1, cs.getTile());
        assertEquals(t1.getVisited(), 2);
    }

}