/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.PathFinder;

import java.util.List;

/**
 *
 * @author rajith
 */
public class Node implements Comparable{
    
    private List neighbours;
    private Node pathParent;
    private int weight; 
    private int x;
    private int y;
    private int estimatedCostToGoal;
    private int costFromStart;
    
   
  
    
    
    public Node(int x,int y){
        
        this.weight=1; 
        this.x=x;
        this.y=y;
        pathParent=null;
        neighbours=null;
        
        
    }
    
    
        
    

    //get the cost from start to the end node via this node
    public int getCost() {
        return getCostFromStart() + getEstimatedCostToGoal();
    }

    @Override
    public int compareTo(Object other) {
        int otherValue = ((Node)other).getCost();
        int thisValue = this.getCost();

        return sign(thisValue - otherValue);
    }

    private int sign(int num){
        
        if(num<0){ return -1;}
        else if(num==0){return 0;}
        else return 1;
        
    }

    /**
        Gets the cost between this node and the specified
        adjacent (aka "neighbor" or "child") node.
    */
///////////////////////////////////////////////////////////////////////////////////////////////// get cost

    /**
        Gets the estimated cost between this node and the
        specified node. The estimated cost should never exceed
        the true cost. The better the estimate, the more
        efficient the search.
    */
    public int getEstimatedCost(Node node){
        
        return (Math.abs(node.getX()-this.x)+Math.abs(node.getY()-this.y));
        
    }


    /**
        Gets the children (aka "neighbors" or "adjacent nodes")
        of this node.
    */
    //public abstract List getNeighbors();

    
    
    
    


    public List getNeighbours() {
        return neighbours;
    }


    public void setNeighbours(List neighbours) {
        this.neighbours = neighbours;
    }


    public Node getPathParent() {
        return pathParent;
    }


    public void setPathParent(Node pathParent) {
        this.pathParent = pathParent;
    }

 
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

 
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @return the estimatedCostToGoal
     */
    public int getEstimatedCostToGoal() {
        return estimatedCostToGoal;
    }

    /**
     * @param estimatedCostToGoal the estimatedCostToGoal to set
     */
    public void setEstimatedCostToGoal(int estimatedCostToGoal) {
        this.estimatedCostToGoal = estimatedCostToGoal;
    }

    /**
     * @return the costFromStart
     */
    public int getCostFromStart() {
        return costFromStart;
    }

    /**
     * @param costFromStart the costFromStart to set
     */
    public void setCostFromStart(int costFromStart) {
        this.costFromStart = costFromStart;
    }


    
    
       
      
}

