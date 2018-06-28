package chemicalinventorymanager;

import java.util.Date;
import java.util.Map;

/**
 *
 */
public class Transaction {
    private String customerID, id;
    public Date date;
    public transactionMode mode;
    public Map<String, Integer> transactions;
    public Double creditAmount;
    
    //TODO: Add total money gotten
    
    public String getID() {return id;}
    public String getCustomerID() {return customerID;}
    
    public Transaction(String id, String cid){
        this.id = id;
        customerID = cid;
    }
    
    
    public enum transactionMode{
        debit,
        credit
    }
    
}
