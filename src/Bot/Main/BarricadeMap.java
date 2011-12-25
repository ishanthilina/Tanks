/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Main;

/**
 *
 * @author rajith
 */
import Bot.PathFinder.Node;
import java.util.LinkedList;



/**
 *Map used by the AI
 * 
 * @author rajith
 */
public class BarricadeMap {
    
    private Node[][] map; 
    private int size;
    private boolean mapInitialized;


    
    
    
    
    public BarricadeMap(int size){
        
        this.size=size;
        map= new Node[size][size];        
        setNodes();
        setNeighbours();
        mapInitialized=false;
        
        
        
    }
    
    public void mapInitialized(){
        
        mapInitialized=true;        
        
    }
    
    public boolean ismapInitialized(){
        
        return mapInitialized;      
    }
    
    
    public void setBlocks(Node block){ 
        //water blocks and stonewalls and for updating brickwalls
         map[block.getX()][block.getY()].setWeight(block.getWeight());       
     }
    
   
   
    public Node[][] getMap() {
        
        return map;
    }
 
    
    private void setNodes(){
        
        
        for(int x=0;x<getSize();x++){
            
            for(int y=0;y<getSize();y++){
                
                Node n=new Node(x,y);

                map[x][y]=n;
                
                
            }            
            
        }
        
        
    }


    
    private void setNeighbours(){
        
        for(int x=0;x<getSize();x++){
            
            for(int y=0;y<getSize();y++){    
        
                LinkedList list=new LinkedList();
                
                if(x!=0){ list.add(map[x-1][y]); }
                if(x!=(getSize()-1)){ list.add(map[x+1][y]); }
                if(y!=0){ list.add(map[x][y-1]); }
                if(y!=(getSize()-1)){ list.add(map[x][y+1]); }
     
  
                map[x][y].setNeighbours(list);
                
            }
        }
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    
    
}
