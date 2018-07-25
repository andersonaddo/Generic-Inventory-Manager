/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager;

import javafx.scene.control.ListCell;

/**
 *
 * @author LAMPTEY_d
 */
public class ManageDatabaseActionListCell extends ListCell<ManageDatabaseActions>{
    
    @Override
    public void updateItem(ManageDatabaseActions action, boolean empty) {
        super.updateItem(action, empty);
        if (action != null && !empty) {
            ManageDatabaseActionHandler handler = new ManageDatabaseActionHandler(action.getAction());
            setGraphic(handler.getBox());
        } else {
            setGraphic(null);
        }
    }
}
