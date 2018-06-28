/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.Supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class SupplierSummaryController implements Initializable {

    Supplier supplier;
    
    @FXML
    Label supplierNameLabel;
    
    @FXML
    Label supplierEmailLabel;
    
    @FXML
    Label supplierPhoneLabel;
    
    @FXML
    ListView itemsSuppliedList;
    
    public SupplierSummaryController(String id) throws SQLException{
        supplier = DatabaseManager.getSupplierWithId("1");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        supplierNameLabel.setText(supplier.name);
        supplierEmailLabel.setText(supplier.email);
        supplierPhoneLabel.setText(supplier.phone);
        
        
        ArrayList<String> items = supplier.getItemsSupplied();
        
        items.forEach((item) -> {
            itemsSuppliedList.getItems().add(item);
        });
    }    
    
}
