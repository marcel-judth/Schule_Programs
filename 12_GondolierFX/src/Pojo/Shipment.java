/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import Controllers.SimulationController;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class Shipment extends Task<String>{
    private int shId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String gondoliere;
    private ArrayList<Route> collRoutes;
    private static SimulationController controller;

    public Shipment(int shId, LocalDate startDate, LocalDate endDate, String gondoliere) {
        this.shId = shId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gondoliere = gondoliere;
        this.collRoutes = new ArrayList<>();
    }

    public void addRoute(Route route){
        this.collRoutes.add(route);
    }
    
    public void deleteRoute(Route route){
        this.collRoutes.remove(route);
    }
    
    public int getShId() {
        return shId;
    }

    public void setShId(int shId) {
        this.shId = shId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGondoliere() {
        return gondoliere;
    }

    public void setGondoliere(String gondoliere) {
        this.gondoliere = gondoliere;
    }

    public ArrayList<Route> getCollRoutes() {
        return collRoutes;
    }

    public void setCollRoutes(ArrayList<Route> collRoutes) {
        this.collRoutes = collRoutes;
    }

    public static void setController(SimulationController controller) {
        Shipment.controller = controller;
    }

    @Override
    public String toString() {
        return "Shipment{" + "shId=" + shId + ", startDate=" + startDate + ", endDate=" + endDate + ", gondoliere=" + gondoliere + ", collRoutes=" + collRoutes + '}';
    }

    @Override
    protected String call() throws Exception {
        System.out.println(this.toString());
        for(Route r : this.collRoutes){
            r.getSection().getDrivePermission().acquire();
            System.out.println(this.shId + "in call" + r.getSection().getLocEnd().getCoo());
            controller.moveGondolier(this.shId, r.getSection().getLocEnd().getCoo());
            Thread.sleep(1000);
            r.getSection().getDrivePermission().release();
        }
        return "";
    }
}
