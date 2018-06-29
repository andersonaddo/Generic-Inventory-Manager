package chemicalinventorymanager;

import chemicalinventorymanager.Screens.AddTransactionController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This class is present to return the graphic that represents a ItemQuantityPair in listviews
 * @author ADDO_a
 */
public class TransDataHolder {
    
    @FXML
    private HBox hbox;

    @FXML
    private ChoiceBox<String> idDrpDown;

    @FXML
    private TextField quantity;

    @FXML
    private Label totalPrice;

    @FXML
    private ImageView deleteButton;
    
    ItemQuantityPair pair; //Keeping a reference to the object so we can edit it from here
    
    @FXML
    void deletePair(MouseEvent event) {
        AddTransactionController.Instance.observableList.remove(pair);
        AddTransactionController.Instance.updateTotalCost();
    }
    
    public TransDataHolder()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Screens/NewTransactionListCell.fxml"));      
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(ItemQuantityPair pair) throws SQLException
    {
        this.pair = pair;
        HelperClass.makePositiveIntegerOnly(quantity);
        idDrpDown.setItems(FXCollections.observableArrayList(AddTransactionController.Instance.idList));
        
        if (pair.itemID != null || !"".equals(pair.itemID)) idDrpDown.setValue(pair.itemID);
        if (pair.quantity != 0) quantity.setText(pair.quantity + "");
        updatePrice();
        
        
        //Adding listener tp dropdown menu
        idDrpDown.getSelectionModel()
        .selectedItemProperty()
        .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            try {
                updatePrice();
            } catch (Exception ex) {
                Logger.getLogger(TransDataHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Also adding a listener to the quantity text field
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updatePrice();
            } catch (SQLException ex) {
                Logger.getLogger(TransDataHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        }

    void updatePrice() throws SQLException{
        String dropDownValue = idDrpDown.getValue();
        if (dropDownValue == null){
            return;
        }
        totalPrice.setText((DatabaseManager.getItemWithId(dropDownValue).price * getQuantity())+ "");
        pair.totalPrice = Double.parseDouble(totalPrice.getText());
        pair.itemID = dropDownValue;
        pair.quantity = getQuantity();
        
        AddTransactionController.Instance.updateTotalCost();
    }
    
    
    int getQuantity(){
        if (quantity.getText() == null || quantity.getText().isEmpty()) return 0;
        return Integer.parseInt(quantity.getText());
    }
    
    public HBox getBox()
    {
        return hbox;
    }
}
