/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Customer;
import chemicalinventorymanager.DatabaseManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class CustomerSummaryController implements Initializable {
    Customer customer;
    Map<String, Double> debts;
    
    @FXML
    Label customerNameLabel;
    
    @FXML
    Label customerIdLabel;
    
    @FXML
    Label customerGenderLabel;
    
    @FXML
    Label customerTotalDebtLabel;
    
    @FXML
    TableView <Map.Entry<String, Double>> customerDebtsTable;
    
    @FXML
    TableColumn <Map.Entry<String, Double>, String>transIDColumn;
    
    @FXML 
    TableColumn <Map.Entry<String, Double>, Double> debtColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            customer = DatabaseManager.getCustomerWithId("42");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerNameLabel.setText(customer.getfullName());
        customerIdLabel.setText(customer.getID());
        customerGenderLabel.setText(customer.getGender());
        
        /* transIDColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Double>, String> p) 
                -> new SimpleObjectProperty<>(p.getValue().getKey())); 
        
        debtColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Double>, Double> p) 
                -> new SimpleObjectProperty<>(p.getValue().getValue())); 
        
        //make the list of Entry items
        debts = customer.getDebts(); 
        ObservableList<Map.Entry<String, Double>> oDebtsList = FXCollections.observableArrayList(debts.entrySet()); 
        
        customerDebtsTable.setItems(oDebtsList);
        customerDebtsTable.getColumns().setAll(transIDColumn, debtColumn); */
        
        
        
    }  
}
