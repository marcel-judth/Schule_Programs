/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import fx.Controller;
import javafx.concurrent.Task;

import static constants.Constants.CAR_SPAWN_DELAY;

public class CarGenerator extends Task<String> {
    private static Controller controller;
    private static final Random random = new Random(Instant.now().getEpochSecond());

    public static void setController(Controller controller) {
        CarGenerator.controller = controller;
    }

    private final Crossing crossing;
    private List<Car> cars;

    private boolean active = true;

    public CarGenerator(Crossing crossing) throws Exception {
        this.crossing = crossing;

        Car.setController(controller);

        cars = new ArrayList<>();
    }

    @Override
    protected String call() throws Exception {
        int carId = 1;
        try {
            
            while (active) {
                Town origin = getRandomTown(null);
                Town destination = getRandomTown(origin);

                Car car = new Car(carId++, origin, destination);
                new Thread(car).start();

                controller.log(car + " generated (" + car.getOrigin() + " -> " + car.getDestination() + ")");

                Thread.sleep(CAR_SPAWN_DELAY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "simulation finished";
    }

    private Town getRandomTown(Town origin) {
        List<Town> towns = crossing.getTowns().stream()
                .filter(t -> !t.equals(origin))
                .collect(Collectors.toList());
        return towns.get(random.nextInt(towns.size()));
    }

    public void start() {
        new Thread(this).start();
        controller.log("simulation started");
    }

    public void stop() {
        this.active = false;
        controller.log("simulation stopped");
    }
}
