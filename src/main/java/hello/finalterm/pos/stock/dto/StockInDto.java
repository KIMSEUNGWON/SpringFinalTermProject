package hello.finalterm.pos.stock.dto;

import java.time.LocalDateTime;

public class StockInDto {

    private Integer stockQuantity; // 제품 수량

    private LocalDateTime stockDateTime; // 제품 입고 날짜

    private Long productId; // 제폼 ID

    public StockInDto(Integer stockQuantity, LocalDateTime stockDateTime, Long productId) {
        this.stockQuantity = stockQuantity;
        this.stockDateTime = stockDateTime;
        this.productId = productId;
    }

    public StockInDto() {
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getStockDateTime() {
        return stockDateTime;
    }

    public void setStockDateTime(LocalDateTime stockDateTime) {
        this.stockDateTime = stockDateTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}