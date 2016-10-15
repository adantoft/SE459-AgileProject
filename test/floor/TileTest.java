package floor;


import map.Point;
import map.Space;
import org.junit.Test;



import static floor.Tile.*;
import static floor.Tile.Direction.*;
import static floor.Tile.Floor.*;
import static org.junit.Assert.*;


public class TileTest {


    map.Map testMap = new map.Map(10, 10);
    Point p1 = new Point(0, 0);
    Space testRoom1HighCarp = new Space(new Point(0, 0), new Point(3, 4));
    Space testRoom2LowCarp = new Space(new Point(0, 5), new Point(3, 5));
    Space testRoom3Bare = new Space(new Point(0, 7), new Point(3, 9));

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
    public void TileTestTwoInput() throws Exception {
        int dirtInt = 0;
        int dirtInt2 = 1;

        Tile testTileTwoInput = new Tile(dirtInt, BARE);
        Tile testTileTwoDirtInput = new Tile(dirtInt2, LOW);

        assertNotNull(testTileTwoInput);

        assertEquals("Dirt: 0"
                +"\nFloor type: Bare floor", testTileTwoInput.toString());
        assertEquals("Dirt: 1"
                +"\nFloor type: Low pile", testTileTwoDirtInput.toString());

    }

    @Test
    public void TileTest() throws Exception {

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
        System.out.println(testTile1.toString());
        System.out.println(testTileTwoInput.toString());
    }

    @Test
    public void attachTileTest() throws Exception {


        map.Map testMap = new map.Map(10, 10);
        Tile testTile1 = new Tile(BARE);
        Tile testTile2 = new Tile(LOW);
        Tile testTile3 = new Tile(HIGH);

        testTile2.attachTile(testTile1, NORTH);

    }

    @Test
    public void detachTile() throws Exception {

    }

    @Test
    public void getVisited() throws Exception {
        Tile testTile1 = new Tile(BARE);
        assertNotNull(testTile1.getVisited());
        assertEquals(0, testTile1.getVisited());

    }


    @Test
    public void getDirt() throws Exception {
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
        int dirtInt = 1;
        Tile testTileTwoInput = new Tile(dirtInt, BARE);

       // testTileTwoInput.getAdjacent(NORTH);
       // assertNotNull(testTileTwoInput.getAdjacent(NORTH));
       // assertNotNull(testTileTwoInput.getAdjacent(SOUTH));

        //assertEquals("north", testTileTwoInput.getAdjacent(NORTH));


    }


    @Test
    public void getAdjacentTiles() throws Exception {

    }

    @Test
    public void visit() throws Exception {
        int dirtInt = 0;
        int dirtInt2 = 1;
        Tile testTileTwoInput = new Tile(dirtInt, BARE);
        Tile testTileTwoDirtInput = new Tile(dirtInt2, LOW);

        testTileTwoInput.visit();



    }


    @Test
    public void getDirectionTo() throws Exception {
        int dirtInt = 0;
        int dirtInt2 = 1;
        Tile testTileTwoInput = new Tile(dirtInt, BARE);
        Tile testTileTwoDirtInput = new Tile(dirtInt2, LOW);


    }

}

