package vacuum;

import floor.Tile;
import map.Map;
import map.Point;
import map.Space;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static floor.Tile.Floor.BARE;
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
        /* 1 create space
        *  2 navigate space
        *  3 show print output
        *  4 test that all spaces are visited
        * */

        Map map = new Map(2, 2);
        Space testRoomBare = new Space(new Point(0, 0), new Point(1,1));
        map.setSpace(testRoomBare, BARE);

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            assertEquals(tile.getVisited(), 0);
        }

        cs.setTile(map.getTile(0,0));
        assertNotEquals(cs.getTile().getAdjacentTiles().size(), 0); //TODO figure out why there are no adjacent tiles

        Navigation.getInstance().traverseWholeFloor();

        for (Tile tile : map.getTiles()) { // tests that all tiles are not visited
            System.out.println(tile.getVisited());
        }



    }

    @Test
    public void calculatePath() throws Exception {

    }

    @Test
    public void getEfficientPath() throws Exception {

    }

}