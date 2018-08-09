/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Customer;
import chemicalinventorymanager.DatabaseManager;
import chemicalinventorymanager.ManageDatabaseActionDirectory;
import chemicalinventorymanager.ManageDatabaseActions;
import java.io.IOException;
import chemicalinventorymanager.InventoryItem;
import chemicalinventorymanager.Supplier;
import chemicalinventorymanager.Transaction;
import java.util.List;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADDO_a
 */
public class SearchScreenController implements Initializable {
    
    @FXML
    private TextField SearchTerm;

    @FXML
    private ComboBox Filter;
    
    @FXML
    private ListView ResultsView;
    
    @FXML
    private void search() throws SQLException {
        String search = SearchTerm.getText();
        String filter = Filter.getValue().toString();
        
        if(!search.isEmpty()){
            List<String> SearchResults;
            if("All".equals(filter)){
                SearchResults = DatabaseManager.searchEntireDatabase(search);
            }else{
                SearchResults = DatabaseManager.searchWithFilter(search, filter);
            }
            ResultsView.getItems().clear();
            for(Object Result:SearchResults){
                ResultsView.getItems().add(Result);
            }
        }
        
    }
    
    @FXML
    private void manageRecord() throws IOException {
        String action = ResultsView.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        InventoryItem item;
        Customer customer;
        Supplier supplier;
        Transaction trans;
        
        FXMLLoader loader = new FXMLLoader();
        
        try {
            item = (InventoryItem) ResultsView.getSelectionModel().getSelectedItem();
            System.out.println(item.getID());
            open("ItemSummary.fxml", loader);
            ItemSummaryController controller = loader.getController();
            controller.setID(item.getID());
        } catch (Exception e) {
            
        }
        
        try {
            customer = (Customer) ResultsView.getSelectionModel().getSelectedItem();
            System.out.println(customer.getID());
            open("CustomerSummary.fxml", loader);
            CustomerSummaryController controller = loader.getController();
            controller.setID(customer.getID());
        } catch (Exception e) {
            
        }
        try {
            supplier = (Supplier) ResultsView.getSelectionModel().getSelectedItem();
            System.out.println(supplier.getID());
            open("SupplierSummary.fxml", loader);
            SupplierSummaryController controller = loader.getController();
            controller.setID(supplier.getID());
        } catch (Exception e) {
            
        }
        try {
            trans = (Transaction) ResultsView.getSelectionModel().getSelectedItem();
            System.out.println(trans.getID());
            open("TransactionView.fxml", loader);
            SupplierSummaryController controller = loader.getController();
            controller.setID(trans.getID());
        } catch (Exception e) {
            
        }
        System.out.println(action);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        Stage current = (Stage) SearchTerm.getScene().getWindow();
        stage.setTitle(action);
        stage.setScene(new Scene(p, stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        current.close();
        
    }
    
    private void open(String controller, FXMLLoader loader) {
        loader.setLocation(getClass().getResource(controller));
        try {
            loader.load();
        } catch (IOException e) {
                
            }
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Filter.getItems().clear();
        Filter.getItems().addAll("All", "Inventory Items", "Customers", "Suppliers", "Transactions");
        Filter.getSelectionModel().select(0);
        try {
            List<String> SearchResults;
            String search ="_";
            SearchResults = DatabaseManager.searchEntireDatabase(search);
            ResultsView.getItems().clear();
            for(Object Result:SearchResults){
                ResultsView.getItems().add(Result);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        SearchTerm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                try {
                    search();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        });
        
        Filter.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                try {
                    search();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        });
    }    
    
}