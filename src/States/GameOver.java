/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import Map.Map;
import Map.Player;
import java.awt.Font;
import java.util.LinkedList;
import java.util.ListIterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author ishan
 */
public class GameOver extends BasicGameState {

    /** The ID given to this state */
    public static final int id = 3;
    //to store the current game
    private StateBasedGame game;
    //for sound
    Music backMusic;
    //map
    private Map map;
    
    //background
    private Image back;
    
    
    //setup fonts
    Font fTitle ;
    TrueTypeFont ttfTitle ;
    
    Font fScore;
    TrueTypeFont ttfScore;

    @Override
    public int getID() {

        return id;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;

        //load music
        backMusic = new Music("data"+System.getProperty("file.separator") +"music"+System.getProperty("file.separator") +"gameOver.wav");
        // backMusic.play();
        
        //background
        back=new Image("data"+System.getProperty("file.separator") +"back"+System.getProperty("file.separator") +"2.jpg");
        
        //setup fonts
        
        //title font
        fTitle = new Font("Times New Roman", Font.BOLD, 30);
        ttfTitle = new TrueTypeFont(fTitle, false);


        //score font
        fScore=new Font("Veradana", Font.PLAIN, 15);
        ttfScore=new TrueTypeFont(fScore, false);


    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        
        
        back.draw(0, 0);
        
        int tableX=550;
        int tableY=100;
        int width=50;
        int height=30;
        
        //setup fonts
        
        
        
        

        //graphics.drawString("The GAME", 900, 100);
        
       // ttfTitle.drawString(950, 22, "The GAME", Color.yellow) ;
        
        //print table headings
        
        ttfScore.drawString(tableX + (width * 2)-10, tableY-height, "Coins", Color.white);
        
        ttfScore.drawString(tableX + (width * 4)-10, tableY-height, "Health", Color.white);
        
        
        
        //ff.drawString(600, 300, "TST",Color.green);
        
        
       // TrueTypeFont dd=new TrueTypeFont
        
        /*
         * Draw players details
         */
        
       // graphics.drawString("My player:   \t\t\t"+myPlayer.getCoins()+"   \t\t\t"+myPlayer.getHealth(), 800 + 500/4 * 0, tableY+(myPlayer.getIndex()*20));
        
        int highestScore=0;

//        
        //get the players
        LinkedList<Player> enemies = map.getContestants();

        ListIterator playerIter = enemies.listIterator();

        Player player;
        
        

        if (playerIter.hasNext()) {
            
            Player myPlayer=map.getMyPlayer();
        //ttfScore.drawString( 800 + 500/4 * 0, tableY+(myPlayer.getIndex()*width),"Scorpio:   \t\t\t"+myPlayer.getCoins()+"        \t\t\t"+myPlayer.getHealth(), Color.lightGray);
        
        ttfScore.drawString(tableX + width * 0, tableY + (myPlayer.getIndex() * height), "Scorpio", Color.lightGray);
        ttfScore.drawString(tableX + width * 2, tableY + (myPlayer.getIndex() * height), Integer.toString(myPlayer.getCoins()), Color.lightGray);
        ttfScore.drawString(tableX + width * 4, tableY+(myPlayer.getIndex()*height),Integer.toString(myPlayer.getHealth()), Color.lightGray);
       // f1ttf.drawString(800, 300, "Othre font", Color.yellow);
            
            
            
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
               
               if(player.getCoins()>highestScore){
                   highestScore=player.getCoins();
               }
               


                if (playerIter.hasNext()) {
                    player = (Player) playerIter.next();

                } else {

                    break;
                }


            }


        }
        
        if(highestScore<map.getMyPlayer().getCoins()){
            //grphcs.drawString("You won...!", 650, 350);
            ttfTitle.drawString(590, 270, "You Won...!", Color.white);
        }
        else{
            //grphcs.drawString("You Lost...!", 650, 350);
            
            ttfTitle.drawString(590, 270, "You Lost...!", Color.red);
        }
       
//
//        grphcs.drawString("GAME OVER", 650, 350);
//        
//        grphcs.drawString("B - Move back to the map", 100, 500);
//        

    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if (sbg.getCurrentStateID() == id && !backMusic.playing()) {
            backMusic.play();
        } else if (sbg.getCurrentStateID() != id) {

            backMusic.stop();
        }

    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_B) {
            game.enterState(Play.id);

        }
    }
    
    public void setMap(Map map) {
        this.map = map;
    }
}
