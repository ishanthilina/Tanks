/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author ishan
 */
public class Bullet {

    //stores the initial location of the bullet
    private float initFloatX;
    private float initFloatY;

    private int initIntX;
    private int initIntY;

    //direction of the bullet
    private int direction;

    //stores the map drawn place
    private float mapX;
    private float mapY;

    //stores the tile size
    private float tileSize;

    //bullet speed
    private int speed;
    private float speedPerMilliS;

    //to keep track of the time that the bullet was created
    private long iniTime;

    //tells whether the bullet has hit an object or not
    private boolean destroyed;

    public Bullet(int intX, int intY, int dir) {

        //map drawn locations
        mapX=0f;
        mapY=0f;

        //tile size
        tileSize=35f;

        //set the bullet location in float
        initFloatX=intX*tileSize+mapX;
        initFloatY=intY*tileSize+mapY;

        initIntX=intX;
        initIntY=intY;

        //set the location of the bullet
        this.direction=dir;

        //speed of the bullet(tiles per second)
        speed=5;

        //speed of the bullet(floats per milli second)
        speedPerMilliS=(speed*tileSize)/1000;


        //bullet created time
        iniTime=System.currentTimeMillis();

        destroyed=false;
        
        //sounds
        try {
            
            new Sound("data"+System.getProperty("file.separator") +"sounds"+System.getProperty("file.separator") +"shoot.wav").play();
            
        } catch (SlickException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    /**
     * return the current position of the bullet according to the direction
     * @return
     */
    public float getFloatX() {

        float output=0f;

        //if the bullet is headin north
        if(direction==0){
            output= initFloatX;
        }

        //if the bullet is heading East
         else if(direction==1){
             output= initFloatX-speedPerMilliS*(iniTime-System.currentTimeMillis());

         }

        //if the bullet is heading south
         else if(direction==2){
             output= initFloatX;

         }

        //if the bullet is heading West
         else if(direction==3){
             output= initFloatX+speedPerMilliS*(iniTime-System.currentTimeMillis());

         }

        return output;
        
    }

    /**
     * Return the current tile x coordinate
     */

    public int getX(){

        int output=0;

        //if the bullet is headin north
        if(direction==0){
            output= (int) (initFloatX / tileSize);
        }

        //if the bullet is heading East
         else if(direction==1){
             output= (int) ((initFloatX - speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize);

         }

        //if the bullet is heading south
         else if(direction==2){
             output= (int) (initFloatX / tileSize);

         }

        //if the bullet is heading West
         else if(direction==3){
             output= (int) ((initFloatX + speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize);

         }

        return output;

    }


    /**
     * return the current position of the bullet according to the direction
     * @return
     */
    public float getFloatY() {
        float output=0f;

        //if the bullet is headin north
        if(direction==0){
            output= initFloatY+speedPerMilliS*(iniTime-System.currentTimeMillis());
        }

        //if the bullet is heading East
         else if(direction==1){
             output= initFloatY;

         }

        //if the bullet is heading south
         else if(direction==2){
             output= initFloatY-speedPerMilliS*(iniTime-System.currentTimeMillis());

         }

        //if the bullet is heading West
         else if(direction==3){
             output= initFloatY;

         }

        return output;
    }

    public int getY(){

        int output=0;

        //if the bullet is headin north
        if(direction==0){
           // System.out.println("dir 0: "+((initY + speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize));
            output= (int) ((initFloatY + speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize);
        }

        //if the bullet is heading East
         else if(direction==1){
             output=  (int) (initFloatY / tileSize);

         }

        //if the bullet is heading south
         else if(direction==2){
            // System.out.println("dir 2: "+((initY + speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize));
             output= (int) ((initFloatY - speedPerMilliS * (iniTime - System.currentTimeMillis())) / tileSize);

         }

        //if the bullet is heading West
         else if(direction==3){
             output=  (int) (initFloatY / tileSize);

         }

        return output;
    }

   

    public int getDirection() {
        return direction;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * used to get the origin X tile of the bullet
     */

    public int getOriginX(){
        return initIntX;

    }

    /**
     * used to get the origin Y tile of the bullet
     */
    public int getOriginY(){
        return initIntY;
    }

    



}
