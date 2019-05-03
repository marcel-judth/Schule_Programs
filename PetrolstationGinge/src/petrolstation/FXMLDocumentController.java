/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolstation;

import Data.AnimCoordinates;
import Data.CarDriver;
import Data.CarGenerator;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author schueler
 */
public class FXMLDocumentController implements Initializable, AnimCoordinates{
    private ObservableList<String> obsList;
    private CarGenerator cg = null;
    private ArrayList<ImageView> collImages;
    //private Stage myStage;
    
    @FXML
    private TextField tbServicetime;

    @FXML
    private TextField tbArrivaltime;

    @FXML
    private TextField tbNumberPp;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private ListView<String> tvWorkflow;

    @FXML
    private Label txtMessage;
    
    @FXML
    private Pane aPane;

    @FXML
    void onClickButton(ActionEvent event) {
        try{
            if(event.getSource() == btnStart){
                collImages.forEach((iv) -> {
                    aPane.getChildren().remove(iv);
                });
                obsList.clear();
                collImages.clear();
                cg = new CarGenerator(Long.parseLong(tbArrivaltime.getText()), 
                        Long.parseLong(tbServicetime.getText()), 
                        Integer.parseInt(tbNumberPp.getText()), 
                        obsList, this);
                new Thread((Runnable) cg).start();
                txtMessage.setText("car generator started");
            }
            if(event.getSource() == btnStop && cg != null){
                cg.setEnd();
                txtMessage.setText("car generator stopped");
            }
        }catch(Exception ex){
            txtMessage.setText("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obsList = FXCollections.observableArrayList();
        tvWorkflow.setItems(obsList);
        collImages = new ArrayList<>();
    }   
    
    public void createCar (CarDriver cd){
        Pane myPane = (Pane)(aPane.getScene().getRoot());
        ImageView iv = new ImageView();
        iv.setImage(new Image(getClass().getResourceAsStream("/res/cars.png")));
        iv.setFitHeight(HEIGHT);
        iv.setFitWidth(WIDTH);
        myPane.getChildren().add(iv);
        collImages.add(iv);
    }

    public ObservableList<String> getObsList() {
        return obsList;
    }

//    public void setStage(Stage stage) {
//        this.myStage = stage;
//    }
 
    public void doAnimationMoving(CarDriver cd) throws Exception{
        ImageView iv = collImages.get(cd.getDriverId() - 1);
        Path path = new Path();
        path.getElements().add(new MoveTo(cd.getOldCooX(), START_Y + LANE_WIDTH * cd.getDriverId()));
        path.getElements().add(new HLineTo(cd.getCurrentCooX()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }
}
