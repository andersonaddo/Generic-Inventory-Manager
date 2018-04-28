package chemicalinventorymanager;


/**
 *
 * @author ADDO_a
 */
public class InventoryItem {
    public String name, description, imageName, supplierId;
    public boolean stillSold = true;
    public float price;
    public int amountAvailable;
    String id;
    
    public InventoryItem(String id){
        this.id = id;
    }
    
    public String getID() {return id;}
}
