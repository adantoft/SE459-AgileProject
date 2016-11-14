package map;

import floor.Tile;
import floor.Tile.Floor;
import general.DataValidationException;

import java.util.ArrayList;
import java.util.List;

import static floor.Tile.Direction.*;

public class FloorPlan {

	public final Tile[][] map;
	public FloorPlan(int x, int y) {
		this.map = new Tile[x][y];
	}

	public void printMap() {
		for (int i = 0; i < this.map.length; i ++) {
			for (int j = 0; j < this.map[0].length; j ++) {

				// System.out.printf("(%d, %d) ", i, j);
				// System.out.println(this.map[i][j]);

			}
		}
	}

	public void setFloor(int random, Space space, Floor floor) {
//        Random rand = new Random();
//        random = rand.nextInt(3) + 1;

		for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x ++) {
			for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y ++) {
				this.map[x][y] = new Tile(random, floor, new Point(x,y));
			}
		}
		
		setNextTiles();
	}

	public void updateFloorType(Space space, Floor floor) {
		for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x ++) {
			for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y ++) {
				this.map[x][y].setFloor(floor);
			}
		}
	}

	public void attachTiles(Space space) {
		try {

			// Moves along the x axis
			for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x ++) {

				// Moves along the y axis
				for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y ++) {

					// Connects tiles upwards; if not all the way to the top
					if (y != space.getTopRight().getY()){
						this.map[x][y].attachTile(this.map[x][y + 1], NORTH);
					}
					// Connects tiles leftwards; if not all the way to the right
					if (x != space.getTopRight().getX()){
						this.map[x][y].attachTile(this.map[x + 1][y], EAST);
					}
				}
			}

		} catch (DataValidationException e) {
			e.printStackTrace();
		}
	}

	public void setNextTiles() {
		try {
			for (int y = 0; y < map[0].length; y ++) {
				for (int x = 0; x < map.length; x ++) {
					
					if (map[x][y] == null) {
						continue;
					}
					
					if (x > 0) {
						if (map[x - 1][y] == null) {
							continue;
						}
						map[x][y].setNext(map[x - 1][y], WEST);
					}
					
					if (y > 0) {
						if (map[x][y - 1] == null) {
							continue;
						}
						map[x][y].setNext(map[x][y - 1], SOUTH);
					}
				}
			}
		} catch (DataValidationException e) {
			e.printStackTrace();
		}
	}

	public void setSpace(int random, Space space, Floor floor) {
		setFloor(random, space, floor);
		attachTiles(space);
	}

	/**
	 * Returns all the tiles in the map.
	 * @return
	 */
	public List<Tile> getTiles(){
		List<Tile> tiles = new ArrayList<>();

		for (Tile[] tileArray : map) {
			for (Tile tile : tileArray) {
				tiles.add(tile);
			}
		}

		return tiles;

	}

	/**
	 * Returns all the tiles that have been assigned a space.
	 * @return
	 */
	public List<Tile> getActiveTiles(){
		List<Tile> tiles = new ArrayList<>();

		for (Tile[] tileArray : map) {
			for (Tile tile : tileArray) {
				if (tile != null)
					tiles.add(tile);
			}
		}

		return tiles;

	}

	public Tile getTile(int x, int y) {
		return map[x][y];
	}

	public void setDoorway(Point startPt, Point endPt) {

		Tile startTile = this.map[startPt.getX()][startPt.getY()];
		Tile endTile = this.map[endPt.getX()][endPt.getY()];

		try {
			if (startPt.getX() == endPt.getX()) {
				if (startPt.getY() > endPt.getY()) {
					startTile.attachTile(endTile, SOUTH);
				} else if (startPt.getY() < endPt.getY()) {
					startTile.attachTile(endTile, NORTH);
			}
			} else if (startPt.getY() == endPt.getY()) {
				if (startPt.getX() > endPt.getX()) {
					startTile.attachTile(endTile, WEST);
				} else if (startPt.getX() < endPt.getX()) {
					startTile.attachTile(endTile, EAST);
				}
			}
		} catch (DataValidationException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Tile tile = new Tile(Floor.BARE);
	}

}