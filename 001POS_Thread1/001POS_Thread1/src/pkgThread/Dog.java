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
public class Dog extends Animal{

    public Dog(String nameAnimal, int from, int to) {
        super(nameAnimal, from, to);
    }
    
    public void run () {
        try{
            for(int i = from; i > this.to; i--){
                Thread.sleep(200);
                System.out.println (this.getClass() + " " + this.getNameAnimal()+ ": " + i);
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
