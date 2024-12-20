import java.util.Date;

public class Stock {
    // Attributes
	static int totalStock = 0 ;
    private Product product;
    private int stockID;
    private int quantity;
    private double totalCost;
    private Date arrivalDate;

    // Constructor
    public Stock() {
    	totalStock = totalStock + 1;
    	this.stockID = totalStock;
        arrivalDate = new Date();
    }
    public Stock(int ID, Product product, int quantity, double totalCost, Date arrivalDate) {
    	totalStock = totalStock + 1;
    	this.stockID = totalStock;
    	this.product = product;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.arrivalDate = arrivalDate;
        
    }


    // Method Signatures
    @Override
    public String toString() {
        return "Stock ID: " + stockID + "\n" +
            "Product Details: \n" +
            "   Product ID: " + product.getProductID() + "\n" +
            "   Name: " + product.getName() + "\n" +
            "   Description: " + product.getDescription() + "\n" +
            "   Price: $" + product.getPrice() + "\n" +
            "   Manufacturing Date: " + product.getMFG() + "\n" +
            "   Expiry Date: " + product.getEXP() + "\n" +
            "Quantity in Stock: " + quantity + "\n" +
            "Total Cost: $" + totalCost + "\n" +
            "Arrival Date: " + arrivalDate + "\n" +
            "-----------------------------------------";
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
