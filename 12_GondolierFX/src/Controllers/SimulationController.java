/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DataAccess.Database;
import Pojo.Location;
import Pojo.Route;
import Pojo.Section;
import Pojo.Shipment;
import java.awt.Point;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

/**
 *
 * @author schueler
 */
public class SimulationController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnLoadData;

    @FXML
    private Button btnShowImg;

    @FXML
    private Button btnStartSim;

    @FXML
    private Label lblMessage;

    @FXML
    private AnchorPane anchorPane;

    private Database db;
    private ArrayList<Shipment> collshipments;
    private final static String IMAGE_PATH = "/Res/gondoliere.png";
    private ArrayList<ImageView> collImages;
    private final static int ANIMATION_DURATION = 1000;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.db = Database.getInstance();
            this.collImages = new ArrayList<>();
            this.collshipments = new ArrayList<>();
            Shipment.setController(this);
        } catch (SQLException ex) {
            this.lblMessage.setText(ex.getMessage());
            Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource() == this.btnLoadData) {
                this.loadData();
            } else if (event.getSource() == this.btnShowImg) {
                this.showImg();
            } else if (event.getSource() == this.btnStartSim) {
                this.startSim();
            }
        } catch (Exception ex) {
            this.lblMessage.setText(ex.getMessage());
            Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData() throws SQLException {
        this.collshipments = db.getShipments(this.datePicker.getValue());
        for (Shipment s : this.collshipments) {
            s.setCollRoutes(db.getRouteByShipment(s));
        }
    }

    private void showImg() {
        for (Shipment shipment : this.collshipments) {
            if (shipment.getCollRoutes().size() > 0) {
                Route r = shipment.getCollRoutes().get(0);
                this.addNewImage(r.getSection().getLocStart());
            }
        }
        this.lblMessage.setText("All Images displayed!");
    }

    private void startSim() {
        for (Shipment s : this.collshipments) {
            new Thread(s).start();
        }
    }

    public void addNewImage(Location l) {
        try {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResource(IMAGE_PATH).toExternalForm()));
            imageView.setFitWidth(40);
            imageView.setFitHeight(20);
            imageView.setX(l.getCoo().x);
            imageView.setY(l.getCoo().y);
            this.collImages.add(imageView);
            Platform.runLater(() -> {
                anchorPane.getChildren().add(imageView);
            });
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moveGondolier(int shId, Point coo) throws InterruptedException {

        ImageView iv = this.collImages.get(shId - 1);
        Path path = new Path();
        path.getElements().add(new MoveTo(iv.getX(), iv.getY()));
        path.getElements().add(new HLineTo(coo.x));
        path.getElements().add(new VLineTo(coo.y));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        pathTransition.setPath(path);
        pathTransition.setNode(iv);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        Thread.sleep(ANIMATION_DURATION);
    }
}
