package chemicalinventorymanager;

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
        
        if(pair != null)
        {
            TransDataHolder data = new TransDataHolder();
            data.setInfo(pair);
            setGraphic(data.getBox());
        }
        
    }
    
}
