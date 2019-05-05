/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maturatraining;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import static maturatraining.MaturaTraining.myStage;

/**
 *
 * @author Valon
 */
public class FXMLDocumentController implements Initializable, AnimCoordinates {

    private List<ImageView> collImages = new ArrayList<>();

    @FXML
    private Button btnStart;

    @FXML
    void startCG(ActionEvent event) {
        CarGenerator.setController(this);
        Car.setController(this);

        CarGenerator cg = new CarGenerator();
        new Thread((Runnable) cg).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void start(ActionEvent event) {

    }

    public void createCar() {
        Pane myPane = (Pane) (myStage.getScene().getRoot());
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("car.png")));
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        iv.setX(270);
        iv.setY(85);
        collImages.add(iv);
        myPane.getChildren().add(iv);
    }

  

    public void doAnimationMoving(Car c) throws Exception {
        ImageView iv = collImages.get(c.getId() - 1);
        Path path = new Path();

        path.getElements().add(new MoveTo(c.getOldX(), c.getOldY()));
        path.getElements().add(new HLineTo(c.getCurrentX()));
        path.getElements().add(new VLineTo(c.getCurrentY()));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setAutoReverse(false);
       
        pathTransition.play();
    }

    void deleteCar(Car c) {
        ImageView iv = collImages.get(c.getId() - 1);
        Pane myPane = (Pane) (myStage.getScene().getRoot());
        myPane.getChildren().remove(iv);
       
    }
}
