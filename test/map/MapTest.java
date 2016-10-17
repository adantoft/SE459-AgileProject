package map;

import floor.Tile;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

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

        map.Map testMapNew = new map.Map(2, 2);
        testMapNew.printMap();

    }


    @Test(timeout = 10000)
    public void setFloorTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapNewCompare = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);
        testMapNew.printMap();
        assertNotNull(testMapNew);
        assertNotEquals(testMapNewCompare, testMapNew.toString());

    }


    @Test(timeout = 10000)
    public void attachTilesTest() throws Exception {

    }


    @Test
    public void setSpaceTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        map.Map testMapNewCompare = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));

        testMapNew.setSpace(testRoom1HighCarpNew, Tile.Floor.BARE);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(testRoom1HighCarpNew, Tile.Floor.HIGH);
        assertNotEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

        testMapNewCompare.setSpace(testRoom1HighCarpNew, Tile.Floor.BARE);
        assertEquals(testMapNew.getTile(0,0).toString(), testMapNewCompare.getTile(0,0).toString());

    }

    @Test(timeout = 10000)
    public void getTilesTest() throws Exception {


    /*
            public List<Tile> getTiles(){
        List<Tile> lst = new ArrayList();

        for (int i = 0; i < this.map.length; i ++) {
            for (int j = 0; j < this.map[0].length; j ++) {
                lst.add(map[i][j]);

            }
        }
        return lst;

    }
         */




    }

    @Test
    public void getTileTest() throws Exception {

        map.Map testMapNew = new map.Map(2, 2);
        Space testRoom1HighCarpNew = new Space(new Point(0, 0), new Point(1, 1));
        testMapNew.setFloor(testRoom1HighCarpNew, Tile.Floor.HIGH);


        assertNotNull(testMapNew.getTile(0,0));
        assertEquals("Dirt: 0\n" + "Floor type: High pile", testMapNew.getTile(0,0).toString());
        assertNotEquals("Dirt: 1\n" + "Floor type: High pile", testMapNew.getTile(0,0).toString());

    }
}
