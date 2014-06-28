/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author ishan
 */
public class Map {

    //My player
    private Player myPlayer;
    //A list to store bricks
    LinkedList<Brick> bricks;
    //A list to store other players
    LinkedList<Player> contesters;
    //A list to store life packs
    LinkedList<LifePack> lifePacks;
    //A list to store the coin packs
    LinkedList<CoinPack> coinPacks;
    //A list to store stones
    LinkedList<Stone> stones;
    //A list to store sea
    LinkedList<Water> sea;
    //A list to store the bullets
    LinkedList<Bullet> bullets;

    //to find out whether the game has started
    public Map() {

        //create the bricks linked list
        bricks = new LinkedList<Brick>();

        //create the other players linked list
        contesters = new LinkedList<Player>();

        lifePacks = new LinkedList<LifePack>();

        coinPacks = new LinkedList<CoinPack>();

        sea = new LinkedList<Water>();

        stones = new LinkedList<Stone>();

        bullets=new LinkedList<Bullet>();




    }

    //////////////////////////////////////////
    //////////////////////////////////////////
    /**
     * Set the stone positions at the beginning
     *
     */
    public void setStone(Stone stone) {
        stones.add(stone);
    }

    /**
     * Set the water positions at the beginning
     *
     */
    public void setWater(Water water) {
        sea.add(water);
    }

    /**
     * Set the brick positions at the beginning
     * 
     */
    public void setBrick(Brick brick) {
        bricks.add(brick);
    }

    /**
     * Returns the list of stones in the map
     *
     */
    public LinkedList<Stone> getStones() {

        return (LinkedList<Stone>) stones.clone();
    }

    /**
     * Returns the list of water in the map
     *
     */
    public LinkedList<Water> getWater() {

        return (LinkedList<Water>) sea.clone();
    }

    /**
     * Returns the list of bricks in the map
     *
     */
    public LinkedList<Brick> getBricks() {

        return (LinkedList<Brick>) bricks.clone();
    }

    /**
     * Sets the health of bricks
     *
     */
    public void setBrickHealth(int healthMatrix[][]) {

        ListIterator iterator = bricks.listIterator();

        try {

            Brick brick = (Brick) iterator.next();

            while ((brick) != null) {
                //get the relevant health value
                brick.setHealth(healthMatrix[brick.getxLocation()][brick.getyLocation()]);


                if (iterator.hasNext()) {
                    brick = (Brick) iterator.next();
                } else {

                    break;
                }

            }


        } catch (NoSuchElementException ex) {
            //System.out.println("Brick list is empty. Can't set the Health");
        }

        /*
         * Bullet cleanup was added here because this method gets called at
         * every global update.
         */

         ListIterator bullIt=bullets.listIterator();
         Bullet bullet;

          try {

              if (bullIt.hasNext()) {
                bullet = (Bullet) bullIt.next();

                while (bullet != null) {

                    //if the bullet is out of the x coodinates
                    
                    if(bullet.getX()>20 || bullet.getX()<0 || bullet.getY()>20 ||bullet.getY()<0){
                       // System.out.println("bullet deleted @"+bullet.getX()+", "+bullet.getY());
                        bullIt.remove();

                    }



                    if (bullIt.hasNext()) {
                        bullet = (Bullet) bullIt.next();
                    } else {

                        break;
                    }
                }

              }

          } catch (ConcurrentModificationException ex) {
            System.out.println("Exception in bullet iterator :'(");
        }




    }

    /**
     * Set other players
     *
     */
    public void setContesters(Player player) {

        contesters.add(player);
    }

    /**
     * Updates a contestants data.
     * 
     * @param player
     */
    public void updateContestant(Player player) {
        int index = player.getIndex();

        ListIterator iter = contesters.listIterator();


        while (iter.hasNext()) {
            Player savedPlayer = (Player) iter.next();

            //if this is the matching contestant
            if (index == savedPlayer.getIndex()) {



                savedPlayer.setCoins(player.getCoins());
                savedPlayer.setHealth(player.getHealth());
                savedPlayer.setPlayerDir(player.getPlayerDir());
                savedPlayer.setPlayerX(player.getPlayerX());
                savedPlayer.setPlayerY(player.getPlayerY());
                savedPlayer.setPoints(player.getPoints());
                savedPlayer.setShot(player.hasShot());

                //checks whether the player takes a life pack
                ListIterator li = lifePacks.listIterator();

                while (li.hasNext()) {
                    LifePack lp = (LifePack) li.next();

                    //if the cordinates are equal and the life pack is still available and the player is alive
                    if ((lp.getxLocation() == savedPlayer.getPlayerX()) && (lp.getyLocation() == savedPlayer.getPlayerY()) && lp.isAvailable() && savedPlayer.isAlive()) {
                        lp.markAsUnavailable();
                    }
                }

                //checks whether the player takes a coin pack
                ListIterator cpLi = coinPacks.listIterator();

                while (cpLi.hasNext()) {
                    CoinPack cp = (CoinPack) cpLi.next();

                    //if the cordinates are equal and the coin pack is still available and the player is alive
                    if ((cp.getxLocation() == savedPlayer.getPlayerX()) && (cp.getyLocation() == savedPlayer.getPlayerY()) && cp.isAvailable() && savedPlayer.isAlive()) {
                        cp.markAsUnavailable();

                    }
                }
                
                //if the player has shot a bullet
        if(player.hasShot()){

            Bullet bullet=new Bullet(player.getPlayerX(), player.getPlayerY(), player.getPlayerDir());
            bullets.add(bullet);
        }

            }
        }





    }

    public void updateMyPlayer(Player player) {


        myPlayer.setCoins(player.getCoins());
        myPlayer.setHealth(player.getHealth());
        myPlayer.setPlayerDir(player.getPlayerDir());
        myPlayer.setPlayerX(player.getPlayerX());
        myPlayer.setPlayerY(player.getPlayerY());
        myPlayer.setPoints(player.getPoints());
        myPlayer.setShot(player.hasShot());

        //checks whether the player takes a life pack
        ListIterator li = lifePacks.listIterator();

        while (li.hasNext()) {
            LifePack lp = (LifePack) li.next();

            //if the cordinates are equal and the lp is still available
            if ((lp.getxLocation() == myPlayer.getPlayerX()) && (lp.getyLocation() == myPlayer.getPlayerY()) && lp.isAvailable()) {
                lp.markAsUnavailable();
            }
        }

        //checks whether the player takes a coin pack
        ListIterator cpLi = coinPacks.listIterator();

        while (cpLi.hasNext()) {
            CoinPack cp = (CoinPack) cpLi.next();

            //if the cordinates are equal and the coin pack is still available
            if ((cp.getxLocation() == myPlayer.getPlayerX()) && (cp.getyLocation() == myPlayer.getPlayerY()) && cp.isAvailable()) {
                cp.markAsUnavailable();
            }
        }
        
        

        //if the player has shot a bullet
        if(player.hasShot()){

            Bullet bullet=new Bullet(player.getPlayerX(), player.getPlayerY(), player.getPlayerDir());
            bullets.add(bullet);
        }

    }

    /**Used to at my player to the map.
     *
     * @param p
     */
    public void addMyPlayer(Player p) {
        this.myPlayer = p;

    }

    /**
     * To get my player
     */
    public Player getMyPlayer() {
        return myPlayer;
    }

    /**
     *
     * gets a copy of the contestants list
     *
     * IS IT ESSENTIAL TO CLONE THIS? NO ELEMENT WILL BE ADDED OR
     * REMOVED FROM THE LINKED LIST AFTER THE START MESSAGE.
     */
    public LinkedList<Player> getContestants() {

        return (LinkedList<Player>) contesters.clone();
    }

    /**
     * Adds a life pack to the map.
     *
     * Also cleans the expired life packs.
     *
     * @note: Possibility of an iterator exception.
     */
    public void addLifePack(LifePack pack) {

////        Removes expired life packs

        ListIterator iter = lifePacks.listIterator();

        while (iter.hasNext()) {
            LifePack lp = (LifePack) iter.next();

            if (!lp.isAvailable()) {
                iter.remove();
            }
        }
////


        lifePacks.add(pack);

    }

    /**
     *
     * Returns the set of life packs
     */
    public LinkedList<LifePack> getLifePacks() {
        return (LinkedList<LifePack>) lifePacks.clone();
    }

    /**
     * adds coin packs to the linked list.
     *
     * @note: Possibility of an iterator exception.
     *
     * @param coinPack
     */
    public void addCoinPack(CoinPack coinPack) {

        ListIterator iter = coinPacks.listIterator();

        //remove expired/taken coin packs
        while (iter.hasNext()) {
            CoinPack cp = (CoinPack) iter.next();

            if (!cp.isAvailable()) {
                iter.remove();
            }
        }

        coinPacks.add(coinPack);


    }

    /**
     * returns all the coin packs
     * @return
     */
    public LinkedList<CoinPack> getCoinPacks() {
        return (LinkedList<CoinPack>) coinPacks.clone();
    }

    /**
     * returns the bullets
     */

    public LinkedList<Bullet> getBullets(){
        return bullets;
    }
}
