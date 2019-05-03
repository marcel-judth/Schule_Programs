package constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Coordinates {
    PARKING_AFRITZ(250 , 25),
    PARKING_UDINE(250 , 475),
    START_AFRITZ(300, 0),
    START_KLAGENFURT_UPPER(600, 225),
    START_KLAGENFURT_LOWER(600, 275),
    START_UDINE(300, 500),
    START_SPITTAL_UPPER(0, 225),
    START_SPITTAL_LOWER(0, 275),
    STOP_AFRITZ(300, 170),
    STOP_KLAGENFURT_UPPER(350, 225),
    STOP_KLAGENFURT_LOWER(350, 275),
    STOP_UDINE(300, 330),
    STOP_SPITTAL_UPPER(250, 225),
    STOP_SPITTAL_LOWER(250, 275),
    CROSSING_UPPER(300, 225),
    CROSSING_LOWER(300, 275);

    private int x;
    private int y;

    Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Coordinates get(int x, int y) {
        return Arrays.stream(Coordinates.values())
                .filter(c -> c.x == x && c.y == y)
                .findFirst()
                .get();
    }
}
