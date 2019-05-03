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
public class Delivery implements Serializable{
    private String username;
    private LocalDate deliverdate;
    private int delTotalPrice;

    public Delivery(String username, LocalDate deliverdate, int delTotalPrice) {
        this.username = username;
        this.deliverdate = deliverdate;
        this.delTotalPrice = delTotalPrice;
    }

    public Delivery() {
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDeliverdate() {
        return deliverdate;
    }

    public void setDeliverdate(LocalDate deliverdate) {
        this.deliverdate = deliverdate;
    }

    public int getDelTotalPrice() {
        return delTotalPrice;
    }

    public void setDelTotalPrice(int delTotalPrice) {
        this.delTotalPrice = delTotalPrice;
    }

    @Override
    public String toString() {
        return "Delivery{" + "username=" + username + ", deliverdate=" + deliverdate + ", delTotalPrice=" + delTotalPrice + '}';
    }
}
