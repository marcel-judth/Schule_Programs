/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class Shipment {
    private int shId;
    private String gondoliere;
    private String details;
    
    
    public Shipment(int shId, String gondoliere, String details) {
        this.shId = shId;
        this.details = details;
        this.gondoliere = gondoliere;
    }
    
    public int getShId() {
        return shId;
    }

    public void setShId(int shId) {
        this.shId = shId;
    }

    public String getGondoliere() {
        return gondoliere;
    }

    public void setGondoliere(String gondoliere) {
        this.gondoliere = gondoliere;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Shipment{" + "shId=" + shId + ", gondoliere=" + gondoliere + ", details=" + details + '}';
    }

    
}
