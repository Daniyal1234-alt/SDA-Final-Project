import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.mysql.cj.QueryReturnType;

public class HisaabTrack {
    // Attributes
    private List<Admin> admins;
    private List<Supplier> suppliers;
    private List<InventoryManager> managers;
    private List<Store> stores;
    private ITService IT;
    private SQLDBHandler DB;

    // Constructor
    public HisaabTrack() {
        admins = new ArrayList<>();
        suppliers = new ArrayList<>();
        managers = new ArrayList<>();
        stores = new ArrayList<>();
        IT = new ITService();
        DB = SQLDBHandler.getInstance();
        for(Admin a: this.admins) {
        	System.out.println("Admin: " + a.getAdminID());
        }
        for(InventoryManager a: this.managers) {
        	System.out.println("Manager: " + a.getManagerID());
        }
        for(Store a: this.stores) {
        	System.out.println("Store: " + a.getStoreID());
        }
        for(Supplier s: this.suppliers) {
        	System.out.println("Supplier: " + s.getSupplierID());
        }
        DB.loadFromDB(this);
        for(Admin a: this.admins) {
        	System.out.println("Admin: " + a.getAdminID());
        }
        for(InventoryManager a: this.managers) {
        	System.out.println("Manager: " + a.getManagerID());
        }
        for(Store a: this.stores) {
        	System.out.println("Store: " + a.getStoreID());
        }
        for(Supplier s: this.suppliers) {
        	System.out.println("Supplier: " + s.getSupplierID());
        }
        
    }
    public boolean removeAdminUnpaidInvoice(int adminID,int invoiceID){
         
    	return		DB.removeAdminUnpaidInvoice(adminID,invoiceID);
        
    }
    public String Login(String username, String password) {
        String userType = DB.Login(username, password);
        return userType;
    }
    public Admin getLoggedAdmin(String username, String password){
        for (Admin admin : admins) {
            // Check if the username and password match
            if (admin.getName().equals(username) && admin.getPassword().equals(password)) {
                return admin;  // Return the admin object if a match is found
            }
        }
        return null;  // Return null if no match is found
    }
    public Supplier getLoggedSupplier(String username, String password){
        for (Supplier admin : suppliers) {
            // Check if the username and password match
            if (admin.getCompany().equals(username) && admin.getPasswordString().equals(password)) {
                return admin;  // Return the admin object if a match is found
            }
        }
        return null;  // Return null if no match is found
    }
    public InventoryManager getLoggedInventoryManager(String username, String password){
        for (InventoryManager admin : managers) {
            // Check if the username and password match
            if (admin.getName().equals(username) && admin.getPassword().equals(password)) {
                return admin;  // Return the admin object if a match is found
            }
        }
        return null;  // Return null if no match is found
    }

    // Method signatures
    public boolean login() {
        return true;
    }
    public boolean signUp() {
        return true;
    }

    // Admin functions
    public boolean updateAdminProfile(int adminID, String Name, String cnic, String Address,String password) {
        Admin admin = getAdminByID(adminID);
        if(admin == null)
            return false;
        admin.updateProfile(Name, cnic, Address, password);
        DB.updateAdmin(admin);
        return true;
    }
    public Store generateAdminReport(int adminID, int managerID) {
        if(getAdminByID(adminID).isMyManager(managerID))
            return generateManagerReport(managerID);
        return null;
    }
    public void addSupplier(int adminID, int supplierID, String company, String location, int regNo,String password,  boolean DBCall) {
        Supplier e = null;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
                if(DBCall)
                    e = admins.get(i).addSupplier(supplierID,company,location,regNo, password);
                else {
                    int ID = 1;
                    if(!suppliers.isEmpty())
                        ID = suppliers.getLast().getSupplierID() + 1;
                    e = admins.get(i).addSupplier(ID,company,location,regNo, password);
                }
                break;
            }
        }
        suppliers.add(e);
        //add new supplier to db
        if(!DBCall) {
            DB.addSupplier(e);
            DB.addAdminSupplier(adminID, e.getSupplierID());
        }
    }
    public boolean removeSupplier(int adminID, int supplierID) {
        boolean flag = false;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
                flag = admins.get(i).removeSupplier(supplierID, suppliers);
                if (flag) {
                    //UI displays successful removal
                    //update DB
                	DB.removeSupplier(supplierID);
                	DB.removeAdminSupplier(adminID, supplierID);
                } else {
                    //not removed
                }
                break;
            }
        }
        return flag;
    }
    public boolean updateSupplier(int adminID, int supplierID, String company, String location, int regNo) {
        boolean flag = false;
        Supplier e = null;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
                e = admins.get(i).updateSupplier(suppliers, supplierID, company, location, regNo);
                //UI displays successful update
                //Update DB
                if(e!=null)
                	DB.updateSupplier(e);
            }
        }
        return flag;
    }
    public Admin addAdmin(int adminID, String Name, String cnic, String Address,String password, boolean DBCall) {
        int ID = 1;
        if(DBCall)
            ID = adminID;
        else {
            if(!admins.isEmpty())
                ID = admins.getLast().getAdminID() + 1;
        }
        Admin newAdmin = new Admin(ID, Name, cnic, Address, password);
        admins.add(newAdmin);
        if(!DBCall)
            DB.addAdmin(newAdmin);
        return newAdmin;
    }
    public void addDeliveredOrder(int supplierID, Invoice i) {
    	for(Supplier s: this.suppliers) {
    		if(s.getSupplierID() == supplierID) {
    			s.addDeliveredOrder(i);
    		}
    	}
    }
    public Store getStore(int storeID) {
    	 for (Store s : this.stores) {
             // Check if the manager's ID matches the provided ID
             if (s.getStoreID() == storeID) {
                 // Return the manager object if a match is found
                 return s;
             }
         }
    	 return null;
    }
    public Supplier getSupplier(int supplierID) {
    	for (Supplier s : this.suppliers) {
            // Check if the manager's ID matches the provided ID
            if (s.getSupplierID() == supplierID) {
                // Return the manager object if a match is found
                return s;
            }
        }
   	 return null;
    }
    public void addManager(int adminID, int managerID, String Name, String cnic, String address, String password, Store s, boolean DBCall) {
        InventoryManager e = null;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
                int ID = -1;
                if(DBCall)
            	    e = admins.get(i).addInventoryManager(managerID, Name, cnic, address,  s, password );
                else{
                    if(!managers.isEmpty())
                        ID = managers.getLast().getManagerID() + 1;
                    e = admins.get(i).addInventoryManager(ID, Name, cnic, address,  s, password );
                }
                s.setManagerID(e.getManagerID());
                break;
            }
        }
        managers.add(e);
        //add new manager to db
        if(!DBCall) {
            DB.addInventoryManager(e);
            DB.addAdminInventoryManager(adminID, e.getManagerID());
            DB.addManagerStore(e.getManagerID(), s.getStoreID());
        }
    }  
    public void addUnpaidInvoice(int adminID, Invoice invoice) {
    	for(Admin a: this.admins) {
    		if(a.getAdminID()==adminID) {
    			a.addunpaidinvoice(invoice);
    		}
    	}
    }
    public void addStore(Store s) {
    	this.stores.add(s);
    }
    public boolean removeManager(int adminID, int managerID) {
        boolean flag = false;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
            	int storeID = getManagerByID(managerID).getManagingStore().getStoreID();
                flag = admins.get(i).removeInventoryManager(managerID);
                if (flag) {
                    //UI displays successful removal
                    //update DB
                	DB.removeInventoryManager(findManagerByID(managerID));
                	DB.removeAdminInventoryManager(adminID, managerID);
                	DB.removeManagerStore(managerID, storeID);
                } else {
                    //not removed
                }
                break;
            }
        }
        return flag;
    }
    public boolean updateManager(int adminID, int managerID, String Name, String cnic, String Address) {
        InventoryManager e = null;
        for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
                e = admins.get(i).updateInventoryManager(managerID, Name, cnic, Address);
                //UI displays successful update
                //Update DB
                if(e!=null)
                	DB.updateInventoryManager(e);	
            }
        }
        return true;
    }
    public List<InventoryManager> getAdminsInventoryManagers(int adminID) {
         for(int i = 0; i < admins.size(); ++i) {
            if(adminID == admins.get(i).getAdminID()) {
               return admins.get(i).getMyManagers();
            }
        }
        return null;
    }
    public List<Invoice> handleUnpaidInvoices(int adminID) {
        for(Admin obj : admins) {
            if(obj.getAdminID() == adminID) {
                return obj.getUnpaidInvoices();
            }
        }
        return null;
    }
    public void payInvoice(int adminID, int invoiceID, int supplierID) {
        for(Admin obj : admins) {
            if(obj.getAdminID() == adminID) {
                double payment = obj.payInvoice(invoiceID);
                for(Supplier s : suppliers) {
                    if(s.getSupplierID() == supplierID) {
                        s.receivePayment(invoiceID, payment);
                        break;
                    }
                }
                break;
            }
        }
    }
    public boolean approveOrder(int adminID, int invoiceID) {
        Invoice e = null;
        boolean flag = false;
        for(Admin obj : admins) {
            if(obj.getAdminID() == adminID) {
                e = obj.getInvoiceByID(invoiceID);
                for(Supplier s : suppliers) {
                    if(s.getSupplierID() == e.getSupplierID()) {
                        flag = s.addIncomingOrder(e);
                        if(flag) {
                        	payInvoice(adminID, invoiceID,e.getSupplierID());
                        	getManagerByID(e.getCreatedBy()).findInvoiceByID(invoiceID).setPaymentStatus(true);
                        	// Updating
                        	DB.invoicePaid(invoiceID);
                        	DB.removeAdminUnpaidInvoice(adminID, invoiceID);
                        	DB.addSupplierPendingOrder(e.getSupplierID(), invoiceID);
                        } 
                        //update DB
                        break;
                    }
                }
                break;
            }
        }
        return flag;
        // if true then order successful else supplier cannot fulfill order
    }

    // manager functions
    public boolean isValidProduct(int managerID, int productID) {
        if(getManagerByID(managerID).getProduct(productID) == null) {
            return false;
        }
        return true;
    }
    public boolean isValidProductFromSupplier(int supplierID, int productID) {
        for(Product product : getSupplierByID(supplierID).getProducts().getProduct()){
            if(product.getProductID() == productID) {
                return true;
            }
        }
        return false;
    }
    public void makeSale(int managerID, List<Integer> p, List<Integer> q) {
        for(InventoryManager obj:managers) {
            if(obj.getManagerID() == managerID) {
                obj.makeSale(p,q);
            }
        }
        //update DB
   
    }
    public void addInvoiceToRegister(Invoice obj) {
        getManagerByID(obj.getCreatedBy()).addInvoice(obj);
    }
    public void placeOrder(int managerID, int supplierID, List<Integer> p, List<Integer> q) {
        Invoice e = new Invoice();
        
        // System builds the product list
        List<Product> pList = new ArrayList<>();
        for(int pID:p) {
            Product product = getSupplierCatalog(supplierID).getProductByID(pID);
            if(product != null) {
            	pList.add(product);
            	System.out.println("ProductID: " + product.getProductID());
            }
                
        }

        for(InventoryManager manager:managers) {
            if(manager.getManagerID() == managerID) {
                e = manager.placeOrder(pList, q);
                e.setSupplierID(supplierID);
                e.setCreatedBy(managerID);
                System.out.println("Supplier ID in invoice: " + e.getSupplierID());
                DB.addInvoice(e);
            }
        }
        for(Admin s:admins) {
            if(s.isMyManager(managerID)) {
            	if(e!=null) {
            		s.addunpaidinvoice(e);
            		//update DB
            		DB.addAdminUnpaidInvoice(s.getAdminID(), e.getInvoiceID());
            	} 
            }
        }
        //update DB
    }
    public List<Stock> checkStock(int managerID) {
        return getStoreStock(managerID);
    }
    public Store generateManagerReport(int managerID) {
        return getManagerByID(managerID).getManagingStore();
    }
    public List<Invoice> getInvoiceReport(int managerID) {
        return getManagerByID(managerID).getRegister().getInvoices();
    }
    public List<Invoice> viewOrderStatus(int managerID) {
        return getManagerByID(managerID).getOrders();
    }

    // Supplier functions
    public ProductCatalog getProductCatalog(int supplierID) {
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == supplierID) {
                return suppliers.get(i).getProducts();
            }
        }
        return null;
    }
    public void addItem(int supplierID, int itemID, String name, String description, double price, Date MFG, Date EXP, int amount, boolean DBCall) {
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == supplierID) {
                int ID = -1;
                if(DBCall)
                    ID = itemID;
                Product p = new Product(ID, name, description, price, MFG, EXP, DBCall);                
                suppliers.get(i).addProduct(p, amount);
                ProductCatalog catalog = suppliers.get(i).getProducts();
                //update catalog in DB
                if(!DBCall) {
                    DB.addProduct(suppliers.get(i), p, amount);
                    //DB.addProductCatalog(supplierID, p.getProductID(), amount);
                }
            }
        }
    }
    public void removeItem(int supplierID, int productID) {
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == supplierID) {
                suppliers.get(i).removeProduct(productID);
                ProductCatalog catalog = suppliers.get(i).getProducts();
                //update catalog in DB
                DB.removeProductCatalog(productID, supplierID);
                DB.removeProduct(suppliers.get(i), productID);
                
            }
        }
    }
    public void updateItem(int supplierID, int productID, int addedAmount) {
        Product p = null;
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == supplierID) {
                for(int j = 0;j<suppliers.get(i).getProducts().getProduct().size();++j) {
                	System.out.println("Product ID: " + productID);
                    if (suppliers.get(i).getProducts().getProduct().get(j).getProductID() == productID) {
                    	
                        p = suppliers.get(i).getProducts().getProduct().get(j);
                    }
                }
                suppliers.get(i).addProduct(p,addedAmount);  
                //update catalog in DB        
                DB.updateCatalog(supplierID, productID, addedAmount );
            }
        }
    }
    public void sendProducts(int ID, int invoiceID) {
        int iManagerID = 0;
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == ID) {
                iManagerID = suppliers.get(i).sendOrder(invoiceID);
                getManagerByID(iManagerID).findInvoiceByID(invoiceID).setDeliveryStatus(true);
                getManagerByID(iManagerID).updateStock();
                
                //DB Functions
                int storeID = getManagerByID(iManagerID).getManagingStore().getStoreID();
                List<Stock> s = getStoreStock(storeID);
                for(Stock s1 : s) {
                	DB.addStock(s1.getProduct().getProductID(), s1.getQuantity(), s1.getTotalCost(), s1.getArrivalDate());
                    DB.addStoreStock(storeID, s1.getStockID());
                }
                DB.invoiceDelivered(invoiceID);
                DB.addSupplierDeliveredOrder(ID, invoiceID);
                DB.removeSupplierPendingOrder(ID, invoiceID);
            }
        }
    }
    public List<Invoice> viewRecievedOrders(int ID) {  //new
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == ID) {
                //Display recieved orders on UI
                return suppliers.get(i).getRecievedOrders();
            }
        }
        return null;
    } 
    public List<Invoice> viewCompletedOrders(int ID) {  //new
        for(int i=0;i<suppliers.size();++i){
            if(suppliers.get(i).getSupplierID() == ID) {
                //Display recieved orders on UI
                return suppliers.get(i).getCompletedOrders();
            }
        }
        return null;
    } 

    // Utility functions
    public ProductCatalog getSupplierCatalog(int supplierID) {
        Supplier s = getSupplierByID(supplierID);
        if(s!=null) {
            return s.getProducts();
        }
        return null;
    }
    public void addPendingOrder(int supplierID, Invoice obj, boolean DBCall) {
    	getSupplierByID(supplierID).addIncomingOrder(obj);
    	if(!DBCall)
    	DB.addSupplierPendingOrder(supplierID, obj.getInvoiceID());
    }
    public String findManagerByID(int ID) {
        for (InventoryManager manager : managers) {
            if (manager.getManagerID() == ID) {
                return manager.getCNIC();
            }
        }
        return null;
    }
    public InventoryManager getManagerByID(int ID) {
        for (InventoryManager manager : managers) {
            if (manager.getManagerID() == ID) {
                return manager;
            }
        }
        return null;
    }
    public Supplier getSupplierByID(int ID) {
        for (Supplier s : suppliers) {
            if (s.getSupplierID() == ID) {
                return s;
            }
        }
        return null;
    } 
    public List<Stock> getStoreStock(int managerID) {
        for(Store s:stores) {
            if(s.getManagerID() == managerID) {
                return s.getStock();
            }
        }
        return null;
    }
    public Admin getAdminByID(int adminID) {
        Admin admin = null;
        for(Admin a: admins) {
            if(a.getAdminID()==adminID) {
                admin = a;
            }
        }
        return admin;
    }

    // Getters and Setters
    public List<Admin> getAdmins() {
        return admins;
    }
    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
    public List<Supplier> getSuppliers() {
        return suppliers;
    }
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    public List<InventoryManager> getManagers() {
        return managers;
    }
    public void setManagers(List<InventoryManager> managers) {
        this.managers = managers;
    }
    public List<Store> getStores() {
        return stores;
    }
    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
    public ITService getIT() {
        return IT;
    }
    public void setIT(ITService IT) {
        this.IT = IT;
    }
}
