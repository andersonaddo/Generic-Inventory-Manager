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
    RESTOCK("Screens/RestockScreen.fxml"),
    SEARCH("Screens/SearchScreen.fxml");
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
            case RESTOCK: url = RESTOCK; break;
            case SEARCH: url = SEARCH; break;
        }
        return url.value();
    }
}
