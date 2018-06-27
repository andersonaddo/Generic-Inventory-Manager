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
    
    @Override
    public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof Supplier)) {
            return false;
        }
         
        Supplier c = (Supplier) o;
         
        return id.equals(c.id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
