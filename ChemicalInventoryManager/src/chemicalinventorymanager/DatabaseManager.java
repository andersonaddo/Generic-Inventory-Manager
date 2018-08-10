package chemicalinventorymanager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;  
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a static helper class that manages all the logic for the SQLite backend of this application
 */
public final class DatabaseManager {
    private static Connection databaseConnection;

    private static int DEFAULT_ID_LENGTH = 7;
    private static String DATABASE_PATH = "src\\chemicalinventorymanager\\Databases\\ShopDatabase.db";
    //private static String DATABASE_NAME = "ShopDatabase.db";
    
    public static enum tableTypes{
        customer,
        item,
        transaction,
        supplier
    }
  
    private DatabaseManager(){} //This class is static; it shouldn't be able to be instanced
    private static void processError(Exception e){
        System.out.println(e);
        HelperClass.alertError(e);
    }
    
    private static void connect() throws SQLException{
        try {
            if (databaseConnection != null) {
                return;
            }
            String url = "jdbc:sqlite:" + DATABASE_PATH;
            databaseConnection = DriverManager.getConnection(url);
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }    
    }
    
    /**
     * This class is designed for integration with ListViews with search-by-name capabilities
     * @param query
     * @return A list of Customers without their array of debts (for performance reasons)
     * @throws java.sql.SQLException
     */
    public static List<Customer> getCustomersWithName(String Customername) throws SQLException{
        ArrayList<Customer> resultList = new ArrayList <>();
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            String name = Customername.toLowerCase();

            String command = "select `ID`, `full name`, `GENDER`, `TOTAL DEBT` from `Customers` where lower(`FULL NAME`) like '%" + name + "%'";
            
            ResultSet results = statement.executeQuery(command);
            
            while (results.next()) {
                resultList.add(DatabaseManager.convertCustomer(results));
            }
            statement.close();
            return resultList;

        }catch(SQLException sqlexception) {
            System.out.println(sqlexception);
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public static void addCustomer (Customer customer) throws SQLException{      
        try {
            connect();
            Statement statement = databaseConnection.createStatement();

            String command = String.format("INSERT INTO `Customers` (`ID`, `Full Name`, `Gender`, `Total Debt`)"
                    + " VALUES ('%s', '%s', '%s', '%f')", customer.getID(), customer.fullName, customer.getGender(), customer.totalDebt);
            
            statement.executeUpdate(command);
            statement.close(); 
            HelperClass.showSuccess(customer.fullName + " was successfully added to the database!");
        } catch (Exception e) {
            processError(e);
        }    
    }
    /**
     * Should only be called if such an id is guaranteed to exist
     * @param id
     * @return A whole customer object, including the customer's array of debts
     * @throws SQLException 
     */
    public static Customer getCustomerWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            String command = "select * from `Customers` where ID = '" + id + "'";

            
            ResultSet results = statement.executeQuery(command);
            Customer customer = null;
            
            if (results.next()) {
                customer = new Customer(results.getString("ID"));
                customer.fullName = results.getString("FULL NAME");
                Customer.Gender gender = (results.getString("GENDER")).equals("Male") ? Customer.Gender.male : Customer.Gender.female;
                customer.gender = gender;
                customer.totalDebt = results.getDouble("TOTAL DEBT");

                String debtsIds = results.getString("DEBTS IDS");
                String debtsAmounts = results.getString("DEBTS AMOUNTS");
                
                if (debtsIds != null && !debtsIds.isEmpty()){
                    List<String> ids = Arrays.asList(debtsIds.split("\\s*,\\s*"));
                    List<String> amounts = Arrays.asList(debtsAmounts.split("\\s*,\\s*"));
                    LinkedHashMap<String, Double> map = new LinkedHashMap<>();
                    
                    for (int i = 0; i < ids.size(); i++){
                        map.put(ids.get(i), Double.parseDouble(amounts.get(i)));
                    }
                    
                    customer.debts = map;
                }
                
                return customer;
               
            }
            statement.close();
            return customer;

        } catch (Exception e) {
            processError(e);
            return  null;
        } 
    }
    
    
    public static void updateCustomerDebtHistory (String id, LinkedHashMap<String, Double> newMap) throws IOException{
        
        String idStrings = "";
        Set<String> keys = newMap.keySet();
        idStrings = String.join(",", keys);
            
        String amounts = "";
        Collection<Double> mappings = newMap.values();
        amounts = mappings.toString();
        amounts = amounts.substring(1, amounts.length()-1); 
        
        try{
            
            connect();
            String command = "UPDATE `Customers` SET `DEBTS IDS`=?, `DEBTS AMOUNTS`=? WHERE ID=?";
            PreparedStatement statement = databaseConnection.prepareStatement(command);
            statement.setString(1, idStrings); 
            statement.setString(2, amounts); 
            statement.setString(3, id);
            statement.executeUpdate();
            statement.close();
            
        }catch (Exception e){
            processError(e);
        }
        
        
    }
    
     /**
     * This class is designed for integration with ListViews with search-by-name capabilities
     * @param query
     * @return A list of Items
     */
    public static List<InventoryItem> getItemsWithName(String query) throws SQLException{
        ArrayList<InventoryItem> resultList = new ArrayList <>();
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            query = query.toLowerCase();
            String command = "select * from `Inventory Items` where lower(`NAME`) like '%" + query + "%'";

            ResultSet results = statement.executeQuery(command);
            
            while (results.next()) {
                resultList.add(DatabaseManager.convertInventoryItem(results));
            }
            statement.close();
            return resultList;

        } catch (Exception e) {
            processError(e);
            return null;
        }
    }
    public static void addItem (InventoryItem item) throws SQLException{      
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            String command = String.format("INSERT INTO `Inventory Items`"
                    + " VALUES ('%s', '%s','%f', '%s', '%d', '%d', '%s', '%s')", 
                    item.getID(), item.name, item.price ,item.description, (item.stillSold) ? 1 : 0, item.amountAvailable, item.imageName, item.supplierId);
            statement.executeUpdate(command);
            statement.close();    
            HelperClass.showSuccess(item.name + " was successfully added to the database!");
        } catch (Exception e) {
            processError(e);
        }
      
    }
     /**
     * Should only be called if such an id is guaranteed to exist
     * @param id
     * @return
     * @throws SQLException 
     */
    public static InventoryItem getItemWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConnection.createStatement();

            String command = "select * from `Inventory Items` where `ID` = '" + id + "'";
            
            ResultSet results = statement.executeQuery(command);
            InventoryItem item = null;
            
            if (results.next()) {
                item = DatabaseManager.convertInventoryItem(results);
            }
            statement.close();
            return item;
            
        } catch (Exception e) {
            processError(e);
            return null;
        }
    }
    
    
     /**
     * This class is designed for integration with ListViews with search-by-name capabilities
     * @param query
     * @return A list of Suppliers
     */
    public static List<Supplier> getSuppliersWithName(String query) throws SQLException{
        ArrayList<Supplier> resultList = new ArrayList <>();
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            query = query.toLowerCase();
            String command = "select * Suppliers where lower(NAME) like '%" + query + "%'";
            
            ResultSet results = statement.executeQuery(command);
            
            while (results.next()) {
                resultList.add(DatabaseManager.convertSupplier(results));
            }
            statement.close();
            return resultList;

        } catch (Exception e) {
            processError(e);
            return null;
        }
    }
    public static void addSupplier (Supplier supplier) throws SQLException{      
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            String command = String.format("INSERT INTO `Suppliers`"
                    + " VALUES ('%s', '%s', '%s', '%s', '%s')", 
                    supplier.getID(), supplier.name, supplier.email, supplier.phone, String.join(",", supplier.itemsSupplied));
            statement.executeUpdate(command);
            statement.close(); 
            HelperClass.showSuccess(supplier.name + " was successfully added to the database!");
        } catch (Exception e) {
            processError(e);
        }
      
    }
     /**
     * Should only be called if such an id is guaranteed to exist
     * @param id
     * @return
     * @throws SQLException 
     */
    public static Supplier getSupplierWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            

            String command = "select * from `Suppliers` where ID = '" + id + "'";

            ResultSet results = statement.executeQuery(command);
            Supplier supplier = null;
            
            if (results.next()) {
                supplier = DatabaseManager.convertSupplier(results);
            }
            statement.close();
            return supplier;

        } catch (Exception e) {
            processError(e);
            return null;
        } 
    }
    
    
     /**
     * This class is designed for integration with ListViews with search-by-name capabilities
     * @param query
     * @return A list of Suppliers
     */
    public static List<Transaction> getTransactionWithDate(String query) throws SQLException{
        ArrayList<Transaction> resultList = new ArrayList <>();
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            query = query.toLowerCase();
            String command = "select * Transactions where lower(DATE) like '%" + query + "%'";
            ResultSet results = statement.executeQuery(command);  
            
            while (results.next()) {
                resultList.add(DatabaseManager.convertTransaction(results));
            }
            statement.close();
            return resultList;

        }catch (Exception e) {
            processError(e);
            return null;
        }
    }
    public static void addTransaction (Transaction tran) throws SQLException{      
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy"); 
            
            String itemKeys = "";
            Set<String> keys = tran.transactions.keySet();
            itemKeys = String.join(",", keys);
            
            String quantities = "";
            Collection<Integer> mappings = tran.transactions.values();
            quantities = mappings.toString();
            quantities = quantities.substring(1, quantities.length()-1); 
            
            String command = String.format("INSERT INTO `Transactions`"
                    + "VALUES ('%s', '%s', '%s', '%d', '%s', '%s', '%f')", 
                    tran.getID(), tran.getCustomerID(), formatter.format(tran.date), (tran.mode == Transaction.transactionMode.debit) ? 0 : 1, itemKeys, quantities, tran.creditAmount);
            statement.executeUpdate(command);
            statement.close();    
            HelperClass.showSuccess("This transaction was successfully added to the database! \n It's id is " + tran.getID() );
        } catch (Exception e) {
            processError(e);
        }
      
    }
     /**
     * Should only be called if such an id is guaranteed to exist
     * @param id
     * @return Transaction
     * @throws SQLException 
     */
    public static Transaction getTransactionWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConnection.createStatement();
            
            String command = "select * from Transactions where ID = '" + id + "'";

            
            ResultSet results = statement.executeQuery(command);
            Transaction tran = null;
            if (results.next()) {
                tran = DatabaseManager.convertTransaction(results);
            }
            statement.close();
            return tran;
            
        } catch (Exception e) {
            processError(e);
            return null;
        }

    }
    
    
    public static String getTableFromEnum(tableTypes table){
        String tableName = "";
        switch (table){
            case customer:
                tableName = "`Customers`";
                break;
            case transaction:
                tableName = "`Transactions`";
                break;
            case item:
                tableName = "`Inventory Items`";
                break;
            case supplier:
                tableName = "`Suppliers`";
                break;                
        }
        return tableName;
    }
    
    
    public static HashSet<String> getIDs(tableTypes table) throws SQLException{
        connect();
        HashSet<String> idStrings = new HashSet<String>();
        Statement statement = databaseConnection.createStatement();      
        String command = "SELECT DISTINCT ID FROM " + getTableFromEnum(table);            
        ResultSet results = statement.executeQuery(command);
        while (results.next()) idStrings.add(results.getString("ID"));
        return idStrings;
    }
    
        
     /**
     * Generates a unique id for use in the entered table type.
     * This will break the whole app once the user gets a few billion entries (which probably won't happen).
     * @throws SQLException 
     */
    public static String generateUniqueId(tableTypes table) throws SQLException{
        connect();
        String id = "";
        boolean valid = false;
        HashSet<String> idStrings = getIDs(table);                   

        while (!valid){
            id = RandomString.nextString(DEFAULT_ID_LENGTH);
            valid = !idStrings.contains(id);
        }
        return id;
    }


    public static List searchWithFilter(String searchTerm, String filter) throws SQLException{
        try {
            connect();
            String tableName = "["+filter+"]";
            List ArrSearchResults = new ArrayList<>();
            List<String> ColumnNames = new ArrayList<>();
            Statement statement = databaseConnection.createStatement();
            String getColumns = "SELECT * FROM " + tableName + "";
            ResultSet rs = statement.executeQuery (getColumns);
            ResultSetMetaData rsmd = rs.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            for(int i = 1; i<=ColumnCount; i++){
                ColumnNames.add(rsmd.getColumnName(i));
            }
            for(int i = 0; i<ColumnCount; i++){
                String getResults = "SELECT * FROM " + tableName + " WHERE ["+ ColumnNames.get(i) + "] LIKE " + "'%" + searchTerm + "%'";
                ResultSet rs2 = statement.executeQuery(getResults);
                
                while (rs2.next()){
                    switch (filter) {
                        case "Inventory Items":
                            InventoryItem item = DatabaseManager.convertInventoryItem(rs2);
                            if(!ArrSearchResults.contains(item)){
                                ArrSearchResults.add(item);
                            }   break;
                        case "Customers":
                            Customer customer = DatabaseManager.convertCustomer(rs2);
                            if(!ArrSearchResults.contains(customer)){
                                ArrSearchResults.add(customer);
                            }   break;
                        case "Suppliers":
                            Supplier supplier = DatabaseManager.convertSupplier(rs2);
                            if(!ArrSearchResults.contains(supplier)){
                                ArrSearchResults.add(supplier);
                            }   break;
                        case "Transactions":
                            Transaction transaction = DatabaseManager.convertTransaction(rs2);
                            if(!ArrSearchResults.contains(transaction)){
                                ArrSearchResults.add(transaction);
                            }   break;
                        default:
                            break;
                    }
                }
                rs2.close();
            }
            rs.close();
            statement.close();
            return ArrSearchResults;
        } catch (SQLException | ParseException | IOException | ClassNotFoundException e) {
            processError(e);
            return null;
        }
    }
    public static List searchEntireDatabase(String searchTerm) throws SQLException{
        try {
        List<List> ArrSearchList = new ArrayList<>();
        List ArrSearchCustomers;
        List ArrSearchInventoryItems;
        List ArrSearchSuppliers;
        List ArrSearchTransactions;
        List ArrSearchAll = new ArrayList<>();
        
        ArrSearchCustomers = searchWithFilter(searchTerm,"Customers");
        ArrSearchInventoryItems = searchWithFilter(searchTerm,"Inventory Items");
        ArrSearchSuppliers = searchWithFilter(searchTerm,"Suppliers");
        ArrSearchTransactions = searchWithFilter(searchTerm,"Transactions");
        ArrSearchList.add(ArrSearchCustomers);
        ArrSearchList.add(ArrSearchInventoryItems);
        ArrSearchList.add(ArrSearchSuppliers);
        ArrSearchList.add(ArrSearchTransactions);
        
        for(List list:ArrSearchList){
            for(Object result : list){
                ArrSearchAll.add(result);
            }
        }
        return ArrSearchAll;
        }catch (Exception e) {
            processError(e);
            return null;
        }
    }
    
    
    public static InventoryItem convertInventoryItem(ResultSet result)throws SQLException, ParseException, IOException, ClassNotFoundException{
        InventoryItem item;
        item = new InventoryItem(result.getString("ID"));
        item.name = result.getString("NAME");
        item.price = result.getFloat("PRICE");
        item.description = result.getString("DESCRIPTION");
        item.amountAvailable = result.getInt("AMOUNT AVAILABLE");
        item.stillSold = (result.getInt("STILL SOLD")) == 1;
        item.supplierId = result.getString("SUPPLIERS");
        item.imageName = result.getString("PICTURE NAME");
        return item;
    }
    
    
    public static Customer convertCustomer(ResultSet result)throws SQLException, ParseException, IOException, ClassNotFoundException{
        Customer customer;
        customer = new Customer(result.getString("ID"));
        customer.fullName = result.getString("FULL NAME");
        Customer.Gender gender = (result.getString("GENDER")).equals("Male") ? Customer.Gender.male : Customer.Gender.female;
        customer.gender = gender;
        customer.totalDebt = result.getDouble("TOTAL DEBT");
        
        return customer;
    }
    
    
    public static Supplier convertSupplier(ResultSet result)throws SQLException, ParseException, IOException, ClassNotFoundException{
        Supplier supplier;
        supplier = new Supplier(result.getString("ID"));
        supplier.name = result.getString("NAME");
        supplier.email = result.getString("EMAIL");
        supplier.phone = result.getString("PHONE");
        String stringofItemIds = result.getString("ITEMS SUPPLIED");
        List<String> items = Arrays.asList(stringofItemIds.split("\\s*,\\s*"));
        ArrayList<String> idArray = new ArrayList<>(items);
        supplier.itemsSupplied = idArray;
        return supplier;
    }
    
    
    public static Transaction convertTransaction(ResultSet result)throws SQLException, ParseException, IOException, ClassNotFoundException{
        Transaction transaction;
        SimpleDateFormat formatDate = new SimpleDateFormat("E, MMM dd yyyy");
        transaction = new Transaction(result.getString("ID"), result.getString("CUSTOMERID"));
        transaction.date = formatDate.parse(result.getString("DATE"));
        transaction.mode = (result.getInt("DEBIT OR CREDIT") == 0) ? Transaction.transactionMode.debit : Transaction.transactionMode.credit;
        transaction.creditAmount = result.getDouble("CREDIT AMOUNT");
        List<String> itemIDs = Arrays.asList(result.getString("ITEMS SOLD").split("\\s*,\\s*"));
        List<String> quantities = Arrays.asList(result.getString("QUANTITIES SOLD").split("\\s*,\\s*"));
        Map<String, Integer> transactions = new HashMap<>();
        for (int i = 0; i < itemIDs.size(); i++){
            transactions.put(itemIDs.get(i), Integer.parseInt(quantities.get(i)));
        }
        transaction.transactions = transactions;
        return transaction;
    }
    
    public static void deleteCustomer(String id) throws SQLException {
        try {
            connect();
            String command = "DELETE FROM 'Customers' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Deleted");
        } catch (Exception e) {
            processError(e);
        }
    }
    
    public static void deleteItem(String id) throws SQLException {
         try {
            connect();
            String command = "DELETE FROM 'Inventory Items' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Deleted");
        } catch (Exception e) {
             processError(e);
        }
    }
    
    public static void deleteSupplier(String id) throws SQLException {
          try {
            connect();
            String command = "DELETE FROM 'Suppliers' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Deleted");
        } catch (Exception e) {
              processError(e);
        }
    }
    
    public static void updateCustomer(String id, String name, String gender) {
        try {
            connect();
            String command = "UPDATE 'Customers' SET 'Full Name' = '" + name + "', 'GENDER' = '" + gender + "' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Updated");
        } catch (Exception e) {
              processError(e);
        }
    }
    
    public static void updateSupplier(String id, String name, String email, String phone) {
        try {
            connect();
            String command = "UPDATE 'Suppliers' SET 'NAME' = '" + name + "', 'EMAIL' = '" + email + "', 'PHONE' = '" + phone + "' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Updated");
        } catch (Exception e) {
              processError(e);
        }  
    }
    
    public static void updateItem(String id, String name, String price, String desc) {
        try {
            connect();
            String command = "UPDATE 'Inventory Items' SET 'NAME' = '" + name + "', 'PRICE' = '" + price + "', 'DESCRIPTION' = '" + desc + "' WHERE ID = '" + id + "'";
            Statement stmnt = databaseConnection.createStatement();
            stmnt.executeUpdate(command);
            System.out.println("Updated");
        } catch (Exception e) {
              processError(e);
        }
    }
}
