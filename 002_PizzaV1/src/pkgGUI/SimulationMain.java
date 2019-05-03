/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgGUI;

import java.util.concurrent.Semaphore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkgPizza.Bar;
import pkgPizza.Cook;
import pkgPizza.Customer;

/**
 *
 * @author Marcel Judth
 */
public class SimulationMain extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static void simulationMain() throws InterruptedException {
        System.out.println("*******start simulation");
        Semaphore semaPizzaOnBar = new Semaphore(2);
        Semaphore semBarFree = new Semaphore(1);
        Semaphore semaCustIsHungry = new Semaphore(1);
        semaCustIsHungry.acquire();
        semBarFree.acquire();
        Bar b = new Bar();
        
        Customer c1 = new Customer("Ameise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Customer c2 = new Customer("Bmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Customer c3 = new Customer("Cmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);

        Cook cook = new Cook("Adam", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Cook cook2 = new Cook("Eva", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Cook cook3 = new Cook("Moses", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);

        new Thread(cook).start();
        new Thread(cook2).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();

        Thread.sleep(10000);
        System.out.println("Waiting for end!");
        c1.setEnd();
        c2.setEnd();
        c3.setEnd();
        
        cook.setEnd();
        cook2.setEnd();
        cook3.setEnd();
        System.out.println("*******end simulation");
        System.exit(0);
    }    

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSimulation.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
