/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgGUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pkgPizza.Bar;
import pkgPizza.Cook;
import pkgPizza.Customer;

/**
 * FXML Controller class
 *
 * @author Marcel Judth
 */
public class FXMLSimulationController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private ImageView imgCook3;

    @FXML
    private ImageView imgCook1;

    @FXML
    private ImageView imgCook2;

    @FXML
    private ImageView imgCustomer1;

    @FXML
    private ImageView imgCustomer2;
    
    @FXML
    private ImageView imgCustomer3;

    @FXML
    private MenuItem menueItemCreateImages;
        
    @FXML
    private MenuItem menuItemStart;
        
    @FXML
    private MenuItem menuItemStop;
    
    @FXML
    private Label txtMessage;
    
    private ArrayList<ImageView> allImages; 
    
    private ArrayList<Cook> allCooks;
    
    private ArrayList<Customer> allCusts;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.addPersons();
        } catch (Exception ex) {
            Logger.getLogger(FXMLSimulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    public void actionPerform(ActionEvent event) {
        try{
            System.out.println(this.menueItemCreateImages);
            if(event.getSource() == this.menueItemCreateImages){
                this.createImages();
            }else
                if(event.getSource() == this.menuItemStart){
                    this.startSimulation();
                }else
                    if(event.getSource() == this.menuItemStop){
                        this.endSimulation();
                    }
        }catch(Exception ex){
            this.txtMessage.setText(ex.toString());
        }
    }

    private void createImages() {
        int x = 105;
        int y = 219;
        int yoffSet = 138;
        
        for(Cook c : this.allCooks){
            c.setDoubleProperty(105);
            ImageView img = new ImageView();
            img.setFitWidth(80);
            img.setFitHeight(120);
            img.setX(x);
            img.setY(y);
            img.setImage(new Image(getClass().getResource("..\\res\\cook.jpg").toExternalForm()));
            img.xProperty().bind(c.getDoubleProperty());
            this.anchorPane.getChildren().add(img);
            y = y + yoffSet;
        }
        
        x = 850;
        y = 209;
        yoffSet = 147;
        
        for(Customer c : this.allCusts){
            c.setDoubleProperty(850);
            ImageView img = new ImageView();
            img.setFitWidth(70);
            img.setFitHeight(110);
            img.setX(x);
            img.setY(y);
            img.setImage(new Image(getClass().getResource("..\\res\\customer.jpg").toExternalForm()));
            img.xProperty().bind(c.getDoubleProperty());
            this.anchorPane.getChildren().add(img);
            y = y + yoffSet;
        }
    }
    
    private void simulationMain() throws InterruptedException {
        System.out.println("*******start simulation");
        Semaphore semaPizzaOnBar = new Semaphore(2);
        Semaphore semBarFree = new Semaphore(1);
        Semaphore semaCustIsHungry = new Semaphore(1);
        semaCustIsHungry.acquire();
        Bar b = new Bar();
        
        Customer c1 = new Customer("Ameise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Customer c2 = new Customer("Bmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Customer c3 = new Customer("Cmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);

        Cook cook = new Cook("Adam", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Cook cook2 = new Cook("Eva", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);
        Cook cook3 = new Cook("Moses", b, semBarFree, semaPizzaOnBar, semaCustIsHungry);

        this.imgCook1.xProperty().bind(cook.getDoubleProperty());
        this.imgCook2.xProperty().bind(cook2.getDoubleProperty());
        this.imgCook3.xProperty().bind(cook3.getDoubleProperty());
        
        this.imgCustomer1.xProperty().bind(c1.getDoubleProperty());
        this.imgCustomer2.xProperty().bind(c2.getDoubleProperty());
        this.imgCustomer3.xProperty().bind(c3.getDoubleProperty());

        
        new Thread(cook).start();
        new Thread(cook2).start();
        new Thread(cook3).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();        
    }    

    private void addPersons() {
        try {
            this.allCooks = new ArrayList<>();
            this.allCusts = new ArrayList<>();
            Semaphore semaPizzaOnBar = new Semaphore(2);
            Semaphore semBarFree = new Semaphore(1);
            Semaphore semaCustIsHungry = new Semaphore(1);
            semaCustIsHungry.acquire();
            semBarFree.acquire();
            Bar b = new Bar();
            
            this.allCusts.add(new Customer("Ameise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
            this.allCusts.add(new Customer("Bmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
            this.allCusts.add(new Customer("Cmeise", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
            
            this.allCooks.add(new Cook("Adam", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
            this.allCooks.add(new Cook("Eva", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
            this.allCooks.add(new Cook("Moses", b, semBarFree, semaPizzaOnBar, semaCustIsHungry));
        } catch (Exception ex) {
            Logger.getLogger(FXMLSimulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startSimulation() {
        for(Cook c : this.allCooks){
            new Thread(c).start();
        }
        
        for(Customer c : this.allCusts){
            new Thread(c).start();
        }
    }

    private void endSimulation() {
        for(Cook c : this.allCooks){
            c.setEnd();
        }
        
        for(Customer c : this.allCusts){
            c.setEnd();
        }
    }
    
}
