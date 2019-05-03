package data;

import constants.Coordinates;

import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Town {
    private final String name;
    private Coordinates startpoint;
    private Coordinates endpoint;
    private Coordinates crossingIn;
    private Coordinates crossingOut;
    private boolean isVorrang;
    private Town opposite;
    private Town left;
    private Town right;
    private final Semaphore drivePermit;

    public Town(String name, int lanes, Coordinates startpoint, Coordinates endpoint, Coordinates crossingIn, Coordinates crossingOut) {
        this.name = name;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.crossingIn = crossingIn;
        this.crossingOut = crossingOut;
        this.drivePermit = new Semaphore(lanes == 1 ? 1 : Integer.MAX_VALUE, true);
    }

    public String getName() {
        return name;
    }

    public Coordinates getStartpoint() {
        return startpoint;
    }

    public Coordinates getEndpoint() {
        return endpoint;
    }

    public Coordinates getCrossingIn() {
        return crossingIn;
    }

    public Coordinates getCrossingOut() {
        return crossingOut;
    }

    public void beginCrossing() throws InterruptedException {
        drivePermit.acquire(1);
    }

    public void endCrossing() {
        drivePermit.release(1);
    }

    public boolean isIsVorrang() {
        return isVorrang;
    }

    public void setIsVorrang(boolean isVorrang) {
        this.isVorrang = isVorrang;
    }

    public Town getOpposite() {
        return opposite;
    }

    public void setOpposite(Town opposite) {
        this.opposite = opposite;
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
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return Objects.equals(name, town.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
