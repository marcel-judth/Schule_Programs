/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author Marcel Judth
 */
public class CarGenerator extends Task<String>{
    private static final int DEVIATION = 20;
    private static final int MSEC = 1000;
    private boolean end;
    private final long timeBetweenArrival;
    private final Semaphore semaPertrolPumpFree;
    private final Semaphore semaCashRegisterFree;
    private final PetrolStation petrolstation;
    private final ObservableList<String> obsList;
    private final ArrayList<CarDriver> colCars;
    private final ArrayList<PetrolPump> colPP;
    private final ArrayList<CashRegister> colCR;


    public CarGenerator(long timeBetweenArrival,long serviceTimePP, int numberOfPP,long serviceTimeCR, int numberofCR, ObservableList<String> obsList) {
        this.timeBetweenArrival = timeBetweenArrival * MSEC;
        this.obsList = obsList;
        this.end = false;
        this.colCars = new ArrayList<>();
        this.colPP = new ArrayList<>();
        this.colCR = new ArrayList<>();

                
        this.semaPertrolPumpFree = new Semaphore(numberOfPP);
        this.semaCashRegisterFree = new Semaphore(numberofCR);
        this.petrolstation = new PetrolStation();
        PetrolPump.setServiceTime(serviceTimePP * MSEC);
        CashRegister.setServiceTime(serviceTimeCR * MSEC);
        for(int i = 0; i < numberOfPP; i++){
            PetrolPump pp = new PetrolPump("PP " + i, this.obsList);
            this.petrolstation.add(pp);
            this.colPP.add(pp);
        }
            
        for(int i = 0; i < numberofCR; i++){
            CashRegister cc = new CashRegister("Cr", obsList);
            this.petrolstation.add(cc);
            this.colCR.add(cc);
        }
    }

    @Override
    protected String call() throws Exception {
        int countDriver = 0;
        System.out.println("*******Starting car generator");
        
        while(!this.end){
            long rndTime = TimeGenerator.getRandomTime(this.timeBetweenArrival, DEVIATION);
            this.informUser("next car is generated in: " + rndTime + "...[" + (double)rndTime/1000 + "min.]");
            Thread.sleep(rndTime);
            CarDriver cd = new CarDriver("driver " + countDriver++, this.petrolstation, this.semaPertrolPumpFree, this.semaCashRegisterFree, this.obsList);
            this.colCars.add(cd);
            new Thread(cd).start();
            this.informUser("car generated");
        }
        
        double usageRatePP = this.petrolstation.calculateUsageRatePP();
        double usageRateCR = this.petrolstation.calculateUsageRateCR();

        
        this.informUser("=================== avg usage rate of petrol pumps: " + usageRatePP + "%");
        this.informUser("=================== avg usage rate of cash register: " + usageRateCR + "%");
        return "finished!";
    }
    
    public double getAveragePercentagPP(){
        return this.petrolstation.calculateUsageRatePP();
    }
    
    public double getAveragePercentagCR(){
        return this.petrolstation.calculateUsageRateCR();
    }

    public long getAveragePumpingTime(){
        return this.petrolstation.getAveragePumpingTime();
    }
    
    public double getWaitingPercentagePP(){
        long waitingTime = 0;
        for(CarDriver cd : this.colCars){
            waitingTime += cd.getWaitingTimePP();
        }
        
        return ((double)waitingTime / this.colCars.size()) / MSEC;
    }
    
    public double getWaitingPercentageCR(){
        long waitingTime = 0;
        for(CarDriver cd : this.colCars){
            waitingTime += cd.getWaitingTimeCR();
        }
        
        return ((double)waitingTime / this.colCars.size()) / MSEC;
    }
    
    public double getAverageExitTime(){
        long waitingTime = 0;
        for(CarDriver cd : this.colCars){
            waitingTime += cd.getRespondTime();
        }
        
        return ((double)waitingTime / this.colCars.size()) / MSEC;
    }
    
    public double getAverageUsageTimePP(){
        long waitingTime = 0;
        for(PetrolPump pp : this.colPP){
            waitingTime += pp.getUsageTime();
        }
        
        return ((double)waitingTime / this.colCars.size()) / MSEC;
    }
    
    public double getAverageUsageTimeCR(){
        long waitingTime = 0;
        for(CashRegister cr : this.colCR){
            waitingTime += cr.getUsageTime();
        }
        
        return ((double)waitingTime / this.colCars.size()) / MSEC;
    }
    
    public void setEnd() {
        this.end = true;
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
