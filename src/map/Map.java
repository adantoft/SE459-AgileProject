package map;

import map.Space;
import general.DataValidationException;
import floor.Tile;
import floor.Tile.Floor;

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

				this.map[x][y] = new Tile(1, floor);

			}
		}
	}

	public void attachSpace(Space space) {
		try {
			for (int x = space.getBottomLeft().getX(); x < space.getTopRight().getX(); x ++) {
				for (int y = space.getBottomLeft().getY(); y < space.getTopRight().getY(); y ++) {
					
					this.map[x][y + 1].attachTile(this.map[x][y + 1], 'n');
					System.out.printf("(%d, %d) attached north to (%d, %d).\n", x, y + 1, x, y);
				}
			}

			for (int x = space.getBottomLeft().getX(); x < space.getTopRight().getX(); x ++) {
				for (int y = space.getBottomLeft().getY(); y < space.getTopRight().getY(); y ++) {

					this.map[x][y].attachTile(this.map[x + 1][y], 'e');
					System.out.printf("(%d, %d) attached east to (%d, %d).\n", x + 1, y, x, y);
				}
			}


		} catch (DataValidationException e) {
			e.printStackTrace();
		}
	}


	public void setSpace(Space space, Floor floor) {
		setFloor(space, floor);
		attachSpace(space);
	}
}