/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.PathFinder;

import java.util.LinkedList;

/**
 *
 * @author rajith
 */
public class Path implements Comparable{ 
    
    private LinkedList path;
    private int cost;
   

    
    public Path(LinkedList <Node> path,int cost){
        
        this.path=path;
        this.cost=cost;
        
    }
    
    
    /**
     * @return the path
     */   
    public LinkedList <Node> getPath() {
        return path;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    public int compareTo(Object other) {
        
        
        return sign(this.cost-((Path)other).getCost());
        
        
    }
    
    private int sign(int num){
        
        if(num<0){ return -1;}
        else if(num==0){return 0;}
        else return 1;
        
    }

    /**
     * @param cost the cost to set
     */
    public void addCost(int addcost) {
        this.cost =cost+addcost;
    }
    
    
}
