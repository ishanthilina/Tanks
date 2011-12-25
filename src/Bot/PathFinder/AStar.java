/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.PathFinder;


import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rajith
 */
public class AStar{
    
    
    
    
        /**
        Construct the path, not including the start node.
    */
    private Path constructPath(Node node) {
        
        Node gNode=node;
        LinkedList path = new LinkedList();
        while (node.getPathParent() != null) {
            path.addFirst(node);
            node = node.getPathParent();
        }
        Path sPath=new Path(path,gNode.getCostFromStart());
        return sPath;
    }


    public Path findPath(Node startNode, Node goalNode)
{

        PriorityList openList = new PriorityList();
        LinkedList closedList = new LinkedList();

        startNode.setCostFromStart(0);
        startNode.setEstimatedCostToGoal(startNode.getEstimatedCost(goalNode));
        startNode.setPathParent(null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            
            Node node = (Node)openList.removeFirst();
            if (node == goalNode) {
                // construct the path from start to goal
                return constructPath(goalNode);
            }

            List neighbours = node.getNeighbours();
            
            for (int i=0; i<neighbours.size(); i++) {
                Node neighbourNode = (Node)neighbours.get(i);
                boolean isOpen = openList.contains(neighbourNode);
                boolean isClosed =closedList.contains(neighbourNode);
                
                int costFromStart = node.getCostFromStart() + neighbourNode.getWeight();
                    //node.getCost(neighborNode);

                // check if the neighbor node has not been
                // traversed or if a shorter path to this
                // neighbor node is found.
                if ((!isOpen && !isClosed)||costFromStart < neighbourNode.getCostFromStart())
                {
                    neighbourNode.setPathParent(node);
                    neighbourNode.setCostFromStart(costFromStart);
                    neighbourNode.setEstimatedCostToGoal(neighbourNode.getEstimatedCost(goalNode));
                    if (isClosed) { closedList.remove(neighbourNode);  }
                    if (!isOpen) { openList.add(neighbourNode); }
                }
            }
            closedList.add(node);
        }

        // no path found
        return null;
    }


    
    
    
    
    
     public static class PriorityList extends LinkedList {

        public void add(Comparable object) {
            for (int i=0; i<size(); i++) {
                if (object.compareTo(get(i)) <= 0) {
                    add(i, object);
                    return;
                }
            }
            addLast(object);
        }
    }
    
}

