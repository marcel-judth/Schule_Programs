package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Crossing {
    private final List<Town> towns;
    
    private final Map<Town, Car> waitingCars;

    private final Semaphore drivePermit = new Semaphore(1);

    public Crossing() {
        towns = new ArrayList<>();
        waitingCars = new HashMap<>();
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void addTown(Town town) {
        towns.add(town);
    }

    public Semaphore getDrivePermit() {
        return drivePermit;
    }
    
    public void addWaitingCar(Car c){
        waitingCars.put(c.getOrigin(), c);
    }
    
    public void removeWaitingCar(Car c){
        if(waitingCars.get(c.getOrigin()) == c){
            waitingCars.remove(c.getOrigin());
        }
    }
    
    public boolean canCarMove(Car c){
        if(c.getOrigin().isIsVorrang()){
            if(c.getDestination() == c.getOrigin().getOpposite())
                return true;
            if(c.getDestination() == c.getOrigin().getRight())
                return true;
            if(c.getDestination() == c.getOrigin().getLeft() && !waitingCars.containsKey(c.getOrigin().getOpposite()))
                return true;
        }
        else {
           if(c.getDestination() == c.getOrigin().getOpposite() && !waitingCars.containsKey(c.getOrigin().getLeft()) && !waitingCars.containsKey(c.getOrigin().getRight()) && !waitingCars.containsKey(c.getOrigin().getOpposite()))
                return true;
            if(c.getDestination() == c.getOrigin().getRight() && !waitingCars.containsKey(c.getOrigin().getLeft()))
                return true;
            if(c.getDestination() == c.getOrigin().getLeft() && !waitingCars.containsKey(c.getOrigin().getLeft()) && !waitingCars.containsKey(c.getOrigin().getRight())
                    && (!waitingCars.containsKey(c.getOrigin().getOpposite()) || waitingCars.get(c.getOrigin().getOpposite()).getDestination() == c.getOrigin().getRight()))
                return true; 
        }
        return false;
    }
}
