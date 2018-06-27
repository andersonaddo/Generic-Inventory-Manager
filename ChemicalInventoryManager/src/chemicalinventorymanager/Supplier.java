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
    
    public Supplier(String id){
        this.id = id;
    }
    
    @Override
    public String toString(){
        return "Supplier: " + name;
    }
}
