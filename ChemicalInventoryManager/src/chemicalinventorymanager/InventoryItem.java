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
    
    @Override
    public String toString(){
        return "Item Name: " + name + "\tPrice: " + price;
    }
    
    @Override
    public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof InventoryItem)) {
            return false;
        }
         
            InventoryItem c = (InventoryItem) o;
         
        return id.equals(c.id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
