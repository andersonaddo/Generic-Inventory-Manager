package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Customer;
import chemicalinventorymanager.InventoryItem;
import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.HelperClass;
import chemicalinventorymanager.ItemQuantityPair;
import chemicalinventorymanager.ItemQuantityPairListCell;
import chemicalinventorymanager.Transaction;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


public class AddTransactionController implements Initializable {

    public static AddTransactionController Instance;
    public ObservableList observableList = FXCollections.observableArrayList();
    public ArrayList<String> idList = new ArrayList<String>();
    Map<String, Integer> transactions;
    
    @FXML
    private ChoiceBox<String> customerDrawer;

    @FXML
    private DatePicker date;

    @FXML
    private ListView<ItemQuantityPair> transactionListView;
    
    @FXML
    Label totalPrice;
    
    @FXML
    private TextField credit;

    @FXML
    void addItemPair(MouseEvent event) {
        observableList.add(new ItemQuantityPair());
        updateTotalCost();
    }

    @FXML
    void recordTransaction (ActionEvent event) { //TODO: This dosn't udate the customer in the db if there's debt
        try{
            if (!inputsAreValid()) return;
            if (!HelperClass.confirmUser("Are you sure you want to record this transaction?")) return;
            Transaction tran = new Transaction(DatabaseManager.generateUniqueId(DatabaseManager.tableTypes.transaction), customerDrawer.getValue());
            tran.transactions = transactions;
            tran.date = Date.valueOf(date.getValue()); //TODO: Look into this more
            
            if (credit.getText().isEmpty() || Double.parseDouble(credit.getText()) == 0){
                tran.mode = Transaction.transactionMode.debit;
            }else{
                tran.mode = Transaction.transactionMode.credit;
                tran.creditAmount = Double.parseDouble(credit.getText());
            }
            
            DatabaseManager.addTransaction(tran);
            
            
        }catch(Exception e){
            HelperClass.alertError(e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        Instance = this;
        HelperClass.makeNumericOnly(credit);
        try {
            for(InventoryItem item: DatabaseManager.getItemsWithName("")){
                idList.add(item.getID());
            }
            for(Customer cus: DatabaseManager.getCustomersWithName("")){
                customerDrawer.getItems().add(cus.getID());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddTransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        observableList.add(new ItemQuantityPair());
        transactionListView.setItems(observableList);
        
        //This configures the listview to parse ItemQuantityPairs and display NewTransactionLostCell.fxml as cells
        transactionListView.setCellFactory(new CallbackImpl());
    }    

    private static class CallbackImpl implements Callback<ListView<ItemQuantityPair>, ListCell<ItemQuantityPair>> {

        @Override
        public ListCell<ItemQuantityPair> call(ListView<ItemQuantityPair> listView)
        {
            return new ItemQuantityPairListCell();
        }
    }
    
    public void updateTotalCost(){
        double total = 0;
        if (observableList.isEmpty()){
            totalPrice.setText(total + "");
        }else{
            for (Object pair: observableList) total += ((ItemQuantityPair)pair).totalPrice;
            totalPrice.setText(total + "");
        }       
    }
    
    boolean inputsAreValid() throws SQLException{
        try{
            if (customerDrawer.getValue() == null){
                HelperClass.alertInvalidInput("There is no customer associated with this record!!");
                return false;
            }
            
            if (observableList.isEmpty()){
                HelperClass.alertInvalidInput("There are no items being bought!");
                return false; 
            }
            
            if (date.getValue() == null){
                HelperClass.alertInvalidInput("You haven't enetered a valid date!");
                return false; 
            }
            
            transactions = new HashMap<>();
            for (Object object : observableList) {
                ItemQuantityPair pair = (ItemQuantityPair) object;
                
                if ("".equals(pair.itemID) || pair.quantity == 0){
                    HelperClass.alertInvalidInput("One of our items has no quantity or hasn't been specified!");
                    return false; 
                }
                
                if (transactions.containsKey(pair.itemID)){
                    HelperClass.alertInvalidInput("You've etered an item more than once");
                    return false; 
                }
                transactions.put(pair.itemID, pair.quantity);
            }
            
            if (Double.parseDouble(credit.getText()) > Double.parseDouble(totalPrice.getText())){
                HelperClass.alertInvalidInput("Your credit is more than your total price");
                return false;   
            }
            
            return true; 
            
        }catch(Exception e){
            HelperClass.alertError(e);
            return false;
        }
    }
    
}
