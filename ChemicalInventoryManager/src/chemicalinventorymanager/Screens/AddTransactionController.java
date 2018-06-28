package chemicalinventorymanager.Screens;

import chemicalinventorymanager.InventoryItem;
import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.ItemQuantityPair;
import chemicalinventorymanager.ItemQuantityPairListCell;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


public class AddTransactionController implements Initializable {

    ObservableList observableList = FXCollections.observableArrayList();
    
    @FXML
    private ChoiceBox<String> customerDrawer;

    @FXML
    private DatePicker date;

    @FXML
    private ListView<ItemQuantityPair> transactionListView;
    
    public static ArrayList<String> idList = new ArrayList<String>();

    @FXML
    void addItemPair(MouseEvent event) {

    }

    @FXML
    void addSupplier(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            for(InventoryItem item: DatabaseManager.getItemsWithName("")){
                idList.add(item.getID());
            }               
        } catch (SQLException ex) {
            Logger.getLogger(AddTransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        observableList.add(new ItemQuantityPair());
        observableList.add(new ItemQuantityPair());
        observableList.add(new ItemQuantityPair());
        observableList.add(new ItemQuantityPair());
        observableList.add(new ItemQuantityPair());
        transactionListView.setItems(observableList);
        
        //This configures the listview to parse ItemQuantityPairs and display NewTransactionLostCell.fxml as cells
        //I don't fully understand it honestly ._.
        transactionListView.setCellFactory(new CallbackImpl());
    }    

    private static class CallbackImpl implements Callback<ListView<ItemQuantityPair>, ListCell<ItemQuantityPair>> {

        @Override
        public ListCell<ItemQuantityPair> call(ListView<ItemQuantityPair> listView)
        {
            return new ItemQuantityPairListCell();
        }
    }
    
}
