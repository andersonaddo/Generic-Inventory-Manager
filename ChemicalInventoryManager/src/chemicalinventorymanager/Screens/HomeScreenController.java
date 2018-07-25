/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager.Screens;

import chemicalinventorymanager.HomeScreenActionListCell;
import chemicalinventorymanager.HomeScreenActions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author LAMPTEY_d
 */
public class HomeScreenController implements Initializable {

    @FXML
    private ListView<HomeScreenActions> actionslistview;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<HomeScreenActions> list = FXCollections.observableArrayList(HomeScreenActions.values());
        actionslistview.setItems(list);
        actionslistview.setCellFactory(new CallBackImpl());
    }    

    private static class CallBackImpl implements Callback<ListView<HomeScreenActions>, ListCell<HomeScreenActions>> {

        @Override
        public ListCell<HomeScreenActions> call(ListView<HomeScreenActions> param) {
            return new HomeScreenActionListCell();
        }

        
    }
    
}
