/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author schueler
 */
public class Delivery implements Serializable {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy");
    
    private User user;
    private LocalDate date;
    private int price;

    public Delivery() {
    }

    public Delivery(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }
    
    public Delivery(User user, LocalDate date, int price) {
        this.user = user;
        this.date = date;
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public String getDateString() {
        return (date != null ? date.format(dtf) : null);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Delivery{" + "user=" + user + ", date=" + date + ", price=" + price + '}';
    }
}
