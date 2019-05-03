/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgPizza;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 *
 * @author Marcel Judth
 */
public class Person extends Task<String> implements ISubject{
    protected final String name;
    protected final Semaphore semBarFree;
    protected final Semaphore semaPizzaOnBar;
    protected final Semaphore semaOrderExists;
    protected boolean isEnd;
    protected final Bar bar;
    protected StringProperty stringProperty = new SimpleStringProperty();
    protected DoubleProperty doubleProperty = new SimpleDoubleProperty();
    protected static final Random RND = new Random();



    public Person(String name, Bar bar, Semaphore semBarFree, Semaphore semaPizzaOnBar, Semaphore semaOrderExists) {
        this.name = name;
        this.semBarFree = semBarFree;
        this.semaPizzaOnBar = semaPizzaOnBar;
        this.semaOrderExists = semaOrderExists;
        this.bar = bar;
    }

    public String getName() {
        return name;
    }

    public Semaphore getSemBarFree() {
        return semBarFree;
    }

    public Semaphore getSemaPizzaOnBar() {
        return semaPizzaOnBar;
    }

    public Semaphore getSemaOrderExists() {
        return semaOrderExists;
    }

    public StringProperty getStringProperty() {
        return stringProperty;
    }

    public DoubleProperty getDoubleProperty() {
        return doubleProperty;
    }
    
    public boolean isIsEnd() {
        return isEnd;
    }

    public Bar getBar() {
        return bar;
    }

    public void setDoubleProperty(double value) {
        this.doubleProperty.set(value);
    }
    

    public void setStringProperty(StringProperty stringProperty) {
        this.stringProperty = stringProperty;
    }

    @Override
    protected String call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
