/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientpinakas;

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */

import java.io.*;
import java.net.*;

public class ClientPinakas {

public static void main(String[] args) throws IOException {
        String serverHostname = "127.0.0.1";
        
        int portTest=8888;         //default port - fallback
        if (args.length > 0) {
           portTest = Integer.valueOf(args[0]);
        }
        
        System.out.println("Attempting to connect to host " + serverHostname + " on port "+portTest+".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echoSocket = new Socket(serverHostname, portTest);
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

        System.out.println("Type Message \"(0,0)\" to quit \n Commands as (X,X,X) \n1:Add \n2:Delete \n3:Search");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equals("(0,0)"))
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
