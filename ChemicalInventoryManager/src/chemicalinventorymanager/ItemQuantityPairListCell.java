package chemicalinventorymanager;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListCell;

/**
 * A custom list cell for the AddTransaction screen
 * @author ADDO_a
 */
public class ItemQuantityPairListCell extends ListCell<ItemQuantityPair>{
    
    @Override
    public void updateItem(ItemQuantityPair pair, boolean empty)
    {
        super.updateItem(pair, empty);
        if(pair != null && !empty)
        {
            TransDataHolder data = new TransDataHolder();
            try {
                data.setInfo(pair);
            } catch (SQLException ex) {
                Logger.getLogger(ItemQuantityPairListCell.class.getName()).log(Level.SEVERE, null, ex);
            }
            setGraphic(data.getBox());
        }else{
            setGraphic(null);
        }
        
    }
    
}
