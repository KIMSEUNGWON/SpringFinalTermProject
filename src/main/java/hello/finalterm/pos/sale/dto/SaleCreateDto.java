package hello.finalterm.pos.sale.dto;

import java.time.LocalDateTime;

public class SaleCreateDto {

    private Integer saleQuantity; // 제품 판매 수량

    private LocalDateTime saleDateTime; // 제품 판매 날짜

    private String productCode; // 판마할 제폼 Code

    public SaleCreateDto(Integer saleQuantity, LocalDateTime saleDateTime, String productCode) {
        this.saleQuantity = saleQuantity;
        this.saleDateTime = saleDateTime;
        this.productCode = productCode;
    }

    public SaleCreateDto() {
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}