/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.io.Serializable;

/**
 *
 * @author schueler
 */
public class Hotel implements Serializable{
    private int id;
    private String hotelname;
    private String address;
    private String information;
    private int number_rooms_total;
    private String pathimage;

    public Hotel() {
    }

    public Hotel(int id, String hotelname, String address, String information, int number_rooms_total, String pathimage) {
        this.id = id;
        this.hotelname = hotelname;
        this.address = address;
        this.information = information;
        this.number_rooms_total = number_rooms_total;
        this.pathimage = pathimage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getNumber_rooms_total() {
        return number_rooms_total;
    }

    public void setNumber_rooms_total(int number_rooms_total) {
        this.number_rooms_total = number_rooms_total;
    }

    public String getPathimage() {
        return pathimage;
    }

    public void setPathimage(String pathimage) {
        this.pathimage = pathimage;
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hotel other = (Hotel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hotelname;
    }
}
