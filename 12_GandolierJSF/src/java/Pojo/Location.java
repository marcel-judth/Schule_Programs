/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

import java.awt.Point;

/**
 *
 * @author schueler
 */
public class Location {
    private int locId;
    private String locName;
    private Point coo;

    public Location(int locId, String locName, int xCoo, int yCoo) {
        this.locId = locId;
        this.locName = locName;
        this.coo = new Point(xCoo, yCoo);
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Point getCoo() {
        return coo;
    }

    public void setCoo(Point coo) {
        this.coo = coo;
    }

    @Override
    public String toString() {
        return "Location{" + "locId=" + locId + ", locName=" + locName + ", coo=" + coo + '}';
    }
}
