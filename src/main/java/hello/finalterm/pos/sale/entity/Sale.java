package hello.finalterm.pos.sale.entity;

import java.time.LocalDateTime;

public class Sale {

    private Long id;

    private Integer saleQuantity;

    private LocalDateTime saleDateTime;

    // 외래키
    private Long product_id;

    private Long user_id;

    public Sale(Integer saleQuantity, LocalDateTime saleDateTime, Long product_id, Long user_id) {
        this.saleQuantity = saleQuantity;
        this.saleDateTime = saleDateTime;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public Sale(Long id, Integer saleQuantity, LocalDateTime saleDateTime, Long product_id, Long user_id) {
        this.id = id;
        this.saleQuantity = saleQuantity;
        this.saleDateTime = saleDateTime;
        this.product_id = product_id;
        this.user_id = user_id;
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
        return "Sale{" +
                "id=" + id +
                ", saleQuantity=" + saleQuantity +
                ", saleDateTime=" + saleDateTime +
                ", product_id=" + product_id +
                ", user_id=" + user_id +
                '}';
    }
}
