/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_worker;

import car_worker.pojo.Worker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author Julian
 */
public class FXMLDocumentController implements Initializable {
    
    private Worker worker; 
    
    @FXML
    private Button btn_start;
    
    @FXML
    private Button btn_show_result;
    
    @FXML
    private Button btn_stop;
    
    @FXML
    private ImageView img_car;
    
    @FXML
    private Label label_message;
    
    @FXML
    private void onActionPerformed(ActionEvent event) {
        if(event.getSource() == this.btn_start){
            Worker worker = new Worker();
            this.label_message.textProperty().bind(worker.getStringProperty());
            this.img_car.xProperty().bind(worker.getDp());
            new Thread(worker).start();
        }else if (event.getSource() == this.btn_show_result){
            
        }else if (event.getSource() == this.btn_stop){
            worker.cancel();
            this.label_message.setText("worker cancelled");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
