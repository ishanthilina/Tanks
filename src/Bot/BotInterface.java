/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import Bot.Main.BotInfo;
import Bot.Main.Brain;
import Bot.PathFinder.Node;
import Controlling.StringGenerator;
import Map.Brick;
import Map.CoinPack;
import Map.LifePack;
import Map.Player;
import Map.Stone;
import Map.Water;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


/**
 *Interfaces AI with the GUI
 * 
 * @author rajith
 */


public class BotInterface {
    
    private Brain AI;
    private boolean mapInitialized;                             //set this values after setting the maps init variable
    private boolean sGeneratorInitialized;
    private boolean playerInitialized;
    private Thread Bot;
    private LinkedList <Node> mapBots;
    private boolean setMapBots;

    
    public BotInterface(){
        
        
        mapInitialized=false;
        sGeneratorInitialized=false;
        playerInitialized=false;
        mapBots=new LinkedList<Node>();
        setMapBots=false;
        //
        AI=new Brain();
        
        
    }
    
    
    public boolean AIStart(){
        
        if(mapInitialized&&sGeneratorInitialized&&playerInitialized){
            
            
            Bot=new Thread(AI);
            Bot.start();
            return true;
            
        }
        else {
            System.out.println("Bot AI initialization failure");
            return false;
        }
        
    }
    
    public void AIStop(){
        
        AI.requestStop();
        mapInitialized=false;
        sGeneratorInitialized=false;
        playerInitialized=false;
        
    }
    
    
    public void setStringGenerator(StringGenerator sg){ sGeneratorInitialized=AI.setStringGenerator(sg); }          //1
        
            
        
   
    public void updateBrickHealth(LinkedList <Brick> brickWalls){                           //must be reset with every general update
        
        Iterator <Brick>i=brickWalls.iterator();
        while(i.hasNext()){            
            Brick brick=i.next();            
            //map[brick.getxLocation()][brick.getyLocation()].setWeight(5-brick.getHealth());        
            Node node=new Node(brick.getxLocation(),brick.getyLocation());
            node.setWeight(5-brick.getHealth());   
            AI.initMapBlock(node);//need to verify
        }
        
    }
    public void initializeMap(LinkedList <Stone> stoneWalls,LinkedList <Water> water,LinkedList <Brick> brickWalls){            //1
        
        Iterator <Water> i=water.iterator();
        while(i.hasNext()){            
            Water block=i.next();
            Node node=new Node(block.getxLocation(),block.getyLocation());
            node.setWeight(1100);
            AI.initMapBlock(node);
            //map[block.getxLocation()][block.getyLocation()].setWeight(1000);
            
                  
        }
        Iterator <Stone> j=stoneWalls.iterator();
        while(j.hasNext()){            
            Stone block=j.next();
            Node node=new Node(block.getxLocation(),block.getyLocation());
            node.setWeight(1200);
            AI.initMapBlock(node);
            //map[block.getxLocation()][block.getyLocation()].setWeight(1000);
                  
        }
        Iterator <Brick> k=brickWalls.iterator();
        while(k.hasNext()){            
            Brick block=k.next(); 
            Node node=new Node(block.getxLocation(),block.getyLocation());
            node.setWeight(5);
            AI.initMapBlock(node);
            //map[block.getxLocation()][block.getyLocation()].setWeight(5);
                 
        }
        
        mapInitialized=AI.mapInitialized();
        
        
    }
    
    public void initMyPlayer(Player me){                                                        //initialize my player
        playerInitialized=AI.initPlayer(me.getPlayerX(),me.getPlayerY(),me.getPlayerDir(),me.getHealth(),me.getIndex());        
    }
    
    public void reSetPlayer(Player me){                                                         //must reset with every general update
        
        AI.resetPlayer(me.getPlayerX(),me.getPlayerY(),me.getPlayerDir(),me.getHealth());
        
        
        
    }
    
    
    //put references to the Maps coin pile list
    public void coinPilesSpawned(LinkedList<CoinPack> cpack){ AI.setCpiles(cpack); }    
    //put references to the Maps life pack list
    public void lifePacksSpawned(LinkedList<LifePack> hPack){AI.setLPacks(hPack); }
            
    public void setOtherBots(LinkedList <Player> bots){                                 //must be reset with every general update
        
        
        //reset the weights of previously added bots
        reSetMapBots();  

        
        
        
        ArrayList <BotInfo>vec=new ArrayList();
        Iterator <Player>i= bots.iterator();
        while(i.hasNext()){
            
            Player players=i.next();
            BotInfo bot=new BotInfo(players.getPlayerX(),players.getPlayerY(),players.getPlayerDir(),players.getHealth(),players.getIndex());
            Node d=new Node(bot.getPlayerX(),bot.getPlayerY());
            d.setWeight(1000);
            AI.initMapBlock(d);
            vec.add(bot);
            
            
            
           // System.out.print("; "+d.getX()+","+d.getY());
            mapBots.addFirst(d);
            
        }
        setMapBots=true;
       // System.out.println("changed bots: 1000");
        AI.setOtherBots(vec);
        
    }
    
    
    private void reSetMapBots(){
        
            if(setMapBots){
            while(!mapBots.isEmpty()){
                
                Node p=mapBots.removeFirst();
                //Node n=new Node(p.getPlayerX(),p.getPlayerY());
                p.setWeight(1);
                AI.initMapBlock(p);
               // System.out.print("; "+p.getX()+","+p.getY());
            }
           // System.out.println("resetting bots: 1");
            setMapBots=false;
        }
        
               
        
        
        
    }
    
    
}
