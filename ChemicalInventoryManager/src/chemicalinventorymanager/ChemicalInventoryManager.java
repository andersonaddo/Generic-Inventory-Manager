/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ADDO_a
 */
public class ChemicalInventoryManager extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getResource("Screens/TransactionView.fxml"));
=======
        Parent root = FXMLLoader.load(getClass().getResource("Screens/AddTransaction.fxml"));
>>>>>>> master
        
        Scene scene = new Scene(root);        
        primaryStage.setScene(scene);
        primaryStage.show();        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
    
}
