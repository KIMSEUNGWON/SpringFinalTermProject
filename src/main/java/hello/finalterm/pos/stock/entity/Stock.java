package hello.finalterm.pos.stock.entity;

import java.time.LocalDateTime;

public class Stock {

    private Long id;

    private Integer stockQuantity;

    private LocalDateTime stockDateTime;

    // 외래키
    private Long product_id;

    private Long user_id;

    public Stock(int stockQuantity, LocalDateTime stockDateTime, Long product_id, Long user_id) {
        this.stockQuantity = stockQuantity;
        this.stockDateTime = stockDateTime;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public Stock(Long id, int stockQuantity, LocalDateTime stockDateTime, Long product_id, Long user_id) {
        this.id = id;
        this.stockQuantity = stockQuantity;
        this.stockDateTime = stockDateTime;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getStockDateTime() {
        return stockDateTime;
    }

    public void setStockDateTime(LocalDateTime stockDateTime) {
        this.stockDateTime = stockDateTime;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockQuantity=" + stockQuantity +
                ", stockDateTime=" + stockDateTime +
                ", product_id=" + product_id +
                ", user_id=" + user_id +
                '}';
    }
}
