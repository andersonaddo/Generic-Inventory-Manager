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
    private String id;
    
    public Customer(String id){
        this.id = id;
    }
    
    public String getGender(){
        if (gender == Gender.male) return "Male";
        return "Female";
    }
    
    public String getID() {return id;}
    
    public enum Gender{
        male,
        female
    }  
    
}