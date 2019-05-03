/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;

/**
 *
 * @author schueler
 */
public class PetrolStation {
    private ArrayList<PetrolPump> collPetrolPumps;
    //kassa
    private ArrayList<Kassa> collKassa;

    public PetrolStation() {
        collPetrolPumps = new  ArrayList<PetrolPump>();
        
        //Kassa
        collKassa = new ArrayList<Kassa>();
    }
    
    public void addPetrolPump(PetrolPump pp){
        collPetrolPumps.add(pp);
    }
    
    //Kassa
    public void addKassa(Kassa k){
        collKassa.add(k);
    }
    
    public PetrolPump getFreePump(){
        PetrolPump freePump = null;
            for(int idx = 0; idx < collPetrolPumps.size() && freePump == null; idx++){
                if(collPetrolPumps.get(idx).isFree())
                    freePump = collPetrolPumps.get(idx);
            }
        return freePump;
    }
    
    //Kassa
    public Kassa getFreeKassa(){
        Kassa freeKassa = null;
        for(int idx = 0; idx < collKassa.size() && freeKassa == null; idx++){
            if(collKassa.get(idx).isFree())
                freeKassa = collKassa.get(idx);
        }
        return freeKassa;
    }
}
