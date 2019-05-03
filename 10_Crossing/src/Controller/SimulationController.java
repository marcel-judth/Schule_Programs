/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Car;
import Data.CarGenerator;
import Data.Crossing;
import Data.Place;
import Data.Town;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class SimulationController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
  
    private final static int ANIMATION_DURATION = 1000;
    private final static String IMAGE_CAR_PATH = "/Res/car.png";
    
    private CarGenerator carGenerator;
    private ArrayList<ImageView> collImages;
    private Crossing crossing;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> obs = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
        this.initCrossing();
        this.collImages = new ArrayList<>();
        this.carGenerator = new CarGenerator(crossing, 100, 200, obs);
        Car.setController(this);
        new Thread(this.carGenerator).start();
    }
    
    public void addNewCarImage(Car c) {
        try{
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResource(IMAGE_CAR_PATH).toExternalForm()));
//            if(c.getOrgin()== Place.AFRITZ || c.getStartPoint() == Place.UDINE)
//                imageView.setRotate(90);
            imageView.setFitWidth(40);
            imageView.setFitHeight(20);
            imageView.setX(c.getCurrentLocation().x);
            imageView.setY(c.getCurrentLocation().y);
            this.collImages.add(imageView);
            Platform.runLater(()->{anchorPane.getChildren().add(imageView);});
        }catch(Exception ex){
            java.util.logging.Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }    

    public void doAnimationMoving(Car car) {
        ImageView iv = this.collImages.get(car.getId() - 1);
        Path path = new Path();
        path.getElements().add(new MoveTo(car.getOldLocation().x, car.getOldLocation().y));
        path.getElements().add(new HLineTo(car.getCurrentLocation().x));
        path.getElements().add(new VLineTo(car.getCurrentLocation().y));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }

    public void removeImage(Car car) {
        ImageView image = this.collImages.get(car.getId() - 1);
        image.setVisible(false);
    }

    private void initCrossing() {
        this.crossing = new Crossing(new Point(275, 180));
        Town afritz = new Town("Afritz", false, new Point(275, 30), new Point(275, 134));
        Town klagenfurt = new Town("Klagenfurt", true, new Point(475, 180), new Point(320, 180));
        Town spittal = new Town("Spittal", true, new Point(31, 180), new Point(230, 180));
        Town udine = new Town("Udine", false, new Point(275, 300), new Point(275, 220));
        
        afritz.setOpposite(udine);
        afritz.setLeft(spittal);
        afritz.setRight(klagenfurt);
        this.crossing.addTown(afritz);

        klagenfurt.setOpposite(spittal);
        klagenfurt.setLeft(udine);
        klagenfurt.setRight(afritz);
        this.crossing.addTown(klagenfurt);
        
        spittal.setOpposite(klagenfurt);
        spittal.setLeft(afritz);
        spittal.setRight(udine);
        this.crossing.addTown(spittal);
        
        udine.setOpposite(afritz);
        udine.setLeft(spittal);
        udine.setRight(klagenfurt);
        this.crossing.addTown(udine);
        
    }
}
