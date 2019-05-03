/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg006_performancecomparison;

import Data.MyMath;
import DataAccess.DatabaseMongoDB;
import DataAccess.DatabaseOracle;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 *
 * @author schueler
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private MenuItem mItemOrDeleteAll;

    @FXML
    private MenuItem mItemOrCreateSingle;

    @FXML
    private MenuItem mItemOrCreateBulk;

    @FXML
    private MenuItem mItemOrSearchKey;

    @FXML
    private MenuItem mItemOrSearchDate;

    @FXML
    private MenuItem mItemMoDeleteAll;

    @FXML
    private MenuItem mItemMoCreateSingle;

    @FXML
    private MenuItem mItemMoCreateBulk;

    @FXML
    private MenuItem mItemMoSearchKey;

    @FXML
    private MenuItem mItemMoSearchDate;
    
    @FXML
    private MenuItem mItemMoSearchDateISO;
    
    @FXML
    private TextField txtNumberOfAnimals;

    @FXML
    private Label lblMessage;

    
    DatabaseOracle dbOracle;
    DatabaseMongoDB dbMongo;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            this.dbOracle = DatabaseOracle.getInstance();
            this.dbMongo = DatabaseMongoDB.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    void actionPerform(ActionEvent event) {
        try {
            
            if(event.getSource() == this.mItemOrCreateSingle){
                this.createSingleOr();
            }else
                if(event.getSource() == this.mItemMoCreateSingle){
                    this.createSingleMo();
                }else
                    if(event.getSource() == this.mItemOrCreateBulk){
                        this.createWithBulkOr();
                    }else
                        if(event.getSource() == this.mItemMoCreateBulk){
                            this.createBulkMo();
                        }else
                            if(event.getSource() == this.mItemOrSearchKey){
                                this.searchKeyOr();
                            }else
                                if(event.getSource() == this.mItemMoSearchKey){
                                    this.searchKeyMo();
                                }else
                                    if(event.getSource() == this.mItemMoSearchDateISO){
                                        this.searchDateISOMo();
                                    }else
                                        if(event.getSource() == this.mItemOrSearchDate){
                                            this.searchDateOr();
                                        }else
                                            if(event.getSource() == this.mItemMoSearchDate){
                                                this.searchDateMo();
                                            }else
                                                if(event.getSource() == this.mItemOrDeleteAll){
                                                    this.deleteAllOr();
                                                }else
                                                    if(event.getSource() == this.mItemMoDeleteAll){
                                                        this.deleteAllMo();
                                                    }
        } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSingleOr() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        this.dbOracle.createSingleEntries(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec.");
    }

    private void deleteAllOr() throws SQLException, Exception {
        LocalTime start = LocalTime.now();
        this.dbOracle.deleteAll();
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("delete all lasted: " + mSec + "msec.");
    }

    private void createSingleMo() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        this.dbMongo.createSingleEntries(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec.");
    }

    private void createWithBulkOr() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        this.dbOracle.createWithBulk(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec.");
    }

    private void searchKeyOr() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        long found = this.dbOracle.searchNumberOfAnimalsByKey(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec and found " + found + " from " + this.txtNumberOfAnimals.getText() + ".");
    }

    private void deleteAllMo() throws Exception {
        LocalTime start = LocalTime.now();
        this.dbMongo.deleteAll();
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("delete all lasted: " + mSec + "msec.");
    }

    private void searchKeyMo() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        long found = this.dbMongo.searchNumberOfAnimalsByKey(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec and found " + found + " from " + this.txtNumberOfAnimals.getText() + ".");
    }

    private void searchDateOr() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        long found = this.dbOracle.searchNumberOfAnimalsByDate(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec and found " + found + " from " + this.txtNumberOfAnimals.getText() + ".");
    }

    private void searchDateMo() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        long found = this.dbMongo.searchNumberOfAnimalsByDate(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec and found " + found + " from " + this.txtNumberOfAnimals.getText() + ".");
    }

    private void createBulkMo() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        this.dbMongo.createWithBulk(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec.");
    }

    private void searchDateISOMo() throws Exception {
        if(this.txtNumberOfAnimals.getText().equals(""))
            throw new Exception("Please enter the number of animals!");
        LocalTime start = LocalTime.now();
        long found = this.dbMongo.searchNumberOfAnimalsByISODate(Integer.parseInt(this.txtNumberOfAnimals.getText()));
        long mSec = MyMath.getMiliSecsBetween(start, LocalTime.now());
        this.lblMessage.setText("create lasted: " + mSec + "msec and found " + found + " from " + this.txtNumberOfAnimals.getText() + ".");
    }
    
    
}
