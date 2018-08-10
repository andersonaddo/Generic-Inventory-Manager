/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import chemicalinventorymanager.Supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class SupplierSummaryController implements Initializable {

    Supplier supplier;
    private String id;
    
    @FXML
    TextField supplierNameTextField;
    
    @FXML
    Label supplierIDLabel;
    
    @FXML
    TextField supplierEmailTextField;
    
    @FXML
    TextField supplierPhoneTextField;
    
    @FXML
    ListView itemsSuppliedList;
    
    @FXML
    Button deletebtn;
    
    @FXML
    Button updatebtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void delete() throws SQLException {
        if (!HelperClass.confirmUser("Are you sure you want to delete this supplier?")) return;
        DatabaseManager.deleteSupplier(id);
        Stage stage = (Stage) deletebtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void update() throws SQLException {
        if (!HelperClass.confirmUser("Are you sure you want to update this supplier?")) return;
        DatabaseManager.updateSupplier(supplier.getID(), supplierNameTextField.getText(), supplierEmailTextField.getText(), supplierPhoneTextField.getText());
        Stage stage = (Stage) updatebtn.getScene().getWindow();
        stage.close();
    }
    
    public void setID(String id) {
        this.id = id;
        setValues(id);
    }
    
    private void setValues(String id) {
        try {
            supplier = DatabaseManager.getSupplierWithId(id);
        } catch (SQLException ex) {
            Logger.getLogger(SupplierSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        supplierNameTextField.setText(supplier.name);
        supplierEmailTextField.setText(supplier.email);
        supplierPhoneTextField.setText(supplier.phone);
        supplierIDLabel.setText(id);
        
        
        ArrayList<String> items = supplier.getItemsSupplied();
        
        items.forEach((item) -> {
            itemsSuppliedList.getItems().add(item);
        });
    }
}
