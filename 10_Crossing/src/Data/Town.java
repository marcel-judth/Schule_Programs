/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.awt.Point;
import java.util.concurrent.Semaphore;

/**
 *
 * @author schueler
 */
public class Town {
    private String name;
    private boolean hasVorrang;
    private Semaphore drivingPermit;
    private Town left;
    private Town right;
    private Town opposite;
    private Point startPoint;
    private Point crossingPoint;

    public Town(String name, boolean hasVorrang, Point startPoint, Point crossingPoint, int lanes) {
        this.name = name;
        this.hasVorrang = hasVorrang;
        this.left = left;
        this.right = right;
        this.opposite = opposite;
        this.startPoint = startPoint;
        this.crossingPoint = crossingPoint;
        this.drivingPermit = new Semaphore(lanes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasVorrang() {
        return hasVorrang;
    }

    public void setHasVorrang(boolean hasVorrang) {
        this.hasVorrang = hasVorrang;
    }

    public Semaphore getDrivingPermit() {
        return drivingPermit;
    }

    public void setDrivingPermit(Semaphore drivingPermit) {
        this.drivingPermit = drivingPermit;
    }

    public Town getLeft() {
        return left;
    }

    public void setLeft(Town left) {
        this.left = left;
    }

    public Town getRight() {
        return right;
    }

    public void setRight(Town right) {
        this.right = right;
    }

    public Town getOpposite() {
        return opposite;
    }

    public void setOpposite(Town opposite) {
        this.opposite = opposite;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getCrossingPoint() {
        return crossingPoint;
    }

    public void setCrossingPoint(Point crossingPoint) {
        this.crossingPoint = crossingPoint;
    }

    @Override
    public String toString() {
        return "Town{" + "name=" + name + ", hasVorrang=" + hasVorrang + ", drivingPermit=" + drivingPermit + ", left=" + left + ", right=" + right + ", opposite=" + opposite + ", startPoint=" + startPoint + ", crossingPoint=" + crossingPoint + '}';
    }
}
