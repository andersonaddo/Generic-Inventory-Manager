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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author ADDO_a
 */
public class AddCustomerController implements Initializable {

    
    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<String> genderDropdown;

    @FXML
    private TextField customerID;

    @FXML
    private Label customerIdText;

    @FXML
    private TextField debt;

    @FXML
    private Label debtText;

    
    @FXML
    void addCustomer(ActionEvent event) throws SQLException {
        try{
            if (!inputsAreValid()) return;
            if (!HelperClass.confirmUser("Are you sure you want to add this customer?")) return;
            String id = (!customerID.getText().equals("")) ? customerID.getText() : DatabaseManager.generateUniqueId(DatabaseManager.tableTypes.customer);
            Customer customer = new Customer(id);
            customer.fullName = name.getText();
            customer.totalDebt =  (!debt.getText().isEmpty()) ? Double.parseDouble(debt.getText()) : 0;
            customer.gender = (genderDropdown.getValue()).equals("Male") ? Customer.Gender.male : Customer.Gender.female;
            DatabaseManager.addCustomer(customer);
        }catch(Exception e){
            HelperClass.alertError(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelperClass.makeNumericOnly(debt);
        genderDropdown.setItems(FXCollections.observableArrayList("Male", "Female"));
        HelperClass.disallowSpaces(customerID);
        
        //TODO: Edit the delay for these tooltips
        debtText.setTooltip(new Tooltip("How much debt has this customer already accumulated (if any)? Default is 0 cedis."));
        customerIdText.setTooltip(new Tooltip("The ID of this customer for the database. If not entered, it will be automatically generated"));
    }   
    
    boolean inputsAreValid() throws SQLException{
        try{
            if (DatabaseManager.getIDs(DatabaseManager.tableTypes.customer).contains(customerID.getText())){
                HelperClass.alertInvalidInput("The id you entered is aleady associated to another customer.");
                return false;
            }

            if (name.getText().trim().equals("")){
                HelperClass.alertInvalidInput("The name you entered isn't valid!");
                return false;            
            }
            
            if (genderDropdown.getValue() == null){
                HelperClass.alertInvalidInput("You haven't entered a gender yet!");
                return false;                  
            }
            return  true; 
        }catch(Exception e){
            HelperClass.alertError(e);
            return false;
        }
    }
    
    
}
