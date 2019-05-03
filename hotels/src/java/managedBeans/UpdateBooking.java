/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name="updateBooking")
@SessionScoped
public class UpdateBooking {

    @ManagedProperty(value="#{listbookings}")
    ListBookings listbookings;
    @ManagedProperty(value="#{listbookings.changed}")
    Boolean changed;
    
    /**
     * Creates a new instance of UpdateBooking
     */
    public UpdateBooking() {
    }

    public Booking getBooking() {
        return listbookings.currentBooking;
    }

    public void setBooking(Booking booking) {
        listbookings.currentBooking = booking;
    }

    public ListBookings getListbookings() {
        return listbookings;
    }

    public void setListbookings(ListBookings listbookings) {
        this.listbookings = listbookings;
    }
    
    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }
    
    public String update(){
        try {
            listbookings.currentBooking.update();
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
