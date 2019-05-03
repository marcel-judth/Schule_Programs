/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgPizza;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Marcel Judth
 */
public class Cook extends Person{  
    private final static int MIN_PIZZA_DURATION = 2000;
    private final static int MAX_PIZZA_DURATION = 7000;
        private final static int X_COO_STARTPOSITION = 105;
    private final static int X_COO_POSITION1 = 250;
    private final static int X_COO_POSITION2 = 395;
    private final static int X_COO_POSITION3 = 535;

    
    public Cook(String name, Bar bar, Semaphore semBarFree, Semaphore semaPizzaOnBar, Semaphore semaOrderExists) {
        super(name, bar, semBarFree, semaPizzaOnBar, semaOrderExists);
    }
    
    @Override
    public void setEnd() {
        this.isEnd = true;
        this.semaPizzaOnBar.release();
        this.semaOrderExists.release();
    }

    @Override
    protected String call() throws Exception {
        try {
            while(!isEnd){
                System.out.println(this.getClass().getName() + " " + this.name + ": is waiting for next order");
                if(!isEnd){
                    Thread.sleep(1000);
                    this.semaOrderExists.acquire();
                    Order o = this.bar.getNextOrder();
                    Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION1));
                    System.out.println(this.getClass().getName() + " " + this.name + ": got order " + o.toString());
                    Pizza p = new Pizza("Pizza");
                    System.out.println(this.getClass().getName() + " " + this.name + ": starts creating "  + p.toString());
                    Thread.sleep(RND.nextInt(MAX_PIZZA_DURATION - MIN_PIZZA_DURATION) + MIN_PIZZA_DURATION);
                    Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION2));
                    Thread.sleep(500);
                    o.setPizza(p);
                    System.out.println(this.getClass().getName() + " " + this.name + ": finished creating "  + p.toString());

                    System.out.println(this.getClass().getName() + " " + this.name + ": starts waiting for free bar");
                    Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION3));
                    this.semaPizzaOnBar.acquire();
                    this.bar.addFinishedOrder(o);

                    Thread.sleep(1000);
                    
                    System.out.println(this.getClass().getName() + " " + this.name + ": laying on bar " + p.toString());
                    Thread.sleep(1000);
                    this.semBarFree.release();
                    Platform.runLater(()-> this.setDoubleProperty(X_COO_STARTPOSITION));
                }
               }
            System.out.println(this.getClass().getName() + " " + this.name + ": has finished");
        } catch (Exception ex) {
            Logger.getLogger(Cook.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return "finished";
    }

    @Override
    public void start() {
        
    }
    
}
