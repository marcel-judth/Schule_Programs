/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import DataAccess.Database;
import Pojo.Book;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author schueler
 */
@ManagedBean
@RequestScoped
public class ListBooks implements Serializable{

    ArrayList<Book> books;
    String title;
    String message = "type in title";
    
    /**
     * Creates a new instance of ListBooks
     */
    public ListBooks() {
        this.books = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
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
    
    public void search(){
        try {
            books = Database.selectBooksByTitle(title);
            message = books.size() + " books found";
            System.out.println(message);
        } catch (Exception ex) {
            Logger.getLogger(ListBooks.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
    }
    
}
