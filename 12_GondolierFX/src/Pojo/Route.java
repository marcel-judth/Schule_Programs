/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.time.LocalDate;

/**
 *
 * @author schueler
 */
public class Route {
    private Shipment shipment;
    private int routeId;
    private Section section;
    private LocalDate routeStartTime;
    private LocalDate routeEndTime;

    public Route(Shipment shipment, int routeId, Section section, LocalDate routeStartTime, LocalDate routeEndTime) {
        this.shipment = shipment;
        this.routeId = routeId;
        this.section = section;
        this.routeStartTime = routeStartTime;
        this.routeEndTime = routeEndTime;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public LocalDate getRouteStartTime() {
        return routeStartTime;
    }

    public void setRouteStartTime(LocalDate routeStartTime) {
        this.routeStartTime = routeStartTime;
    }

    public LocalDate getRouteEndTime() {
        return routeEndTime;
    }

    public void setRouteEndTime(LocalDate routeEndTime) {
        this.routeEndTime = routeEndTime;
    }

    @Override
    public String toString() {
        return "Route{" + "shipment=" + shipment.getShId() + ", routeId=" + routeId + ", section=" + section + ", routeStartTime=" + routeStartTime + ", routeEndTime=" + routeEndTime + '}';
    }
    
}
