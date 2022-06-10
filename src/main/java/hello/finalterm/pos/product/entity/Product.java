package hello.finalterm.pos.product.entity;

public class Product {

    private Long id;

    private String code; // 제품 코드

    private int price; // 제품 가격

    private String name; // 제품 이름

    public Product(String code, int price, String name) {
        this.code = code;
        this.price = price;
        this.name = name;
    }

    public Product(Long id, String code, int price, String name) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.name = name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
