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

    public void setInfo(ItemQuantityPair pair)
    {
        this.pair = pair;
        HelperClass.makePositiveIntegerOnly(quantity);
        idDrpDown.setItems(FXCollections.observableArrayList(AddTransactionController.idList));
        
        //Adding listener tp dropdown menu
        idDrpDown.getSelectionModel()
        .selectedItemProperty()
        .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            try {
                totalPrice.setText((DatabaseManager.getItemWithId(newValue).price * (Integer)quantity.getTextFormatter().getValue()) + "");
                pair.itemID = newValue;
            } catch (Exception ex) {
                Logger.getLogger(TransDataHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    }

    public HBox getBox()
    {
        return hbox;
    }
}
