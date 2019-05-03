/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Hotel;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author schueler
 */
@ManagedBean(name ="listHotels")
@SessionScoped
public class ListHotels implements Serializable{

    private ArrayList<Hotel> hotels;
    private String message;
    private Hotel currentHotel;
    /**
     * Creates a new instance of ListHotels
     */
    public ListHotels() {
        try {
            hotels = Database.get();
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListHotels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Hotel getCurrentHotel() {
        return currentHotel;
    }

    public void setCurrentHotel(Hotel currentHotel) {
        this.currentHotel = currentHotel;
    }
    
    public void newBooking(Hotel hotel){
        try {
            this.setCurrentHotel(hotel);
            FacesContext.getCurrentInstance().getExternalContext().redirect("NewBooking.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListHotels.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
    }
    
    public void listBookings(Hotel hotel){
        try {
            this.setCurrentHotel(hotel);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListBookings.xhtml");
        } catch (IOException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListHotels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listAllBookings(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListAllBookings.xhtml");
        } catch (IOException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListHotels.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
