package fx;

import constants.Coordinates;
import data.*;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import data.CarGenerator;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import static constants.Constants.*;
import static constants.Coordinates.*;

public class Controller implements Initializable {

    @FXML
    Pane pane_crossing;

    @FXML
    ListView<String> listview_log;

    @FXML
    Button button_start, button_stop;

    private CarGenerator generator;

    private Crossing crossing;

    private ObservableList<String> log;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.log = listview_log.getItems();

            initCrossing();
            //drawHelpPoints();

            CarGenerator.setController(this);
            this.generator = new CarGenerator(crossing);

            log("ready");
        } catch (Exception e) {
            log(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onAction(ActionEvent event) {
        if (event.getSource().equals(button_start)) {
            generator.start();
        } else if (event.getSource().equals(button_stop)) {
            generator.stop();
        }
    }

    private void initCrossing() {
        crossing = new Crossing();
        Town afritz = new Town("Afritz", 1, START_AFRITZ, START_AFRITZ, STOP_AFRITZ, STOP_AFRITZ);
        Town klagenfurt = new Town("Klagenfurt", 2, START_KLAGENFURT_UPPER, START_KLAGENFURT_LOWER, STOP_KLAGENFURT_UPPER, STOP_KLAGENFURT_LOWER);
        Town udine = new Town("Udine", 1, START_UDINE, START_UDINE, STOP_UDINE, STOP_UDINE);
        Town spittal = new Town("Spittal", 2, START_SPITTAL_LOWER, START_SPITTAL_UPPER, STOP_SPITTAL_LOWER, STOP_SPITTAL_UPPER);

        afritz.setIsVorrang(false);
        afritz.setLeft(klagenfurt);
        afritz.setOpposite(udine);
        afritz.setRight(spittal);

        spittal.setIsVorrang(true);
        spittal.setLeft(afritz);
        spittal.setOpposite(klagenfurt);
        spittal.setRight(udine);

        udine.setIsVorrang(false);
        udine.setLeft(spittal);
        udine.setOpposite(afritz);
        udine.setRight(klagenfurt);

        klagenfurt.setIsVorrang(true);
        klagenfurt.setLeft(udine);
        klagenfurt.setOpposite(spittal);
        klagenfurt.setRight(afritz);

        crossing.addTown(afritz);
        crossing.addTown(klagenfurt);
        crossing.addTown(udine);
        crossing.addTown(spittal);
    }

    private void drawHelpPoints() {
        for (Coordinates c : Coordinates.values()) {
            pane_crossing.getChildren().add(new Circle(c.getX(), c.getY(), 3));
        }
    }

    public void log(String message) {
        Platform.runLater(() -> log.add(message));
    }

    public void placeCar(Car car, Coordinates place) {
        String uri = "";
        switch (car.getId() % 3) {
            case 0:
                uri = "/res/car.png";
                break;
            case 1:
                uri = "/res/redcar.png";
                break;
            case 2:
                uri = "/res/greencar.png";
                break;
        }
        ImageView iv = new ImageView();
        iv.setImage(new Image(getClass().getResourceAsStream(uri)));
        iv.setFitWidth(CAR_WIDTH);
        iv.setFitHeight(CAR_HEIGHT);
        iv.setX(place.getX() - CAR_WIDTH / 2);
        iv.setY(place.getY() - CAR_HEIGHT / 2);

        car.setImage(iv);

        car.setPosition(place);

        Platform.runLater(() -> {
            pane_crossing.getChildren().add(iv);
        });
    }

    public void moveCar(Car car, Coordinates to) throws InterruptedException {
        ImageView iv = car.getImage();
        Coordinates from = car.getPosition();

        Path path = new Path();
        path.getElements().add(new MoveTo(from.getX(), from.getY()));

        double duration = Routing.distanceBetween(from, to) / CAR_SPEED;

        if (from.getX() == to.getX() || from.getY() == to.getY()) {
            path.getElements().add(new LineTo(to.getX(), to.getY()));
        } else {
            boolean sweep = car.getDestination() == car.getOrigin().getRight();
            path.getElements().add(new ArcTo(Math.abs(from.getX() - to.getX()),
                    Math.abs(from.getY() - to.getY()),
                    0,
                    to.getX(),
                    to.getY(),
                    false,
                    sweep));
            //path.getElements().add(new CubicCurveTo(ctrl1.getX(), ctrl1.getY(), ctrl2.getX(), ctrl2.getY(), to.getX(), to.getY()));
            duration *= CURVE_FACTOR;
        }

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(duration));
        pathTransition.setPath(path);

        pathTransition.setNode(iv);

        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        car.setPosition(to);

        Platform.runLater(() -> {
            pathTransition.play();
        });

        Thread.sleep((long) duration);
    }

    public void removeCar(Car car) {
        Platform.runLater(() -> {
            pane_crossing.getChildren().remove(car.getImage());
        });
    }

    public Crossing getCrossing() {
        return crossing;
    }
}
