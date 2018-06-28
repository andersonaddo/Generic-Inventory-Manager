package chemicalinventorymanager;

import java.util.ArrayList;

/**
 *
 */
public class Supplier {
    public String name, email, phone;
    private String id;
    ArrayList <String> itemsSupplied;
    
    public String getID() {return id;}

    public ArrayList<String> getItemsSupplied() {return itemsSupplied;}
    
    
    public Supplier(String id){
        this.id = id;
    }
}
