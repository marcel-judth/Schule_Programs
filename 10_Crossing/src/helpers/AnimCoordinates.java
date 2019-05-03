/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Data.Place;
import java.util.HashMap;

/**
 *
 * @author schueler
 */
public interface AnimCoordinates {

    public static final int START_X_UDINE = 255;
    public static final int START_Y_UDINE = 317;
    public static final int CROSSING_X_UDINE = 255;
    public static final int CROSSING_Y_UDINE = 200;
    
    public static final int START_X_SPITTAL = 31;
    public static final int START_Y_SPITTAL = 170;
    public static final int CROSSING_X_SPITTAL = 200;
    public static final int CROSSING_Y_SPITTAL = 170;
    
    public static final int START_X_AFRITZ = 255;
    public static final int START_Y_AFRITZ = 30;
    public static final int CROSSING_X_AFRITZ = 255;
    public static final int CROSSING_Y_AFRITZ = 134;
    
    public static final int START_X_KLAGENFURT = 475;
    public static final int START_Y_KLAGENFURT = 170;
    public static final int CROSSING_X_KLAGENFURT = 300;
    public static final int CROSSING_Y_KLAGENFURT = 170;
    
    public static final int HEIGHT = 10;
    public static final int WIDTH = 20;
    public static final int LANE_WIDTH = 15;
    
    public static HashMap<String, Integer> getCoo(){
        HashMap<String, Integer> allCoo = new HashMap<>();
        allCoo.put("START_X_UDINE", 275);
        allCoo.put("START_Y_UDINE", 300);
        allCoo.put("CROSSING_X_UDINE", 275);
        allCoo.put("CROSSING_Y_UDINE", 220);

        allCoo.put("START_X_SPITTAL", 31);
        allCoo.put("START_Y_SPITTAL", 180);
        allCoo.put("CROSSING_X_SPITTAL", 230);
        allCoo.put("CROSSING_Y_SPITTAL", 180);
        
        allCoo.put("START_X_AFRITZ", 275);
        allCoo.put("START_Y_AFRITZ", 30);
        allCoo.put("CROSSING_X_AFRITZ", 275);
        allCoo.put("CROSSING_Y_AFRITZ", 140);
        
        allCoo.put("START_X_KLAGENFURT", 475);
        allCoo.put("START_Y_KLAGENFURT", 180);
        allCoo.put("CROSSING_X_KLAGENFURT", 320);
        allCoo.put("CROSSING_Y_KLAGENFURT", 180);
       
        
        allCoo.put("MIDDEL_CROSSING_X", 275);
        allCoo.put("MIDDEL_CROSSING_Y", 180);
        return allCoo;
    }
}
