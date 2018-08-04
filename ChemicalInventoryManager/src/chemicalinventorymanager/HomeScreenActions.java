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
    RESTOCK("Restock"),
    SEARCH("Search");

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
            case "Restock": a = RESTOCK; break;
            case "Search": a = SEARCH; break;
            default: break;
        }
        return a;
    }
}
