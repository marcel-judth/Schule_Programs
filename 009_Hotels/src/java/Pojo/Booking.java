/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author schueler
 */
public class Booking implements Serializable{
    private int id;
    private Hotel hotel;
    private LocalDate check_in;
    private LocalDate check_out;
    private int number_rooms;
    private String guest;
    private long ora_rowscn;

    
    public Booking(int id, Hotel hotel, LocalDate check_in, LocalDate check_out, int number_rooms, String guest) {
        this.id = id;
        this.hotel = hotel;
        this.check_in = check_in;
        this.check_out = check_out;
        this.number_rooms = number_rooms;
        this.guest = guest;
    }

    public Booking(int id, Hotel hotel, LocalDate check_in, LocalDate check_out, int number_rooms, String guest, long ora_rowscn) {
        this.hotel = hotel;
        this.check_in = check_in;
        this.check_out = check_out;
        this.number_rooms = number_rooms;
        this.guest = guest;
        this.ora_rowscn = ora_rowscn;
        this.id = id;
    }

    public LocalDate getCheck_in() {
        return check_in;
    }

    public void setCheck_in(LocalDate check_in) {
        this.check_in = check_in;
    }

    public LocalDate getCheck_out() {
        return check_out;
    }

    public void setCheck_out(LocalDate check_out) {
        this.check_out = check_out;
    }

    public int getNumber_rooms() {
        return number_rooms;
    }

    public void setNumber_rooms(int number_rooms) {
        this.number_rooms = number_rooms;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public long getOra_rowscn() {
        System.out.println("hello with " + this.ora_rowscn);
        return ora_rowscn;
    }

    public void setOra_rowscn(long ora_rowscn) {
        this.ora_rowscn = ora_rowscn;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", hotel=" + hotel + ", check_in=" + check_in + ", check_out=" + check_out + ", number_rooms=" + number_rooms + ", guest=" + guest + ", ora_rowscn=" + ora_rowscn + '}';
    }
    
}
