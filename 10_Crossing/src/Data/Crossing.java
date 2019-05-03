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

/**
 *
 * @author schueler
 */
public class Crossing {
    private final List<Town> towns;
    private Point middle;
    private final Map<Town, Car> waitingCars;

    public Crossing(Point middle) {
        this.middle = middle;
        this.waitingCars = new HashMap<>();
        this.towns = new ArrayList<>();
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

    public Map<Town, Car> getWaitingCars() {
        return waitingCars;
    }
    
    
}
