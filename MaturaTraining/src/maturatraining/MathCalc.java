/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturatraining;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author schueler
 */
public class MathCalc {

    private MathCalc() {
    }

    public static long calcNumberWithDeviation(long number, int deviation) throws Exception{
        double offSet = (number / (double) 100) * deviation;
        long offSetAsLong = (long) offSet;
        return MathCalc.getRand(number + offSetAsLong, number - offSetAsLong);
       
    }

    private static long getRand(long upperbound, long lowerbound) throws Exception {
        return ThreadLocalRandom.current().nextLong(lowerbound, upperbound);
    }

}
