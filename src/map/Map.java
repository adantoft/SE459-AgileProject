package map;

import map.Space;
import general.DataValidationException;
import floor.Tile;
import floor.Tile.Direction;
import floor.Tile.Floor;

import java.util.ArrayList;
import java.util.List;

public class Map {

	public final Tile[][] map;
	public Map(int x, int y) {
		this.map = new Tile[x][y];
	}

	public void printMap() {
		for (int i = 0; i < this.map.length; i ++) {
			for (int j = 0; j < this.map[0].length; j ++) {

				System.out.printf("(%d, %d) ", i, j);
				System.out.println(this.map[i][j]);

			}
		}
	}

	public void setFloor(Space space, Floor floor) {
		for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x ++) {
			for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y ++) {

				this.map[x][y] = new Tile(floor);
			}
		}
	}

	public void attachTiles(Space space) {
		try {
			// Moves along the x axis
			for (int x = space.getBottomLeft().getX(); x < space.getTopRight().getX(); x ++) {
				for (int y = space.getBottomLeft().getY(); y < space.getTopRight().getY(); y ++) {
					
					// Connects tiles upwards
					this.map[x][y + 1].attachTile(this.map[x][y + 1], Direction.NORTH);
					System.out.printf("(%d, %d) attached north to (%d, %d).\n", x, y + 1, x, y);
				}
			}

			// Moves along the y axis
			for (int y = space.getBottomLeft().getY(); y < space.getTopRight().getY(); y ++) {
				for (int x = space.getBottomLeft().getX(); x < space.getTopRight().getX(); x ++) {

					// Connects tiles rightwards
					this.map[x][y].attachTile(this.map[x + 1][y], Direction.EAST);
					System.out.printf("(%d, %d) attached east to (%d, %d).\n", x + 1, y, x, y);
				}
			}


		} catch (DataValidationException e) {
			e.printStackTrace();
		}
	}


	public void setSpace(Space space, Floor floor) {
		setFloor(space, floor);
		attachTiles(space);
	}

	public List<Tile> getTiles(){
		List<Tile> lst = new ArrayList<Tile>();

		for (int i = 0; i < this.map.length; i ++) {
			for (int j = 0; j < this.map[0].length; j ++) {
				lst.add(map[i][j]);

			}
		}
		return lst;

	}

	public Tile getTile(int x, int y) {
		return map[x][y];
	}
}