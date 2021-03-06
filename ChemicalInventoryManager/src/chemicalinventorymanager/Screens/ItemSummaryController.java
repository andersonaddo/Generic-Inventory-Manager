/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import chemicalinventorymanager.InventoryItem;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class ItemSummaryController implements Initializable {

    InventoryItem item;
    private String id;
    
    @FXML
    Label itemIDLabel;
    
    @FXML
    TextField ItemNameTextField;
    
    @FXML
    TextField itemPriceTextField;
    
    @FXML
    Label itemSupplierID;
    
    @FXML
    TextField itemStockNumberTextField;
    
    @FXML
    TextArea itemDesc;
    
    @FXML
    ImageView itemImage;
    
    @FXML
    private Button deletebtn;
    
    @FXML
    private Button updatebtn;
    
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
    
    private void setValues(String id) {
        try {
            item = DatabaseManager.getItemWithId(id);
        } catch (SQLException ex) {
            Logger.getLogger(ItemSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemIDLabel.setText(item.getID());
        ItemNameTextField.setText(item.name);
        itemPriceTextField.setText(String.valueOf(item.price));
        itemSupplierID.setText(item.supplierId);
        itemStockNumberTextField.setText(String.valueOf(item.amountAvailable));
        itemDesc.setText(item.description);
    }
    
    @FXML
    private void delete() throws SQLException {
        if (!HelperClass.confirmUser("Are you sure you want to delete this item?")) return;
        DatabaseManager.deleteItem(id);
        Stage stage = (Stage) deletebtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void update() throws SQLException {
        if (!HelperClass.confirmUser("Are you sure you want to update this item?")) return;
        DatabaseManager.updateItem(item.getID(), ItemNameTextField.getText(), itemPriceTextField.getText(), itemDesc.getText());
        Stage stage = (Stage) updatebtn.getScene().getWindow();
        stage.close();
    }
    
}
