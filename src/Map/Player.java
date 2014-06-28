/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

/**
 *
 * @author ishan
 */
public class Player {

    /*
     * Testing animation
     */
    //stores the previous location where the player was drawn
    private float x;
    private float y;
    //check this if player is not drawn at the correct location
    private float drawConstant;

    /*
     *
     *
     */
    //stores the data of the player
    private int playerX;
    private int playerY;
    //direction of the player
    private int playerDir;
    //previous direction of the player
    private int prevDir;
    private int health;
    private boolean shot;
    private int coins;
    private int points;
    //index of the player
    private int index;

    public Player(int playerX, int playerY, int playerDir, int health, int index) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDir = playerDir;
        this.prevDir=playerDir;
        this.health = health;
        this.index = index;

        drawConstant = 35f;
        x = playerX * drawConstant;
        y = playerY * drawConstant;


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
        return playerDir;
    }

    public void setPlayerDir(int playerDir) {
        prevDir=this.playerDir;
        this.playerDir = playerDir;

    }

    public int getPrevDir() {
        return prevDir;
    }

    

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public boolean isAlive() {
        return health > 0 ? true : false;
    }

    /**
     * the previous x coordinate where the player was drawn
     * @return
     */
    public float getEarlierX() {
        return x;
    }

    /**
     * the previous y coordinate where the player was drawn
     * @return
     */
    public float getEarlierY() {
        return y;
    }

    /**
     * Set the x,y coordinate where the player was drawn
     * @param x
     * @param y
     */
    public void setDrawnX(float x) {
        this.x = x;


    }

    public void setDrawnY(float y) {
        this.y = y;
    }
}
