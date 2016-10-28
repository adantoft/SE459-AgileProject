package vacuum;

import floor.Tile;
import general.DataValidationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import static floor.Tile.Direction.*;

public class Navigation {

    private static ArrayList<Tile.Direction> successPath = new ArrayList<>();
    private static HashSet<Tile> stateSpace = new HashSet();
	private static Navigation instance;

	private Navigation() {}

	public static Navigation getInstance() {
		if (instance == null) {
			instance = new Navigation();
		}
		return instance;
	}
	CleanSweep cs = CleanSweep.getInstance();

    public HashSet<Tile> getStateSpace(){
        return stateSpace;
    }

    /**
     * Method to DFS traverse entire floor
     */
    public void traverseWholeFloor() {
		List<Tile> successorTiles = new ArrayList<>();

		do {
			// call clean code here
			successorTiles.clear();
			for (Tile tile : cs.getTile().getAdjacentTiles()) {
				if (tile.getVisited() == 0){
					successorTiles.add(tile);
				}
			}
			if (!successorTiles.isEmpty()) {
				try {
					cs.move(cs.getTile().getDirectionTo(successorTiles.get(0))); //picks direction (first added) - random?
				} catch (DataValidationException e) {
					e.printStackTrace();
				}
			} else {
				cs.moveBack();
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
            System.err.print("Going Direction: " + currentNode.getAction() + "\t");
            if (currentNode.getTile() == end) { //Test the tile if it meets requirements
                calcSuccessPath(currentNode);
                return successPath;
            }
            System.err.print("Adding Successor States...");
            for (Node node : currentNode.getSuccessorStates()) { //only states in which have not been visited
                history.add(node); //add to begin of queue
                System.err.print(node.getAction() + " ");
            }
            System.err.println();
        }
        return null; //path not found
    }

    /**
     * Recursive function to build path from node tree
     * @param node ending node
     * @return node
     */

    private static Node calcSuccessPath(Node node){  //recursive function to navigate from end node back up to start node
        if (node.getAction() != null) {
            successPath.add(0,node.getAction());
        }
        if (node.getParent() == null){
            return node;
        } else {
            return calcSuccessPath(node.getParent());
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

    public Node(Node parentNode, Tile tile, Tile.Direction direction, int runningPathCost){
        this.parentNode = parentNode;
        this.tile = tile;
        this.direction = direction;
        this.runningPathCost = runningPathCost;
        Navigation.getInstance().getStateSpace().add(tile);
    }
    public Tile getTile(){
        return tile;
    }
    public Tile.Direction getAction(){
        return direction;
    }
    public Node getParent(){
        return parentNode;
    }
    public ArrayList<Node> getSuccessorStates(){ //returns list of successor states (children of this node); excludes already visited states
        ArrayList<Node> successorStates = new ArrayList<>();
        HashSet<Tile> stateSpace = Navigation.getInstance().getStateSpace();

        try {
            for (Tile.Direction dir : Tile.Direction.values()){

                if (tile.getAdjacent(dir)!=null && !Navigation.getInstance().getStateSpace().contains(tile.getAdjacent(dir))) {

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
                            throw new Error("Invalid Direction");
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
    public int compare(Node n1, Node n2){
        if (n1.runningPathCost < n2.runningPathCost){
            return -1;
        }else if (n1.runningPathCost > n2.runningPathCost){
            return 1;
        }
        return 0;
    }
}