/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemicalinventorymanager;

/**
 *
 * @author LAMPTEY_d
 */
public enum Actions {
    ADDCUSTOMER("Add Customer"),
    ADDITEM("Add Item"),
    ADDSUPPLIER("Add Supplier"),
    ADDTRANSACTION("Add Transaction"),
    CUSTOMERSUMMARY("Customer Summary"),
    ITEMSUMMARY("Item Summary"),
    RESTOCK("Restock"),
    SEARCH("Search"),
    SUPPLIERSUMMARY("Supplier Summary"),
    TRANSACTIONVIEW("Transaction View");

    private String action;
    
    Actions(String action) {
        this.action = action;
    }
    
    public String getAction() { return this.action; }
    
    public static Actions identify(String action) {
        Actions a = ADDCUSTOMER;
        switch (action) {
            case "Add Customer": break;
            case "Add Item": a = ADDITEM; break;
            case "Add Supplier": a = ADDSUPPLIER; break;
            case "Add Transactions": a = ADDTRANSACTION; break;
            case "Customer Summary": a = CUSTOMERSUMMARY; break;
            case "Item Summary": a = ITEMSUMMARY; break;
            case "Restock": a = RESTOCK; break;
            case "Search": a = SEARCH; break;
            case "Supplier Summary": a = SUPPLIERSUMMARY; break;
            case "TransactionView": a = TRANSACTIONVIEW; break;
            default: break;
        }
        return a;
    }
}
