/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import Controlling.StringDecoder;
import Controlling.StringGenerator;
import GUI.ElementPainter;
import Map.Map;
import Map.Player;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author ishan
 */
public class Play extends BasicGameState {

    /** The ID given to this state */
    public static final int id = 2;
//    private Receiver receiver;
    private StringGenerator generator;
    private StringDecoder decoder;
    //map
    private Map map;
    private TiledMap grassMap;
    //stores map position
    private int mapX;
    private int mapY;
    private ElementPainter painter;
    //images of the map
    private Image brickImage;
    private Image stoneImage;
    private Image waterImage;
    private Image lpImage;
    private Image cpImage;
    private Image bulletImage;
    //my player
    private Player myPlayer;
    private Image myPlayerImg;
    //enemy images
    private Image enemy1;
    private Image enemy2;
    private Image enemy3;
    private Image enemy4;
    private Image enemy5;


    //load playres images
    private Image up;
    private Image down;
    private Image left;
    private Image right;
    //fonts
    private TrueTypeFont ttf;
    //for animation
    private int delta;
    //music used in the game
    Music backMusic;
    
    //side bar
    private Image sideBar;
    
   // private Image side;


    @Override
    public int getID() {

        return id;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        try {

            //set the map location
            mapX = 0;
            mapY = 0;

            //load music
            backMusic = new Music("data"+System.getProperty("file.separator") +"music"+System.getProperty("file.separator") +"play.wav");

            //load map static images
            brickImage = new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"brick.png");
            stoneImage = new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"stone.png");
            waterImage = new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"water.png");
            lpImage = new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"LP.png");
            cpImage = new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"coin.png");
            bulletImage=new Image("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"bullet.png");



            //load player images
            myPlayerImg = new Image("data"+System.getProperty("file.separator") +"sprites"+System.getProperty("file.separator") +"up.png");
            enemy1=new Image("data"+System.getProperty("file.separator") +"sprites"+System.getProperty("file.separator") +"enemy.png");
            enemy2=new Image("data"+System.getProperty("file.separator") +"sprites"+System.getProperty("file.separator") +"enemy.png");
            enemy3=new Image("data"+System.getProperty("file.separator") +"sprites"+System.getProperty("file.separator") +"enemy.png");
            enemy4=new Image("data"+System.getProperty("file.separator") +"sprites/enemy.png");
            enemy5=new Image("data"+System.getProperty("file.separator") +"sprites"+System.getProperty("file.separator") +"enemy.png");

            painter = new ElementPainter(map, brickImage, stoneImage, waterImage, myPlayer, lpImage, cpImage,bulletImage);
            painter.setMyPlayerImage(myPlayerImg);
            painter.setEnemies(enemy1, enemy2, enemy3, enemy4,enemy5);

            //load the map
            grassMap = new TiledMap("data"+System.getProperty("file.separator") +"map"+System.getProperty("file.separator") +"map.tmx");


            //load fonts
            ttf = new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 10), false);




            //update the game of the string decoder
            decoder.setCurrentGame(sbg);
            
            //side bar
        sideBar=new Image("data"+System.getProperty("file.separator") +"back"+System.getProperty("file.separator") +"1.jpg");



       // side=new Image("/data/1.gif");

        } catch (SlickException ex) {
            Logger.getLogger(ElementPainter.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {

        sideBar.draw(700, 0);
        grassMap.render(mapX, mapY);
        grphcs.setFont(ttf);
        painter.draw(delta, grphcs);
        //side.draw(700, 350);
        
        

    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

        Input input = gc.getInput();



        if (input.isKeyDown(input.KEY_UP)) {
            generator.goUp();


        } else if (input.isKeyDown(input.KEY_DOWN)) {
            generator.goDown();


        } else if (input.isKeyDown(input.KEY_LEFT)) {
            generator.goLeft();

        } else if (input.isKeyDown(input.KEY_RIGHT)) {
            generator.goRight();


        } else if (input.isKeyDown(input.KEY_S)) {
            generator.shoot();

        }

        //for music playback
        if (sbg.getCurrentStateID() == id && !backMusic.playing()) {
            backMusic.play();
            backMusic.loop();
        } else if (sbg.getCurrentStateID() != id) {

            if (sbg.getCurrentStateID() == id && !backMusic.playing()) {
             backMusic.stop();
            }

        }

        delta = i;




    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMyPlayer(Player player) {
        this.myPlayer = player;
    }

    public void setStrGenerator(StringGenerator generator) {
        this.generator = generator;
    }

    public void setStrDecoder(StringDecoder decoder) {
        this.decoder = decoder;
    }
}
