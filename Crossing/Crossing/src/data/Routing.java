package data;

import constants.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Routing {
    public static List<Coordinates> getRoute(Town from, Town to) {
        List<Coordinates> route = new ArrayList<>();

        //route.add(from.getStartpoint());
        route.add(from.getCrossingIn());
        route.add(to.getCrossingOut());
        route.add(to.getEndpoint());

        return route;
    }

    public static double distanceBetween(Coordinates a, Coordinates b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
