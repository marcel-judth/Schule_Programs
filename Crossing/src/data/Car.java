/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import constants.Coordinates;
import fx.Controller;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car extends Task<String> {
    private static Controller controller;

    public static void setController(Controller controller) {
        Car.controller = controller;
    }

    private synchronized static void begin(Car c) throws InterruptedException {
        c.spawn(c.origin.getStartpoint());
        c.origin.beginCrossing();
        c.destination.beginCrossing();
    }

    private final int id;
    private final Town origin;
    private final Town destination;
    private ImageView image;
    private Coordinates position;

    public Car(int id, Town origin, Town destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public Town getOrigin() {
        return origin;
    }

    public Town getDestination() {
        return destination;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView iv) {
        this.image = iv;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    @Override
    protected String call() throws Exception {
        try {
            // create car
            begin(this);

            // calculate route
            List<Coordinates> route = Routing.getRoute(this.origin, this.destination);

            move(route);
            
            boolean hasToWait;
            
            controller.getCrossing().addWaitingCar(this);
            
            if(!this.origin.isIsVorrang())
                Thread.sleep(1000);
            else
                Thread.sleep(10);
            
            do{
                // wait for free road
                controller.getCrossing().getDrivePermit().acquire();
                hasToWait = !this.isAllowedToDrive();
                if(hasToWait){
                    controller.getCrossing().getDrivePermit().release();
                }
            } while(hasToWait);
            
            controller.getCrossing().removeWaitingCar(this);

            // cross
            move(route);
            controller.getCrossing().getDrivePermit().release();
            this.origin.endCrossing();
            
            // drive away
            move(route);
            this.destination.endCrossing();

            controller.removeCar(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "Car " + id + "reached " + destination.getName();
    }

    private void spawn(Coordinates place) {
        controller.placeCar(this, place);
    }

    private void move(List<Coordinates> route) throws InterruptedException {
        controller.moveCar(this, route.remove(0));
    }

    @Override
    public String toString() {
        return "Car#" + id;
    }

    private boolean isAllowedToDrive() {
        return controller.getCrossing().canCarMove(this);
    }
}
