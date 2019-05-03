/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import helpers.TimeGenerator;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class CarGenerator extends Task<String>{
    private static final int DEVIATION = 20;
    private boolean isEnd;
    private ArrayList<Car> colCars;
    private long waitingTime;
    private long timeBetweenArrival;
    private final ObservableList<String> obsList;
    private Crossing crossing;


    public CarGenerator(Crossing crossing, long timeBetweenArrival, long waitingTime, ObservableList<String> obsList) {
        this.timeBetweenArrival = timeBetweenArrival;
        this.waitingTime = waitingTime;
        this.obsList = obsList;
        this.colCars = new ArrayList<>();
        this.isEnd = false;
        this.crossing = crossing;
    }

    
    
    @Override
    protected String call() {
        try{
        int countCars = 0;
        while(!this.isEnd){
            long rndTime = TimeGenerator.getRandomTime(this.timeBetweenArrival, DEVIATION);
            this.informUser("next car is generated in: " + rndTime + "...[" + (double)rndTime/1000 + "min.]");
            Thread.sleep(rndTime);
            int index = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            int index2 = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            while(index == index2)
                index2 = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            Car car = new Car("Car-" + countCars, crossing.getTowns().get(index), crossing.getTowns().get(index2), 2000);
            new Thread(car).start();
            this.informUser("generated: " + car.toString());
        }
        }catch(Exception ex){
            java.util.logging.Logger.getLogger(CarGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "finished";
    }
    
    private void informUser(String value){
        System.out.println(value);
        Platform.runLater(() -> {
            obsList.add(value);
        });
    }    
}
