/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.TreeSet;
import org.bson.types.ObjectId;


/**
 *
 * @author schueler
 */
public class Car {
    private ObjectId _id;
    private String name;
    private int hp, year;
    private String description;
    private TreeSet<Ownership> ownerships = new TreeSet<>();

    public Car(String name, int hp, int year, String description) {
        this.name = name;
        this.hp = hp;
        this.year = year;
        this.description = description;
    }
    
    public Car(ObjectId id, String name, int hp, int year, String description) {
        this._id = id;
        this.name = name;
        this.hp = hp;
        this.year = year;
        this.description = description;
    }
    
    public void addOwnership(Ownership newOwnership){
        if(this.ownerships == null)
            this.ownerships = new TreeSet<>();
        this.ownerships.add(newOwnership);
    }

    public void setOwnerships(TreeSet<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }
    
    public ObjectId getId() {
        return _id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public TreeSet<Ownership> getOwnerships() {
        return ownerships;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Car{" + "_id=" + _id + ", name=" + name + ", hp=" + hp + ", ccm=" + year + ", description=" + description + ", ownerships=" + ownerships + '}';
    }
}
