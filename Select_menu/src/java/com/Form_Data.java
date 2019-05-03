package com;

import data.Database;
import data.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "form_data")
@RequestScoped
public class Form_Data {

    private String userName;
    private String password;
    private List<User> users = new ArrayList<>();
    
    private String disabled = "true";
    private String message;
    
    private User user;
    
    public List<User> getUsers() {
        try {
            Database db = Database.getInstance();
            users.clear();
            users.addAll(db.getUsers());
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }

    public String verify() {
        String retUrl = "index";
        try {
            Database db = Database.getInstance();
            user = db.getUser(userName, password);
            if(user != null){
                disabled = "false";
                message = "login ok";
            }else{
                disabled = "true";
                message = "login not ok";
            }
            
        } catch (Exception e) {
            message = "error happened: " + e.getMessage();
            e.printStackTrace();
        }
        return retUrl;
    }

    public Form_Data() {
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }
    
    
}
