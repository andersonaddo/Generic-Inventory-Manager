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
public enum ManageDatabaseActions {
    MANAGECUSTOMERS("Manage Customers"),
    MANAGEINVENTORY("Manage Inventory"),
    MANAGESUPPLIERS("Manage Suppliers");
    
    private String action;
    
    ManageDatabaseActions(String action) {
        this.action = action;
    }
    
    public String getAction() { return this.action; }
    
    public static ManageDatabaseActions identify(String action) {
        ManageDatabaseActions a = MANAGECUSTOMERS;
        switch (action) {
            case "Name:": break;
            case "Item": a = MANAGEINVENTORY; break;
            case "Supplier:": a = MANAGESUPPLIERS; break;
            default: break;
        }
        return a;
    }
}
