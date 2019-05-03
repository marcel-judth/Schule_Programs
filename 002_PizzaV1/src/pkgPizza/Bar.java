/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgPizza;

import java.util.TreeSet;

/**
 *
 * @author Marcel Judth
 */
public class Bar {
    private final int pizzaCapacity = 2;
    private static TreeSet<Order> currentOrders;
    private static TreeSet<Order> finishedOrders;
    
    public Bar() {
        Bar.currentOrders = new TreeSet<>();
        Bar.finishedOrders = new TreeSet<>();
    }
    
    public synchronized void addOrder(Order o) throws Exception{
        Bar.currentOrders.add(o);
    }
    
    public synchronized Order getNextOrder() throws Exception{
        if(Bar.currentOrders.isEmpty())
            throw new Exception("No current orders!");
        Order o = Bar.currentOrders.first();
        Bar.currentOrders.remove(o);
        return o;
    }
    
    public synchronized void addFinishedOrder(Order order) throws Exception {        
        Bar.finishedOrders.add(order);
    }
    
    public Pizza getPizza(Order o) throws Exception{
        Pizza p = null;
        for(Order order : Bar.finishedOrders){
            if(order.getId() == o.getId()){
                p = order.getPizza();
                Bar.finishedOrders.remove(order);
            }
        }
        if(p == null)
            throw new Exception("Order " + o.toString() + " is not finished!!");
        return p;
    }

    public boolean hasOrder() {
        return !Bar.currentOrders.isEmpty();
    }

    public boolean orderIsFinished(Order order)  {
        return Bar.finishedOrders.contains(order);
    }
   
}
