/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Booking;
import Pojo.Hotel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author schueler
 */
@ManagedBean(name ="updateBooking")
@SessionScoped
public class UpdateBooking {
    @ManagedProperty(value = "#{listAllBookings}")
    private ListAllBookings listBookings = null;
    private String guest;
    private String checkin;
    private String checkout;
    private int numberOfRooms;
    private Hotel currentHotel;
    private String message;

    /**
     * Creates a new instance of NewBooking
     */
    public UpdateBooking() {
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

    public ListAllBookings getListBookings() {
        return listBookings;
    }

    public void setListBookings(ListAllBookings listBookings) {
        this.listBookings = listBookings;
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
    
    public void updateBooking(){
        try {
            LocalDate checkInDate = LocalDate.parse(this.checkin, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalDate checkOutDate = LocalDate.parse(this.checkout, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Booking oldBooking = listBookings.getCurrentBooking();
            Booking newBooking = new Booking(oldBooking.getId(), oldBooking.getHotel(), checkInDate, checkOutDate, numberOfRooms, guest, oldBooking.getOra_rowscn());
            
            Database.update(newBooking);
            this.listBookings.setBookings(Database.getBookings());
            message = newBooking + ", updated";
        } catch (Exception ex) {
            message = ex.getMessage();
            Logger.getLogger(UpdateBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
