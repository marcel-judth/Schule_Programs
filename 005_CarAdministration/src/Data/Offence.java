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
public class Offence implements Comparable<Offence>{
    private ObjectId _id;
    private LocalDate date;
    private String details;

    public Offence(LocalDate date, String details) {
        this.date = date;
        this.details = details;
    }

    public Offence(ObjectId _id, LocalDate date, String details) {
        this._id = _id;
        this.date = date;
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
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
        return "Offence{" + "date=" + date + ", details=" + details + '}';
    }    

    @Override
    public int compareTo(Offence o) {
        int result = this.date.compareTo(o.date);
        if(result == 0)
            result = this.details.compareTo(o.details);
        return result;
    }
}
