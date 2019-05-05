///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package maturatraining;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author schueler
 */
public class Car extends Task<String> implements AnimCoordinates {

    int id;
    Semaphore roadMiddle,
            road_up,
            road_down;
    Location start, end;
    private static FXMLDocumentController controller;
    private int currentX, currentY, oldX, oldY;
    static AtomicInteger counterUdine=new AtomicInteger(0);
    static AtomicInteger counterAfritz=new AtomicInteger(0);
    
    public Car(int id, Semaphore roadMiddle, Semaphore road_up, Semaphore road_down, Location start, Location end) {
        this.id = id;
        this.roadMiddle = roadMiddle;
        this.road_up = road_up;
        this.road_down = road_down;

        this.start = start;
        this.end = end;
    }

    @Override
    public String call() {

        try {
            if (this.start == Location.Spittal) {
                /*this.oldX = START_SPITTAL_X;
                this.oldY = START_SPITTAL_Y;
                this.currentX = MIDDLE_SPITTAL_X;
                this.currentY = START_SPITTAL_Y;*/
                if (this.end == Location.Udine) {

                    move(START_SPITTAL_X,START_SPITTAL_Y,MIDDLE_SPITTAL_X,START_SPITTAL_Y); // move right forward, horizontal
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...waiting at crossing");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    
                    this.oldX = MIDDLE_SPITTAL_X;
                    this.oldY = START_SPITTAL_Y;
                    this.currentX = START_UDINE_X;
                    this.currentY = START_UDINE_Y;
                    if(counterUdine.get()==0){
                        counterUdine.incrementAndGet();
                        this.road_down.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }else{
                        counterUdine.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Afritz) {
                    move(); // move right forward, horizontal
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...waiting at crossing");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free kreuzung. turning to afritz road");
                    this.oldX = MIDDLE_SPITTAL_X;
                    this.oldY = START_SPITTAL_Y;
                    this.currentX = START_AFRITZ_X;
                    this.currentY = START_AFRITZ_Y;
                    
                    if(counterAfritz.get()==0){
                        counterAfritz.incrementAndGet();
                        this.road_up.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }else{
                        counterAfritz.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Klagenfurt) {
                    move(); // move right to middle
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...waiting at crossing");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free kreuzung. moving forward to klagenfurt");
                    this.oldX = MIDDLE_SPITTAL_X;
                    this.oldY = START_SPITTAL_Y;
                    this.currentX = START_KLAGENFURT_X;
                    this.currentY = START_KLAGENFURT_Y;
                    move(); // forward horizontal
                    Thread.sleep(ANIMATION_DURATION);
                    this.roadMiddle.release();
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                }
            } else if (this.start == Location.Klagenfurt) {
                //anfang
                this.oldX = START_KLAGENFURT_X;
                this.oldY = START_KLAGENFURT_Y;
                this.currentX = MIDDLE_KLAGENFURT_X;
                this.currentY = START_KLAGENFURT_Y;
                if (this.end == Location.Udine) {

                    move(); // left move forward, horizontal
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...corssing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...spend 2.5 sec. now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, turning to udine road");
                   
                    this.oldX = MIDDLE_KLAGENFURT_X;
                    this.oldY = START_KLAGENFURT_Y;
                    this.currentX = START_UDINE_X;
                    this.currentY = START_UDINE_Y;
                    if(counterUdine.get()==0){
                        counterUdine.incrementAndGet();
                        this.road_down.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }else{
                        counterUdine.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }
//delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Afritz) {
                    move(); // left move forward, horizontal
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...corssing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing. moving to afritz");
                    
                    this.oldX = MIDDLE_KLAGENFURT_X;
                    this.oldY = START_KLAGENFURT_Y;
                    this.currentX = START_AFRITZ_X;
                    this.currentY = START_AFRITZ_Y;
                    if(counterAfritz.get()==0){
                        counterAfritz.incrementAndGet();
                        this.road_up.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }else{
                        counterAfritz.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Spittal) {
                    move(); // move to middle
                    Thread.sleep(ANIMATION_DURATION);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...corssing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing. moving forward to spital");
                    //nach spittal
                    this.oldX = MIDDLE_KLAGENFURT_X;
                    this.oldY = START_KLAGENFURT_Y;
                    this.currentX = START_SPITTAL_X;
                    this.currentY = START_SPITTAL_Y;
                    move(); // forward horizontal
                    Thread.sleep(ANIMATION_DURATION);
                    this.roadMiddle.release();
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");

                }
            } else if (this.start == Location.Udine) {
                //anfang udine
                this.oldX = START_UDINE_X;
                this.oldY = START_UDINE_Y;
                this.currentX = START_UDINE_X;
                this.currentY = MIDDLE_UDINE_Y;
                System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...is waiting for free udine road");
                this.road_down.acquire(); // waiting in park zone
                if (this.end == Location.Afritz) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...moving forward to crossing");
move();                    
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...crossing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to afritz");
                    
                    
                     this.oldY = MIDDLE_UDINE_Y;
                    this.oldX = START_UDINE_X;
                    this.currentX = START_AFRITZ_X;
                    this.currentY = START_AFRITZ_Y;
                   
                     if(counterAfritz.get()==0){
                        counterAfritz.incrementAndGet();
                        this.road_up.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                                            this.road_down.release();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }else{
                        counterAfritz.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterAfritz.decrementAndGet();
                                            this.road_down.release();
                        this.roadMiddle.release();
                        if(counterAfritz.get()==0){
                            this.road_up.release();
                        }
                    }
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Spittal) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...is moving to crossing");
                    move(); //move to middle (vertical)
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...arrived at crossing");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to spitall");
                    //nach spittal
                    this.oldY = MIDDLE_UDINE_Y;
                    this.oldX = START_UDINE_X;
                    this.currentX = START_SPITTAL_X;
                    this.currentY = START_SPITTAL_Y;
                    move(); //move to spittal (left, horizontzal)
                    Thread.sleep(ANIMATION_DURATION);
                    this.road_down.release();
                    this.roadMiddle.release();
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");

                } else if (this.end == Location.Klagenfurt) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...moving to crossing");
                    move(); //move to middle (vertical)
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...crossing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to klagnfurt");

                    this.oldY = MIDDLE_UDINE_Y;
                    this.oldX = START_UDINE_X;
                    this.currentX = START_KLAGENFURT_X;
                    this.currentY = START_KLAGENFURT_Y;
                    move(); //move to klagenfurt (right, horizontzal)
                    Thread.sleep(ANIMATION_DURATION);
                    this.road_down.release();
                    this.roadMiddle.release();
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                }
            } else if (this.start == Location.Afritz) {
                //anfang Afritz
                this.oldX = START_AFRITZ_X;
                this.oldY = START_AFRITZ_Y;
                this.currentX = START_AFRITZ_X;
                this.currentY = MIDDLE_AFRITZ_Y;
                System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...is waiting for free afritz road");
                this.road_up.acquire(); // waiting in park zone
                if (this.end == Location.Udine) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...moving to crossing");
                    move(); //move to middle (vertical)
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...crossing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to udine");
                    
                    this.oldY = MIDDLE_AFRITZ_Y;
                    this.oldX = START_AFRITZ_X;
                    this.currentX = START_UDINE_X;
                    this.currentY = START_UDINE_Y;
                    if(counterUdine.get()==0){
                        counterUdine.incrementAndGet();
                        this.road_down.acquire();   
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.road_up.release();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }else{
                        counterUdine.incrementAndGet();
                        move(); // turn right
                        Thread.sleep(ANIMATION_DURATION);
                        counterUdine.decrementAndGet();
                        this.road_up.release();
                        this.roadMiddle.release();
                        if(counterUdine.get()==0){
                            this.road_down.release();
                        }
                    }
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");

                } else if (this.end == Location.Spittal) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...moving to crossing");
                    move(); //move to middle (vertical)
                    
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...crossing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "..now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to spittal");
                    this.oldY = MIDDLE_AFRITZ_Y;
                    this.oldX = START_AFRITZ_X;
                    this.currentX = START_SPITTAL_X;
                    this.currentY = START_SPITTAL_Y;
                    move(); //move to spittal (left, horizontzal)
                    Thread.sleep(ANIMATION_DURATION);
                    this.road_up.release();
                    this.roadMiddle.release();
                    //delete();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...finished");
                } else if (this.end == Location.Klagenfurt) {
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...moving to crossing");
                    move(); //move to middle (vertical)
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...crossing arrived");
                    Thread.sleep(2500);
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "..now waiting for free crossing");
                    this.roadMiddle.acquire();
                    System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "...free crossing, moving forward to klagenfurt");
                    this.oldY = MIDDLE_AFRITZ_Y;
                    this.oldX = START_AFRITZ_X;
                    this.currentX = START_KLAGENFURT_X;
                    this.currentY = START_KLAGENFURT_Y;
                    move(); //move to klagenfurt (right, horizontzal)
                    Thread.sleep(ANIMATION_DURATION);
                    this.road_up.release();
                    this.roadMiddle.release();
                    //delete();
                    System.out.println(this.id + " finished");

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex:" + ex.getMessage());
        }
        return "hahahahahahahahahahahahahahahahah " + id;
    }

    private void move() {
        Platform.runLater(() -> {
            try {
                controller.doAnimationMoving(this);
            } catch (Exception ex) {
                System.out.println("error in move:" + ex.getMessage());
            }
        });
    }
    
    private void move(int x, int y, int curX, int curY) {
        this.oldY = x;
        this.oldX = y;
        this.currentX = curX;
        this.currentY = curY;

        Platform.runLater(() -> {
            try {
                controller.doAnimationMoving(this);
            } catch (Exception ex) {
                System.out.println("error in move:" + ex.getMessage());
            }
        });
    }

     

    public static void setController(FXMLDocumentController controllerC) {
        Car.controller = controllerC;
    }

    public int getId() {
        return id;
    }

    public Semaphore getRoadMiddle() {
        return roadMiddle;
    }

    public void setRoadMiddle(Semaphore roadMiddle) {
        this.roadMiddle = roadMiddle;
    }

    public Semaphore getRoad_up() {
        return road_up;
    }

    public void setRoad_up(Semaphore road_up) {
        this.road_up = road_up;
    }

    public Semaphore getRoad_down() {
        return road_down;
    }

    public void setRoad_down(Semaphore road_down) {
        this.road_down = road_down;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

  
}

//    public void doAnimationMoving(CarDriver cd) throws Exception {
//        ImageView iv = collImages.get(cd.getIdOfDriver() - 1);
//        Path path = new Path();
//
//        path.getElements().add(new MoveTo(cd.getOldCooX(), START_Y + LANE_WIDTH * cd.getIdOfDriver()));
//        path.getElements().add(new HLineTo(cd.getCurrentCooX()));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
//        pathTransition.setPath(path);
//        pathTransition.setNode(iv);
//        pathTransition.setAutoReverse(false);
//        pathTransition.play();
//
//    }


/*
package maturatraining;

import java.util.concurrent.Semaphore;
import javafx.application.Platform;

public class Car implements Runnable, AnimCoordinates {

    int id;
    Semaphore roadMiddle,
            road_up,
            road_down;
    Location start, end;
    private static FXMLDocumentController controller;
    private int currentX, currentY, oldX, oldY;

    public Car(int id, Semaphore roadMiddle, Semaphore road_up, Semaphore road_down, Location start, Location end) {
        this.id = id;
        this.roadMiddle = roadMiddle;
        this.road_up = road_up;
        this.road_down = road_down;

        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            if (this.start == Location.Spittal) {
               this.oldX = START_SPITTAL_X;
                this.oldY = START_SPITTAL_Y;
                this.currentX = MIDDLE_SPITTAL_X;
                this.currentY = START_SPITTAL_Y;
                move();
                printMsg("waiting at crossing");
                Thread.sleep(2500);
                if (this.end == Location.Udine) {
                    printMsg("is waiting for free udine road");
                    this.road_down.acquire(); // block Udine
                    printMsg("spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    printMsg("free kreuzung. turning to udine road");

                    move(MIDDLE_SPITTAL_X,START_SPITTAL_Y,START_UDINE_X,START_UDINE_Y); // turn right
                    this.roadMiddle.release();
                    this.road_down.release();
                }
                
                
                move(START_SPITTAL_X, START_SPITTAL_Y, MIDDLE_SPITTAL_X, START_SPITTAL_Y);
                printMsg("waiting at crossing");
                Thread.sleep(2500);
                if (this.end == Location.Udine) {
                    printMsg("is waiting for free udine road");
                    this.road_down.acquire(); // block Udine
                    printMsg("spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    printMsg("free kreuzung. turning to udine road");
                    move(MIDDLE_SPITTAL_X, START_SPITTAL_Y, START_UDINE_X, START_UDINE_Y); // turn right
                    this.roadMiddle.release();
                    this.road_down.release();
                } else if (this.end == Location.Afritz) {
                    printMsg("is waiting for free afritz road");
                    this.road_up.acquire(); // block Afritz
                    printMsg("spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    printMsg("free kreuzung. turning to afritz road");
                    move(MIDDLE_SPITTAL_X, START_SPITTAL_Y, START_AFRITZ_X, START_AFRITZ_Y); // turn left
                    this.roadMiddle.release();
                    this.road_up.release();
                } else if (this.end == Location.Klagenfurt) {
                    printMsg("spend 2.5 sec waiting on crossing. now waiting for freie kreuzung ");
                    this.roadMiddle.acquire();
                    printMsg("free kreuzung. moving forward to klagenfurt");
                    move(MIDDLE_SPITTAL_X, START_SPITTAL_Y, START_KLAGENFURT_X, START_KLAGENFURT_Y); // forward horizontal
                    this.roadMiddle.release();
                }
                printMsg("finished");
            } else if (this.start == Location.Klagenfurt) {
                move(START_KLAGENFURT_X, START_KLAGENFURT_Y, MIDDLE_KLAGENFURT_X, START_KLAGENFURT_Y); // left move forward, horizontal
                printMsg("crossing arrived");
                Thread.sleep(2500);
                if (this.end == Location.Udine) {
                    printMsg("is waiting for free udine road");
                    this.road_down.acquire(); // block Udine
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, turning to udine road");
                    move(MIDDLE_KLAGENFURT_X, START_KLAGENFURT_Y, START_UDINE_X, START_UDINE_Y);
                    this.roadMiddle.release();
                    this.road_down.release();
                } else if (this.end == Location.Afritz) {
                    printMsg("is waiting for free afritz road");
                    this.road_up.acquire(); // block Afritz
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing. moving to afritz");
                    move(MIDDLE_KLAGENFURT_X, START_KLAGENFURT_Y, START_AFRITZ_X, START_AFRITZ_Y); // turn right
                    this.roadMiddle.release();
                    this.road_up.release();
                } else if (this.end == Location.Spittal) {
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing. moving forward to spital");
                    //nach spittal
                    move(MIDDLE_KLAGENFURT_X, START_KLAGENFURT_Y, START_SPITTAL_X, START_SPITTAL_Y); // forward horizontal
                    this.roadMiddle.release();
                }
                printMsg("finished");

            } else if (this.start == Location.Udine) {
                printMsg("is waiting for free udine road");
                this.road_down.acquire(); // waiting in park zone
                printMsg("moving forward to crossing");
                move(START_UDINE_X, START_UDINE_Y, START_UDINE_X, MIDDLE_UDINE_Y); //move to middle (vertical)
                printMsg("crossing arrived");
                Thread.sleep(2500);
                if (this.end == Location.Afritz) {
                    printMsg("is waiting for free afritz road");
                    this.road_up.acquire();
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to afritz");
                    move(START_UDINE_X, MIDDLE_UDINE_Y, START_AFRITZ_X, START_AFRITZ_Y); //move to middle (vertical) //move to aftritz (vertical)
                    this.road_down.release();
                    this.roadMiddle.release();
                    this.road_up.release();
                } else if (this.end == Location.Spittal) {
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to spitall");
                    move(START_UDINE_X, MIDDLE_UDINE_Y, START_SPITTAL_X, START_SPITTAL_Y); //move to klagenfurt (right, horizontzal)
                    this.road_down.release();
                    this.roadMiddle.release();
                } else if (this.end == Location.Klagenfurt) {
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to klagnfurt");
                    move(START_UDINE_X, MIDDLE_UDINE_Y, START_KLAGENFURT_X, START_KLAGENFURT_Y); //move to klagenfurt (right, horizontzal)
                    this.road_down.release();
                    this.roadMiddle.release();
                }
                printMsg("finished");
            } else if (this.start == Location.Afritz) {
                printMsg("is waiting for free afritz road");
                this.road_up.acquire(); // waiting in park zone
                printMsg("moving to crossing");
                move(START_AFRITZ_X, START_AFRITZ_Y, START_AFRITZ_X, MIDDLE_AFRITZ_Y); //move to middle (vertical)
                printMsg("crossing arrived");
                Thread.sleep(2500);
                if (this.end == Location.Udine) {
                    printMsg("is waiting for free udine road");
                    this.road_down.acquire();
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to udine");
                    move(START_AFRITZ_X, MIDDLE_AFRITZ_Y, START_UDINE_X, START_UDINE_Y); //move to aftritz (vertical)
                    this.road_up.release();
                    this.roadMiddle.release();
                    this.road_down.release();
                } else if (this.end == Location.Spittal) {
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to spittal");
                    move(START_AFRITZ_X, MIDDLE_AFRITZ_Y, START_SPITTAL_X, START_SPITTAL_Y); //move to spittal (left, horizontzal)
                    this.road_up.release();
                    this.roadMiddle.release();
                    printMsg("finished");
                } else if (this.end == Location.Klagenfurt) {
                    printMsg("now waiting for free crossing");
                    this.roadMiddle.acquire();
                    printMsg("free crossing, moving forward to klagenfurt");
                    move(START_AFRITZ_X, MIDDLE_AFRITZ_Y, START_KLAGENFURT_X, START_KLAGENFURT_Y);
                    this.road_up.release();
                    this.roadMiddle.release();
                }
                printMsg("finished");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ex:" + ex.getMessage());
        }

    }

    private void printMsg(String msg) {
        System.out.println("car#" + this.id + "...source=" + this.start + "...destination=" + this.end + "..." + msg);
    }

    
    private void move() {


        Platform.runLater(() -> {
            try {
                controller.doAnimationMoving(this);
            } catch (Exception ex) {
                System.out.println("error in move:" + ex.getMessage());
            }
        });
    }
    
    private void move(int x, int y, int curX, int curY) {
        this.oldY = x;
        this.oldX = y;
        this.currentX = curX;
        this.currentY = curY;

        Platform.runLater(() -> {
            try {
                controller.doAnimationMoving(this);
            } catch (Exception ex) {
                System.out.println("error in move:" + ex.getMessage());
            }
        });
    }

    public static void setController(FXMLDocumentController controllerC) {
        Car.controller = controllerC;
    }

    public int getId() {
        return id;
    }

    public Semaphore getRoadMiddle() {
        return roadMiddle;
    }

    public void setRoadMiddle(Semaphore roadMiddle) {
        this.roadMiddle = roadMiddle;
    }

    public Semaphore getRoad_up() {
        return road_up;
    }

    public void setRoad_up(Semaphore road_up) {
        this.road_up = road_up;
    }

    public Semaphore getRoad_down() {
        return road_down;
    }

    public void setRoad_down(Semaphore road_down) {
        this.road_down = road_down;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

}
*/
//    public void doAnimationMoving(CarDriver cd) throws Exception {
//        ImageView iv = collImages.get(cd.getIdOfDriver() - 1);
//        Path path = new Path();
//
//        path.getElements().add(new MoveTo(cd.getOldCooX(), START_Y + LANE_WIDTH * cd.getIdOfDriver()));
//        path.getElements().add(new HLineTo(cd.getCurrentCooX()));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
//        pathTransition.setPath(path);
//        pathTransition.setNode(iv);
//        pathTransition.setAutoReverse(false);
//        pathTransition.play();
//
//    }


