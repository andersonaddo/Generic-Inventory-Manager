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
public enum HomeScreenActions {
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
    
    HomeScreenActions(String action) {
        this.action = action;
    }
    
    public String getAction() { return this.action; }
    
    public static HomeScreenActions identify(String action) {
        HomeScreenActions a = ADDCUSTOMER;
        switch (action) {
            case "Add Customer": break;
            case "Add Item": a = ADDITEM; break;
            case "Add Supplier": a = ADDSUPPLIER; break;
            case "Add Transaction": a = ADDTRANSACTION; break;
            case "Customer Summary": a = CUSTOMERSUMMARY; break;
            case "Item Summary": a = ITEMSUMMARY; break;
            case "Restock": a = RESTOCK; break;
            case "Search": a = SEARCH; break;
            case "Supplier Summary": a = SUPPLIERSUMMARY; break;
            case "Transaction View": a = TRANSACTIONVIEW; break;
            default: break;
        }
        return a;
    }
}
