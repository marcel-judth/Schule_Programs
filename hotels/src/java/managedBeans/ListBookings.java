/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pojo.Booking;

/**
 *
 * @author Julian
 */
@ManagedBean(name ="listbookings")
@SessionScoped
public class ListBookings implements Serializable {
    @ManagedProperty(value="#{listhotels}")
    ListHotels listhotels;
    Booking currentBooking;
    ArrayList<Booking> bookings;
    Boolean changed = true;
    /**
     * Creates a new instance of ListBookings
     */
    public ListBookings() {
        changed = true;
    }

    public ArrayList<Booking> getBookings() throws Exception {
        // note:    after testing, it turned out in my case this is only called on page refresh, and not randomly throughout
            this.setBookings(Booking.find(this.listhotels.currentHotel.getId()));
        
        return this.bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public ListHotels getListhotels() {
        return listhotels;
    }

    public void setListhotels(ListHotels listhotels) {
        this.listhotels = listhotels;
    }

    public Booking getCurrentBooking() {
        return currentBooking;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }
    
    public void setCurrentBooking(Booking currentBooking) {
        this.currentBooking = currentBooking;
    }
    
    public String update(Booking booking){
        this.setCurrentBooking(booking);
        return "/updateBooking.xhtml";
    }
    
    public String delete(Booking booking) throws Exception{
         try {
            booking.delete();
            this.changed = true;
            return "/bookings.xhtml";
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(ex.toString());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
    }
}
