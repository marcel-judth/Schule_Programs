/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.sql.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import pojo.Booking;

/**
 *
 * @author Julian
 */
@ManagedBean(name = "newbooking")
@SessionScoped
public class NewBooking {
    @ManagedProperty(value="#{listhotels}")
    ListHotels listhotels = null;
    @ManagedProperty(value="#{listbookings}")
    ListBookings listbookings;
    String guest,check_in,check_out;
    Integer number_rooms;

    public NewBooking() {
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public Integer getNumber_rooms() {
        return number_rooms;
    }

    public void setNumber_rooms(Integer number_rooms) {
        this.number_rooms = number_rooms;
    }

    public ListHotels getListhotels() {
        return listhotels;
    }

    public void setListhotels(ListHotels listhotels) {
        this.listhotels = listhotels;
    }

    public ListBookings getListbookings() {
        return listbookings;
    }

    public void setListbookings(ListBookings listbookings) {
        this.listbookings = listbookings;
    }
    
    public String book() throws Exception{
        Booking booking = new Booking(null,this.listhotels.getCurrentHotel().getId(),
                Date.valueOf(this.getCheck_in()),
                Date.valueOf(this.getCheck_out()),
                this.getGuest(),
                this.getNumber_rooms());
        booking.save();
        this.listbookings.changed = true;
        return "/bookings.xhtml";
    }
    
}
