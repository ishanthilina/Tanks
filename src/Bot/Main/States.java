/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Main;

import Map.CoinPack;
import Map.LifePack;
import java.util.ArrayList;
import java.util.LinkedList;




/**
 *
 * @author rajith
 */
public class States {
    
    private BotInfo me;
    private BarricadeMap map;
    private ArrayList <BotInfo> otherBots;
    private boolean playerSet;
    private LinkedList <CoinPack> coinPiles;
    private LinkedList <LifePack> healthPacks;
    
    private int Mode;                                                       /**
                                                                             * idle--------------------- 0
                                                                             * Shooting----------------- 1
                                                                             * collecting coins--------- 2
                                                                             * collecting health packs-- 3 
                                                                             */ 


    public States(BarricadeMap map){
        
        this.map=map;        
        playerSet=false;
        Mode=0;
        
    }
    
    public void initPlayer(int playerX,int playerY,int playerDir,int playerHealth,int index){
        
        me=new BotInfo(playerX,playerY,playerDir,playerHealth,index);
        
    }
   
    public void updatePlayer(int x,int y,int dir,int health){
        
        me.setPlayerX(x);
        me.setPlayerY(y);
        me.setPlayerDir(dir);
        me.setHealth(health);
      
        
    }
    
    /**
     * @param playerReset the playerReset to set
     */
    public void setPlayerReset(boolean playerSet) { this.playerSet = playerSet; }

    /**
     * @return the me
     */
    public BotInfo getMe() { return me; }


    /**
     * @return the map
     */
    public BarricadeMap getMap() { return map; }

    /**
     * @return the otherBots
     */
    public ArrayList <BotInfo> getOtherBots() { return otherBots; }

   
    /**
     * @return the Mode
     */
    public int getMode() {  return Mode; }

    /**
     * @param Mode the Mode to set
     */
    public void setMode(int Mode) {  this.Mode = Mode; }

    /**
     * @return the playerReset
     */
//    public boolean isPlayerReset() { return playerSet; }
    
    public boolean isPlayerReset() { return playerSet; }


    /**
     * @param otherBots the otherBots to set
     */
    public void setOtherBots(ArrayList <BotInfo> otherBots) {
        this.otherBots = otherBots;
    }

    /**
     * @return the coinPiles
     */
    public LinkedList <CoinPack> getCoinPiles() {
        return coinPiles;
    }

    /**
     * @param coinPiles the coinPiles to set
     */
    public void setCoinPiles(LinkedList <CoinPack> coinPiles) {
        this.coinPiles = coinPiles;
    }

    /**
     * @return the healthPacks
     */
    public LinkedList <LifePack> getHealthPacks() {
        return healthPacks;
    }

    /**
     * @param healthPacks the healthPacks to set
     */
    public void setHealthPacks(LinkedList <LifePack> healthPacks) {
        this.healthPacks = healthPacks;
    }
    
  
 
    
    
}
