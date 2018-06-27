package chemicalinventorymanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Transaction {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private String customerID, id;
    Date date;
    transactionMode mode;
    Map<String, Integer> transactions;
    Double creditAmount;
    
    public String getID() {return id;}
    public String getCustomerID() {return customerID;}
    public String getDate() {return df.format(date);}
    
    public String getMode() {
        if (mode == transactionMode.debit) return "debit";
        return "credit";
    }
    
    public Map<String, Integer> getTransactions() {return transactions;    }
    public Double getCreditAmount() {return creditAmount;}
    
    public Transaction(String id, String cid){
        this.id = id;
        customerID = cid;
    }
    
    
    enum transactionMode{
        debit,
        credit
    }
    
}
