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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class NewService implements Serializable{

    @ManagedProperty(value = "#{listAllBookings}")
    private ListAllBookings listAllBookings = null;
    private String serviceDate;
    private String serviceDesc;
    private String message;

    /**
     * Creates a new instance of NewService
     */
    public NewService() {
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public ListAllBookings getListAllBookings() {
        return listAllBookings;
    }

    public void setListAllBookings(ListAllBookings listAllBookings) {
        this.listAllBookings = listAllBookings;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void newService() {
        try {
            System.out.println("Hallo");
            LocalDate serviceDate = LocalDate.parse(this.serviceDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Service newService = new Service(serviceDate, listAllBookings.getCurrentBooking(), this.serviceDesc);
            Database.insert(newService);
            message = "inserted Service!";
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(NewService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
