/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg005_caradministration;

import Data.Car;
import Data.Owner;
import Data.Ownership;
import DataAccess.Database;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class FXMLMainController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField txtCarName;

    @FXML
    private TextField txtCarCcm;

    @FXML
    private TextField txtCarHP;

    @FXML
    private TextArea txtCarInfo;

    @FXML
    private ListView<Car> listCars;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtIPAdress;

    @FXML
    private TextField txtOwnerName;

    @FXML
    private TextField txtBirthdate;

    @FXML
    private TextArea txtOwnerDetails;

    @FXML
    private ListView<Owner> listOwners;
    
    @FXML
    private MenuItem menuItemInsert;

    @FXML
    private MenuItem menuItemDelete;

    @FXML
    private MenuItem menuItemUpdate;

    @FXML
    private MenuItem menuItemReplace;

    @FXML
    private MenuItem menuItemFindAll;

    @FXML
    private MenuItem menuItemFindRelevance;

    @FXML
    private MenuItem menuItemConnect;

    @FXML
    private MenuItem menuItemClose;
    
    @FXML
    private MenuItem menuItemCreateTextInd;
    
    @FXML
    private MenuItem menuItemInsertOwner;

    @FXML
    private MenuItem menuItemDeleteOwner;

    @FXML
    private MenuItem menuItemUpdateOwner;

    @FXML
    private MenuItem menuItemReplaceOwner;

    @FXML
    private MenuItem menuItemFindAllOwner;
    
    @FXML
    private ListView<Ownership> listOwnerships;

    @FXML
    private DatePicker datePickerStartDate;

    @FXML
    private DatePicker datePickerEndDate;
    
    @FXML
    private MenuItem menuItemInsertOwnership;

    @FXML
    private MenuItem menuItemRemoveOwnership;
    
    @FXML
    private MenuItem menuItemOffences;
    
    Database db = null;
    
    public static ObjectId currentOwnerId = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.setListListener();
        this.db = Database.getInstance();
        this.fillListCars(db.getCars());
        this.fillListOwners(db.getOwners());
        this.datePickerStartDate.setValue(LocalDate.of(2015, Month.MARCH, 7));
        this.datePickerEndDate.setValue(LocalDate.now());
    }    
    
    @FXML
    void actionPerform(ActionEvent event) {
        try{
            if (event.getSource() == this.menuItemConnect) {
                this.db = Database.getInstance();
                this.txtMessage.setText("Connection established!");
            } else if (event.getSource() == this.menuItemInsert) {
                this.insertCar();
            } else if (event.getSource() == this.menuItemFindAll) {
                this.fillListCars(db.getCars());
            } else if (event.getSource() == this.menuItemUpdate) {
                this.updateCar();
            } else if (event.getSource() == this.menuItemReplace) {
                this.replaceCar();
            } else if (event.getSource() == this.menuItemDelete) {
                this.deleteCar();
            } else if (event.getSource() == this.menuItemFindRelevance) {
                this.getCarsByRelevance();
            } else if (event.getSource() == this.menuItemCreateTextInd) {
                this.db.createTextIndex();
                this.txtMessage.setText("Text Index created!");
            } else if (event.getSource() == this.menuItemClose) {
                this.db.close();
                this.txtMessage.setText("Connection closed!");
            } else if (event.getSource() == this.menuItemReplaceOwner){
                this.replaceOwner();
            }          
            else if (event.getSource() == this.menuItemInsertOwner) {
                this.insertOwner();
            } else if (event.getSource() == this.menuItemFindAllOwner) {
                this.fillListOwners(db.getOwners());
            } else if (event.getSource() == this.menuItemUpdateOwner) {
                this.updateOwner();
            } else if (event.getSource() == this.menuItemDeleteOwner) {
                this.deleteOwner();
            } else if (event.getSource() == this.menuItemInsertOwnership) {
                this.insertOwnership();
            } else if (event.getSource() == this.menuItemRemoveOwnership) {
                this.removeOwnership();
            }else if (event.getSource() == this.menuItemOffences) {
                openOffences();
            }
        }catch(Exception ex){
            this.txtMessage.setText(ex.toString());
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    public void showDetails(MouseEvent event) {
        Car selectedCar = this.listCars.getSelectionModel().getSelectedItem();
        this.txtCarCcm.setText("" + selectedCar.getYear());
        this.txtCarName.setText(selectedCar.getName());
        this.txtCarInfo.setText(selectedCar.getDescription());
        this.txtCarHP.setText("" + selectedCar.getHp());
    }
    
    private void fillListCars(ArrayList<Car> allCars){
        this.listCars.getItems().clear();
        this.listCars.getItems().addAll(allCars);
    }
    
    private void fillListOwners(ArrayList<Owner> allOwners){
        this.listOwners.getItems().clear();
        this.listOwners.getItems().addAll(allOwners);
    }
    
    private void fillListOwnerships(TreeSet<Ownership> allOwnerships) {
        this.listOwnerships.getItems().clear();
        this.listOwnerships.getItems().addAll(allOwnerships);
    }
    
    private void insertCar() throws Exception {
        String name = this.txtCarName.getText();
        String desc = this.txtCarInfo.getText();
        int hp = Integer.parseInt(this.txtCarHP.getText());
        int ccm = Integer.parseInt(this.txtCarCcm.getText());
        this.db.insert(new Car(name, hp, ccm, desc));
        this.fillListCars(db.getCars());
        this.txtMessage.setText("Sucessfully inserted!");
    }

    private void setListListener() {
        this.listCars.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Car>() {
            @Override
            public void changed(ObservableValue<? extends Car> observable, Car oldValue, Car newValue) {
                if(newValue == null){
                    txtCarCcm.setText("");
                    txtCarName.setText("");
                    txtCarInfo.setText("");
                    txtCarHP.setText("");
                }else{
                    txtCarCcm.setText("" + newValue.getYear());
                    txtCarName.setText(newValue.getName());
                    txtCarInfo.setText(newValue.getDescription());
                    txtCarHP.setText("" + newValue.getHp());
                    TreeSet<Ownership> ownerships = newValue.getOwnerships();
                    fillListOwnerships(ownerships);
                }
            }            
        });
        
        this.listOwners.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Owner>() {
            @Override
            public void changed(ObservableValue<? extends Owner> observable, Owner oldValue, Owner newValue) {
                if(newValue == null){
                    txtOwnerName.setText("");
                    txtBirthdate.setText("");
                    txtOwnerDetails.setText("");
                }else{
                    txtOwnerName.setText(newValue.getName());
                    txtBirthdate.setText(newValue.getDate().toString());
                    txtOwnerDetails.setText(newValue.getDetails());
                    ArrayList<Car> allCars = db.getCarsByOwner(newValue.getId());
                    fillListCars(allCars);
                    currentOwnerId = newValue.getId();
                    txtMessage.setText(newValue.toString());
                }
            }            
        });
        
        this.listOwnerships.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ownership>() {
            @Override
            public void changed(ObservableValue<? extends Ownership> observable, Ownership oldValue, Ownership newValue) {
                if(newValue == null){
                    txtCarCcm.setText("");
                    txtCarName.setText("");
                    txtCarInfo.setText("");
                    txtCarHP.setText("");
                }else{
                    Owner owner = db.getOwnerById(newValue.getId());
                    txtOwnerName.setText(owner.getName());
                    txtBirthdate.setText(owner.getDate().toString());
                    txtOwnerDetails.setText(owner.getDetails());
                    txtMessage.setText(owner.toString());
                }
                    
            }
                   
        });
    }

    private void updateCar() throws Exception {
        this.checkCarInput();
        Car selectedCar = this.listCars.getSelectionModel().getSelectedItem();
        if(selectedCar == null)
            throw new Exception("Please select a car!");
        String name = this.txtCarName.getText();
        String desc = this.txtCarInfo.getText();
        int hp = Integer.parseInt(this.txtCarHP.getText());
        int ccm = Integer.parseInt(this.txtCarCcm.getText());
        Car newCar = new Car(selectedCar.getId(), name, hp, ccm, desc);
        newCar.setOwnerships(selectedCar.getOwnerships());
        this.db.update(newCar);
        this.fillListCars(db.getCars());
        this.txtMessage.setText("Sucessfully updated!");
    }

    private void checkCarInput() throws Exception {
        if(this.txtCarCcm.getText().equals("") || this.txtCarHP.getText().equals("") || this.txtCarInfo.getText().equals("") || this.txtCarName.getText().equals(""))
            throw new Exception("Check Input!");
    }

    private void replaceCar() throws Exception {
        this.checkCarInput();
        Car selectedCar = this.listCars.getSelectionModel().getSelectedItem();
        if(selectedCar == null)
            throw new Exception("Please select a car!");
        String name = this.txtCarName.getText();
        String desc = this.txtCarInfo.getText();
        int hp = Integer.parseInt(this.txtCarHP.getText());
        int ccm = Integer.parseInt(this.txtCarCcm.getText());
        this.db.replace(new Car(selectedCar.getId(), name, hp, ccm, desc));
        this.fillListCars(db.getCars());
        this.txtMessage.setText("Sucessfully replaced!");
    }

    private void deleteCar() throws Exception {
        Car car = this.listCars.getSelectionModel().getSelectedItem();
        this.db.delete(car);
        this.fillListCars(db.getCars());
        this.txtMessage.setText("Sucessfully deleted!");
    }

    private void getCarsByRelevance() {
        String searchText = this.txtCarInfo.getText();
        ArrayList<Car> searchedCars = this.db.getCarsByRelevance(searchText);
        this.fillListCars(searchedCars);
    }

    private void insertOwner() throws Exception {
        this.checkOwnerInput();
        String name = this.txtOwnerName.getText();
        String details = this.txtOwnerDetails.getText();
        LocalDate date = LocalDate.parse(this.txtBirthdate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.db.insert(new Owner(name, details, date));
        this.fillListOwners(db.getOwners());
        this.txtMessage.setText("Sucessfully inserted!");
    }
    
    private void checkOwnerInput() throws Exception {
        if(this.txtBirthdate.getText().equals("") || this.txtOwnerDetails.getText().equals("") || this.txtOwnerName.getText().equals(""))
            throw new Exception("Check Input!");
    }

    private void updateOwner() throws Exception {
        this.checkOwnerInput();
        String name = this.txtOwnerName.getText();
        String details = this.txtOwnerDetails.getText();
        LocalDate date = LocalDate.parse(this.txtBirthdate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ObjectId _id = this.listOwners.getSelectionModel().getSelectedItem().getId();
        long result = this.db.update(new Owner(_id, name, details, date));
        this.fillListOwners(db.getOwners());
        this.txtMessage.setText("Sucessfully updated!" + result);
    }

    private void deleteOwner() throws Exception {
        Owner selectedOwner = this.listOwners.getSelectionModel().getSelectedItem();
        if(selectedOwner == null)
            throw new Exception("Please select an Owner in list!");
        this.db.delete(selectedOwner);
        this.fillListOwners(db.getOwners());
        this.txtMessage.setText("Sucessfully deleted!");
    }

    private void insertOwnership() throws Exception {
        Owner selectedOwner = this.listOwners.getSelectionModel().getSelectedItem();
        if(selectedOwner == null)
            throw new Exception("Please select an Owner in list!");
        Car selectedCar = this.listCars.getSelectionModel().getSelectedItem();
        if(selectedCar == null)
            throw new Exception("Please select a Car in list!");
        LocalDate startDate = this.datePickerStartDate.getValue();
        LocalDate endDate = this.datePickerEndDate.getValue();
        Ownership ownership = new Ownership(startDate, endDate, selectedOwner.getId());
        selectedCar.addOwnership(ownership);
        this.db.update(selectedCar);
        this.fillListCars(db.getCars());
        this.listOwnerships.getItems().clear();
        this.txtMessage.setText(selectedOwner.toString() + " ==> " + selectedCar.toString());
    }

    private void removeOwnership() {
        Ownership selectedOwnership = this.listOwnerships.getSelectionModel().getSelectedItem();
        this.db.delete(selectedOwnership);
        this.fillListCars(db.getCars());
        this.listOwnerships.getItems().clear();
    }

    private void openOffences() throws Exception {
        if(currentOwnerId == null)
            throw new Exception("Please select an owner!");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLOffences.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Offences of " + this.listOwners.getSelectionModel().getSelectedItem().getName());
        stage.setScene(scene);
        stage.show();
    }

    private void replaceOwner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
