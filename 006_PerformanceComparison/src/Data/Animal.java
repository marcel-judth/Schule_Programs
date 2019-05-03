/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class Animal {
    private ObjectId id;
    private String name;
    private LocalDate date;
    private String details;

    public Animal(ObjectId id, String name, LocalDate date, String details) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.details = details;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", name=" + name + ", date=" + date + ", details=" + details + '}';
    }
}
