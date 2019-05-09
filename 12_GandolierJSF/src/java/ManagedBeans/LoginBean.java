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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author schueler
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{

    /**
     * Creates a new instance of LoginBean
     */
    private String password;
    private String username;
    private String message;

    public LoginBean() {
        this.message = "Type in username and password!";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void validateUsernamePassword() {
        try {
            User user = Database.login(username, password);
            if (user != null) {
                Database.setCurrentName(user);
                this.redirect();
            } else {
                throw new Exception("Authentication Failed. Check password!");
            }
        } catch (Exception ex) {
            this.message = ex.getMessage();
        }
    }
    
    public void redirect(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListShipments.xhtml");
        } catch (IOException ex) {
            this.message = ex.getMessage();
        }
    }

    String getCurrentUser() {
        return this.username;
    }
}
