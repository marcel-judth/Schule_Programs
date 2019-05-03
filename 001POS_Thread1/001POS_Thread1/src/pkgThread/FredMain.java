/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgThread;

/**
 *
 * @author Marcel Judth
 */
public class FredMain {
    private static Thread Thread1;
    private static Thread Thread2;
    private static Thread Thread3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            startThreads();
            awaitThreads();
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public static void startThreads(){
            Thread1 = new Thread (new Person ("Bmeise", 15, 25));
            Thread1. start ();
            Thread2 = new Thread (new Dog ("Lessie", 25, 15));
            Thread2. start ();
            Thread3 = new Thread (new Dog ("Lessie", 25, 15));
            Thread3. start ();
    }
    
    public static void awaitThreads() throws InterruptedException{
        try {
            Thread2.join();
            Thread1.join();
            Thread3.join();
            System.out.println("Threads finished!");
        } catch (InterruptedException ex) {
            throw ex;
        }
    }
    
}
