package hello.finalterm.pos.product.dto;

import hello.finalterm.pos.product.entity.Product;

public class ProductDto {

    private Long id;

    private String code;

    private Integer price;

    private String name;

    public ProductDto(String code, Integer price, String name) {
        this.code = code;
        this.price = price;
        this.name = name;
    }

    public static ProductDto productToDto(Product product) {
        return new ProductDto(product);
    }

    private ProductDto(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.price = product.getPrice();
        this.name = product.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}