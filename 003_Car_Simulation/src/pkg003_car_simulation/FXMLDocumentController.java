/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg003_car_simulation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

/**
 *
 * @author Marcel Judth
 */
public class FXMLDocumentController implements Initializable {
    private FadeTransition ft = null;
    private PathTransition pathTransition = null;
    private static final double START_X = 20d;
    private static final double START_Y = 20d;
    private static final double WIDTH = 600d;
    private static final double HEIGHT = 250d;
    private static final double WIDTH_RTN = 400d;
    private static final double HEIGHT_RTN = 200d;

    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private Label txtMessage;

    @FXML
    private ImageView imgCar;


    @FXML
    void oncklick(ActionEvent event) {
        try{
            if(event.getSource() == btnStart){
                this.doAnimationFading();
                doAnimationMoving();
                this.txtMessage.setText("start of animation");
            }else if (event.getSource() == btnStop){
                doAnimationStop();
                this.txtMessage.setText("stop animation");
            }
        }catch(Exception ex){
            this.txtMessage.setText("error:" + ex.getMessage());
        }
    }

    private void doAnimationFading() {
        this.ft = new FadeTransition(Duration.millis(3000), imgCar);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void doAnimationStop() {
        ft.stop();
    }

    private void doAnimationMoving() {
        Path path = new Path();
        path.getElements().add(new MoveTo(START_X, START_Y));
        path.getElements().add(new HLineTo(START_X + WIDTH));
        path.getElements().add(new VLineTo(START_Y + HEIGHT));
        path.getElements().add(new HLineTo(START_X + WIDTH - WIDTH_RTN));
        path.getElements().add(new ArcTo(1d, 1d, 10, START_X, START_Y + HEIGHT_RTN, false, false));
        path.getElements().add(new VLineTo(START_Y));
        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(imgCar);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
                
    }
   
}
