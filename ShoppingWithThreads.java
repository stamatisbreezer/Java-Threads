/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */

public class ShoppingWithThreads {
   //Χωρητικότητα καταστήματος
   private static final int CAPACITY = 40;
   private static final int MENS_ROOMS = 5;
   private static final int WOMENS_ROOMS = 5;
   
   private static final int CASHIER = 10;
   
   //Ελάχιστο και μέγιστο χρόνοι πελατών
   private static final int TIME_TO_PAY = 5 * 1000; //5 second
   private static final int MIN_TIME_FOR_CLIENT_ARRIVAL = 2 * 1000; //2 second
   private static final int MAX_TIME_FOR_CLIENT_ARRIVAL = 5 * 1000; //5 seconds

   private static final int MIN_TIME_CLIENT_PROVA = 10 * 1000; //3 second
   private static final int MAX_TIME_CLIENT_PROVA = 15 * 1000; //10 seconds

public static void main(String[] args) throws Exception {
        boolean fyloNextPelati=true;
        int i=0;
        Dokimastirio dokimastiria = new Dokimastirio(MENS_ROOMS,WOMENS_ROOMS, MIN_TIME_CLIENT_PROVA,MAX_TIME_CLIENT_PROVA); //δημιουργία δοκιμαστήριων
        Tameio tameio = new Tameio(CASHIER,TIME_TO_PAY);
        Semaphore securitas = new Semaphore(CAPACITY, true);  //πόρτα στο κατάστημα κάνει ο Semaphore
        
        //Πελάτες έρχονται συνεχώς
        while(true)
        {
            i++;
            fyloNextPelati=fyloNextPelati^true;  //Ανδρας - γυναίκα αλλάζει κάθε φορά
            
            Pelatis pelatis = new Pelatis (dokimastiria, fyloNextPelati, i, tameio, securitas); //ορίζουμε τον πελάτη

            pelatis.start();          //Ξεκινάει το νήμα του πελάτη. Νέος Πελάτης μπαίνει στο κατάστημα
   
         //Χρονική απόσταση στους πελάτες
            int randomTimeUntilCustomerArrives = ThreadLocalRandom.current().nextInt
               (MIN_TIME_FOR_CLIENT_ARRIVAL,MAX_TIME_FOR_CLIENT_ARRIVAL + 1);
         
         try
         { 
            Thread.sleep(randomTimeUntilCustomerArrives); //Αναμονή πριν τον επόμενο πελάτη
            }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
      }
    }
}

class Dokimastirio {
    private int xoritikotitaA;
    private int xoritikotitaG;
    private int min_xronos;
    private int max_xronos;
    
    private Semaphore SemKatilimenoA;  //Δοκιμαστήριο Ανδρών
    private Semaphore SemKatilimenoG;   //Δοκιμαστήριο Γυναικών
    
    public Dokimastirio(int xoritikotitaA,int xoritikotitaG, int min_xronos, int max_xronos) {
        this.xoritikotitaA=xoritikotitaA;
        this.xoritikotitaG=xoritikotitaG;
        this.min_xronos=min_xronos;
        this.max_xronos=max_xronos;
        SemKatilimenoA= new Semaphore(xoritikotitaA,true);
        SemKatilimenoG= new Semaphore(xoritikotitaG,true);
    }

    public int getXoritikotita(boolean fylo) { //για bug-control sys-out
        if(fylo) 
            return this.xoritikotitaA;
        else 
            return this.xoritikotitaG;
    }
        
    public int makeTime(){   //δημιουργία rantom χρόνου παραμονής στο δοκιμαστήριο
        int randomTimeProva = ThreadLocalRandom.current().nextInt
        (min_xronos, max_xronos + 1); 
        return randomTimeProva;
    }

    public void setSemKatilimeno(boolean fylo) {    //Ορίζουμε την δεσμευση Semaphore για τα δυο δοκιμαστήρια
        if(fylo) 
            try {
                this.SemKatilimenoA.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Dokimastirio.class.getName()).log(Level.SEVERE, null, ex);
        }
        else
            try {
                this.SemKatilimenoG.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Dokimastirio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setSemExodos(boolean fylo) {    //Ορίζουμε την απόδεσμευση Semaphore για τα δυο δοκιμαστήρια
        if(fylo) 
            this.SemKatilimenoA.release();
        else
            this.SemKatilimenoG.release();
    }
    
        public int SemInside(boolean fylo) {   //Μετρητής πολυκοσμίας
            int i=0;
        if(fylo) 
            i=xoritikotitaA-this.SemKatilimenoA.availablePermits();
        else
            i=xoritikotitaG-this.SemKatilimenoG.availablePermits();
        return i;
    }
    
    
    
    public void prova(Pelatis pelatis){
            setSemKatilimeno(pelatis.isAntras());  //Κάνουμε την δεσμευση ανάλογα το φύλο του πελάτη
            
            //Ανακοινώνουμε την είσοδο του πελάτη
            System.out.println((pelatis.isAntras() ? "Men customer" : "Women customer") + " enters fitting Room "+pelatis.getSeira()+"| "+Thread.currentThread().getName()+"    "+SemInside(pelatis.isAntras())+"/"+getXoritikotita(pelatis.isAntras()));
        try {
            //Χρόνος πρόβας
            Thread.sleep(makeTime()); 
        }   catch (InterruptedException ex) {Logger.getLogger(Pelatis.class.getName()).log(Level.SEVERE, null, ex);}
            
            //O πελάτης έχει δοκιμάσει το ρούχο
            pelatis.setDokimi(true);
            
            //Έξοδος του πελάτη από το δοκιμαστήριο
            setSemExodos(pelatis.isAntras());
            System.out.println("Out "+ (pelatis.isAntras() ? "Men customer" : "Women customer")+" from fitting room. "+pelatis.getSeira()+"| "+Thread.currentThread().getName()+"    "+SemInside(pelatis.isAntras())+"/"+getXoritikotita(pelatis.isAntras()));

   }
}



class Pelatis extends Thread {
    private boolean antras, dokimi,pliromi; 
    private final Tameio tameio;
    private final int seira;
    private final Dokimastirio dokimastirio;
    private final Semaphore securitas;

    public Pelatis(Dokimastirio dokimastiria,boolean fylo, int seira, Tameio tameio, Semaphore semSecuritas) {
        this.dokimastirio=dokimastiria;
        this.antras=fylo;
        this.seira=seira;
        this.tameio=tameio;
        this.securitas=semSecuritas;
    }

    public boolean isAntras() {
        return antras;
    }

    public int getSeira() {
        return seira;
    }
    
    public boolean isDokimi() {
        return dokimi;
    }

    public void setAntras(boolean antras) {
        this.antras = antras;
    }

    public void setDokimi(boolean dok) {
        dokimi = dok;
    }
    
    public void setPliromi(boolean pli) {
        pliromi = pli;
    }

    public boolean isPliromi() {
        return pliromi;
    }
    
    


    @Override
    public synchronized void run(){
        try {
            //Τον στέλνουμε κατευθείαν στο δοκιμαστήριο αφού δεν έχει οριστεί χρόνος για χάσιμο από την εκφώνηση.
            this.securitas.acquire();   //δεσμεύουμε μια θέση στο κατάστημα
            System.out.println(">>Good Morning "+ (antras ? "Men customer" : "Women Customer")+" number "+this.seira+"| "+Thread.currentThread().getName());
            dokimastirio.prova(this);    //εκκινείτε η διαδικασία δοκιμαστηρίου
            if (isDokimi()) tameio.Pliromi(this);    //εκκινείτε η διαδικασία πληρωμής
            if (isPliromi()) this.securitas.release(); //εφόσον ολοκληρώθηκε η πληρωμή και βγαίνει από το κατάστημα
        } catch (InterruptedException ex) {
            Logger.getLogger(Pelatis.class.getName()).log(Level.SEVERE, null, ex);
        }

        
   }
       
}

class Tameio extends Thread {
    private final Semaphore semTameio;
    private final int xronos;
    
    public Tameio(int xoritikotita,int xronos) {
        this.xronos=xronos;
        semTameio = new Semaphore(xoritikotita,true);
    }
    
    public void Pliromi(Pelatis pelatis){
        try {
            semTameio.acquire();   //Δεσμευση θέσης ταμείου
            System.out.println("\n="+(pelatis.isAntras() ? "Mr " : "Mss ")+"No "+pelatis.getSeira()+">>Your total is:  "+makeCost()+"€ "+Thread.currentThread().getName());
            
            //Πληρωμή
            Thread.sleep(xronos);
            pelatis.setPliromi(true);  //Ο πελάτης πλήρωσε
            semTameio.release();   //Απόδεσμευση θέσης ταμείου
        } catch (InterruptedException ex) {
            Logger.getLogger(Tameio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int makeCost(){   //δημιουργία rantom αξίας
        int randomCost = ThreadLocalRandom.current().nextInt
        (5, 99); 
    return randomCost;
    }
   }
