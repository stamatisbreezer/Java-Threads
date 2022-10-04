/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package PLH47GE2SUB3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */

public class Server {
    private int port1, port2;
    private  int[] CONSUMERS;  //Δηλώσεις πελατών
    private  int[] PRODUCERS;  //Δηλώσεις πελατών
      
    public Server (int port)  {
        this.CONSUMERS=Main.CONSUMERS;;
        this.PRODUCERS =Main.PRODUCERS;
        this.port1=CONSUMERS[port];
        this.port2=PRODUCERS[port];
        Metritis metritis= new Metritis();
        ServerPort serverport1 = new ServerPort(port1,metritis); //Ο κάθε server έχει 2 ServerPorts
        ServerPort serverport2 = new ServerPort(port2,metritis);
    }


    public class Metritis {
        protected  AtomicInteger counter = new AtomicInteger();

        public Metritis() {
            this.counter.set(ThreadLocalRandom.current().nextInt(Main.MEGISTO)+1);
            System.out.println("Initialize Server "+ port1+"/"+port2+"....:"+counter.get());
        }
        
        
        
        public synchronized String setMetriti(AtomicInteger metaboli,int eiserxomeno) {
            boolean prosthese=false; //ανάλογα το port έχει διαφορετικό νόημα το μήνυμα.
            String epibebaiosi;  //το επιστρεφόμενο μήνυμα
            for (int i=0;i<PRODUCERS.length;i++) if (eiserxomeno==PRODUCERS[i]) prosthese=true;
        if (Math.abs(metaboli.get())>=Main.ELAXISTI_METABOLI && Math.abs(metaboli.get())<=Main.MEGISTI_METABOLI //έλεγχος τιμής ελάχιστης / μέγιστης
            && ((counter.get()-metaboli.get()>=Main.ELAXISTO && prosthese==false) || //κάτω όριο για τον consumer 
                (prosthese==true && counter.get()+metaboli.get()<=Main.MEGISTO))) {  //πάνω όριο για τον producer
            if (prosthese)  {  
                counter.set(counter.get()+metaboli.get());
                epibebaiosi="Producer accepted";
            }
            else {
                counter.set(counter.get()-metaboli.get());
                epibebaiosi="Consumer accepted";
            }
            System.out.println(port1+"/"+port2+" New Value :"+counter.get());
        }
        else {
            System.out.println("Transaction not accepted :"+counter.get());
            epibebaiosi="Command droped ";
        }
        return epibebaiosi;
        }

        public int getMetriti() {
            return counter.get();
        }
    }
}
