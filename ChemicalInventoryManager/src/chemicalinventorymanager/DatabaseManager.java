package chemicalinventorymanager;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.text.SimpleDateFormat;  
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a static helper class that manages all the logic for the SQLite backend of this application
 */
public final class DatabaseManager {
    private DatabaseManager(){} //This class is static; it shouldn't be able to be instanced
    
    private static Connection databaseConenction;
    private static String DATABASE_PATH = "src\\chemicalinventorymanager\\Databases\\ShopDatabase.db";
    private static String DATABASE_NAME = "ShopDatabase.db";
    
    private static void processError(Exception e){
        System.out.println(e);
    }
    
    private static void connect() throws SQLException{
        try {
            if (databaseConenction != null) {
                return;
            }
            String url = "jdbc:sqlite:" + DATABASE_PATH;
            databaseConenction = DriverManager.getConnection(url);
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }    
    }
    
    /**
     * This class is designed for integration with ListViews with search-by-name capabilities
     * @param query
     * @return A list of Customers
     * @throws java.sql.SQLException
     */
    public static List<Customer> getCustomersWithName(String query) throws SQLException{
        ArrayList<Customer> resultList = new ArrayList <>();
        try {
            connect();
            Statement statement = databaseConenction.createStatement();
            
            query = query.toLowerCase();
            String command = "select ID, GENDER, [TOTAL DEBT] from Customers "
                    + "where lower([FULL NAME]) like '%" + query + "%'";
            
            ResultSet results = statement.executeQuery(command);
            
            while (results.next()) {
                Customer customer = new Customer(results.getString("ID"));
                customer.fullName = results.getString("FULL NAME");
                Customer.Gender gender = (results.getString("GENDER")).equals("Male") ? Customer.Gender.male : Customer.Gender.female;
                customer.gender = gender;
                customer.totalDebt = results.getDouble("TOTAL DEBT");
                resultList.add(customer);
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
            Statement statement = databaseConenction.createStatement();
            String command = String.format("INSERT INTO `Customers` (id, full name, gender)"
                    + " VALUES ('$s', '%s', '%s')", customer.getID(), customer.fullName, customer.getGender());
            statement.executeUpdate(command);
            statement.close();            
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
    public static Customer getCustomerWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConenction.createStatement();
            
            String command = "select * from Customers where ID = " + id;
            
            ResultSet results = statement.executeQuery(command);
            Customer customer = null;
            
            if (results.next()) {
                customer = DatabaseManager.convertCustomer(results);
            }
            statement.close();
            return customer;

        } catch (Exception e) {
            processError(e);
        } finally{
            return null;
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
            Statement statement = databaseConenction.createStatement();
            
            query = query.toLowerCase();
            String command = "select * from [Inventory Items] where lower(NAME) like '%" + query + "%'";
            
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
            Statement statement = databaseConenction.createStatement();
            String command = String.format("INSERT INTO `Inventory Items`"
                    + " VALUES ('%s', '%s','%f', '%s', '%d', '%d', '%s', '%s')", 
                    item.getID(), item.name, item.price ,item.description, (item.stillSold) ? 1 : 0, item.amountAvailable, item.imageName, item.supplierId);
            statement.executeUpdate(command);
            statement.close();            
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
            Statement statement = databaseConenction.createStatement();
            
            String command = "select * from [Inventory Items] where ID = " + id;
            
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
            Statement statement = databaseConenction.createStatement();
            
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
            Statement statement = databaseConenction.createStatement();
            
            String command = String.format("INSERT INTO `Suppliers`"
                    + " VALUES ('%s', '%s', '%s', '%s', '%s')", 
                    supplier.getID(), supplier.name, supplier.email, supplier.phone, String.join(",", supplier.itemsSupplied));
            statement.executeUpdate(command);
            statement.close();            
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
            Statement statement = databaseConenction.createStatement();
            
            String command = "select * from Suppliers where ID = " + id;
            
            ResultSet results = statement.executeQuery(command);
            Supplier supplier = null;
            
            if (results.next()) {
                supplier = DatabaseManager.convertSupplier(results);
            }
            statement.close();
            return supplier;

        } catch (Exception e) {
            processError(e);
        } finally{
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
            Statement statement = databaseConenction.createStatement();
            
            query = query.toLowerCase();
            String command = "select * Transactions where lower(DATE) contains " + query;
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
            Statement statement = databaseConenction.createStatement();
            SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy"); 
            
            String itemKeys = "";
            Set<String> keys = tran.transactions.keySet();
            itemKeys = String.join(",", keys);
            
            String quantities = "";
            Collection<Integer> mappings = tran.transactions.values();
            quantities = mappings.toString();
            quantities = quantities.substring(1, quantities.length()-1); 
            
            String command = String.format("INSERT INTO Suppliers"
                    + "VALUES ('%s', '%s', '%s', '%d', '%s', '%s', '%f')", 
                    tran.getID(), tran.getCustomerID(), formatter.format(tran.date), (tran.mode == Transaction.transactionMode.debit) ? 0 : 1, itemKeys, quantities, tran.creditAmount);
            statement.executeUpdate(command);
            statement.close();            
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
    public static Transaction getTransactionWithId(String id) throws SQLException{
        try {
            connect();
            Statement statement = databaseConenction.createStatement();
            
            String command = "select * from Inventory Items where ID = " + id;
            
            ResultSet results = statement.executeQuery(command);
            Transaction tran = null;
            if (results.next()) {
                tran = DatabaseManager.convertTransaction(results);
            }
            statement.close();
            return tran;
            
        } catch (Exception e) {
            processError(e);
        } finally{
            return null;
        }
    }
    
    
    public static List searchWtihFilter(String searchTerm, String filter) throws SQLException{
        try {
            connect();
            String tableName = "["+filter+"]";
            List ArrSearchResults = new ArrayList<>();
            List<String> ColumnNames = new ArrayList<>();
            Statement statement = databaseConenction.createStatement();
            String getColumns = "SELECT * FROM " + tableName + "";
            ResultSet rs = statement.executeQuery (getColumns);
            ResultSetMetaData rsmd = rs.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            for(int i = 1; i<=ColumnCount; i++){
                ColumnNames.add(rsmd.getColumnName(i));
            }
            for(int i = 0; i<ColumnCount; i++){
                String getResults = "SELECT * FROM " + tableName + " WHERE ["+ ColumnNames.get(i) + "] LIKE " +"'%" +searchTerm + "%'";
                ResultSet rs2 = statement.executeQuery(getResults);
                
                while (rs2.next()){
                    switch (filter) {
                        case "Inventory Items":
                            InventoryItem item = DatabaseManager.convertInventoryItem(rs2);
                            if(!ArrSearchResults.contains(item)){
                                ArrSearchResults.add(item);
                            }   break;
                        case "Customer":
                            Customer customer = DatabaseManager.convertCustomer(rs2);
                            if(!ArrSearchResults.contains(customer)){
                                ArrSearchResults.add(customer);
                            }   break;
                        case "Supplier":
                            Supplier supplier = DatabaseManager.convertSupplier(rs2);
                            if(!ArrSearchResults.contains(supplier)){
                                ArrSearchResults.add(supplier);
                            }   break;
                        case "Transaction":
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
        List ArrSearchCustomers, ArrSearchInventoryItems, ArrSearchSuppliers, ArrSearchTransactions;
        List ArrSearchAll = new ArrayList<>();
        
        ArrSearchCustomers = searchWtihFilter(searchTerm,"Customers");
        ArrSearchInventoryItems = searchWtihFilter(searchTerm,"Inventory Items");
        ArrSearchSuppliers = searchWtihFilter(searchTerm,"Suppliers");
        ArrSearchTransactions = searchWtihFilter(searchTerm,"Transactions");
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

        Blob debtsBytes = result.getBlob("ARRAY OF CREDITS");
        InputStream binaryInput = null;
        if (null != debtsBytes && debtsBytes.length() > 0) {
            binaryInput = debtsBytes.getBinaryStream();
            ObjectInputStream inputStream = new ObjectInputStream(binaryInput);
            customer.debts = (Map<String, Double>)inputStream.readObject();
        }else {
            customer.debts = null;
        }
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
}
