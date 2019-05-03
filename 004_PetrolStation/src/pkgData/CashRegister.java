/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.Date;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author schueler
 */
public class CashRegister {
    private static long serviceTime;
    private final int DEVIATION = 20;
    private String name;
    private boolean free;
    ObservableList<String> obsList;
    private long usageTime;
    private long startTime;

    public CashRegister(String name, ObservableList<String> obsList) {
        this.name = name;
        this.obsList = obsList;
        this.usageTime = 0; 
        this.startTime = new Date().getTime();
        this.free = true;
    }

    void pay() throws Exception {
        this.setFree(false);
        long payTime = TimeGenerator.getRandomTime(serviceTime, DEVIATION);
        this.informUser(this.getClass().getName() + " " + this.getName() + ": payment starts: " + payTime);
        Thread.sleep(payTime);
        this.informUser(this.getClass().getName() + " " + this.getName() + ": payment ends");
        this.setFree(true);
        this.usageTime = this.usageTime + payTime;
    }

    
    boolean isFree() {
        return this.free;
    }
    
    public String getName() {
        return name;
    }
    public static long getServiceTime() {
        return serviceTime;
    }
    
    private void setFree(boolean b) {
        this.free = b;
    }

    public static void setServiceTime(long serviceTime) {
        CashRegister.serviceTime = serviceTime;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getUsageTimePercentage() {
        long exsistingTime = new Date().getTime() - this.startTime;
        return (double) this.usageTime / exsistingTime * 100;
    }

    public long getUsageTime() {
        return usageTime;
    }
    
    @Override
    public String toString() {
        return "CashRegister{" + "name=" + name + ", free=" + free + ", usageTime=" + usageTime + '}';
    }
    
    private void informUser(String value){
        System.out.println(value);
        Platform.runLater(new Runnable() {
            public void run() {
                obsList.add(value);
            }
        });
    }    
    
}
