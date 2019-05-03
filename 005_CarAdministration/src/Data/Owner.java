/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.time.LocalDate;
import java.util.TreeSet;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class Owner {
    private ObjectId _id;
    private String name, details;
    private LocalDate date;
    private TreeSet<Offence> collOffences = new TreeSet<>();

    public Owner(ObjectId _id, String name, String details, LocalDate date) {
        this._id = _id;
        this.name = name;
        this.details = details;
        this.date = date;
    }

    public Owner(String name, String details, LocalDate date) {
        this.name = name;
        this.details = details;
        this.date = date;
    }
    
    public void add(Offence offence){
        this.collOffences.add(offence);
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCollOffences(TreeSet<Offence> collOffences) {
        this.collOffences = collOffences;
    }
    
    public int getNumberOfOffences(){
        return this.collOffences.size();
    }
    
    public TreeSet<Offence> getCollOffences() {
        return collOffences;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Owner{" + "_id=" + _id + ", name=" + name + ", details=" + details + ", date=" + date + ", #offences=" + getNumberOfOffences() + '}';
    }

    public void remove(Offence offence) throws Exception {
        if(!this.collOffences.contains(offence))
            throw new Exception("Offence not in List!");
        this.collOffences.remove(offence);
    }
    
    
}
