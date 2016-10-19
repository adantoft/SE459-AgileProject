package floor;


import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static floor.Tile.*;
import static floor.Tile.Direction.*;
import static floor.Tile.Floor.*;
import static org.junit.Assert.*;


public class TileTest {


    @Test
    public void getOppositeTest() throws Exception {

        assertNotNull(NORTH.getOpposite(NORTH));
        assertNotNull(SOUTH.getOpposite(SOUTH));
        assertNotNull(WEST.getOpposite(WEST));
        assertNotNull(EAST.getOpposite(EAST));

        assertEquals(SOUTH, NORTH.getOpposite(NORTH));
        assertEquals(NORTH, SOUTH.getOpposite(SOUTH));
        assertEquals(EAST, WEST.getOpposite(WEST));
        assertEquals(WEST, EAST.getOpposite(EAST));

        assertNotSame(NORTH.getOpposite(NORTH), SOUTH.getOpposite(SOUTH));
        assertNotSame(NORTH.getOpposite(NORTH), EAST.getOpposite(EAST));
        assertNotSame(NORTH.getOpposite(NORTH), WEST.getOpposite(WEST));

        assertNotSame(SOUTH.getOpposite(SOUTH), NORTH.getOpposite(NORTH));
        assertNotSame(SOUTH.getOpposite(SOUTH), EAST.getOpposite(EAST));
        assertNotSame(SOUTH.getOpposite(SOUTH), WEST.getOpposite(WEST));

        assertNotSame(EAST.getOpposite(EAST), NORTH.getOpposite(NORTH));
        assertNotSame(EAST.getOpposite(EAST), SOUTH.getOpposite(SOUTH));
        assertNotSame(EAST.getOpposite(EAST), WEST.getOpposite(WEST));

        assertNotSame(WEST.getOpposite(WEST), NORTH.getOpposite(NORTH));
        assertNotSame(WEST.getOpposite(WEST), SOUTH.getOpposite(SOUTH));
        assertNotSame(WEST.getOpposite(WEST), EAST.getOpposite(EAST));

    }


    @Test
    public void getFloorCodeAsStringTest() throws Exception {


        assertNotNull(Floor.BARE.getFloorCodeAsString());
        assertNotNull(Floor.LOW.getFloorCodeAsString());
        assertNotNull(Floor.HIGH.getFloorCodeAsString());

        assertEquals("Bare floor", Floor.BARE.getFloorCodeAsString());
        assertEquals("Low pile", Floor.LOW.getFloorCodeAsString());
        assertEquals("High pile", Floor.HIGH.getFloorCodeAsString());

        assertNotSame(Floor.BARE.getFloorCodeAsString(), Floor.LOW.getFloorCodeAsString());
        assertNotSame(Floor.BARE.getFloorCodeAsString(), Floor.HIGH.getFloorCodeAsString());

        assertNotSame(Floor.LOW.getFloorCodeAsString(), Floor.BARE.getFloorCodeAsString());
        assertNotSame(Floor.LOW.getFloorCodeAsString(), Floor.HIGH.getFloorCodeAsString());

        assertNotSame(Floor.HIGH.getFloorCodeAsString(), Floor.LOW.getFloorCodeAsString());
        assertNotSame(Floor.HIGH.getFloorCodeAsString(), Floor.BARE.getFloorCodeAsString());

        }

    @Test
    public void tileTestTwoInput() throws Exception {
        int dirtInt = 0;
        int dirtInt2 = 1;

        Tile testTileTwoInput = new Tile(dirtInt, BARE);
        Tile testTileTwoDirtInput = new Tile(dirtInt2, LOW);



        assertEquals("Dirt: 0"
                +"\nFloor type: Bare floor", testTileTwoInput.toString());
        assertEquals("Dirt: 1"
                +"\nFloor type: Low pile", testTileTwoDirtInput.toString());

    }

    @Test
    public void TileCreateTest() throws Exception {

        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        assertNotNull(testTile1);

        assertEquals("Dirt: 0"
                +"\nFloor type: Bare floor", testTile1.toString());
        assertEquals("Dirt: 0"
                +"\nFloor type: Low pile", testTile2.toString());

    }


    @Test
    public void toStringTest() throws Exception {
        int dirtInt = 1;
        Tile testTile1 = new Tile(BARE);
        Tile testTileTwoInput = new Tile(dirtInt, BARE);

        String newTestString = testTile1.toString();
        String newTestString2 = testTileTwoInput.toString();

        assertNotNull(newTestString);
        assertNotNull(newTestString2);

 
    }

    @Test
    public void attachTileTest() throws Exception {
        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(HIGH);

        testTile1.attachTile(testTile2, NORTH);
        testTile2.attachTile(testTile3, EAST);
        
        assertEquals(testTile1.getAdjacent(NORTH), testTile2);
        assertEquals(testTile3.getAdjacent(WEST), testTile2);
        assertNotEquals(testTile3.getAdjacent(EAST), testTile1);
    }

    @Test
    public void detachTileTest() throws Exception {
    	Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(HIGH);
    	
    	testTile1.attachTile(testTile2, NORTH);
    	testTile1.attachTile(testTile3, EAST);
    	
    	testTile1.detachTile(NORTH);
    	assertNull(testTile1.getAdjacent(NORTH));
    	assertNotNull(testTile1.getAdjacent(EAST));
    }

    @Test
    public void getVisitedTest() throws Exception {
        Tile testTile1 = new Tile(BARE);
        assertNotNull(testTile1.getVisited());
        assertEquals(0, testTile1.getVisited());
    }


    @Test
    public void getDirtTest() throws Exception {
        int dirtInt = 0;
        int dirtInt2 = 1;
        Tile testTileTwoInput = new Tile(dirtInt, BARE);
        Tile testTileTwoDirtInput = new Tile(dirtInt2, LOW);

        assertNotNull(testTileTwoInput.getDirt());
        assertNotNull(testTileTwoDirtInput.getDirt());

        assertEquals(0, testTileTwoInput.getDirt());
        assertEquals(1, testTileTwoDirtInput.getDirt());

        assertNotEquals(0, testTileTwoDirtInput.getDirt());
        assertNotEquals(1, testTileTwoInput.getDirt());


    }

    @Test
    public void getAdjacentTest() throws Exception {


        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(HIGH);

        List<Tile> adjacents = new ArrayList<>();

        testTile1.attachTile(testTile2, NORTH);
        testTile1.attachTile(testTile3, EAST);
        adjacents.add(testTile1.getAdjacent(NORTH));


        assertNotNull(adjacents);
        assertEquals("[" + testTile2 + "]", adjacents.toString());
        assertNotEquals("[" + testTile3 + "]", adjacents.toString());
        assertEquals("Dirt: 0"
                +"\nFloor type: Low pile", testTile1.getAdjacent(NORTH).toString());

    }


    @Test(timeout = 10000)
    public void getAdjacentTilesTest() throws Exception {


        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(LOW);

        testTile1.attachTile(testTile1, NORTH);
        testTile1.attachTile(testTile2, EAST);
        testTile1.attachTile(testTile3, WEST);

        assertNotNull(testTile1);


    }


    @Test
    public void getDirectionTo() throws Exception {

        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(HIGH);

        testTile1.attachTile(testTile2, NORTH);
        testTile1.attachTile(testTile3, EAST);

        assertEquals("SOUTH", testTile2.getDirectionTo(testTile1).toString());
        assertNotEquals("NORTH", testTile2.getDirectionTo(testTile1).toString());

    }

}

