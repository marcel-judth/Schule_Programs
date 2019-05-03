/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.time.LocalTime;

/**
 *
 * @author schueler
 */
public class MyMath {
    private static final int MONTH_UPPER = 12;
    private static final int DAY_UPPER = 28;
    private static final int YEAR_UPPER = 2000;
    private static final int YEAR_LOWER = 2000;
    
    static public long random(long lower, long upper) throws Exception{
        long interval = (long) ((upper - lower) * Math.random());
        return (lower + interval);
    }
    
    static public long getMiliSecsBetween(LocalTime start, LocalTime end) throws Exception{
        return Math.abs((start.toNanoOfDay() - end.toNanoOfDay()) / 1000000);
    }
    
    static public String getRandomDate() throws Exception {
        String day = Long.toString(MyMath.random(1, DAY_UPPER) + 100).substring(1);
        String month = Long.toString(MyMath.random(1, MONTH_UPPER) + 100).substring(1);
        String year = Long.toString(MyMath.random(YEAR_LOWER, YEAR_UPPER));
        return day + "." + month + "." + year;
    }
}
