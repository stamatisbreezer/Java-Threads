/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thema1;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 * The program creates two tables of 
 * MultiplyTables
 * from Linear Algebra, we can multiply a matrix by a vector from the right, as long as the number of its columns is sufficient
 * matrix to be equal to the number of rows of the vector. For example if we have a matrix A of dimensions n x m and a vector v of dimensions m x 1, 
 * then the product A * v equals an n x 1 vector by applying the well-known matrix multiplication method with vector. 
 * An example is given in figure1.
 * 
 * Assuming we have k threads, where k is a power of 2 and the array has dimensions n x m where n is also a power of 2 and n > k, 
 * design a solution that calculates the product A * v using the k threads in the best possible way. 
 * The program should "fill" the array A and the vector v with random numbers between 0 and 10.
 * 
 * What the time needed for 1,2,4 and 8 Threads.
 */

public class MultiplyTables extends Thread {
    private int[][] arr1;
    private int[] arr2,arr3;
    private int numberM,arxi,plithos;
    
    public MultiplyTables(int[][] arr1, int[] arr2, int[] arr3, int arxi, int plithos, int numberM) {
        this.arr1 = arr1;
        this.arr2 = arr2;
        this.arr3 = arr3;
        this.arxi = arxi;
        this.plithos = plithos;
        this.numberM = numberM;
        }

    @Override
    public void run() {
            for (int n=arxi; n<arxi+plithos; n++)
            for (int m=0; m<numberM; m++) 
                arr3[n] += arr1[n][m]*arr2[m];
    }
    
    
    public static int[] muplip(int[][] arr1, int[] arr2, int numbN, int numbM, int nimata ) throws InterruptedException {
        // Δημιουργία νημάτων και εκκίνηση
        int[] arr3 = new int[numbN];
        int k=nimata;
        
        MultiplyTables[] nima = new MultiplyTables[k];
        for (int i = 0; i < k; i++) {
            nima[i] = new MultiplyTables(arr1,arr2,arr3,i*numbN/k, numbN/k, numbM);
            nima[i].start();
        }
        
        // Wait for the threads to finish and sum their results.
        for (int i = 0; i < k; i++) {
            nima[i].join();
            arr3[i] = (int) nima[i].arr3[i];
        }
        return arr3;
    }

   
    //έλεγχος για δύναμη του 2
    public static boolean isPerfectSquare(int n) {
    if (n <= 0) {
        return false;
    }
    int result = (int)(Math.log(n) / Math.log(2));
    return Math.pow(2,result) == n;
}
    
 
    
    
     public static void main(String[] args) throws InterruptedException {
        
        //Αμυντικός έλεγοχς για δυνάμεις του 2 μόνο
        int numberN=3;
        while (!isPerfectSquare(numberN)) {
            Scanner input=new Scanner(System.in);
            System.out.print("Rows n table (power of ^2: ");
            numberN=input.nextInt();
        }
        
        //Δεν γίνεται αμυντικός έλεγχος για τις δυνάμεις το 2, string, double
        System.out.print("Columns m table: ");
        Scanner input=new Scanner(System.in);
        int numberM=input.nextInt();

            
        //Δημιουργία πινάκων
        int[][] pinakas  = new int[numberN][numberM];
        int[]   dianisma = new int[numberM];
        int[] apotelesma = new int[numberN];
        //Γέμισμα πινάκων με τυχαίους αριθμούς 0-10
        Random rand = new Random();
       
        for (int n=0; n<numberN; n++){
            System.out.print("| " );
            for (int m=0; m<numberM; m++) {
                pinakas[n][m]= rand.nextInt(10);
                System.out.print(pinakas[n][m]+" ");
            }
            System.out.print("|   " );
            
        //προτιμώ να εμφανιστεί το διάνυσμα δίπλα στον πίνακα
            if (n<numberM) {  
                dianisma[n]= rand.nextInt(10);
                System.out.print("|"+dianisma[n]+"|");
            }
            System.out.println("");
        }    
        //και στην περίπτωση που το διάνυσμά έχει περισσότερες γραμμές από τον πίνακα
            for (int n=numberN; n<numberM; n++) {
                dianisma[n]= rand.nextInt(10);
                for (int i=0; i<numberM; i++) System.out.print("  ");   
                System.out.println("      |"+dianisma[n]+"|");               
            }
     
/* Τεστ πριν το setup των νημάτων  
        System.out.println("--Αποτέλεσμα χωρίς νήματα--");
        for (int n=0; n<numberN; n++){
            for (int m=0; m<numberM; m++) {
                apotelesma[n] += pinakas[n][m]*dianisma[m];
            }
            System.out.println("|"+apotelesma[n]+"|" );
        }
*/        
        System.out.println("--Result--");
        apotelesma=muplip(pinakas, dianisma, numberN, numberM, 4 );
        for (int n=0; n<numberN; n++)
            System.out.println("|"+apotelesma[n]+"|" );
        
        if (numberN>=8) {  //Θέμα δύο: Μετρήσεις χρόνου για 1, 2, 4 και 8 νήματα. 
        for (int i=0; i<4; i++) {
            double start=System.nanoTime();
            int nimata = (int) Math.pow(2, i);
            apotelesma=muplip(pinakas, dianisma, numberN, numberM, nimata );

//          Εμφάνιση αποτελεσμάτων για κάθε νήμα ξεχωριστά            
//          for (int n=0; n<numberN; n++) System.out.println(nimata+" |"+apotelesma[n]+"|" ); 
            
            double finish=System.nanoTime()-start;
            
            System.out.println("Result with "+nimata+" Threads: "+finish/1000);
        }
    }
     }
}