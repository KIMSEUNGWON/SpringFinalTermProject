package hello.finalterm.pos.product.dto;

public class ProductCreateDto {

    private String code;

    private Integer price;

    private String name;

    public ProductCreateDto(String code, Integer price, String name) {
        this.code = code;
        this.price = price;
        this.name = name;
    }

    public ProductCreateDto() {
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