/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgPizza;

/**
 *
 * @author Marcel Judth
 */
public class Pizza implements Comparable<Pizza>{
    private static int numberOfPizza = 0;
    private final String name;

    public Pizza(String name) {
        numberOfPizza++;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + numberOfPizza;
    }

    @Override
    public int compareTo(Pizza o) {
        return this.name.compareTo(o.name);
    }
    
    
}
