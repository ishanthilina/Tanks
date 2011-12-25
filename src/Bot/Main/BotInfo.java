/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Main;

/**
 *
 * @author rajith
 */
public class BotInfo {
    
    
    //stores the data of the player
    private int botX;
    private int botY;
    //direction of the player
    private int botDir;
    //previous direction of the player
    private int prevDir;
    private int health;
    private boolean shot;
    private int coins;
    private int points;
    //index of the player
    private int index;

    public BotInfo(int playerX, int playerY, int playerDir, int health, int index) {
        
        this.botX = playerX;
        this.botY = playerY;
        this.botDir = playerDir;
        this.prevDir=playerDir;
        this.health = health;
        this.index = index;

 
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean hasShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPlayerDir() {
        return botDir;
    }

    public void setPlayerDir(int playerDir) {
        prevDir=this.botDir;
        this.botDir = playerDir;

    }

    public int getPrevDir() {
        return prevDir;
    }

    

    public int getPlayerX() {
        return botX;
    }

    public void setPlayerX(int playerX) {
        this.botX = playerX;
    }

    public int getPlayerY() {
        return botY;
    }

    public void setPlayerY(int playerY) {
        this.botY = playerY;
    }

    public boolean isAlive() {
        return health > 0 ? true : false;
    }


    
}
