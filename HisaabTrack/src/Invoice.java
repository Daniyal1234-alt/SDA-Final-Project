import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {
    // Attributes
    private int invoiceID;
    private int createdBy; //inventory manager ID
    private Date createdOn;
    private List<Product> products;
    private List<Integer> amount;
    private boolean deliveryStatus;
    private boolean paymentStatus;
    private String creatorType;
    public String getCreatorType() {
		return creatorType;
	}

	public void setCreatorType(String creatorType) {
		this.creatorType = creatorType;
	}

	// Constructor
    public Invoice() {
        products = new ArrayList<>();
        amount = new ArrayList<>();
        deliveryStatus = false;
        paymentStatus = false;
        createdOn = new Date();
        creatorType = "\0";
    }

    // Method Signatures
    public Invoice generateInvoice(List<Product> P) {
        return null; // Placeholder for implementation
    }

    public List<Integer> getAmount() {
        return amount;
    }
    
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isDelivered() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public boolean isPaidFor() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
