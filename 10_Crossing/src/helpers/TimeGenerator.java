/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Marcel Judth
 */
public class TimeGenerator {
    public static long getRandomTime(long serviceTime, int deviation){
        int offSet = ThreadLocalRandom.current().nextInt(deviation * -1, deviation);
        return (long) (serviceTime + serviceTime * ((double) offSet) / 100);
    }
}
