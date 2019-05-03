/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.Date;

/**
 *
 * @author Marcel Judth
 */
public class Person {
    private String name;
    private int age;
    private Date birthdate;
    private String SVNr;

    public Person(String name, int age, Date birthdate, String SVNr) {
        this.name = name;
        this.age = age;
        this.birthdate = birthdate;
        this.SVNr = SVNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSVNr() {
        return SVNr;
    }

    public void setSVNr(String SVNr) {
        this.SVNr = SVNr;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + ", birthdate=" + birthdate + ", SVNr=" + SVNr + '}';
    }
}
