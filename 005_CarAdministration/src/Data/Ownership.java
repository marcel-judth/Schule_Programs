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
public class Ownership implements Comparable<Ownership>{
    private LocalDate startDate, endDate;
    private ObjectId _id;

    public Ownership(LocalDate startDate, LocalDate endDate, ObjectId ownerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this._id = ownerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getId() {
        return _id;
    }

    @Override
    public String toString() {
        return "OwnerShip{" + "startDate=" + startDate + ", endDate=" + endDate + ", ownerId=" + _id + '}';
    }

    @Override
    public int compareTo(Ownership o) {
        int result = this.startDate.compareTo(o.startDate);
        if(result == 0)
            return this._id.compareTo(o.getId());
        return result;
    }
       
}
