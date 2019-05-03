/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Service;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author schueler
 */
@ManagedBean
@SessionScoped
public class ListServices implements Serializable{
    @ManagedProperty(value = "#{listAllBookings}")
    private ListAllBookings listAllBookings = null;
    private ArrayList<Service> services;
    private String message;
    /**
     * Creates a new instance of ListServices
     */
    public ListServices() {
    }

    public ArrayList<Service> getServices() {
        try {
            services = Database.getServices(listAllBookings.getCurrentBooking());
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ListAllBookings getListAllBookings() {
        return listAllBookings;
    }

    public void setListAllBookings(ListAllBookings listAllBookings) {
        this.listAllBookings = listAllBookings;
    }
    
    public void delete(Service service){
        try {
            Database.delete(service);
            message = "service: " + service + " deleted!";
        } catch (Exception ex) {
            message = ex.getMessage();
            Logger.getLogger(ListServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
