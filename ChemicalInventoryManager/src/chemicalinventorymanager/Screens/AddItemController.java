package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import chemicalinventorymanager.InventoryItem;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;


/**
 *
 * @author ADDO_a
 */
public class AddItemController implements Initializable {

    @FXML
    private TextField newItemName;

    @FXML
    private TextField newItemPrice;

    @FXML
    private TextField amountInStock;

    @FXML
    private Label amountInStockText;

    @FXML
    private ChoiceBox<?> supplierDropdown;

    @FXML
    private TextArea newItemDescription;

    @FXML
    private TextField newItemId;

    @FXML
    private Label itemIdText;

    @FXML
    void addItem(ActionEvent event) throws SQLException {
        if (!inputsAreValid()) return;
        String id = (!newItemId.getText().equals("")) ? newItemId.getText() : DatabaseManager.generateUniqueId(DatabaseManager.tableTypes.item);
        InventoryItem item = new InventoryItem(id);
        item.supplierId = ""; //TODO: Gotta fix this too
        item.name = newItemName.getText();
        item.description = newItemDescription.getText();
        item.price = (!newItemPrice.getText().equals(""))? Float.parseFloat(newItemPrice.getText()) : 0;
        item.amountAvailable = Integer.parseInt(amountInStock.getText());
        DatabaseManager.addItem(item);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelperClass.makeNumericOnly(newItemPrice);
        HelperClass.makePositiveIntegerOnly(amountInStock);
        
        //TODO: Edit the delay for these tooltips
        amountInStockText.setTooltip(new Tooltip("How much of this item do you aleady have? Default is 0."));
        itemIdText.setTooltip(new Tooltip("The ID of this item of for the database. If not entered, it will be automatically generated"));
    }   
    
    boolean inputsAreValid(){
        return true; //TODO: Complete this method
    }
    
}
