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
 * @author Marcel Judth
 */
public class PetrolPump {
    private static long serviceTime;
    private final int DEVIATION = 20;
    private String name;
    private boolean free;
    ObservableList<String> obsList;
    private long usageTime;
    private long startTime;

    public PetrolPump(String name, ObservableList<String> obsList) {
        this.usageTime = 0;
        this.name = name;
        this.obsList = obsList;
        this.free = true;
        this.startTime = new Date().getTime();
    }
    
    public void doFuelUp() throws Exception{
        this.setFree(false);
        long fuelUpTime = TimeGenerator.getRandomTime(serviceTime, DEVIATION);
        this.informUser(this.getClass().getName() + " " + this.getName() + ": starts filling: " + fuelUpTime);
        Thread.sleep(fuelUpTime);
        this.informUser(this.getClass().getName() + " " + this.getName() + ": ends filling");
        this.setFree(true);
        this.usageTime = this.usageTime + fuelUpTime;
    }

    public String getName() {
        return name;
    }

    public double getUsageTimePercentage() {
        long exsistingTime = new Date().getTime() - this.startTime;
        return (double) this.usageTime / exsistingTime * 100;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public static long getServiceTime() {
        return serviceTime;
    }

    public static void setServiceTime(long serviceTime) {
        PetrolPump.serviceTime = serviceTime;
    }

    public long getUsageTime() {
        return usageTime;
    }
    
    @Override
    public String toString() {
        return "PetrolPump{" + "name=" + name + ", free=" + free + ", usageTime=" + usageTime + '}';
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
