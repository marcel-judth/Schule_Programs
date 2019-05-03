/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgThread;

/**
 *
 * @author Marcel Judth
 */
public class Animal extends Thread{
    String nameAnimal;
    int from;
    int to;

    public Animal(String nameAnimal, int from, int to) {
        this.nameAnimal = nameAnimal;
        this.from = from;
        this.to = to;
    }

    public String getNameAnimal() {
        return nameAnimal;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setNameAnimal(String nameAnimal) {
        this.nameAnimal = nameAnimal;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Animal{" + "nameAnimal=" + nameAnimal + ", from=" + from + ", to=" + to + '}';
    }
   
    
}
