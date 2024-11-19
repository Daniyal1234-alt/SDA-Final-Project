import java.awt.im.InputMethodHighlight;

import java.io.CharConversionException;
import java.lang.foreign.AddressLayout;
import java.sql.*;

import javax.naming.ldap.ManageReferralControl;
import javax.net.ssl.ManagerFactoryParameters;

import com.mysql.cj.callback.UsernameCallback;
public class SQLDBHandler {
	private String connection;
	private String className;
	private String userName;
	private String password;
	
	SQLDBHandler(){
		className = "com.mysql.cj.jdbc.Driver";
		connection = "jdbc:mysql://localhost:3306/HisaabTrack";
		userName = "root";
		password = "dani";
	}
	// Adding an Admin to the SQL DB
	public boolean addAdmin(Admin admin) {
	    try (Connection conn =  DriverManager.getConnection(connection,userName, password)) {
	        String sql = "INSERT INTO Admin (name, CNIC, address, active) VALUES (?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, admin.getName());
	        pstmt.setString(2, admin.getCNIC());
	        pstmt.setString(3, admin.getAddress());
	        pstmt.setBoolean(4, admin.isActive());
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing an Admin based on it's CNIC
	public boolean removeAdmin(String CNIC) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "DELETE FROM Admin WHERE CNIC = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, CNIC);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding an Inventory Manager
	public boolean addInventoryManager(InventoryManager manager) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "INSERT INTO InventoryManager (name, CNIC, address, storeID) VALUES (?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, manager.getName());
	        pstmt.setString(2, manager.getCNIC());
	        pstmt.setString(3, manager.getAddress());
	        if (manager.getManagingStore() != null) {
	            pstmt.setInt(4, manager.getManagingStore().getStoreID());
	        } else {
	            pstmt.setNull(4, java.sql.Types.INTEGER);
	        }
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing an Inventory Manager based on it's CNIC
	public boolean removeInventoryManager(String CNIC) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "DELETE FROM InventoryManager WHERE CNIC = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, CNIC);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding a supplier to the DB
	public boolean addSupplier(Supplier supplier) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "INSERT INTO Supplier (companyName, location, registrationNum) VALUES (?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, supplier.getCompany());
	        pstmt.setString(2, supplier.getLocation());
	        pstmt.setInt(3, supplier.getRegNo());
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing a supplier based on it's registration number
	public boolean removeSupplier(int supplierID) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "DELETE FROM Supplier WHERE supplierID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, supplierID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding a generic invoice to invoice table 
	public boolean addInvoice(Invoice invoice) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "INSERT INTO Invoice (createdByID, createdOn, userType, paid, deliverd) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, invoice.getCreatedBy());
	        pstmt.setDate(2, (Date)invoice.getCreatedOn());
	        pstmt.setString(3, invoice.getCreatorType());
	        pstmt.setBoolean(4, invoice.isPaidFor());
	        pstmt.setBoolean(5, invoice.isDelivered());
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing an invoice based on invoiceID
	public boolean removeInvoice(int invoiceID) {
	    try (Connection conn = DriverManager.getConnection(connection,userName, password)) {
	        String sql = "DELETE FROM Invoice WHERE invoiceID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, invoiceID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Add to Stock of a specific store
	public boolean addProductStock(Stock stock, int StoreID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO Stock (productID, storeID, quantity, totalCost, arrivalDate) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, stock.getProduct().getProductID());
	        pstmt.setInt(2, StoreID);
	        pstmt.setInt(3, stock.getQuantity());
	        pstmt.setDouble(4, stock.getTotalCost());
	        pstmt.setDate(5, (Date)stock.getArrivalDate());
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing a stock from a specific store
	public boolean removeProductStock(int stockID, int StoreID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM Stock WHERE stockID = ? AND storeID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, stockID);
	        pstmt.setInt(2, StoreID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding into StoreStocks Relationship table
	public boolean addStoreStock(int storeID, int stockID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO StoreStock (storeID, stockID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, storeID);
	        pstmt.setInt(2, stockID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing from StoreStock Relationship table
	public boolean removeStoreStock(int storeID, int stockID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM StoreStock WHERE storeID = ? AND stockID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, storeID);
	        pstmt.setInt(2, stockID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding to SupplierCatalogID
	public boolean addSupplierCatalog(int supplierID, int catalogID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO SupplierCatalog (supplierID, catalogID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, supplierID);
	        pstmt.setInt(2, catalogID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing Supplier CatalogID 
	public boolean removeSupplierCatalog(int supplierID, int catalogID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM SupplierCatalog WHERE supplierID = ? AND catalogID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, supplierID);
	        pstmt.setInt(2, catalogID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding product to product catalog
	public boolean addProductCatalog(int productCatalogID, int productID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO ProductCatalogProducts (catalogID, productID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, productCatalogID);
	        pstmt.setInt(2, productID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing product from a product catalog ID
	public boolean removeProductCatalog(int productCatalogID, int productID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM ProductCatalogProducts WHERE catalogID = ? AND productID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, productCatalogID);
	        pstmt.setInt(2, productID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding an invoice that admin will have to pay
	public boolean addAdminUnpaidInvoice(int adminID, int invoiceID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO AdminUnpaidInvoices (adminID, invoiceID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, adminID);
	        pstmt.setInt(2, invoiceID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing an invoice that has been paid
	public boolean removeAdminUnpaidInvoice(int adminID, int invoiceID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM AdminUnpaidInvoices WHERE adminID = ? AND invoiceID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, adminID);
	        pstmt.setInt(2, invoiceID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Adding an inventory manager under Admin
	public boolean addAdminInventoryManager(int adminID, int inventoryManagerID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO AdminInventoryManager (adminID, inventoryManagerID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, adminID);
	        pstmt.setInt(2, inventoryManagerID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing an inventory manager from an admin
	public boolean removeAdminInventoryManager(int adminID, int inventoryManagerID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM AdminInventoryManager WHERE adminID = ? AND inventoryManagerID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, adminID);
	        pstmt.setInt(2, inventoryManagerID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	//  Add a supplier pending order
	public boolean addSupplierPendingOrder(int supplierID, int invoiceID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "INSERT INTO SupplierPendingOrders (supplierID, invoiceID) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, supplierID);
	        pstmt.setInt(2, invoiceID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Removing a supplier pending order which has been fulfilled
	public boolean removeSupplierPendingOrder(int supplierID, int invoiceID) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "DELETE FROM SupplierPendingOrders WHERE supplierID = ? AND invoiceID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, supplierID);
	        pstmt.setInt(2, invoiceID);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// Sql query for when the invoice is paid
	public boolean invoicePaid(int invoiceID) {
		 try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
		        String sql = "UPDATE Invoices SET paid = true  WHERE invoiceID = ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, invoiceID);
		        int rows = pstmt.executeUpdate();
		        if(rows>0) {
		        	sql = "DELETE FROM adminunpaidinvoices WHERE invoiceID = ?";
			        pstmt = conn.prepareStatement(sql);
			        pstmt.setInt(1, invoiceID);
			        rows = pstmt.executeUpdate();
			        if(rows>0) {
			        	return true;
			        }
			        else {
			        	return false;
			        }
		        }
		        else {
		        	return false;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	}
	// SQL Query for when an invoice is fulfilled 
	public boolean invoiceDelivered(int invoiceID) {
		 try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
		        String sql = "UPDATE Invoices SET deliverd = true  WHERE invoiceID = ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, invoiceID);
		        int rows = pstmt.executeUpdate();
		        if(rows>0) {
		        	sql = "DELETE FROM supplierpendingorders WHERE InvoiceID = ?";
			        pstmt = conn.prepareStatement(sql);
			        pstmt.setInt(1, invoiceID);
			        rows = pstmt.executeUpdate();
			        if(rows>0) {
			        	return true;
			        }
			        else {
			        	return false;
			        }
		        }
		        else {
		        	return false;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	}
	// SQL Query for report generation 
	public Report generateReport(InventoryManager manager) {
		Report report = new Report();
	    StringBuilder reportContent = new StringBuilder();

	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        // Query to get product information for the store managed by the given InventoryManager
	        String sql = "SELECT p.productID, p.productName, s.quantity, s.arrivalDate " +
	                     "FROM Store st " +
	                     "JOIN Stock s ON st.storeID = s.storeID " +
	                     "JOIN Product p ON s.productID = p.productID " +
	                     "WHERE st.storeID = ?";
	        report.setCreatedBy("Manager: " + manager.getManagerID() );
	        Date date = new Date(0);
	        report.setCreatedOn(date);
	        report.setUserType("Manager");
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, manager.getManagingStore().getStoreID()); // Assuming InventoryManager has getStoreID() method

	        ResultSet rs = pstmt.executeQuery();

	        // Add a header to the report
	        reportContent.append("Inventory Report for Store ID: ").append(manager.getManagingStore().getStoreID()).append("\n");
	        reportContent.append("=========================================================\n");
	        reportContent.append(String.format("%-10s %-20s %-10s %-15s\n", 
	                                           "ProductID", "ProductName", "Quantity", "ArrivalDate"));
	        reportContent.append("---------------------------------------------------------\n");

	        // Process the result set and build the report
	        while (rs.next()) {
	            int productID = rs.getInt("productID");
	            String productName = rs.getString("productName");
	            int quantity = rs.getInt("quantity");
	            Date arrivalDate = rs.getDate("arrivalDate");

	            reportContent.append(String.format("%-10d %-20s %-10d %-15s\n", 
	                                               productID, productName, quantity, arrivalDate.toString()));
	        }

	        reportContent.append("=========================================================\n");

	        // Set the report content in the Report object
	        report.setReportData(reportContent.toString());
	     // 2. Insert report into the 'report' table
	        String insertQuery = "INSERT INTO report ( createdByID, createdOn, userType) VALUES (?, ?, ?, ?)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	        insertStmt.setInt(1, manager.getManagerID());
	        insertStmt.setDate(2, new Date(System.currentTimeMillis()));
	        insertStmt.setString(3, "Manager");

	        int affectedRows = insertStmt.executeUpdate();

	        // 3. Retrieve and set the generated report ID
	        if (affectedRows > 0) {
	            System.out.println("Inserted into Report Table");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        report.setReportData("Error generating report: " + e.getMessage());
	    }

	    return report;
	}
	// SQL Query for updating inventory manager
	public boolean updateInventoryManager(InventoryManager manager) {
		 try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
		        String sql = "UPDATE inventorymanager SET name = ?, CINC = ?, address = ?, storeID = ?  WHERE managerID = ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, manager.getName());
		        pstmt.setString(2, manager.getCNIC());
		        pstmt.setString(3, manager.getAddress());
		        pstmt.setInt(4, manager.getManagingStore().getStoreID());
		        pstmt.setInt(5, manager.getManagerID());
		        int rows = pstmt.executeUpdate();
		        return rows > 0 ;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
	}
	//SQL Query for updating supplier
	public boolean updateSupplier(Supplier supplier) {
		try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        String sql = "UPDATE supplier SET companyName = ?, location = ?, registrationNum = ? WHERE supplierID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, supplier.getCompany());
	        pstmt.setString(2, supplier.getLocation());
	        pstmt.setInt(3, supplier.getRegNo());
	        pstmt.setInt(4, supplier.getSupplierID());
	        int rows = pstmt.executeUpdate();
	        return rows > 0 ;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	//Delete an invoice 
	public boolean deleteInovice(int InvoiceID) {
		try(Connection conn = DriverManager.getConnection(connection, userName, password)){
			String sql = "DELETE FROM Invoice WHERE invoiceID = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, InvoiceID);
	        int rows = pstmt.executeUpdate();
			return rows > 0;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	// Adding a product to a catalog and product table
	public boolean addProduct(Supplier s, Product p) {
	    try (Connection conn = DriverManager.getConnection(connection, userName, password)) {
	        // Insert Product into Product table
	        String productSql = "INSERT INTO Product (name, description, price, MFG, EXP) "
	                + "VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement productPstmt = conn.prepareStatement(productSql)) {
	            productPstmt.setString(1, p.getName());
	            productPstmt.setDouble(2, p.getPrice());
	            productPstmt.setDouble(3, p.getPrice());
	            productPstmt.setDate(4, (Date)p.getMFG());
	            productPstmt.setDate(5, (Date)p.getEXP());
	            int rows = productPstmt.executeUpdate();
	            if(rows > 0 ) {
	            	String productcatalogproductString = "INSERT INTO productcatalogproducts (CatalogID, ProductID) VALUES (?, ?)";
	            	PreparedStatement pstmtPreparedStatement = conn.prepareStatement(productcatalogproductString);
	            	pstmtPreparedStatement.setInt(1, s.getSupplierID());
	            	pstmtPreparedStatement.setInt(2, p.getProductID());
	            	rows = pstmtPreparedStatement.executeUpdate();
	            	if(rows>0) {
	            		return true;
	            	}
	            	else {
	            		return false;
	            	}
	            }
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	

}
