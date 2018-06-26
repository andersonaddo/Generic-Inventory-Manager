/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import chemicalinventorymanager.InventoryItem;
import chemicalinventorymanager.Supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author ADDO_a
 */
public class AddSupplierController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField newSupplierId;

    @FXML
    private Label supplierIdText;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    void addSupplier(ActionEvent event) throws SQLException {
        try{
            if (!inputsAreValid()) return;
            if (!HelperClass.confirmUser("Are you sure you want to add this supplier?")) return;
            String id = (!newSupplierId.getText().equals("")) ? newSupplierId.getText() : DatabaseManager.generateUniqueId(DatabaseManager.tableTypes.supplier);
            Supplier supp = new Supplier(id);
            supp.name = name.getText();
            supp.email = email.getText();
            supp.phone = phone.getText();       
            DatabaseManager.addSupplier(supp);
        }catch(Exception e){
            HelperClass.alertError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelperClass.disallowSpaces(newSupplierId);
        
        //TODO: Edit the delay for these tooltips 
        supplierIdText.setTooltip(new Tooltip("The ID of this supplier for the database. If not entered, it will be automatically generated"));
    }   
    
    boolean inputsAreValid() throws SQLException{
        try{
            if (DatabaseManager.getIDs(DatabaseManager.tableTypes.supplier).contains(newSupplierId.getText())){
                HelperClass.alertInvalidInput("The id you entered is aleady associated to another supplier.");
                return false;
            }

            if (name.getText().trim().equals("")){
                HelperClass.alertInvalidInput("The name you entered isn't valid!");
                return false;            
            }
            
            return true; 
        }catch(Exception e){
            HelperClass.alertError(e);
            return false;
        }
    }
    
}
