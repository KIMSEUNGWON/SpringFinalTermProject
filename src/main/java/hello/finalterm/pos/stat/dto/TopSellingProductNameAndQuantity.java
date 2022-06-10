package hello.finalterm.pos.stat.dto;

public class TopSellingProductNameAndQuantity {
    private String productName;
    private int totalSellingQuantity;

    public TopSellingProductNameAndQuantity(String productName, int totalSellingQuantity) {
        this.productName = productName;
        this.totalSellingQuantity = totalSellingQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalSellingQuantity() {
        return totalSellingQuantity;
    }

    @Override
    public String toString() {
        return "productName: " + productName + ", totalSellingQuantity: " + totalSellingQuantity;
    }
}
