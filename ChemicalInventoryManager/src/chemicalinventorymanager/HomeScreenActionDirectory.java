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
public enum HomeScreenActionDirectory {
    ADDCUSTOMER("Screens/AddCustomer.fxml"),
    ADDITEM("Screens/AddItem.fxml"),
    ADDSUPPLIER("Screens/AddSupplier.fxml"),
    ADDTRANSACTION("Screens/AddTransaction.fxml"),
    CUSTOMERSUMMARY("Screens/CustomerSummary.fxml"),
    ITEMSUMMARY("Screens/ItemSummary.fxml"),
    RESTOCK("Screens/RestockScreen.fxml"),
    SEARCH("Screens/SearchScreen.fxml"),
    SUPPLIERSUMMARY("Screens/SupplierSummary.fxml"),
    TRANSACTIONVIEW("Screens/TransactionView.fxml"),
    MANAGEDATABASE("Screens/ManageDatabase.fxml");

    private String actionURL;
    
    HomeScreenActionDirectory(String action) {
        this.actionURL = action;
    }
    
    private String value() { return this.actionURL; }
    
    public static String getActionURL(HomeScreenActions action) { 
        HomeScreenActionDirectory url = ADDCUSTOMER;
        switch (action) {
            case ADDCUSTOMER: url = ADDCUSTOMER; break;
            case ADDITEM: url = ADDITEM; break;
            case ADDSUPPLIER: url = ADDSUPPLIER; break;
            case ADDTRANSACTION: url = ADDTRANSACTION; break;
            case CUSTOMERSUMMARY: url = CUSTOMERSUMMARY; break;
            case ITEMSUMMARY: url = ITEMSUMMARY; break;
            case RESTOCK: url = RESTOCK; break;
            case SEARCH: url = SEARCH; break;
            case SUPPLIERSUMMARY: url = SUPPLIERSUMMARY; break;
            case TRANSACTIONVIEW: url = TRANSACTIONVIEW; break;
            case MANAGEDATABASE: url = MANAGEDATABASE; break;
        }
        return url.value();
    }
}
