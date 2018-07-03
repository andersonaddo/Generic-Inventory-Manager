/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.InventoryItem;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author BENTSI-ENCHILL_e
 */
public class ItemSummaryController implements Initializable {

    InventoryItem item;
    
    @FXML
    Label itemIDLabel;
    
    @FXML
    Label ItemNameLabel;
    
    @FXML
    Label itemPriceLabel;
    
    @FXML
    Label itemSupplierID;
    
    @FXML
    Label itemStockNumber;
    
    @FXML
    TextArea itemDesc;
    
    @FXML
    ImageView itemImage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            item = DatabaseManager.getItemWithId("545454");
        } catch (SQLException ex) {
            Logger.getLogger(ItemSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemIDLabel.setText(item.getID());
        ItemNameLabel.setText(item.name);
        itemPriceLabel.setText(String.valueOf(item.price));
        itemSupplierID.setText(item.supplierId);
        itemStockNumber.setText(String.valueOf(item.amountAvailable));
        itemDesc.setText(item.description);
    }    
    
}
