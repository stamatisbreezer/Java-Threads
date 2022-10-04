/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PLH47GE2SUB3;

import PLH47GE2SUB3.Server.Metritis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */
public class ServerPort {
    public int port;
    AtomicInteger metaboli = new AtomicInteger(0);
    private Metritis metritis;
   
    public ServerPort(int port, Metritis metritis ){ //
        this.port = port;
        this.startServer(port);
        this.metritis=metritis;
        //this.metaboli.set(0);
    }
    
    public void main(String[] args) {
    }

    public void startServer(int port) {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10); //σύνολο threads για κάθε port

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    // System.out.println("Waiting for clients :"+port);  //For debugging 
                    try {
                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            clientProcessingPool.submit(new ClientTask(clientSocket));  //ξεκινά thread για σύνδεση client
                        }
                    } catch (IOException e) {
                        System.err.println("Accept failed.");
                        System.exit(1);
                    }
                } catch (IOException e) {
                System.err.println("Could not listen on port: " + port);
                System.exit(1);
                } 
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start(); //ξεκινά thread για port
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            // Do whatever required to process the client's request
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);   //output stream
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //input stream        

                System.out.println("Connection successful "+clientSocket.getLocalPort());   //επιτυχής σύνδεση
                String inputLine;
                String message;
                while ((inputLine = in.readLine()) != null) {   //ξεκινάει η ανταλλαγή πληροφοριών
                    try {
                        metaboli.set(Integer.parseInt(inputLine));
                        System.out.println(clientSocket.getLocalPort()+" Received "+metaboli.get());
                        message=metritis.setMetriti(metaboli,clientSocket.getLocalPort());
                        out.println(message);
                    } catch (NumberFormatException e) {
                        System.err.println("Wrong Format " + inputLine);
                        out.println("E R R O R");
                        //System.exit(1);
                    }
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.err.println("Communication Problem");
                //System.exit(1);
            }
        }
    }
}