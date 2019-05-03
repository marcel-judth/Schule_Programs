/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import petrolstation.FXMLDocumentController;

/**
 *
 * @author schueler
 */
public class CarGenerator extends Task<String>{
    private static FXMLDocumentController controller;
    private final int DEVIATION;
    private final int MSEC;
    private boolean end;
    private long timeBetweenArrival;
    private Semaphore semaPetrolPumpFree;
    private PetrolStation petrolstation;
    private ObservableList<String> obsList;
    
    private long servicetime;
    private int numberOfPP;
    
    private Semaphore semaKassaFree;
    
    public CarGenerator(long timeBetweenArrival, long servicetime, int numberOfPP, ObservableList<String> obsList, FXMLDocumentController controller) throws Exception {
        if(controller == null)
            throw new Exception("cardriver has no controller");
        this.timeBetweenArrival = Calculator.toMsec(timeBetweenArrival);
        this.obsList = obsList; 
        this.controller = controller;
        this.servicetime = Calculator.toMsec(servicetime);
        this.numberOfPP = numberOfPP;
        
        this.end = false;      
        DEVIATION = 20;
        MSEC = 1000;      
        petrolstation = new PetrolStation();
        generatePP(numberOfPP);
        
        //kassa
        generateKassa(2);
        
        PetrolPump.setServicetime(this.servicetime);
        semaPetrolPumpFree = new Semaphore(numberOfPP);
        
        //kassa
        Kassa.setServicetime(this.servicetime);
        semaKassaFree = new Semaphore(2);
    }

    @Override
    protected String call() throws Exception {      
        int numberOfDrivers = 1;  
        while(!end){
            long time = Calculator.random(timeBetweenArrival);
            Platform.runLater(()-> {obsList.add("next car is generated in: " + time + " msec ... [" +  Calculator.toMin(time) + " min.]");});
            Thread.sleep(time);
            new Thread(new CarDriver("driver", petrolstation, semaPetrolPumpFree, numberOfDrivers, obsList, controller, semaKassaFree)).start();
            Platform.runLater(()-> {obsList.add("car generated");});
            numberOfDrivers++;
        }
        Platform.runLater(()->{obsList.add("================= avg usage rate of petrol pump: "
        + Math.round(Calculator.calcUsageRateInPercent(servicetime, timeBetweenArrival, numberOfPP)));});
        return "x-x-x-x-x";
    }
    
    public void setEnd(){
        this.end = true;
    }

    private void generatePP(int numberOfPP) {
        for(int i = 0; i <= numberOfPP; i++){
            petrolstation.addPetrolPump(new PetrolPump("PP-" + (i+1), obsList));
        }
    }
    
    //Kassa
    private void generateKassa(int numberOfKassa){
        for(int i = 0; i <= numberOfKassa; i++){
            petrolstation.addKassa(new Kassa("K-" + (i+1) , obsList));
        }
    }
}
