/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import DataAccess.Database;
import Pojo.Shipment;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author schueler
 */
@ManagedBean(name = "listShipments")
@SessionScoped
public class ListShipments implements Serializable{
    private ArrayList<Shipment> shipments;
    private String username;
    private String message;
    private String shipmentid;
    private String shipmentdetails;
    /**
     * Creates a new instance of ListShipments
     */
    public ListShipments() {
        try {
            this.username = Database.getCurrentName().getUsername();
            this.shipments = Database.getShipmentsByUsername(username);
            this.message = "# of found shipments: " + shipments.size();
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListShipments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(ArrayList<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShipmentid() {
        return shipmentid;
    }

    public void setShipmentid(String shipmentid) {
        this.shipmentid = shipmentid;
    }

    public String getShipmentdetails() {
        return shipmentdetails;
    }

    public void setShipmentdetails(String shipmentdetails) {
        this.shipmentdetails = shipmentdetails;
    }
    
    public void newShippment(){
        try {
            Database.insertShipment(this.shipmentid, Database.getCurrentName(), this.shipmentdetails);
            message = "inserted!";
            this.shipments = Database.getShipmentsByUsername(Database.getCurrentName().getUsername());
        } catch (SQLException ex) {
            this.message = ex.getMessage();
            Logger.getLogger(ListShipments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
