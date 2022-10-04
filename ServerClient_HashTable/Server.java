/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */

import java.net.*;
import java.io.*;
import java.util.Hashtable;

public class MainPinakasThread extends Thread {
    protected Socket clientSocket;

private static class Pinak {    //Η κλάση που διαχειρίζεται τον πίνακα δεδομένων
    private static final int MEGETHOS =(int) Math.pow(2, 20); //το μέγεθος του πίνακα
    private static Hashtable<Integer, Integer> dedomena = new Hashtable<>(MEGETHOS,1); //loadfactor 1, να μην μεγαλώνει ο πίνακας

    public synchronized void insert(int number1, int number2) throws InterruptedException {
        if (dedomena.size()<MEGETHOS-1)  //Δεν θα επιτρέψουμε αύξηση του μεγέθους
                dedomena.put(number1,number2);  //καταχώρηση ζεύγους
        else
            throw new InterruptedException();
    }
    
    public String anazitisi(int number1){  //Η εργασία της αναζήτησης στοιχείου
        String minima;
        if (dedomena.containsKey(number1))
            minima="- Search :"+dedomena.get(number1);
        else
            minima="- Not exist: 0";
        return minima;
    }
    
    public synchronized void diagrafi(int number1){  //Η εργασία της διαγραφής στοιχείου
        if (dedomena.containsKey(number1))
        dedomena.remove(number1);
        else
            throw new NullPointerException();
    }
}
    
public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null; //ορίζω το socket

        int portTest=8888;         //default port - fallback
        if (args.length > 0) {
           portTest = Integer.valueOf(args[0]);
        }

        try {
            serverSocket = new ServerSocket(portTest);   //άνοιγμα πόρτας και έλεγχος σφάλματος
            System.out.println("Connection Socket Created");
            try {
                while (true) {
                    System.out.println("Waiting for Connection...");
                    new MainPinakasThread(serverSocket.accept());
                }
            } 
            catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
            }
        } 
        catch (IOException e) { 
            System.err.println("Could not listen on port: "+portTest);
            System.exit(1);
        }
        finally {
            try {
                serverSocket.close();
            } 
            catch (IOException e) {
                System.err.println("Could not close port: "+portTest);
                System.exit(1);
            }
        }
}

    private MainPinakasThread(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }
    
            
    @Override
    public void run() {  
        Pinak pinakas=new Pinak();
        System.out.println("New Communication Thread Started");
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);   //output stream
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //input stream        
            
            System.out.println("Connection successful");   //επιτυχής σύνδεση
            System.out.println("Command (X,X,X) ");

            String inputLine;
            String[] entoli = {"","",""};
            while ((inputLine = in.readLine()) != null) {   //ξεκινάει η ανταλλαγή πληροφοριών
            
                //αμυντικός προγραμματισμός. Να είναι σε () και πάνω από 4 χαρακτήρες το ελάχιστο (0,0)
                inputLine=inputLine.replace((" "), "");   //αφαιρώ κενά
                if (inputLine.startsWith("(") && inputLine.endsWith(")") && inputLine.contains(",") && inputLine.length()>4) {  
                    inputLine=inputLine.replace("(", "");
                    inputLine=inputLine.replace(")","");
                    entoli = inputLine.split(",");
                    if (entoli.length==2 && (Integer.parseInt(entoli[0])==1 || Integer.parseInt(entoli[0])>3))
                    entoli[0]="4";
                }
                else 
                    entoli[0]="4";

                
                System.out.println("Received: " + inputLine+" "); //εμφάνιση στο τερματικό του server των εντολών που λαμβάνει
                switch(Integer.parseInt(entoli[0])) {
                case 0:  //1η συνθήκη εξόδου
                    if (Integer.parseInt(entoli[1])==0)  //2η συνθήκη εξόδου
                        out.println(inputLine+"- Ending. Bye");
                    else 
                        entoli[0]="4";
                break;
                case 1:
                try {
                    pinakas.insert(Integer.parseInt(entoli[1]),Integer.parseInt(entoli[2]));  //εντέλεση insert
                }
                catch (NullPointerException|InterruptedException e) {
                    out.println(inputLine+"- Insert 0");
                }    
                finally {
                    out.println(inputLine+"- Insert 1");
                }
                break;

                case 2:
                    try {
                    pinakas.diagrafi(Integer.parseInt(entoli[1])); //εντέλεση delete
                    }
                    catch (NullPointerException e) { 
                     out.println(inputLine+"- Delete 0");
                    }
                     finally {
                     out.println(inputLine+"- Delete 1");    
                     }
                break;
                
                
                case 3:
                    out.println(pinakas.anazitisi(Integer.parseInt(entoli[1])));
                break;
                
                
                case 4:
                    out.println(inputLine+"- Bad Command or file name");  //άγνωστο συντακτικό
                break;                
                default:
                }
            
                if (Integer.parseInt(entoli[0])==0 && Integer.parseInt(entoli[1])==0)  //συνθήκη εξόδου
                break;
            }

        //κλείσιμο
        out.close();
        in.close();
        clientSocket.close();
        
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}