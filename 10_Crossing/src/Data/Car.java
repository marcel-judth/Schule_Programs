/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Controller.SimulationController;
import helpers.AnimCoordinates;
import helpers.TimeGenerator;
import java.awt.Point;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class Car extends Task<String> implements AnimCoordinates {

    private static final int DEVIATION = 20;
    private static SimulationController controller;
    private static int nextID = 0;
    private int id;
    private String name;
    private Town orgin;
    private Town destination;
    private long waitingTime;
    private Point currentLocation;
    private Point oldLocation;

    public Car(String name, Town orgin, Town destination, long waitingTime) throws Exception {
        if (controller == null) {
            throw new Exception("Car Controller is null!");
        }
        this.id = getNextID();
        this.name = name;
        this.orgin = orgin;
        this.destination = destination;
        this.waitingTime = waitingTime;
        this.currentLocation = orgin.getStartPoint();
    }

    @Override
    protected String call() throws Exception {
        this.controller.addNewCarImage(this);
        this.orgin.getDrivingPermit().acquire();
        this.oldLocation = this.currentLocation;
        this.currentLocation = this.orgin.getCrossingPoint();
        this.moveCar();
        Thread.sleep(1000);
        
        this.orgin.getDrivingPermit().release();
        this.removeCar();
        return "finished!";
    }

    private void moveCar() {
        try {
            controller.doAnimationMoving(Car.this);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeCar() {
        Platform.runLater(() -> {
            try {
                controller.removeImage(Car.this);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private static int getNextID() {
        nextID++;
        return nextID;
    }

    public static void setNextID(int nextID) {
        Car.nextID = nextID;
    }

    public Town getOrgin() {
        return orgin;
    }

    public void setOrgin(Town orgin) {
        this.orgin = orgin;
    }

    public Town getDestination() {
        return destination;
    }

    public void setDestination(Town destination) {
        this.destination = destination;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Point getOldLocation() {
        return oldLocation;
    }

    public void setOldLocation(Point oldLocation) {
        this.oldLocation = oldLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public static void setController(SimulationController controller) {
        Car.controller = controller;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", name=" + name + ", orgin=" + orgin.getName() + ", destination=" + destination.getName() + ", waitingTime=" + waitingTime + ", currentLocation=" + currentLocation + ", oldLocation=" + oldLocation + '}';
    }

}
