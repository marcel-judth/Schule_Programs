/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import data.Book;
import data.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author schueler
 */
@ManagedBean(name = "listBook")
@RequestScoped
public class ListBook {

    String title;
    String message;
    ArrayList<Book> foundBooks;

    public ListBook() {
    }

    public void getBooks() throws SQLException {
        System.out.println("hallo " + title);
        String retUrl = "ListBooks";
        try {
            Database db = Database.getInstance();
            foundBooks = db.getBookByTitle(title);
            System.out.println(foundBooks);
            message = foundBooks.isEmpty() ? "no books found" : "some books found: " + foundBooks.size();
            System.out.println("message : " + message);
        } catch (Exception ex) {
            message = "error happened: " + ex.getMessage();
            ex.printStackTrace();
        }
        //return retUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Book> getFoundBooks() {
        return foundBooks;
    }

    public void setFoundBooks(ArrayList<Book> foundBooks) {
        this.foundBooks = foundBooks;
    }
    
    

}
