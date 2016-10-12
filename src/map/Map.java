package map;

import map.Point;
import general.DataValidationException;
import floor.Tile;
import floor.Tile.Floor;

public class Map {
  public final Tile[][] map;
  public Map(int x, int y) {
    this.map = new Tile[x][y];
  }

  public void setRoom(Point bottom_left, Point top_right, Floor floor) {

    for (int x = bottom_left.getX(); x <= top_right.getX(); x++) {
        for (int y = bottom_left.getY(); y <= top_right.getY(); y++) {

            this.map[x][y] = new Tile(1, floor);

        }
    }
    
    try {
      for (int x = bottom_left.getX(); x < top_right.getX(); x++) {
          for (int y = bottom_left.getY(); y < top_right.getY(); y++) {
            int y_incr = y + 1;

            System.out.println("(" + x + ", " + y_incr + ") attached north to (" + x + ", " + y + ")");

            this.map[x][y].attachTile(this.map[x][y+1], 'n');

            System.out.println("(" + x + ", " + y + ") attached south to (" + x + ", " + y_incr + ")");

            this.map[x][y+1].attachTile(this.map[x][y], 'n');
          }
      }

      for (int x = bottom_left.getX(); x < top_right.getX(); x++) {
          for (int y = bottom_left.getY(); y < top_right.getY(); y++) {
            int x_incr = x + 1;

            System.out.println("(" + x_incr + ", " + y + ") attached west to (" + x + ", " + y + ")");

            this.map[x][y].attachTile(this.map[x+1][y], 'w');

            System.out.println("(" + x + ", " + y + ") attached east to (" + x_incr + ", " + y + ")");

            this.map[x+1][y].attachTile(this.map[x][y], 'w');
          }
      }
      
      
    } catch (DataValidationException e) {
      e.printStackTrace();
    }

  }
}