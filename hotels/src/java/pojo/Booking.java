/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import data.DataAccess;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Julian
 */
public class Booking {
    Integer id,hotel_id;
    Date check_in,check_out;
    String check_in_str,check_out_str;
    String guest;
    Integer number_of_rooms;
    Long ora_rowscn;

    public Booking(Integer id, Integer hotel_id, Date check_in, Date check_out, String guest, Integer number_of_rooms) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.check_in = check_in;
        this.check_out = check_out;
        this.guest = guest;
        this.number_of_rooms = number_of_rooms;
    }
    
    public Booking(Integer id, Integer hotel_id, Date check_in, Date check_out, String guest, Integer number_of_rooms,Long ora_rowscn) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.check_in = check_in;
        this.check_out = check_out;
        this.guest = guest;
        this.number_of_rooms = number_of_rooms;
        this.ora_rowscn = ora_rowscn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Integer hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = Date.valueOf(check_in);
    }
    
    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = Date.valueOf(check_out);
    }
    
    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Integer getNumber_of_rooms() {
        return number_of_rooms;
    }

    public void setNumber_of_rooms(Integer number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }

    public String getCheck_in_str() {
        return check_in.toString();
    }

    public void setCheck_in_str(String check_in_str) {
        this.check_in = Date.valueOf(check_in_str);
    }

    public String getCheck_out_str() {
        return check_out.toString();
    }

    public void setCheck_out_str(String check_out_str) {
        this.check_out = Date.valueOf(check_out_str);
    }

    public Long getOra_rowscn() {
        return ora_rowscn;
    }

    public void setOra_rowscn(Long ora_rowscn) {
        this.ora_rowscn = ora_rowscn;
    }
    
    public static ArrayList<Booking> find(Integer hotel_id) throws Exception{
        return DataAccess.getInstance().getBookings(hotel_id);
    } 
   
    public void save() throws Exception{
        DataAccess.getInstance().insertBooking(this);
    }
    
    public void update() throws Exception{
        DataAccess.getInstance().updateBooking(this);
    }
    
    public void delete() throws Exception{
        DataAccess.getInstance().deleteBooking(this.getId(),this.getOra_rowscn());
    }
    
}
