package hello.finalterm.pos.stock.dto;

import hello.finalterm.pos.stock.entity.Stock;

import java.time.LocalDateTime;
import java.util.Objects;

public class StockDto {

    private Long id;

    private Integer stockQuantity; // 입고 제품 수량

    private LocalDateTime stockDateTime; // 제품 입고 날짜

    private String productCode; // 입고 제폼 코드

    private Long productId;

    private Long userId;

    private String productName; // 입고한 제품 이름

    private String userName; // 입고한 직원 이름

    public static StockDto stockToDto(Stock stock) {
        return new StockDto(stock);
    }

    public StockDto(Stock stock) {
        this.id = stock.getId();
        this.stockQuantity = stock.getStockQuantity();
        this.stockDateTime = stock.getStockDateTime();
        this.productId = stock.getProduct_id();
        this.userId = stock.getUser_id();
    }

    public StockDto(Long id, Integer stockQuantity, LocalDateTime stockDateTime, String productCode, Long productId, String productName) {
        this.id = id;
        this.stockQuantity = stockQuantity;
        this.stockDateTime = stockDateTime;
        this.productCode = productCode;
        this.productId = productId;
        this.productName = productName;
    }

    public StockDto(Long id, Integer stockQuantity, LocalDateTime stockDateTime, String productCode, Long productId, Long userId, String productName, String userName) {
        this.id = id;
        this.stockQuantity = stockQuantity;
        this.stockDateTime = stockDateTime;
        this.productCode = productCode;
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDto stockDto = (StockDto) o;
        return Objects.equals(getProductCode(), stockDto.getProductCode()) && Objects.equals(getProductId(), stockDto.getProductId()) && Objects.equals(getProductName(), stockDto.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getProductId(), getProductName());
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "id=" + id +
                ", stockQuantity=" + stockQuantity +
                ", stockDateTime=" + stockDateTime +
                ", productCode='" + productCode + '\'' +
                ", productId=" + productId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}