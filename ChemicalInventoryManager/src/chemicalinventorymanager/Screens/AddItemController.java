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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


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
    private TextField newItemId;

    @FXML
    private ChoiceBox<?> supplierDropdown;

    @FXML
    private TextArea newItemDescription;

    @FXML
    void addItem(ActionEvent event) throws SQLException {
        if (!inputsAreValid()) return;
        String id = (!newItemId.getText().equals("")) ? newItemId.getText() : "545454";
        InventoryItem item = new InventoryItem(id);
        item.supplierId = "";
        item.name = newItemName.getText();
        item.description = newItemDescription.getText();
        item.price = (!newItemPrice.getText().equals(""))? Float.parseFloat(newItemPrice.getText()) : 0;
        DatabaseManager.addItem(item);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelperClass.makeNumericOnly(newItemPrice);
        HelperClass.makeNumericOnly(amountInStock);
    }   
    
    boolean inputsAreValid(){
        return true; //TODO: Complete this method
    }
    
}
