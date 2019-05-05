/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;

/**
 *
 * @author schueler
 */
public class User implements Serializable{
    private final String username;
    private String password;
    private String information;
    private int countOrders;


    public User(String username, String password, String information, int countOrders) {
        this.username = username;
        this.password = password;
        this.information = information;
        this.countOrders = countOrders;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    @Override
    public String toString() {
        return username;
    }

    public boolean isAdmin() {
        return this.username.equals("admin");
    }
}
