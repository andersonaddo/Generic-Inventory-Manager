/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Customer;
import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class CustomerSummaryController implements Initializable {
    Customer customer;
    private String id;
    Map<String, Double> debts;
    
    @FXML
    Button deletebtn;
    
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
    }  
    
    public void setID(String id) {
        this.id = id;
        setValues(id);
    }
    
    @FXML
    private void delete() throws SQLException {
        if (!HelperClass.confirmUser("Are you sure you want to delete this customer?")) return;
        DatabaseManager.deleteCustomer(id);
        Stage stage = (Stage) deletebtn.getScene().getWindow();
        stage.close();
    }
    
    private void setValues(String id) {
        try {
            customer = DatabaseManager.getCustomerWithId(id);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerNameLabel.setText(customer.getfullName());
        customerIdLabel.setText(customer.getID());
        customerGenderLabel.setText(customer.getGender());
        
        transIDColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Double>, String> p) 
                -> new SimpleObjectProperty<>(p.getValue().getKey())); 
        
        debtColumn.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, Double>, Double> p) 
                -> new SimpleObjectProperty<>(p.getValue().getValue())); 
        
        //make the list of Entry items
        debts = customer.getDebts(); 
        System.out.println(customer.getDebts().toString());
        ObservableList<Map.Entry<String, Double>> oDebtsList = FXCollections.observableArrayList(debts.entrySet()); 
        
        customerDebtsTable.setItems(oDebtsList);
        customerDebtsTable.getColumns().setAll(transIDColumn, debtColumn); 
        customerDebtsTable.refresh();
        
    }
}
