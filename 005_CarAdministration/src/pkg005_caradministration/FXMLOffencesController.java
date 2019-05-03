/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg005_caradministration;

import Data.Offence;
import Data.Owner;
import DataAccess.Database;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class FXMLOffencesController implements Initializable {

     @FXML
    private MenuItem mItemAdd;

    @FXML
    private MenuItem mItemDelete;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtDetails;

    @FXML
    private ListView<Offence> listOffences;

    @FXML
    private Label lblMessage;

    Database db = Database.getInstance();
    Owner currentOwner = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentOwner = db.getOwnerById(FXMLMainController.currentOwnerId);
        this.fillListOffences(currentOwner.getCollOffences());
        this.datePicker.setValue(LocalDate.now());
    }   
    
    @FXML
    void actionPerformed(ActionEvent event) {
        try{
            if(event.getSource() == this.mItemAdd){
                this.addOffence();
            }else if(event.getSource() == this.mItemDelete)
                this.deleteOffence();
        }catch(Exception ex){
            this.lblMessage.setText(ex.toString());
        }
    }

    private void fillListOffences(TreeSet<Offence> allOffences) {
        this.listOffences.getItems().clear();
        this.listOffences.getItems().addAll(allOffences);
    }

    private void addOffence() {
        LocalDate date = this.datePicker.getValue();
        String details = this.txtDetails.getText();
        currentOwner.add(new Offence(date, details));
        this.db.update(currentOwner);
        this.fillListOffences(currentOwner.getCollOffences());
        this.lblMessage.setText("sucessfully added");
    }

    private void deleteOffence() throws Exception {
        Offence offence = this.listOffences.getSelectionModel().getSelectedItem();
        currentOwner.remove(offence);
        this.db.update(currentOwner);
        this.fillListOffences(currentOwner.getCollOffences());
        this.lblMessage.setText("sucessfully deleted");
    }

}
