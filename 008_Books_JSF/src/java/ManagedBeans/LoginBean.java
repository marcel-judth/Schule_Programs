/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import DataAccess.Database;
import Pojo.User;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author schueler
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable{

    /**
     * Creates a new instance of LoginBean
     */
    private String name;
    private String password;
    private User user;
    private String username;
    private String message;
    private String buttonDisabled;
    private ArrayList<User> allUsers;

    public LoginBean() {
        try {
            this.allUsers = Database.getAllUsers();
            this.buttonDisabled = "true";
            this.message = "Type in username and password!";
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> getallUsers() throws SQLException {
        this.allUsers = Database.getAllUsers();
        return this.allUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getButtonDisabled() {
        return buttonDisabled;
    }

    public void setButtonDisabled(String buttonDisabled) {
        this.buttonDisabled = buttonDisabled;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void validateUsernamePassword() {
        try {
            this.user = Database.login(name, password);
            if (user != null) {
                this.setButtonDisabled("false");
            } else {
                throw new Exception("Authentication Failed. Check password!");
            }
        } catch (Exception ex) {
            this.message = ex.getMessage();
        }
    }
    
    public void redirect(){
        try {
            System.out.println("servas");
            FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
        } catch (IOException ex) {
            this.message = ex.getMessage();
        }
    }
}
