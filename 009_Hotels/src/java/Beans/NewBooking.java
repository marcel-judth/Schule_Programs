/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Booking;
import Pojo.Hotel;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class NewBooking implements Serializable{
    @ManagedProperty(value = "#{listHotels}")
    private ListHotels listHotels = null;
    private String guest;
    private String checkin;
    private String checkout;
    private int numberOfRooms;
    private Hotel currentHotel;
    private String message;

    /**
     * Creates a new instance of NewBooking
     */
    public NewBooking() {
        message = "...";
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
    
    public ListHotels getListHotels() {
        return listHotels;
    }

    public void setListHotels(ListHotels listHotels) {
        this.listHotels = listHotels;
    }

    public Hotel getCurrentHotel() {
        return currentHotel;
    }

    public void setCurrentHotel(Hotel currentHotel) {
        this.currentHotel = currentHotel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void newBooking(){
        try {
            LocalDate checkInDate = LocalDate.parse(this.checkin, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalDate checkOutDate = LocalDate.parse(this.checkout, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Booking newBooking = new Booking(0, listHotels.getCurrentHotel(), checkInDate, checkOutDate, numberOfRooms, guest);
            
            Database.insert(newBooking);
            message = newBooking + ", inserted";
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(NewBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
