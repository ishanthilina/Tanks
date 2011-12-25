/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Main;


import Bot.PathFinder.Node;
import Controlling.StringGenerator;
import Map.CoinPack;
import Map.LifePack;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 *Keeps track of the AI states and Nodes
 * @author rajith
 */
public class Brain implements Runnable{
    
    
    private boolean stop;
    private States currentStates;
    private BarricadeMap map;
    private Commander commander;
    private boolean stateSet;   
    private boolean commanderStateSet;
    private boolean stringGenSet;
    
    public Brain(){
        
        stop=false;
        stateSet=false;
        map=new BarricadeMap(10);        
        commander=new Commander(map);
        stringGenSet=false;
        commanderStateSet=false;
    }

    @Override
    public void run() {
        
       commanderStart();
        while(!stop){
            
            //Switch modes
            
            if(((currentStates.getOtherBots()==null||currentStates.getOtherBots().isEmpty()))&&((currentStates.getCoinPiles()).isEmpty())&&((currentStates.getHealthPacks()).isEmpty())){
              currentStates.setMode(0);
            }
            else if(((currentStates.getOtherBots()!=null)&&(!(currentStates.getOtherBots().isEmpty())))&&((currentStates.getCoinPiles()).isEmpty())&&((currentStates.getHealthPacks()).isEmpty())){
                currentStates.setMode(1);
            }
            else if((((currentStates.getMe()).getHealth())<80)&&(!((currentStates.getHealthPacks()).isEmpty()))){
                currentStates.setMode(3);
            }
            else if(!(currentStates.getCoinPiles().isEmpty())){
                currentStates.setMode(2);
            }else {
                currentStates.setMode(3);
                
                
            }
           

            
        }
        commander.commanderStop();
    }
    
  
    private void commanderStart(){
        if(stringGenSet&&commanderStateSet){
            
            //System.out.println("called");
            Thread commanderThread=new Thread(commander);
            commanderThread.start();
            
        }
        
    }
    
    
    //update the nodes
  public void initMapBlock(Node node){ map.setBlocks(node);}
  
  //initialize the map
  public boolean mapInitialized(){map.mapInitialized(); return map.ismapInitialized();}
  
  public void requestStop() { stop = true; }
  
  public boolean initPlayer(int playerX,int playerY,int playerDir,int playerHealth,int index){
      
       currentStates=new States(map);
       currentStates.initPlayer(playerX,playerY,playerDir,playerHealth,index);
       //player=new BotInfo(playerX,playerY,playerDir,playerHealth,index); 
     
      stateSet=true;
      setCommanderStates();
      //currentStates.setPlayer(player);      
      return true;
  }
      

  public boolean setStringGenerator(StringGenerator sGen){
      commander.setSGenerator(sGen);      
      stringGenSet=true;
      return true;
      
  } 
  
  public void setCommanderStates(){
      if(stateSet){
         
      commander.setCurrentStates(this.currentStates);
      commanderStateSet=true;
      }else {
          System.out.println("commander initialising states failed");
      }
      
  }
    
  //reset the player in every update
  public void resetPlayer(int x,int y,int dir,int health){
        
      
      currentStates.updatePlayer(x,y,dir,health);
      
      ///player.setPlayerX(x);
      ///player.setPlayerX(y);
      //player.setPlayerDir(dir);
     // player.setHealth(health);
      
      //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
      //System.out.println("Time       "+System.currentTimeMillis());
      currentStates.setPlayerReset(true);            //After issuing command this must be set to false
      //setCommanderStates();
      
        
    }
  
    public void setCpiles(LinkedList <CoinPack> cpack){
        currentStates.setCoinPiles(cpack);
        
    }
    public void setLPacks(LinkedList <LifePack> lPack){
        currentStates.setHealthPacks(lPack);
    }
    public void setOtherBots(ArrayList <BotInfo> bots){
        currentStates.setOtherBots(bots);
        
    }
  
}
