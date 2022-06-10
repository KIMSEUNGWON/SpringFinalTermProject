package hello.finalterm.pos.sale.dto;

import hello.finalterm.pos.sale.entity.Sale;

import java.time.LocalDateTime;

public class SaleDto {

    private Long id;

    private Integer saleQuantity; // 제품 판매 수량

    private LocalDateTime saleDateTime; // 제품 판매 날짜

    private Long productId; // 판마한 제폼 ID

    private Long userId; // 제품을 판매한 직원 ID

    private String productName; // 판매한 제품 이름

    private Integer productPrice; // 판매한 제품 가격

    private String userName; // 판매한 직원 이름

    public static SaleDto saleToDto(Sale sale) {
        return new SaleDto(sale);
    }

    private SaleDto(Sale sale) {
        this.id = sale.getId();
        this.saleQuantity = sale.getSaleQuantity();
        this.saleDateTime = sale.getSaleDateTime();
        this.productId = sale.getProduct_id();
        this.userId = sale.getUser_id();
    }

    public SaleDto(Long id, Integer saleQuantity, LocalDateTime saleDateTime, Long productId, Long userId, String productName, Integer productPrice, String userName) {
        this.id = id;
        this.saleQuantity = saleQuantity;
        this.saleDateTime = saleDateTime;
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Integer saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SaleDto{" +
                "id=" + id +
                ", saleQuantity=" + saleQuantity +
                ", saleDateTime=" + saleDateTime +
                ", productId=" + productId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", userName='" + userName + '\'' +
                '}';
    }
}