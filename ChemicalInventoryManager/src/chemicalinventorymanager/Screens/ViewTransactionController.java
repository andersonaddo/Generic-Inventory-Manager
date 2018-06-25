/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.Transaction;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author user pc
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
    private TableView<Transaction> ItemsTable;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
