/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg47ge2_3_client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static final int[] ANAMONH={1000,2000};         //Χρόνος αναμονής client msec 
    public static final int ELAXISTI_METABOLI = 0;   //Αποδεκτές μεταβολές (10-100)
    public static final int MEGISTI_METABOLI=130;   //Οι τιμές που έδωσα είναι για να τις κάνει drop ο server
    public static final int[] CONSUMERS={9991,9992,9993};  //Δηλώσεις πελατών
    public static final int[] PRODUCERS={8881,8882,8883};  //Δηλώσεις πελατών
    
    
    Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;    
    Random rand = new Random();
    
    static Random rnd = new Random();
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        String serverHostname = "127.0.0.1";
        int port=8881;  //default port
        try {       //δοκιμή πως θα τρέξει ο client
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {  //αν δεν είναι νούμερο πάμε αυτόματα
        if (args[0].equals("RC")) 
           while (true) {
               int randNum = rnd.nextInt( CONSUMERS.length );  //επιλογή επόμενης σύνδεσης
               ConnectR(CONSUMERS[randNum],serverHostname);
           }
        else
            if (args[0].equals("RP")) 
           while (true) {
               int randNum = rnd.nextInt( PRODUCERS.length );
               ConnectR(PRODUCERS[randNum],serverHostname);
           }                
        }        
        catch (ArrayIndexOutOfBoundsException e) {
        
    }
        if (port>7000) ConnectM(port,serverHostname);  //για χειροκίνητο
        //System.out.println("run Manual"); Debuging
    }

private static  void ConnectR(int port, String serverHostname) throws IOException, InterruptedException {
        System.out.println("Attempting to connect to host " + serverHostname + " on port "+port+".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        int randNum = rnd.nextInt( ELAXISTI_METABOLI, MEGISTI_METABOLI )+1;  //επιλογή ποσότητας
        out.println(randNum);
        System.out.println(randNum+">> Server: " + in.readLine());
        randNum = rnd.nextInt(ANAMONH[0], ANAMONH[1]);  //επιλογή αναμονής
        System.out.println("Sleep for "+randNum);
        Thread.sleep(randNum); //αναμονή

        out.close(); //κλείσιμο σύνδεσης
        in.close();
        stdIn.close();
        echoSocket.close();
    }

private static void ConnectM(int port, String serverHostname) throws IOException {
        System.out.println("Attempting to connect to host " + serverHostname + " on port "+port+".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        
        System.out.println("Type 00 to quit, ");
        
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equals("00"))
                break;
        
            System.out.println("Server: " + in.readLine());
            System.out.print("next command: ");
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
    
}
