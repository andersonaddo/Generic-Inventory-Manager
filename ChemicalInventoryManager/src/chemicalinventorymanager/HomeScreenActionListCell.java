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
public class HomeScreenActionListCell extends ListCell<HomeScreenActions> {
    
    @Override
    public void updateItem(HomeScreenActions action, boolean empty) {
        super.updateItem(action, empty);
        if (action != null && !empty) {
            HomeScreenActionHandler handler = new HomeScreenActionHandler(action.getAction());
            setGraphic(handler.getBox());
        } else {
            setGraphic(null);
        }
    }
    
}
