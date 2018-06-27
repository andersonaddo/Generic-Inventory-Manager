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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
    Label itemNameLabel;
    
    @FXML
    Label itemPriceLabel;
    
    @FXML
    Label itemSupplierId;
    
    @FXML
    Label itemStockNumber;
    
    @FXML
    TextArea itemDesc;
    
    @FXML
    ImageView itemImage;
    
    
    public ItemSummaryController(String id) throws SQLException{
        item = DatabaseManager.getItemWithId(id);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemIDLabel.setText(item.getID());
        itemNameLabel.setText(item.name);
        itemPriceLabel.setText(String.valueOf(item.price));
        itemSupplierId.setText(item.supplierId);
        itemStockNumber.setText(String.valueOf(item.amountAvailable));
        itemDesc.setText(item.description);
    }    
    
}
