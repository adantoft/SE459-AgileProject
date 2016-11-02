package vacuum;

import floor.Tile;
import general.DataValidationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Navigation {

    private static ArrayList<Tile.Direction> successPath = new ArrayList<>();
    private static HashSet<Tile> stateSpace = new HashSet();
	private static Navigation instance;
	private CleanSweep cs;


	private Navigation() {}

	public static Navigation getInstance() {
		if (instance == null) {
			instance = new Navigation();
			instance.cs = CleanSweep.getInstance();
		}
		return instance;
	}


    public HashSet<Tile> getStateSpace(){
        return stateSpace;
    }

	public void clearShortestPath() {
		successPath.removeAll(successPath);
		stateSpace.clear();
	}

    /**
     * Method to DFS traverse entire floor
     */
    public void traverseWholeFloor() {
		List<Tile> successorTiles = new ArrayList<>();

		do {
			successorTiles.clear();
			for (Tile tile : cs.getTile().getAdjacentTiles()) {
				if (tile.getVisited() == 0) {
					successorTiles.add(tile);
				}
			}
			if (!successorTiles.isEmpty()) {
				try {
					cs.move(cs.getTile().getDirectionTo(successorTiles.get(0)));	// Picks direction (first added) - random?

				} catch (DataValidationException e) {
					e.printStackTrace();
				}
			} else {
				if (!cs.moveBack()) {
					cs.moveBack();    // If all adjacent tiles are visited, moves back a space

				} else {
					break;
				}
			}
		} while (!cs.isVisitHistoryEmpty());

	}

    /**
     * Calculates shortest path from start tile to end tile
     * @param start tile to find path from (normally CS current tile)
     * @param end tile to find  path to
     * @return ArrayList of directions to traverse shortest path or null if no path exists
     */
    public static ArrayList<Tile.Direction> calculatePath(Tile start, Tile end) {
        LinkedBlockingDeque<Node> history = new LinkedBlockingDeque<>();

        history.add(new Node(null, start, null, 0));

        while (!history.isEmpty()) {
            Node currentNode = history.poll();//grab next item on queue and remove
            if (currentNode.getTile() == end) { //Test the tile if it meets requirements
                calcSuccessPath(currentNode);
                return successPath;
            }
            for (Node node : currentNode.getSuccessorStates()) { //only states in which have not been visited
                history.add(node); //add to begin of queue
            }
        }
        return null; //path not found
    }

    /**
     * Recursive function to build path from node tree
     * @param node ending node
     * @return node
     */
    private static Node calcSuccessPath(Node node) {	// Navigates from end node to start node
        if (node.getAction() != null) {
            successPath.add(0,node.getAction());
        }
        if (node.getParent() == null){
            return node;
        } else {
            return calcSuccessPath(node.getParent());
        }
    }


	/**
	 * Recursive function to build path from node tree
	 * @param node ending node
	 * @return node
	 */
	private static Node calcSuccessPathReturn(Node node) {	// Navigates from end node to start node
		if (node.getAction() != null) {
			successPath.add(0,node.getAction());
		}
		if (node.getParent() == null){
			return node;
		} else {
			return calcSuccessPathReturn(node.getParent());
		}
	}

}

/**
 * private helper class for the path calculations above
 */
class Node implements Comparator<Node> {

    private Tile tile;
    private Node parentNode;
    private Node childNodeNorth;
    private Node childNodeSouth;
    private Node childNodeEast;
    private Node childNodeWest;
    private int runningPathCost;
    private Tile.Direction direction;

    public Node(Node parentNode, Tile tile, Tile.Direction direction, int runningPathCost) {
        this.parentNode = parentNode;
        this.tile = tile;
        this.direction = direction;
        this.runningPathCost = runningPathCost;
        Navigation.getInstance().getStateSpace().add(tile);
    }



    public Tile getTile() {
        return tile;
    }
    public Tile.Direction getAction() {
        return direction;
    }
    public Node getParent() {
        return parentNode;
    }
    
    // Returns list of successor states (children of this node); excludes already visited states
    public ArrayList<Node> getSuccessorStates() {
        ArrayList<Node> successorStates = new ArrayList<>();
        HashSet<Tile> stateSpace = Navigation.getInstance().getStateSpace();

        try {
            for (Tile.Direction dir : Tile.Direction.values()){

                if (tile.getAdjacent(dir) != null && !stateSpace.contains(tile.getAdjacent(dir))) {

                    Node node = new Node(this, tile.getAdjacent(dir), dir, runningPathCost + 1);

                    switch (dir) {
                        case NORTH:
                            childNodeNorth = node;
                            break;
                        case SOUTH:
                            childNodeSouth = node;
                            break;
                        case EAST:
                            childNodeEast = node;
                            break;
                        case WEST:
                            childNodeWest = node;
                            break;
                        default:
                            throw new DataValidationException("ERROR: Invalid direction");
                    }
                    successorStates.add(node);
                }
            }
        } catch (DataValidationException e) {
            e.printStackTrace();
        }
        return successorStates;
    }

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.runningPathCost < n2.runningPathCost){
            return -1;
        } else if (n1.runningPathCost > n2.runningPathCost){
            return 1;
        }
        return 0;
    }
}


