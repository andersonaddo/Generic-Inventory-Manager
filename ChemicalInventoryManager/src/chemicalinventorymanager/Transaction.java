package chemicalinventorymanager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Transaction {
    private String customerID, id;
    Date date;
    transactionMode mode;
    Map<String, Integer> transactions;
    Double creditAmount;
    
    public String getID() {return id;}
    public String getCustomerID() {return customerID;}
    
    public Transaction(String id, String cid){
        this.id = id;
        customerID = cid;
    }
    
    
    enum transactionMode{
        debit,
        credit
    }
    
    @Override
    public String toString(){
        return "Transaction Date: "+ date + "\tCustomer ID: " + customerID;
    }
    
}
