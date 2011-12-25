/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Controlling.StringDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ishan
 */
public class Receiver implements Runnable {

    private int inPort;
    private ServerSocket sSocket;
    private Socket inSocket;
    private InputStreamReader isReader;
    private BufferedReader reader;
    private String reply;
    private StringDecoder decoder;

    public Receiver(int inPort, StringDecoder dec) {

        this.decoder = dec;
        this.inPort = inPort;


        try {
            sSocket = new ServerSocket(inPort);
            System.out.println("Server socket created...");

        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void run() {


        System.out.println("Trying to listen to the server on: " + inPort);
        try {
            inSocket = sSocket.accept();
            isReader = new InputStreamReader(inSocket.getInputStream());
            reader = new BufferedReader(isReader);

            if ((reply = reader.readLine()) != null) {
                System.out.println(reply);
                decoder.decode(reply);

            }

        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }



        while (true) {
            try {

                inSocket = sSocket.accept();
                isReader = new InputStreamReader(inSocket.getInputStream());
                reader = new BufferedReader(isReader);

                if ((reply = reader.readLine()) != null) {
                    decoder.decode(reply);


                }

            } catch (IOException ex) {
                //Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Receiver class error");
                try {
                    sSocket = new ServerSocket(inPort);
                } catch (IOException ex1) {
                    System.out.println("Receiver class error");
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex1);
                }
                
                continue;
            }

        }

    }
}
