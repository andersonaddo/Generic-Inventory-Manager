/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Transaction;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 * When called, the screen will display details about
 * a specific transaction
 *
 * @author Ekow Bentsi-Enchill
 */
public class ViewTransactionController implements Initializable {
    
    @FXML
    private Label TransactionDateLabel;
    
    @FXML
    private Label CustomerIdLabel;
    
    @FXML
    private Label CustomerNameLabel;
    
    @FXML
    private Label TransactionModeLabel;
    
    @FXML
    private TableView<Map> ItemsTable;
    
    @FXML
    private TableColumn<Map, String> ItemSoldColumn;
    
    @FXML
    private TableColumn<Map, Integer> QuantitySoldColumn;
    
    public static void showTransaction(){/*/name/id?*/}
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
