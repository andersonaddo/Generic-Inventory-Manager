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
public enum ManageDatabaseActionDirectory {
    MANAGECUSTOMERS("CustomerSummary.fxml"),
    MANAGEINVENTORY("ItemSummary.fxml"),
    MANAGESUPPLIERS("SupplierSummary.fxml"),
    TRANSACTIONVIEW("TransactionView.fxml");
    
    private String actionURL;
    
    ManageDatabaseActionDirectory(String action) {
        this.actionURL = action;
    }
    
    private String value() { return this.actionURL; }
    
    public static String getActionURL(ManageDatabaseActions action) { 
        ManageDatabaseActionDirectory url = MANAGECUSTOMERS;
        switch (action) {
            case MANAGECUSTOMERS: break;
            case MANAGEINVENTORY: url = MANAGEINVENTORY; break;
            case MANAGESUPPLIERS: url = MANAGESUPPLIERS; break;
            case TRANSACTIONVIEW: url = TRANSACTIONVIEW; break;
            
        }
        return url.value();
    }
}
