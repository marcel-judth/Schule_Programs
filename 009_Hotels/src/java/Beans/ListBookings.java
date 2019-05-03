/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Booking;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author schueler
 */
@ManagedBean(name ="listBookings")
@SessionScoped
public class ListBookings implements Serializable{
    @ManagedProperty(value="#{listHotels}")
    private ListHotels listHotels;
    private ArrayList<Booking> bookings;
    private String message;
    
    /**
     * Creates a new instance of ListBookings
     */
    public ListBookings() {
    }

    public ArrayList<Booking> getBookings() {
            try {
                bookings = Database.getBookingsByHotelId(listHotels.getCurrentHotel().getId());
                message = bookings.size() + " bookings for hotel " + listHotels.getCurrentHotel().getHotelname() + " listed";
            } catch (Exception ex) {
                message = "error :" + ex.getMessage();
                ex.printStackTrace();
            }
       
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ListHotels getListHotels() {
        return listHotels;
    }

    public void setListHotels(ListHotels listHotels) {
        this.listHotels = listHotels;
    }
    
    public void delete(Booking booking){
        try {
            Database.delete(booking);
//            bookings = Database.getBookingsByHotelId(listHotels.getCurrentHotel().getId());
            message = booking.toString() + " deleted!";
        } catch (Exception ex) {
            message = ex.getMessage();
            Logger.getLogger(ListBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
