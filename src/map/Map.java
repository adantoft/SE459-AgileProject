package map;

import map.Space;
import general.DataValidationException;
import floor.Tile;
import floor.Tile.Direction;
import floor.Tile.Floor;
import floor.Tile.Role;

import java.util.ArrayList;
import java.util.List;

import map.Point;

import static floor.Tile.Direction.*;

public class Map {

	public final Tile[][] map;
	public Map(int x, int y) {
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
		for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x ++) {
			for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y ++) {
				this.map[x][y] = new Tile(random, floor, new Point(x,y));
			}
		}
	}

	public void attachTiles(Space space) {
		try {

			// Moves along the x axis
			for (int x = space.getBottomLeft().getX(); x <= space.getTopRight().getX(); x++) {

				// Moves along the y axis
				for (int y = space.getBottomLeft().getY(); y <= space.getTopRight().getY(); y++) {

					// Connects tiles upwards; if not all the way to the top
					if (y != space.getTopRight().getY()){
						this.map[x][y].attachTile(this.map[x][y+1], NORTH);
					}
					// Connects tiles leftwards; if not all the way to the right
					if (x != space.getTopRight().getX()){
						this.map[x][y].attachTile(this.map[x+1][y], EAST);
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

	public void setDoorway(Point tile1, Point tile2) {

		try {
			if (tile1.getX() == tile2.getX()) {
				if (tile1.getY() > tile2.getY()) {
					this.map[tile1.getX()][tile1.getY()].attachTile(this.map[tile2.getX()][tile2.getY()], SOUTH);
				} else if (tile1.getY() < tile2.getY()) {
					this.map[tile1.getX()][tile1.getY()].attachTile(this.map[tile2.getX()][tile2.getY()], NORTH);
				}
			} else if (tile1.getY() == tile2.getY()) {
				if (tile1.getX() > tile2.getX()) {
					this.map[tile1.getX()][tile1.getY()].attachTile(this.map[tile2.getX()][tile2.getY()], WEST);
				} else if (tile1.getX() < tile2.getX()) {
					this.map[tile1.getX()][tile1.getY()].attachTile(this.map[tile2.getX()][tile2.getY()], EAST);
				}
			}
		} catch (DataValidationException e) {
			e.printStackTrace();
		}

	}
	
	public boolean withinTwoTiles(Tile tile1, Tile tile2) {
		int x1 = tile1.getCoordinates().getX();
		int y1 = tile1.getCoordinates().getY();
		int x2 = tile2.getCoordinates().getX();
		int y2 = tile2.getCoordinates().getY();
		
		int distance = (int) Math.floor(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
		if (distance <= 2) {
			return true;
		}
		
		return false;
	}

	public static void main(String[] args) {
		Tile tile = new Tile(Floor.BARE);
	}

}