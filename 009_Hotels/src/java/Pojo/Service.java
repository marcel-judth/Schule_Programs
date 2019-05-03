/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.time.LocalDate;

/**
 *
 * @author schueler
 */
public class Service {
    private int id;
    private LocalDate service_date;
    private Booking booking;
    private String service_description;
    private long ora_rowscn;

    public Service(int id, LocalDate service_date, Booking booking, String service_description, long ora_rowscn) {
        this.id = id;
        this.service_date = service_date;
        this.booking = booking;
        this.service_description = service_description;
        this.ora_rowscn = ora_rowscn;
    }

    public Service(LocalDate service_date, Booking booking, String service_description) {
        this.service_date = service_date;
        this.booking = booking;
        this.service_description = service_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getService_date() {
        return service_date;
    }

    public void setService_date(LocalDate service_date) {
        this.service_date = service_date;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public long getOra_rowscn() {
        return ora_rowscn;
    }

    public void setOra_rowscn(long ora_rowscn) {
        this.ora_rowscn = ora_rowscn;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", service_date=" + service_date + ", booking=" + booking + ", service_description=" + service_description + ", ora_rowscn=" + ora_rowscn + '}';
    }
}
