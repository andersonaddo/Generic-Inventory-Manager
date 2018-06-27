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
public class ViewTransactionController implements Initializable {
    
    Transaction tran; 
    
    @FXML
    private Label transactionDateLabel;
    
    @FXML
    private Label customerIdLabel;
    
    @FXML
    private Label customerNameLabel;
    
    @FXML
    private Label transactionModeLabel;
    
    @FXML
    private TableView<Map> itemsTable;
    
    @FXML
    private TableColumn<Map, String> itemSoldColumn;
    
    @FXML
    private TableColumn<Map, Integer> quantityColumn;
    
    /**
     * The search screen will call this method
     * @param id
     * @throws SQLException
     */
    @FXML
    public static void viewTransaction(String id) throws SQLException {
        ViewTransactionController.tran = DatabaseManager.getTransactionWithId(id);
    }
     
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemSoldColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("item"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("quantity"));
    }    
    
}
