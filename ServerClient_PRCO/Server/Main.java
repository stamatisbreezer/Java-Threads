/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PLH47GE2SUB3;
/**
 *
 * @author Stamatis Chatzichristodoulou <breezer77@gmail.com>
 */

public class Main {
    public static final int ELAXISTO = 1;           //Accepted Stock
    public static final int MEGISTO = 1000;
    public static final int ELAXISTI_METABOLI = 10;   //Accepted fluctuation
    public static final int MEGISTI_METABOLI=100;
    public static final int[] CONSUMERS={9991,9992,9993};  //Consumer Ports
    public static final int[] PRODUCERS={8881,8882,8883};  //Producers Ports
        
    public static void main(String[] args)  {
        Server s1 = new Server(0); //η πρώτη port, δεύτερη κ.ο.κ.
        Server s2 = new Server(1);
        Server s3 = new Server(2);
    }

}
