/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.Transaction;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleObjectProperty;

/**
 * FXML Controller class
 * When called, the screen will display details about
 * a specific transaction
 *
 * @author Ekow Bentsi-Enchill
 */
public class TransactionViewController implements Initializable {
    
    private String id;
    Transaction tran; 
    Map<String,Integer> transItems;
    
    @FXML
    private Label transactionDateLabel;
    
    @FXML
    private Label transIdLabel;
    
    @FXML
    private Label customerIdLabel;
    
    @FXML
    private Label customerNameLabel;
    
    @FXML
    private Label transactionModeLabel;
    
    @FXML
    private Label creditAmountLabel;
    
    @FXML
    private TableView<Map.Entry<String, Integer>> itemsTable;
    
    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> itemSoldColumn;
    
    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> quantityColumn;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void setID(String id) {
        this.id = id;
        setValues(this.id);
    }
    
    private void setValues(String id) {
         ////get the transaction in question from the database
        try {
            tran = DatabaseManager.getTransactionWithId(id);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //customer ID
        customerIdLabel.setText(tran.getCustomerID());

        //date of transaction
        transactionDateLabel.setText(tran.getDate());
        
        //TODO: credit amount - problematic, returns a NullPointerException Error
        //creditAmountLabel.setText(String.valueOf(tran.getCreditAmount()));
        
        //name of customer (From Database)
        try {
            customerNameLabel.setText(DatabaseManager.getCustomerWithId(tran.getCustomerID()).getfullName());
        } catch (Exception ex) {
            customerNameLabel.setText("Hello");
        }
        
        //transaction ID
        transIdLabel.setText(tran.getID());
        
        //transaction mode
        transactionModeLabel.setText(tran.getMode());
        
        //bind itemsSoldColumn to ObservableValue of items
        itemSoldColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> p) 
                -> new SimpleObjectProperty<>(p.getValue().getKey()));
        
        
        //bind quantityColumn to ObservaValue of items
        quantityColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer> p) 
                -> new SimpleObjectProperty<>(p.getValue().getValue()));
        
        //make the list of Entry items
        transItems = tran.getTransactions(); //the map of transaction items
        ObservableList<Map.Entry<String, Integer>> oItemsList = FXCollections.observableArrayList(transItems.entrySet());
        
        //bind to table view
        itemsTable.setItems(oItemsList);
        itemsTable.getColumns().setAll(itemSoldColumn, quantityColumn);
        
    }
}
