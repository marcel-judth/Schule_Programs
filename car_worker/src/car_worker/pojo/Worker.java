/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_worker.pojo;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 *
 * @author Julian
 */
public class Worker extends Task<String>{

    static final int MAX = 100;
    private StringProperty stringProperty = new SimpleStringProperty();
    private final DoubleProperty dp = new SimpleDoubleProperty();
    
    @Override
    protected String call() throws Exception {
        for(int i=1; i<= MAX && !isCancelled(); i++){
            try{
                Thread.sleep(100);
            }catch(InterruptedException error){
                
            }
            String str = "--> " + i + " <--";
            Platform.runLater(() -> setStringProperty(str));
            final int tmp_i = i + 20;
            Platform.runLater(()-> this.setDp(tmp_i));
        }
        return "finsihed";
    }
    
    public void setStringProperty(String string) {
        this.stringProperty.set(string);
    }

    public static int getMAX() {
        return MAX;
    }

    public StringProperty getStringProperty() {
        return stringProperty;
    }

    public DoubleProperty getDp() {
        return dp;
    }

    public void setDp(double stringProperty) {
        this.dp.set(stringProperty);
    }

    
    
    
    
}
