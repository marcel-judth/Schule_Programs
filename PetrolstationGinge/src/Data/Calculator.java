/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * andoubled open the template in the editor.
 */
package Data;

/**
 *
 * @author chris
 */
public class Calculator {
    private final static int DEVIATION = 20;
    private final static int MSEC = 1000;
    
    public static long random(long time)
    {
        double min = time * (1 - (double)DEVIATION / 100), max = time * (1 + (double)DEVIATION / 100);
        double range = (max - min) + 1;
        return ((long)(Math.random() * range) + (long)min);
    }
    
    public static long toMsec(long time){
        return time * MSEC;
    }
    
    public static double toMin(long time){
        return (double)time / MSEC;
    }
    
    public static double calcUsageRateInPercent(long st, long tba, int numOfPP){
        return (double)st / (double)tba / (double)numOfPP * 100;
    }
}
