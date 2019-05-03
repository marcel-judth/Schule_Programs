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
public class Kassa {
    private static long servicetime;
    private String name;
    private boolean free = true;
    ObservableList<String> obsList;

    public Kassa(String name, ObservableList<String> obsList) {
        this.name = name;
        this.obsList = obsList;
    }
    
    public void doPaying() throws Exception{
        if(free){
            free = false;
            long payingTime = Calculator.random(servicetime);
            Platform.runLater(()-> {obsList.add("Kassa " + getName() + " starts paying: " + payingTime +" msec");});
            Thread.sleep(payingTime);
            Platform.runLater(()-> {obsList.add("Kassa " + getName() + " ends paying");});
            free = true;
        }else if(!free)
            throw new Exception("kassa isn't free");
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
        Kassa.servicetime = servicetime;
    }
}
