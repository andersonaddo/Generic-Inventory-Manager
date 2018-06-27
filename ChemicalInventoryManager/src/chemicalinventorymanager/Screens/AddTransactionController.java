/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

//import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ADDO_a
 */
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
public class AddTransactionController implements Initializable {

    
    @FXML
    private ChoiceBox<?> customerDrawer;

    @FXML
    private DatePicker date;

    @FXML
    private ListView<?> transactionListView;

    @FXML
    void addItemPair(MouseEvent event) {

    }

    @FXML
    void addSupplier(ActionEvent event) {

    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
