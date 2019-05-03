/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import petrolstation.FXMLDocumentController;

/**
 *
 * @author schueler
 */
public class CarDriver extends Task<String> implements AnimCoordinates{
    private static FXMLDocumentController controller;
    private String nameOfDriver = null;
    private PetrolStation station = null;
    private Semaphore semaPetrolPumpFree = null;
    private long timeStart;
    private long timeEnd;
    private ObservableList<String> obsList = null;
    private int driverId = 0;
    private int oldCooX = XCOO_START;
    private int currentCooX = XCOO_START;
    
    private Semaphore semaKassaFree = null;

    public CarDriver(String nameOfDriver, PetrolStation station, Semaphore semaPetrolPumpFree, int driverId, 
            ObservableList<String> obsList,FXMLDocumentController controller, Semaphore semaKassaFree) throws Exception {
        if(controller == null)
            throw new Exception("cardriver has no controller");
        this.nameOfDriver = nameOfDriver + "-" + driverId;
        this.station = station;
        this.semaPetrolPumpFree = semaPetrolPumpFree;
        this.obsList = obsList;
        this.driverId = driverId;
        this.controller = controller;
        
        this.semaKassaFree = semaKassaFree;
        
    }

    @Override
    protected String call() throws Exception {
        PetrolPump pp = null;
        Kassa k = null;
        try{
            //Start
            timeStart = System.currentTimeMillis();
            Platform.runLater(()-> {obsList.add(nameOfDriver + ": driving in patrol station");});
            Platform.runLater(()-> {obsList.add(nameOfDriver + ": waiting for free pump");});
            Platform.runLater(() -> controller.createCar(this));
            
            //waiting for pump
            currentCooX = XCOO_WAITING_PUMP;
            moveCar();
            semaPetrolPumpFree.acquire();
            pp = station.getFreePump();
            String name = pp.getName();
            Platform.runLater(()-> {obsList.add(nameOfDriver + ": got free pump with name: " + name);});
            Platform.runLater(()-> {obsList.add(nameOfDriver+ ": starts pumping");});
            oldCooX = currentCooX;
            currentCooX = XCOO_PUMPING;
            moveCar();
            pp.doFuelUp();       
            Platform.runLater(()-> {obsList.add(nameOfDriver + ": ends pumping");});
            semaPetrolPumpFree.release();
            
            //go to kassa
            semaKassaFree.acquire();
            k = station.getFreeKassa();
            String kName = k.getName();
            Platform.runLater(()->{obsList.add(nameOfDriver + ": got free Kassa with name: " + kName);});
            Platform.runLater(()->{obsList.add(nameOfDriver + ": starts paying");});
            oldCooX = currentCooX;
            currentCooX = XCOO_KASSA;
            moveCar();
            k.doPaying();
            Platform.runLater(()-> {obsList.add(nameOfDriver + ": ends paying");});
            semaKassaFree.release();
            
            //exit
            timeEnd = System.currentTimeMillis();
                //BigDecimal bd = new BigDecimal(((double) getResponseTime()) / 1000, );
            Platform.runLater(()-> {obsList.add("***** " + nameOfDriver + ": leaving patrol station with responsetime: " + getResponseTime() + " msec ...[" 
            + Calculator.toMin(getResponseTime())+" min.]");});
            oldCooX = currentCooX;
            currentCooX = XCOO_EXIT;
            moveCar();            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "x-x-x-x-x";
    }

    public String getNameOfDriver() {
        return nameOfDriver;
    }
    
    public long getResponseTime(){
        return timeEnd - timeStart;
    }

    public int getDriverId() {
        return driverId;
    }

    public int getOldCooX() {
        return oldCooX;
    }

    public int getCurrentCooX() {
        return currentCooX;
    }

    public static void setController(FXMLDocumentController controller) {
        CarDriver.controller = controller;
    }
    
    public void moveCar(){
        Platform.runLater(() ->{
            try{
                controller.doAnimationMoving(this);
            }catch(Exception ex){
                Logger.getLogger(CarDriver.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
}
