/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Map.Brick;
import Map.Bullet;
import Map.CoinPack;
import Map.LifePack;
import Map.Map;
import Map.Player;
import Map.Stone;
import Map.Water;
import java.awt.Font;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


/**
 *
 * @author ishan
 */
public class ElementPainter {

    /*
     * For animation testing
     */
    private int delta;

    /*
     *
     */
    private Map map;
    //images of the map
    private Image brickImage;
    private Image stoneImage;
    private Image waterImage;
    private Image lpImage;
    private Image cpImage;
    private Image bulletImage;
    //players images
    private Image myPlayerImage;
    private Image up;
    private Image down;
    private Image left;
    private Image right;

    //enemy images
    private Image enemy1;
    private Image enemy2;
    private Image enemy3;
    private Image enemy4;
    private Image enemy5;

    //sets the tile size
    private int tileSize = 35;
    private Player myPlayer;
    //sprite animation details
    Image[] movementUp;
    Image[] movementDown;
    Image[] movementLeft;
    Image[] movementRight;
    int[] duration;
    //to draw graphics ( like strings)
    private Graphics graphics;
    
    //setup fonts
    Font fTitle ;
    TrueTypeFont ttfTitle ;
    
    Font fScore;
    TrueTypeFont ttfScore;
    
    
    int angle;
    int rotatingSpeed;

    public ElementPainter(Map map, Image brickImage, Image stoneImage, Image waterImage, Player myPlayer, Image lpImage, Image cpImage,Image bullet) {
        this.map = map;
        this.brickImage = brickImage;
        this.stoneImage = stoneImage;
        this.waterImage = waterImage;
        this.myPlayer = myPlayer;
        //this.myPlayerImage = myPlayerImg;
        this.lpImage = lpImage;
        this.cpImage = cpImage;
        this.bulletImage=bullet;

        /*
         * For animation
         */

        delta = 0;

        /*
         *
         */

        angle=0;
        rotatingSpeed=5;
        
        //setup fonts
        
        //title font
        fTitle = new Font("Times New Roman", Font.BOLD, 24);
        ttfTitle = new TrueTypeFont(fTitle, false);


        //score font
        fScore=new Font("Veradana", Font.PLAIN, 15);
        ttfScore=new TrueTypeFont(fScore, false);


    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    //draws the map
    public void draw(int delta, Graphics graphics) {


        /*
         * for animation
         */
        this.delta = delta;
        /*
         *
         */

        this.graphics = graphics;
        
        graphics.setColor(Color.white);
        
        


        /*
         * create an obstacle matrix
         *
         * This will be used later to check obstacles when drawing the bullet
         */
        int[][] obsMat=new int[20][20];


        //Draw bricks
        LinkedList<Brick> bricks = map.getBricks();

        ListIterator iterator = bricks.listIterator();


        if (iterator.hasNext()) {

            Brick brick = (Brick) iterator.next();


            while ((brick) != null) {

                if (!brick.isDestroyed()) {
                    graphics.setColor(Color.black);
                    drawBrick(brick);
                    graphics.setColor(Color.white);
                    //mark the location as occupied
                    obsMat[brick.getxLocation()][brick.getyLocation()]=1;
                }


                if (iterator.hasNext()) {
                    brick = (Brick) iterator.next();

                } else {

                    break;
                }


            }


        }

        //Draw stones
        LinkedList<Stone> stones = map.getStones();

        ListIterator stoneIter = stones.listIterator();


        if (stoneIter.hasNext()) {

            Stone stone = (Stone) stoneIter.next();


            while ((stone) != null) {

                drawStone(stone);
                
                //mark the location as occupied
                obsMat[stone.getxLocation()][stone.getyLocation()]=1;


                if (stoneIter.hasNext()) {
                    stone = (Stone) stoneIter.next();

                } else {

                    break;
                }


            }


        }

        //Draw water
        LinkedList<Water> sea = map.getWater();

        ListIterator waterIter = sea.listIterator();


        if (waterIter.hasNext()) {

            Water water = (Water) waterIter.next();


            while ((water) != null) {

                drawWater(water);


                if (waterIter.hasNext()) {
                    water = (Water) waterIter.next();

                } else {

                    break;
                }


            }


        }




        //Draw life packs

        LinkedList<LifePack> lifePacks = map.getLifePacks();

        ListIterator lpIter = lifePacks.listIterator();

        LifePack lifePack;

        if (lpIter.hasNext()) {
            lifePack = (LifePack) lpIter.next();

            while ((lifePack) != null) {

                if (lifePack.isAvailable()) {
                    drawLP(lifePack);
                }


                if (lpIter.hasNext()) {
                    lifePack = (LifePack) lpIter.next();

                } else {

                    break;
                }


            }


        }

        //draw coin packs
        LinkedList<CoinPack> coinPacks = map.getCoinPacks();

        ListIterator iter = coinPacks.listIterator();

        CoinPack coinPack;

        if (iter.hasNext()) {
            coinPack = (CoinPack) iter.next();

            while (coinPack != null) {

                if (coinPack.isAvailable()) {
                    drawCoinPack(coinPack);
                }

                if (iter.hasNext()) {
                    coinPack = (CoinPack) iter.next();
                } else {
                    break;
                }

            }
        }


        //Draw players
        LinkedList<Player> players = map.getContestants();

        ListIterator playerIter = players.listIterator();

        Player player;
        
        int index=0;

        if (playerIter.hasNext()) {
            player = (Player) playerIter.next();

            while ((player) != null) {
                
               

                if (player.isAlive()) {
                    //mark the location as occupied
                    obsMat[player.getPlayerX()][player.getPlayerY()]=1;
                    drawEnemies(player);
                }




                if (playerIter.hasNext()) {
                    player = (Player) playerIter.next();

                } else {

                    break;
                }


            }


        }


        myPlayer = map.getMyPlayer();
        //Draw my player
        if (myPlayer != null) {

            //draw only if the player is alive
            if (myPlayer.isAlive()) {

                //mark the location as occupied
                obsMat[myPlayer.getPlayerX()][myPlayer.getPlayerY()]=1;
                drawMyPlayer(myPlayer);
            }

        }

        /*
         * Draw the bullets
         */

        //Draw players
        LinkedList<Bullet> bullets = map.getBullets();

        ListIterator bulletItr = bullets.listIterator();

        Bullet bullet;

        try {

            if (bulletItr.hasNext()) {
                bullet = (Bullet) bulletItr.next();

                while (bullet != null) {

                    //to keep remove the array out bound exceptions
                    if ((0<=bullet.getX() && bullet.getX()<20) && (0<=bullet.getY()&& bullet.getY()<20)) {
                        
//                        System.out.println("get ::"+bullet.getX()+", "+bullet.getY());
//
//                        System.out.println("getOri:: "+bullet.getOriginX()+","+bullet.getOriginY());

                        //to disallow the shooter to be hit by its own bullet
                        

                            //check for collisions
                            if ((obsMat[bullet.getX()][bullet.getY()]) == 1) {

                               // System.out.println("Same coordinates..!");

                                if(!(bullet.getX()==bullet.getOriginX()) || !(bullet.getY()==bullet.getOriginY())){
                                    //System.out.println("X cordinates  match");
                                    //if(!(bullet.getY()==bullet.getOriginY())){
                                       // System.out.println("y coordintes dsnt macth");
                                        bullet.setDestroyed(true);
                                       // System.out.println("Bullet hit @ " + bullet.getX() + "," + bullet.getY());
                                    //}
                                }

                                //if (!((bullet.getX() != bullet.getOriginX()) && (bullet.getY() != bullet.getOriginY()))) {

//                                bullet.setDestroyed(true);
//                                System.out.println("Bullet hit @ " + bullet.getX() + "," + bullet.getY());
                            //}
                        }

                    }
                    
                    //to stop the bullet from being drawn outside the map
                    if((bullet.getFloatX()>700)||(bullet.getFloatY()>700)){
                        bullet.setDestroyed(true);
                    }

                    

                    //if the bullet has not been destroyed
                    if(!bullet.isDestroyed()){
                        drawBullet(bullet);
                    }
                    

                    if (bulletItr.hasNext()) {
                        bullet = (Bullet) bulletItr.next();
                    } else {

                        break;
                    }
                }



            }

        } catch (ConcurrentModificationException ex) {
            System.out.println("Exception in bullet iterator :'(");
        }

        
        drawSideBar();


    }

    //draws the player
    private void drawMyPlayer(Player Player) {


        /*
         *
         * Rewrite this method to support 5 different players
         */

        /*
         * to support animation
         */

        //to control the speed of the animation
        float speed = 0.03f;

        //to control the error of the animation
        float error = 2f;

        //the true position of the sprite
        float trueX = 0f, trueY = 0f;

        //the current position of the sprite

        float currentX = 0f, currentY = 0f;
        /*
         *
         */


        //checks whether the player exists
        if (Player != null) {
            //System.out.println("1");
            //System.out.println("draw player");

            // myPlayerImage.draw(converter(Player.getPlayerX()), converter(Player.getPlayerY()));




            //set the picture of the sprite
           // myPlayerImage = up;
            //set the direction of the sprite
            if (Player.getPlayerDir() == 0 && (myPlayerImage.getRotation()!=0)) {


                if(Player.getPrevDir()==1){
                    myPlayerImage.setRotation(myPlayerImage.getRotation()-rotatingSpeed);
                }
                 else{

                    myPlayerImage.setRotation(myPlayerImage.getRotation()+rotatingSpeed);
                 }

                //angle+=10;
                

            } else if (Player.getPlayerDir() == 1 && (myPlayerImage.getRotation()!=90)) {

                if(Player.getPrevDir()==2){
                    myPlayerImage.setRotation(myPlayerImage.getRotation()-rotatingSpeed);
                }
                 else{

                    myPlayerImage.setRotation(myPlayerImage.getRotation()+rotatingSpeed);
                 }


                

            } else if (Player.getPlayerDir() == 2 && (myPlayerImage.getRotation()!=180)) {

                if(Player.getPrevDir()==3){
                    myPlayerImage.setRotation(myPlayerImage.getRotation()-rotatingSpeed);
                }
                 else{

                    myPlayerImage.setRotation(myPlayerImage.getRotation()+rotatingSpeed);
                 }


            } else if (Player.getPlayerDir() == 3 && (myPlayerImage.getRotation()!=270)) {

                if(Player.getPrevDir()==0){
                    myPlayerImage.setRotation(myPlayerImage.getRotation()-rotatingSpeed);
                }
                 else{

                    myPlayerImage.setRotation(myPlayerImage.getRotation()+rotatingSpeed);
                 }

            }
//////
//////            myPlayerImage.draw(playerDataConverter(Player.getPlayerX()), playerDataConverter(Player.getPlayerY()));

            //set the true location of the player
            trueX = playerDataConverter(Player.getPlayerX());
            trueY = playerDataConverter(Player.getPlayerY());

            /*get the earlier location of the player
             * (The location that the sprite was drawn)
             */

            currentX = Player.getEarlierX();
            currentY = Player.getEarlierY();

            //if the player has gone up
            if (trueY < currentY - error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;
                myPlayerImage.setRotation(0);
                currentY -= delta * speed;
                Player.setDrawnY(currentY);
                // System.out.println("UP");

            } //if the player has gone down
            else if (trueY > currentY + error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                myPlayerImage.setRotation(180);
                currentY += delta * speed;
                Player.setDrawnY(currentY);
                // System.out.println("DOWN");

            } //if the player has gone right
            else if (trueX > currentX + error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                myPlayerImage.setRotation(90);
                currentX += delta * speed;
                //myPlayerImage.setRotation(angle);
                Player.setDrawnX(currentX);
                // System.out.println("RIGHT:"+trueX+","+currentX);
                // System.out.println("RIGHT");

            } //if the player has gone left
            else if (trueX < currentX - error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                myPlayerImage.setRotation(270);
                currentX -= delta * speed;
                Player.setDrawnX(currentX);
                // System.out.println("LEFT:"+trueX+","+currentX);
                // System.out.println("LEFT");

            }

            myPlayerImage.draw(currentX, currentY);
            graphics.drawString(Integer.toString(myPlayer.getIndex()), currentX, currentY);

            //System.out.println("DRAW");
           // System.out.println("rotation::" +myPlayerImage.getRotation());

        }

    }

    /**
     * draw enemies
     */

    private void drawEnemies(Player Player) {


        /*
         *
         * Rewrite this method to support 5 different players
         */

        /*
         * to support animation
         */

        //to control the speed of the animation
        float speed = 0.03f;

        //to control the error of the animation
        float error = 2f;

        //the true position of the sprite
        float trueX = 0f, trueY = 0f;

        //the current position of the sprite

        float currentX = 0f, currentY = 0f;
        /*
         *
         */

        Image enemyImg=enemy1;


        //checks whether the player exists
        if (Player != null) {
            //System.out.println("1");
            //System.out.println("draw player");

            // myPlayerImage.draw(converter(Player.getPlayerX()), converter(Player.getPlayerY()));


            if (Player.getIndex() == 0) {
                enemyImg = enemy1;
            } else if (Player.getIndex() == 1) {
                enemyImg = enemy2;
            } else if (Player.getIndex() == 2) {
                enemyImg = enemy3;

            } else if (Player.getIndex() == 3) {
                enemyImg = enemy4;
            } else if (Player.getIndex() == 4) {

                enemyImg = enemy5;
            }



            //set the picture of the sprite
           // myPlayerImage = up;
            //set the direction of the sprite
            if (Player.getPlayerDir() == 0 && (enemyImg.getRotation()!=0)) {


                if(Player.getPrevDir()==1){
                    enemyImg.setRotation(enemyImg.getRotation()-rotatingSpeed);
                }
                 else{

                    enemyImg.setRotation(enemyImg.getRotation()+rotatingSpeed);
                 }

                //angle+=10;


            } else if (Player.getPlayerDir() == 1 && (enemyImg.getRotation()!=90)) {

                if(Player.getPrevDir()==2){
                    enemyImg.setRotation(enemyImg.getRotation()-rotatingSpeed);
                }
                 else{

                    enemyImg.setRotation(enemyImg.getRotation()+rotatingSpeed);
                 }




            } else if (Player.getPlayerDir() == 2 && (enemyImg.getRotation()!=180)) {

                if(Player.getPrevDir()==3){
                    enemyImg.setRotation(enemyImg.getRotation()-rotatingSpeed);
                }
                 else{

                    enemyImg.setRotation(enemyImg.getRotation()+rotatingSpeed);
                 }


            } else if (Player.getPlayerDir() == 3 && (enemyImg.getRotation()!=270)) {

                if(Player.getPrevDir()==0){
                    enemyImg.setRotation(enemyImg.getRotation()-rotatingSpeed);
                }
                 else{

                    enemyImg.setRotation(enemyImg.getRotation()+rotatingSpeed);
                 }

            }
//////
//////            myPlayerImage.draw(playerDataConverter(Player.getPlayerX()), playerDataConverter(Player.getPlayerY()));

            //set the true location of the player
            trueX = playerDataConverter(Player.getPlayerX())+10f;
            trueY = playerDataConverter(Player.getPlayerY())+10f;

            /*get the earlier location of the player
             * (The location that the sprite was drawn)
             */

            currentX = Player.getEarlierX();
            currentY = Player.getEarlierY();

            //if the player has gone up
            if (trueY < currentY - error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;
                enemyImg.setRotation(0);
                currentY -= delta * speed;
                Player.setDrawnY(currentY);
                // System.out.println("UP");

            } //if the player has gone down
            else if (trueY > currentY + error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                enemyImg.setRotation(180);
                currentY += delta * speed;
                Player.setDrawnY(currentY);
                // System.out.println("DOWN");

            } //if the player has gone right
            else if (trueX > currentX + error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                enemyImg.setRotation(90);
                currentX += delta * speed;
                //myPlayerImage.setRotation(angle);
                Player.setDrawnX(currentX);
                // System.out.println("RIGHT:"+trueX+","+currentX);
                // System.out.println("RIGHT");

            } //if the player has gone left
            else if (trueX < currentX - error) {
                //Sprite.update?? :s

                // y-= delta* 0.1f;

                enemyImg.setRotation(270);
                currentX -= delta * speed;
                Player.setDrawnX(currentX);
                // System.out.println("LEFT:"+trueX+","+currentX);
                // System.out.println("LEFT");

            }

            enemyImg.draw(currentX, currentY);
            graphics.drawString(Integer.toString(Player.getIndex()), currentX, currentY);

            //System.out.println("DRAW");
           // System.out.println("rotation::" +myPlayerImage.getRotation());

        }

    }

    /**
     * draws sea in the map
     * @param water
     */
    private void drawWater(Water water) {

        waterImage.draw(converter(water.getxLocation()), converter(water.getyLocation()));
    }

    /**
     * draws life packs in the map
     * @param lp
     */
    private void drawLP(LifePack lp) {

        float x = converter(lp.getxLocation());
        float y = converter(lp.getyLocation());
        lpImage.draw(x + 10, y + 10);
        graphics.setColor(Color.black);
        graphics.drawString("" + lp.timeLeft(), x, y);
        graphics.setColor(Color.white);
    }

    /**
     * draws coin packs in the map
     * @param cp
     */
    private void drawCoinPack(CoinPack cp) {

        float x = converter(cp.getxLocation());
        float y = converter(cp.getyLocation());
        cpImage.draw(x, y);


        graphics.setColor(Color.black);

        graphics.drawString(" " + cp.getAmount(), x, y);


        //graphics.setFont(uFont);

        graphics.drawString("" + cp.timeLeft(), x, y + 10);
        graphics.setColor(Color.white);

    }

    /*
     * draws a brick in the map
     */
    private void drawBrick(Brick brick) {


        float x = converter(brick.getxLocation());
        float y = converter(brick.getyLocation());
        brickImage.draw(x, y);
        graphics.drawString("" + brick.getHealth(), x + 14f, y + 10f);

    }

    private void drawStone(Stone stone) {
        stoneImage.draw(converter(stone.getxLocation()), converter(stone.getyLocation()));

    }

    /**
     * draws bullets
     */
    private void drawBullet(Bullet bullet) {

        if (bullet.getDirection() == 0) {
            bulletImage.setRotation(0);
            bulletImage.draw(bullet.getFloatX(), bullet.getFloatY());
        } else if (bullet.getDirection() == 1) {
            bulletImage.setRotation(90);
            bulletImage.draw(bullet.getFloatX(), bullet.getFloatY());

        } else if (bullet.getDirection() == 2) {
            bulletImage.setRotation(180);
            bulletImage.draw(bullet.getFloatX(), bullet.getFloatY());

        } else if (bullet.getDirection() == 3) {
            bulletImage.setRotation(270);
            bulletImage.draw(bullet.getFloatX(), bullet.getFloatY());

        }

    }
    
    /**
     * Draws the score table on the screen
     * 
     */
    private void drawSideBar(){
        
        int tableX=850;
        int tableY=100;
        int width=50;
        int height=30;
        
        //setup fonts
        
        
        
        

        //graphics.drawString("The GAME", 900, 100);
        
        ttfTitle.drawString(950, 22, "TANKS", Color.yellow) ;
        
        //print table headings
        
        ttfScore.drawString(tableX + (width * 2)-10, tableY-height, "Coins", Color.white);
        
        ttfScore.drawString(tableX + (width * 4)-10, tableY-height, "Health", Color.white);
        
        
        //ttfScore.drawString( 800 + 500/4 * 0, tableY+(myPlayer.getIndex()*width),"Scorpio:   \t\t\t"+myPlayer.getCoins()+"        \t\t\t"+myPlayer.getHealth(), Color.lightGray);
        
        ttfScore.drawString(tableX + width * 0, tableY + (myPlayer.getIndex() * height), "Scorpio", Color.lightGray);
        ttfScore.drawString(tableX + width * 2, tableY + (myPlayer.getIndex() * height), Integer.toString(myPlayer.getCoins()), Color.lightGray);
        ttfScore.drawString(tableX + width * 4, tableY+(myPlayer.getIndex()*height),Integer.toString(myPlayer.getHealth()), Color.lightGray);
       // f1ttf.drawString(800, 300, "Othre font", Color.yellow);
        
        //ff.drawString(600, 300, "TST",Color.green);
        
        
       // TrueTypeFont dd=new TrueTypeFont
        
        /*
         * Draw players details
         */
        
       // graphics.drawString("My player:   \t\t\t"+myPlayer.getCoins()+"   \t\t\t"+myPlayer.getHealth(), 800 + 500/4 * 0, tableY+(myPlayer.getIndex()*20));
        

//        
        //get the players
        LinkedList<Player> enemies = map.getContestants();

        ListIterator playerIter = enemies.listIterator();

        Player player;
        
        

        if (playerIter.hasNext()) {
            player = (Player) playerIter.next();

            while ((player) != null) {
                
               
               //graphics.drawString("Player"+player.getIndex()+":   \t\t\t"+player.getCoins()+"   \t\t\t"+player.getHealth(), 800 + 500/4 * 0, tableY+(player.getIndex()*20));
               ttfScore.drawString( tableX + width*0, tableY+(player.getIndex()*height),"Player"+player.getIndex(), Color.lightGray);
               ttfScore.drawString( tableX + width * 2, tableY+(player.getIndex()*height),Integer.toString(player.getCoins()), Color.lightGray);
               
               if(player.isAlive()){
                   ttfScore.drawString( tableX + width * 4, tableY+(player.getIndex()*height),Integer.toString(player.getHealth()), Color.lightGray);
               }
               if(!player.isAlive()){
                   ttfScore.drawString( tableX + width * 4, tableY+(player.getIndex()*height),"Dead", Color.red);
               }
               
               


                if (playerIter.hasNext()) {
                    player = (Player) playerIter.next();

                } else {

                    break;
                }


            }


        }
//        
//        players.put(map.getMyPlayer().getCoins(),map.getMyPlayer());
//        System.out.println(players);
//        for(int key : sorted_players.keySet()){
////            graphics.drawString("2", 800 + 50/4 * 0, 22);
//            System.out.println("test");
//            Player p=sorted_players.get(key);
//            graphics.drawString("player"+p.getIndex()+"\t\t"+p.getCoins(), 800 + 500/4 * 0, 22);
//        }
//        
//        for(int index=0;index< allPlayers.length;index++){
//            players.put(allPlayers[index],allPlayers[index].getCoins());
//            
//        }
//        
//        //draw the player scores
//        for(int index=0;index< allPlayers.length;index++){
//            
//            graphics.drawString("player"+allPlayers[index].getIndex(), 800 + 500/4 * 0, 22);
//            
//        }
//        
        
    }

    /**
     * converts int values to float
     * @param value
     * @return
     */
    private float converter(int value) {

        return (float) value * tileSize;

    }

    /*
     * convert int values to float (for players)
     */
    private float playerDataConverter(int value) {
        return (float) value * tileSize - 8f;

    }


    /*
     *
     * Should modify the below two methods to add multiplayer support
     */
    public void setPlayerImages(Image up, Image down, Image left, Image right) {

        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;

    }

    public void setMyPlayerImage(Image myPlayerImage) {
        this.myPlayerImage = myPlayerImage;
    }

    public void setEnemies(Image enemy1,Image enemy2,Image enemy3,Image enemy4,Image enemy5 ){
        this.enemy1=enemy1;
        this.enemy2=enemy2;
        this.enemy3=enemy3;
        this.enemy4=enemy4;
        this.enemy5=enemy5;
    }
}
