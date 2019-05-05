/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 *
 * @author schueler
 */
public class Crossing {
    private final List<Town> towns;
    private Point middle;
    private final Map<Town, Car> waitingCars;
    private Semaphore drivePermission;

    public Crossing(Point middle) {
        this.middle = middle;
        this.waitingCars = new HashMap<>();
        this.towns = new ArrayList<>();
        this.drivePermission = new Semaphore(1);
    }
    
    public void addTown(Town town){
        this.towns.add(town);
    }
    
    public List<Town> getTowns() {
        return towns;
    }

    public Point getMiddle() {
        return middle;
    }

    public Semaphore getDrivingPermission() {
        return drivePermission;
    }

    public Map<Town, Car> getWaitingCars() {
        return waitingCars;
    }
    
    public boolean isAllowedToDrive(Car c){
        if(c.getOrgin().hasVorrang()){
            if(c.getOrgin().getLeft() == c.getDestination()){
                if(this.waitingCars.containsKey(c.getOrgin().getOpposite()))
                    return false;
                return true;
            }
            return true;
        }else{
            if(c.getOrgin().getRight() == c.getDestination() && !this.waitingCars.containsKey(c.getOrgin().getLeft()))
                return true;
            else
            if(c.getOrgin().getOpposite() == c.getDestination() && !this.waitingCars.containsKey(c.getOrgin().getLeft()) && 
                    !this.waitingCars.containsKey(c.getOrgin().getRight()))
                return true;
            else
            if(c.getOrgin().getLeft()== c.getDestination() && !this.waitingCars.containsKey(c.getOrgin().getLeft()) && 
                    !this.waitingCars.containsKey(c.getOrgin().getRight()))
                return true;
            return false;
        }
    }

    void addWaitingCar(Car c) {
        this.waitingCars.put(c.getOrgin(), c);
    }

    void removeWaitingCar(Car c) {
        this.waitingCars.remove(c.getOrgin(), c);
    }
}
