package chemicalinventorymanager;

import java.util.Map;

/**
 *
 * @author ADDO_a
 */
public class Customer {
    public String imageName, fullName;
    public Gender gender;
    public Double totalDebt;
    Map<String, Double> debts;

    public Map<String, Double> getDebts() {return debts;}
    private String id;
    
    public Customer(String id){
        this.id = id;
    }
    
    public String getGender(){
        if (gender == Gender.male) return "Male";
        return "Female";
    }
    
    public String getID() {return id;}
    public String getfullName(){return fullName;}
    
    
    public enum Gender{
        male,
        female
    }  

    
    @Override
    public String toString(){
        return "Name: " + fullName;
    }
    
    @Override
    public boolean equals(Object o) {
       
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof Customer)) {
            return false;
        }
         
            Customer c = (Customer) o;
         
        return id.equals(c.id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}

