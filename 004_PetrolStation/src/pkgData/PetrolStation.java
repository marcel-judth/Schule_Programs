/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.ArrayList;

/**
 *
 * @author Marcel Judth
 */
public class PetrolStation {
    private final ArrayList<PetrolPump> colPetrolPumps;
    private final ArrayList<CashRegister> colCashRegister;


    public PetrolStation() {
        this.colPetrolPumps = new ArrayList<>();
        this.colCashRegister = new ArrayList<>();
    }
    
    public void add(PetrolPump pp){
        this.colPetrolPumps.add(pp);
    }
    
    public void add(CashRegister cr){
        this.colCashRegister.add(cr);
    }
    
    public PetrolPump getFreePump(){
        for(PetrolPump p : this.colPetrolPumps){
            if(p.isFree())
                return p;
        }
        return null;
    }
    
    public CashRegister getFreeRegister(){
        for(CashRegister c : this.colCashRegister){
            if(c.isFree())
                return c;
        }
        return null;
    }

    public double calculateUsageRatePP() {
        long usageTimesPercentage = 0;
        for(PetrolPump pump : this.colPetrolPumps){
            usageTimesPercentage += pump.getUsageTimePercentage();
        }
        return (usageTimesPercentage / this.colPetrolPumps.size());
    }
    
    public double calculateUsageRateCR() {
        long usageTimesPercentage = 0;
        for(CashRegister c : this.colCashRegister){
            usageTimesPercentage += c.getUsageTimePercentage();
        }
        return (usageTimesPercentage / this.colPetrolPumps.size());
    }

    long getAveragePumpingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
