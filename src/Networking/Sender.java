/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ishan
 */
public class Sender {

    private int outPort;
    private String server;
    private Socket outSocket;
    private PrintWriter writer;
    //timer
    private long counter;
    private boolean allowed;

    public Sender(int outPort, String server) {

        this.outPort = outPort;
        this.server = server;


        counter = System.currentTimeMillis();
        allowed = true;



    }

    public void send(String str) {

        /*
         * Is it a good idea to do this chcking with a 1 ms accuracy???
         *
         */

        if (((counter + 1200) < System.currentTimeMillis())) {

            counter = System.currentTimeMillis();
            allowed = true;
        }

        if (allowed && (str != null)) {
            try {

                System.out.println("Sending " + str);
                outSocket = new Socket(server, outPort);
                writer = new PrintWriter(outSocket.getOutputStream(), true);
                writer.write(str);
                writer.flush();
                writer.close();

                allowed = false;

                counter = System.currentTimeMillis();
            } catch (IOException ex) {
                System.out.println("O.o");
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }


    }
}
