package map;

import floor.Tile;
import org.junit.Test;



import static org.junit.Assert.*;


public class MapTest {


    map.Map testMap = new map.Map(10, 10);
    Point p1 = new Point(0, 0);
    Space testRoom1HighCarp = new Space(new Point(0, 0), new Point(3, 4));
    Space testRoom2LowCarp = new Space(new Point(0, 5), new Point(3, 5));
    Space testRoom3Bare = new Space(new Point(0, 7), new Point(3, 9));


    @Test
    public void fillingInMap() throws Exception {

        assertNotNull(testMap);

    }

    @Test(timeout = 10000)
    public void printMapTest() throws Exception {

        testMap.printMap();

    }


    @Test(timeout = 10000)
    public void setFloor() throws Exception {

        testMap.setFloor(testRoom1HighCarp, Tile.Floor.HIGH);
        testMap.setFloor(testRoom2LowCarp, Tile.Floor.LOW);
        testMap.setFloor(testRoom3Bare, Tile.Floor.BARE);

    }



    @Test
    public void attachTiles() throws Exception {

    }


    @Test
    public void setSpace() throws Exception {

    }


}


