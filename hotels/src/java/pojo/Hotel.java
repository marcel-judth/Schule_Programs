/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import data.DataAccess;
import java.util.ArrayList;

/**
 *
 * @author Julian
 */
public class Hotel {
    Integer id,total_rooms;    
    String designation,address, information, image_path;

    public Hotel(Integer id, Integer total_rooms, String designation, String address, String information, String image_path) {
        this.id = id;
        this.total_rooms = total_rooms;
        this.designation = designation;
        this.address = address;
        this.information = information;
        this.image_path = image_path;
    }

    public Hotel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(Integer total_rooms) {
        this.total_rooms = total_rooms;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    
    public static ArrayList<Hotel> find() throws Exception{
        return DataAccess.getInstance().getHotels();
    }

    
}