/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.util.concurrent.Semaphore;

/**
 *
 * @author schueler
 */
public class Section {
    private int secId;
    private Location locStart;
    private Location locEnd;
    private long secTimeSpan;
    private Semaphore drivePermission;

    public Section(int secId, Location locStart, Location locEnd, long secTimeSpan, int maxGondoliereAllowed) {
        this.secId = secId;
        this.locStart = locStart;
        this.locEnd = locEnd;
        this.secTimeSpan = secTimeSpan;
        this.drivePermission = new Semaphore(maxGondoliereAllowed);
    }

    public Location getLocStart() {
        return locStart;
    }

    public void setLocStart(Location locStart) {
        this.locStart = locStart;
    }

    public Location getLocEnd() {
        return locEnd;
    }

    public void setLocEnd(Location locEnd) {
        this.locEnd = locEnd;
    }

    public long getSecTimeSpan() {
        return secTimeSpan;
    }

    public void setSecTimeSpan(long secTimeSpan) {
        this.secTimeSpan = secTimeSpan;
    }

    public int getSecId() {
        return secId;
    }

    public void setSecId(int secId) {
        this.secId = secId;
    }

    public Semaphore getDrivePermission() {
        return drivePermission;
    }

    public void setDrivePermission(Semaphore drivePermission) {
        this.drivePermission = drivePermission;
    }

    @Override
    public String toString() {
        return "Section{" + "secId=" + secId + ", locStart=" + locStart + ", locEnd=" + locEnd + ", secTimeSpan=" + secTimeSpan + ", drivePermission=" + drivePermission + '}';
    }

    
}
