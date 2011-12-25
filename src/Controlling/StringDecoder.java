/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlling;

import Bot.BotInterface;
import Map.Brick;
import Map.CoinPack;
import Map.LifePack;
import Map.Map;
import Map.Player;
import Map.Stone;
import Map.Water;
import States.CannotConnect;
import States.GameOver;
import States.Play;
import java.util.StringTokenizer;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

/**
 *
 * @author ishan
 */
public class StringDecoder {

    private Map map;
    //stores the index of my plyer
    private int myIndex;
    //to check whether the init message has already been decoded
    private boolean initDecoded;
    //to check whether the init message has already been decoded
    private boolean startDecoded;
    //to store the Start message if the Init message is not already decoded
    private StringTokenizer startToken;
    //to check whether the Start message arrived
    private boolean startArrived;
    //to store the current game
    private StateBasedGame game;
    
    //ai
    private BotInterface bi;

    public StringDecoder(Map map, BotInterface bi) {
        this.map = map;

        initDecoded = false;
        startDecoded = false;
        startArrived = false;
        
         //ai
       this.bi=bi;


    }

    public void decode(String reply) {

        StringTokenizer tokenizer = new StringTokenizer(reply, ":");

        String firstLetter = tokenizer.nextToken();

        /*
         * GAME_ALREADY_STARTED#
         * GAME_HAS_FINISHED#
         * GAME_NOT_STARTED_YET#
         */
        if (firstLetter.equalsIgnoreCase("G")) {

            decodeUpdate(tokenizer);

        } /*
         * INVALID_CELL#
         */ else if (firstLetter.equalsIgnoreCase("I")) {


            decodeInitialization(tokenizer);
            initDecoded = true;

            //If the Start message has come and it is not decoded yet
            if (!startDecoded && startArrived) {
                decodeStarting(startToken);
                startDecoded = true;
            }
        } else if (firstLetter.equalsIgnoreCase("S")) {

            if (initDecoded) {
                decodeStarting(tokenizer);
                startDecoded = true;
            } else {
                startToken = tokenizer;
                startDecoded = false;

            }




        } else if (firstLetter.equalsIgnoreCase("L")) {

            decodelifePack(tokenizer);


        } /*
         * CELL_OCCUPIED#
         */ else if (firstLetter.equalsIgnoreCase("C")) {

            decodeCoinPack(tokenizer);


        } else if (firstLetter.equalsIgnoreCase("PITFALL#")) {
            System.out.println("PITFALL#");
            bi.AIStop();
            game.enterState(GameOver.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("DEAD#")) {
            System.out.println("DEAD#");
            bi.AIStop();
            game.enterState(GameOver.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("PLAYERS_FULL#")) {
            System.out.println("PLAYERS_FULL#");
           // bi.AIStop();
            game.enterState(CannotConnect.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("NOT_A_VALID_CONTESTANT#")) {
            System.out.println("NOT_A_VALID_CONTESTANT#");
            //bi.AIStop();
            game.enterState(CannotConnect.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("GAME_FINISHED#")) {
            System.out.println("GAME_FINISHED#");
           // bi.AIStop();
            game.enterState(GameOver.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("GAME_HAS_FINISHED#")) {
            System.out.println("GAME_HAS_FINISHED#");
           // bi.AIStop();
            game.enterState(GameOver.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }else if (firstLetter.equalsIgnoreCase("GAME_ALREADY_STARTED#")) {
            System.out.println("GAME_ALREADY_STARTED#");
          //  bi.AIStop();
            game.enterState(CannotConnect.id, new FadeOutTransition(Color.red), new FadeInTransition(Color.black));

        }
        



    }

    private void decodeStarting(StringTokenizer tokenizer) {


        int totTokens = tokenizer.countTokens();


        String playerData = tokenizer.nextToken();

        for (int count = 0; count < totTokens; count++) {
            String[] data = playerData.split("[;,#]");

            //get my players info
            if (Integer.parseInt(playerData.substring(1, 2)) == myIndex) {

                System.out.println("My player: " + myIndex);

                Player player = new Player(0, 0, 0, 100, myIndex);

                player.setPlayerX(Integer.parseInt(data[1]));
                player.setPlayerY(Integer.parseInt(data[2]));
                player.setPlayerDir(Integer.parseInt(data[3]));
                player.setHealth(100);
                player.setCoins(0);
                player.setShot(false);
                player.setIndex(myIndex);

                //for the proper initial drawing

                float x = Integer.parseInt(data[1]) * 35f;
                float y = Integer.parseInt(data[2]) * 35f;

                System.out.println(x + ", " + y);
                player.setDrawnX(x);
                player.setDrawnY(y);


                map.addMyPlayer(player);





            } //create other playersplayer
            else {


                Player player = new Player(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), 100, Integer.parseInt(playerData.substring(1, 2)));
                //player.setIndex(Integer.parseInt(playerData.substring(1, 2)));
                map.setContesters(player);
            }

            if (count + 1 < totTokens) {
                playerData = tokenizer.nextToken();
            }


        }



        //set map to AI
        bi.initializeMap(map.getStones(), map.getWater(), map.getBricks());
        
        bi.initMyPlayer(map.getMyPlayer());
        bi.coinPilesSpawned(map.getCoinPacks());
        bi.lifePacksSpawned(map.getLifePacks());
        
        bi.setOtherBots(map.getContestants());
        
        //add my player to AI
                
         bi.AIStart();




        System.out.println("=======================================================");
        System.out.println("Game started...!");
        System.out.println("=======================================================");


        game.enterState(Play.id, new VerticalSplitTransition(), new FadeInTransition());


    }

    private void decodeInitialization(StringTokenizer tokenizer) {


        String playerData = tokenizer.nextToken();

        //set the index of my player
        myIndex = Integer.parseInt(playerData.substring(1));


        //get brick data
        String brickData = tokenizer.nextToken();


        //add brick data to the map
        String[] bricks = brickData.split("[;,]");

        for (int count = 0; count < bricks.length; count += 2) {


            Brick brick = new Brick(Integer.parseInt(bricks[count]), Integer.parseInt(bricks[count + 1]));
            map.setBrick(brick);
        }

        //get stone data
        String stoneData = tokenizer.nextToken();


        //add stone data to the map
        String stones[] = stoneData.split("[;,]");

        for (int count = 0; count < stones.length; count += 2) {

            Stone stone = new Stone(Integer.parseInt(stones[count]), Integer.parseInt(stones[count + 1]));

            map.setStone(stone);


        }

        //get water data
        String waterData = tokenizer.nextToken();

        //System.out.println(waterData);

        //add stone data to the map
        String sea[] = waterData.split("[;,#]");

        for (int count = 0; count < sea.length; count += 2) {

            Water water = new Water(Integer.parseInt(sea[count]), Integer.parseInt(sea[count + 1]));

            map.setWater(water);


        }





    }

    private void decodeUpdate(StringTokenizer tokenizer) {


        String data = tokenizer.nextToken();

        //check all players
        while (data.startsWith("P")) {

            //System.out.println(myIndex);
            String[] playerData = data.split("[;,#]");
            if (Integer.parseInt(data.substring(1, 2)) == myIndex) {


                Player player = new Player(0, 0, 0, 0, myIndex);

                player.setPlayerX(Integer.parseInt(playerData[1]));
                player.setPlayerY(Integer.parseInt(playerData[2]));
                player.setPlayerDir(Integer.parseInt(playerData[3]));
                player.setHealth(Integer.parseInt(playerData[5]));
                player.setCoins(Integer.parseInt(playerData[6]));
                player.setPoints(Integer.parseInt(playerData[7]));

                if (Integer.parseInt(playerData[4]) == 0) {
                    player.setShot(false);
                } else {

                    player.setShot(true);
                }

                map.updateMyPlayer(player);



            } else {
                int index = Integer.parseInt(data.substring(1, 2));

                Player player = new Player(Integer.parseInt(playerData[1]), Integer.parseInt(playerData[2]), Integer.parseInt(playerData[3]), Integer.parseInt(playerData[5]), index);
                player.setCoins(Integer.parseInt(playerData[6]));
                player.setPoints(Integer.parseInt(playerData[7]));

                if (Integer.parseInt(playerData[4]) == 0) {
                    player.setShot(false);
                } else {

                    player.setShot(true);
                }

                //player.setIndex(index);
               // System.out.println("player"+index+" shot");

                map.updateContestant(player);


            }

            if (tokenizer.hasMoreTokens()) {
                data = tokenizer.nextToken();

            }

        }

        //decode brick data
        StringTokenizer st = new StringTokenizer(data, ";");

        //to store brick health data
        int brickHealth[][] = new int[20][20];

        while (st.hasMoreTokens()) {
            String[] brickData = st.nextToken().split("[,#]");

            brickHealth[Integer.parseInt(brickData[0])][Integer.parseInt(brickData[1])] = Integer.parseInt(brickData[2]);

            map.setBrickHealth(brickHealth);


        }





        //set ai
        bi.coinPilesSpawned(map.getCoinPacks());
        bi.lifePacksSpawned(map.getLifePacks());

        bi.updateBrickHealth(map.getBricks());
        bi.reSetPlayer(map.getMyPlayer());





    }






    private void decodelifePack(StringTokenizer tokenizer) {
        String data = tokenizer.nextToken();

        String[] lifePackData = data.split("[:,#]");

        data = tokenizer.nextToken();
        data = data.split("[#]")[0];

        LifePack pack = new LifePack(Integer.parseInt(data), Integer.parseInt(lifePackData[0]), Integer.parseInt(lifePackData[1]));

        map.addLifePack(pack);

    }

    private void decodeCoinPack(StringTokenizer tokenizer) {

        String data = tokenizer.nextToken();

        String[] cordinates = data.split("[:,#]");

        String lifeTime = tokenizer.nextToken();
        String amount = tokenizer.nextToken().split("[#]")[0];

        CoinPack cp = new CoinPack(Integer.parseInt(lifeTime), Integer.parseInt(amount), Integer.parseInt(cordinates[0]), Integer.parseInt(cordinates[1]));

        //System.out.println(lifeTime);
        map.addCoinPack(cp);

    }

    public void setCurrentGame(StateBasedGame game) {
        this.game = game;
    }
}
