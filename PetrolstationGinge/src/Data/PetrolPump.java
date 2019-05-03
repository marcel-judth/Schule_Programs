/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author schueler
 */
public class PetrolPump {
    private static long servicetime;
    //private final int DEVIATION;
    private String name;
    private boolean free = true;
    ObservableList<String> obsList;

    public PetrolPump(String name, ObservableList<String> obsList) {
        this.name = name;
        this.obsList = obsList;
        
        //this.DEVIATION = 20;
    }
    
    public void doFuelUp() throws Exception{
        if(free){
            free = false;
            long fillingTime = Calculator.random(servicetime);
            Platform.runLater(()-> {obsList.add("petrol pump " + getName() + " starts filling: " + fillingTime +" msec");});
            Thread.sleep(fillingTime);
            Platform.runLater(()-> {obsList.add("petrol pump " + getName() + " ends filling");});
            free = true;
        }else if(!free)
            throw new Exception("petrop pump isn't free");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isFree(){
        boolean result = false;
        if(free)
            result = true;
        return result;
    }
    
    public void setFree(boolean free) {
        this.free = free;
    }
    
    public static long getServicetime() {
        return servicetime;
    }
    
    public static void setServicetime(long servicetime) {
        PetrolPump.servicetime = servicetime;
    }   
}
