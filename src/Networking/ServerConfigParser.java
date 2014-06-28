package Networking;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ishan
 */
public class ServerConfigParser {
    
    private String configFileLocation;
    private  FileInputStream fstream;
    DataInputStream in;
    BufferedReader br;
    
    //System.out.println("in get addresss");
     String content[];

    public ServerConfigParser()  {
        try {
            //location of the server config file
            configFileLocation="data"+System.getProperty("file.separator") +"server.info";
            
            fstream = new FileInputStream(configFileLocation);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            try {
            String line=br.readLine();
           // System.out.println("line: "+line);
            content=line.split(" ");
            //System.out.println("contenet: "+content[0]);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerConfigParser.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           // System.out.println("File loaded successfully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerConfigParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public String getServerAddress(){
        
        
        return content[0];
    }
    
    public int getServerInPort(){
        
        return Integer.parseInt(content[1]);
        
        
    }
    
    public int getServerOutPort(){
        
        return Integer.parseInt(content[2]);
        
    }
    
    
    
}
