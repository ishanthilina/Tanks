/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlling;

import Networking.Sender;

/**
 *
 * @author ishan
 */
public class StringGenerator {

    private Sender sender;
    private int outPort;
    private String server;
    
    //tracks whther the join message has already been sent
    
    private long joinSentTime;

    public StringGenerator(int outPort, String server) {
        
        this.outPort = outPort;
        this.server = server;
        joinSentTime=0;
    }

    
    

    public void join() {
        
        
        //if join has been last sent within 5 seconds 
        if((joinSentTime+5000)<System.currentTimeMillis()){
            sender = new Sender(outPort, server);
            sender.send("JOIN#");
            joinSentTime=System.currentTimeMillis();
        }
        
        else{
            
            
            System.out.println("Tried to resend the \" #join\" message"
                    + "");
        }
        


    }

    public void goUp() {
        sender.send("UP#");

    }

    public void goDown() {
        sender.send("DOWN#");
    }

    public void goLeft() {
        sender.send("LEFT#");
    }

    public void goRight() {
        sender.send("RIGHT#");
    }

    public void shoot() {
        sender.send("SHOOT#");
    }
}
