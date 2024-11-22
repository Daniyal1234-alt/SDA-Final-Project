import java.util.Date;

public class Stock {
    // Attributes
	int StockID;
    private Product product;
    private int stockID;
    private int quantity;
    private double totalCost;
    private Date arrivalDate;

    // Constructor
    public Stock() {
        arrivalDate = new Date();
    }
    public Stock(int ID, Product product, int quantity, double totalCost, Date arrivalDate) {
        this.StockID = ID;
    	this.product = product;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.arrivalDate = arrivalDate;
    }


    // Method Signatures
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
