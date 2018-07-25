/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author LAMPTEY_d
 */
public class ActionHandler {
    
    private String action;
    
    @FXML
    private HBox box;
    
    @FXML
    private Button btn;
    
    public ActionHandler(String action) {
        this.action = action;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Screens/ActionListCell.fxml"));      
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        btn.setText(action);
    }
    
    @FXML
    private void open() {
        try {
            Parent root = new FXMLLoader().load(getClass().getResource(ActionDirectory.getActionURL(Actions.identify(action))));
            Stage stage = new Stage();
            stage.setTitle(action);
            stage.setScene(new Scene(root, 750, 500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HBox getBox() { return this.box; }
    
}
