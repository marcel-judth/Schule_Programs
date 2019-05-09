/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Data.Database;
import Pojo.Booking;
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
@ManagedBean(name = "listAllBookings")
@SessionScoped
public class ListAllBookings implements Serializable {
    
    private ArrayList<Booking> bookings;
    private String message;
    private String filterName;
    private String curFilterName;
    private String filterType;
    private ArrayList<String> filterTypes;
    private Booking currentBooking;
    private boolean changed;

    public ListAllBookings() {
        try {
            bookings = Database.getBookings();
            message = bookings.size() + " bookings listed.";
            filterTypes = new ArrayList<>();
            filterTypes.add("GUESTS");
            filterTypes.add("HOTELS");
            filterName = "";
            this.curFilterName = "";
            this.filterType = filterTypes.get(0);
        } catch (SQLException ex) {
            message = ex.getMessage();
            Logger.getLogger(ListAllBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Booking> getBookings() {
        try {
            System.out.println("hallo" + filterType + ", filterName" + filterName);
            System.out.println(curFilterName);
            if (!this.curFilterName.equals(filterName) || this.changed) {
                if (filterName.equals("")) {
                    bookings = Database.getBookings();
                } else {
                    if (filterType.equals("GUESTS")) {
                        bookings = Database.getBookingsByGuest(filterName);
                        System.out.println("in guest" + bookings);
                    } else {
                        bookings = Database.getBookingsByHotelName(filterName);
                        System.out.println("in hotel" + bookings);
                    }
                }
                this.curFilterName = this.filterName;
                this.changed = false;
            }
        } catch (Exception ex) {
            message = ex.getMessage();
            Logger.getLogger(ListAllBookings.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public ArrayList<String> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(ArrayList<String> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public Booking getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(Booking currentBooking) {
        this.currentBooking = currentBooking;
    }

    public void delete(Booking booking) {
        try {
            System.out.println(booking);
            Database.delete(booking);
            this.changed = true;
            message = booking.toString() + " deleted!";
        } catch (Exception ex) {
            message = ex.getMessage();
            Logger.getLogger(ListAllBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBooking(Booking booking) throws IOException {
        this.currentBooking = booking;
        this.changed = true;
        FacesContext.getCurrentInstance().getExternalContext().redirect("UpdateBooking.xhtml");
    }

    public void newService(Booking booking) {
        try {
            this.currentBooking = booking;
            FacesContext.getCurrentInstance().getExternalContext().redirect("NewService.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListAllBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listService(Booking booking) {
        try {
            this.currentBooking = booking;
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListServices.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ListAllBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
