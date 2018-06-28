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
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 * When called, the screen will display details about
 * a specific transaction
 *
 * @author Ekow Bentsi-Enchill
 */
public class TransactionViewController implements Initializable {
    
    Transaction tran; 
    
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
    private TableView<Map<String, Integer>> itemsTable;
    
    @FXML
    private TableColumn<Map, String> itemSoldColumn;
    
    @FXML
    private TableColumn<Map, Integer> quantityColumn;
    
    public TransactionViewController(String id) throws SQLException{
        tran = DatabaseManager.getTransactionWithId(id);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerIdLabel.setText(tran.getCustomerID());
        transactionDateLabel.setText(tran.getDate());
        try {
            customerNameLabel.setText(DatabaseManager.getCustomerWithId(tran.getCustomerID()).getfullName());
        } catch (SQLException ex) {
            customerNameLabel.setText(null);
        }
        transIdLabel.setText(tran.getID());
        transactionModeLabel.setText(tran.getMode());

        //to populate the tableview with transaction item details
        Map<String,Integer> transItems = tran.getTransactions();
        ObservableMap<String,Integer> oTransItems 
                = FXCollections.observableMap(transItems);
        ObservableList<ObservableMap.Entry<String,Integer>> oTransItemsList 
                = FXCollections.observableArrayList(oTransItems.entrySet());
        //itemsTable.setItems(oTransItemsList);
    }    
    
}
