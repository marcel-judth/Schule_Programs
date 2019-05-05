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
public class Person extends Thread{
    String namePerson;
    int from;
    int to;

    public Person(String namePerson, int from, int to) {
        this.namePerson = namePerson;
        this.from = from;
        this.to = to;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }    
    
    public void run () {
        try{
            for(int i = from; i < this.to; i++){
                Thread.sleep(400);
                System.out.println (this.getClass() + " " + this.getNamePerson()+ ": " + i);
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
