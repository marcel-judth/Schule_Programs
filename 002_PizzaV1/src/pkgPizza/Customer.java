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
public class Customer extends Person  {
    private static final int MIN_EATING_DURATION = 2000;
    private static final int MAX_EATING_DURATION = 7000;
    private final static int X_COO_STARTPOSITION = 850;
    private static final int X_COO_POSITION1 = 965;
    private static final int X_COO_POSITION2 = 1075;
    private static final int X_COO_POSITION3 = 1190;
    private static final int X_COO_POSITION4 = 1300;

    
    public Customer(String name, Bar bar, Semaphore semBarFree, Semaphore semaPizzaOnBar, Semaphore semaOrderExists) {
        super(name, bar, semBarFree, semaPizzaOnBar, semaOrderExists);
    }

    @Override
    protected String call() throws Exception {
        try {
            while(!this.isEnd){
                Thread.sleep(2000);
                System.out.println(this.getClass().getName() + " " + this.name + ": is hungry and creates order");
                Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION1));
                Thread.sleep(2000);
                Order order = new Order(this);
                this.bar.addOrder(order);
                this.semaOrderExists.release();
                System.out.println(this.getClass().getName() + " " + this.name + ": created Order " + order.toString());
                Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION2));
                System.out.println(this.getClass().getName() + " " + this.name + ": starts waiting for pizza");
                boolean orderIsFinished = false;
                while(!orderIsFinished){
                    this.semBarFree.acquire();
                    orderIsFinished = this.bar.orderIsFinished(order);
                    if(!orderIsFinished)
                        this.semBarFree.release();
                }
                Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION3));
                Thread.sleep(2000);
                Pizza p = order.getPizza();
                System.out.println(this.getClass().getName() + " " + this.name + ": got pizza from order " + order.toString());
                this.semBarFree.release();
                this.semaPizzaOnBar.release();
                Platform.runLater(()-> this.setDoubleProperty(X_COO_POSITION4));
                Thread.sleep(RND.nextInt(MAX_EATING_DURATION - MIN_EATING_DURATION) + MIN_EATING_DURATION);
                Platform.runLater(()-> this.setDoubleProperty(X_COO_STARTPOSITION));
            }
            System.out.println(this.getClass().getName() + " " + this.name + " has finished ");
        } catch (Exception ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "finished";
    }

    @Override
    public void start() {
        
    }
    
    @Override
    public void setEnd() {
        this.isEnd = true;
    }
    
    @Override
    public String toString() {
        return "Customer{" + "name=" + name + '}';
    }
}
