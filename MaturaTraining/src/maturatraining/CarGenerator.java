/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturatraining;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @author schueler
 */
public class CarGenerator extends Task<String> {

    final int DEVIATION = 20;
    final int MSEC = 1000;
    private boolean end = false;
    //private static long timeBetweenArrival=0.5;
    Semaphore roadMiddle, road_up, road_down;
    private static FXMLDocumentController controller;

    public CarGenerator() {
        this.roadMiddle = new Semaphore(1);
        this.road_up = new Semaphore(1);
        this.road_down = new Semaphore(1);

    }

    @Override
    protected String call() throws Exception {
        int idCounter = 1;

        while (!end) {
            try {
                //long time = MathCalc.calcNumberWithDeviation(timeBetweenArrival * MSEC, DEVIATION);
                System.out.println("next car is generated in: 414 msec...");
                int time = ThreadLocalRandom.current().nextInt(1000, 3000 + 1);
                System.out.println("next car is generated in: " + time + " msec...");
                Thread.sleep(time);

                Location start = getRandomLocation();
                Location end = getRandomLocation();
                while (end == start) {
                    end = getRandomLocation();
                }

                Car cd = new Car(idCounter, roadMiddle, road_up, road_down, start, end);
                createCar();
                idCounter++;
                System.out.println("car generated");
                cd.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        System.out.println("delete hahah");
                        System.out.println(""+cd.getValue());
                        controller.deleteCar(cd);
                    }
                });
                    new Thread((Runnable) cd).start();

                
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ex : " + ex.getMessage());
            }

        }
        return "all cars generated and finished";
    }

    public static void setController(FXMLDocumentController controllerC) {
        CarGenerator.controller = controllerC;
    }

    private void createCar() {
        Platform.runLater(() -> controller.createCar());
    }

    private Location getRandomLocation() {
        Location[] cards = Location.values();
        Random random = new Random();
        return cards[random.nextInt(cards.length)];
    }

}
