/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.DatabaseManager;
//import chemicalinventorymanager.InventoryItem;
import java.util.List;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

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
    void search() throws SQLException {
       
        String search = SearchTerm.getText();
        String filter = Filter.getValue().toString();
        if(!search.isEmpty()){
            List<String> SearchResults;
            if(filter.equals("All")){
                SearchResults = DatabaseManager.searchEntireDatabase(search);
            }else{
                SearchResults = DatabaseManager.searchWtihFilter(search, filter);
            }
            
            ResultsView.getItems().clear();
            for(Object Result:SearchResults){
                ResultsView.getItems().add(Result);
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Filter.getItems().clear();
        Filter.getItems().addAll("All", "Inventory Items", "Customers", "Suppliers", "Transactions");
        Filter.getSelectionModel().select(0);
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