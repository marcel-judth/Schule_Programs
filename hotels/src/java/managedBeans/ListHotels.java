/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pojo.Hotel;

/**
 *
 * @author Julian
 */
@ManagedBean(name="listhotels")
@SessionScoped
public class ListHotels implements Serializable {
    
    static final long serialVersionUID = 1L;
    Hotel currentHotel;
    String name ="list-hotels";
    ArrayList<Hotel> hotels = new ArrayList<>();
    
    
    public ListHotels() {
    }

    public ArrayList<Hotel> getHotels() throws Exception {
       return Hotel.find();
    }

    public void setHotels(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hotel getCurrentHotel() {
        return currentHotel;
    }

    public void setCurrentHotel(Hotel currentHotel) {
        this.currentHotel = currentHotel;
    }

    public String book(Hotel hotel){
        this.setCurrentHotel(hotel);
        return "/newBooking.xhtml";
    }
    
    public String bookings(Hotel hotel){
        this.setCurrentHotel(hotel);
        return "/bookings.xhtml";
    }
    
}
