package hello.finalterm.pos.product.repository;

import hello.finalterm.pos.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void saveTest() throws Exception {
        //given
        Product product = new Product("p1", 1000, "product1");

        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(savedProduct.getCode()).isEqualTo(product.getCode());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }

    @Test
    public void saveDuplicateFailTest() throws Exception {
        //given
        Product product = new Product("p1", 1000, "product1");

        //when
        productRepository.save(product);

        //then
        assertThatThrownBy(() -> productRepository.save(product))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        Product savedProduct = productRepository.save(new Product("p1", 1000, "product1"));

        //when
        List<Product> productList = productRepository.findAll();

        //then
        for (Product product1 : productList) {
            log.info("product1={}", product1);
        }
        assertThat(productList.size()).isEqualTo(1);
        assertThat(productList)
                .extracting("id")
                .contains(savedProduct.getId());
        assertThat(productList)
                .extracting("code")
                .contains(savedProduct.getCode());
        assertThat(productList)
                .extracting("price")
                .contains(savedProduct.getPrice());
        assertThat(productList)
                .extracting("name")
                .contains(savedProduct.getName());
    }

    @Test
    public void findByCodeTest() throws Exception {
        //given
        Product savedProduct = productRepository.save(new Product("p1", 1000, "product1"));
        String code = savedProduct.getCode();

        //when
        Product findProduct = productRepository.findByCode(code).orElseThrow(() -> new RuntimeException(Product.class.getSimpleName() + "가 존재하지 않습니다"));

        //then
        assertThat(findProduct.getCode()).isEqualTo(savedProduct.getCode());
        assertThat(findProduct.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(findProduct.getName()).isEqualTo(savedProduct.getName());
    }

    @Test
    public void findByCodeCodeNotExistFailTest() throws Exception {
        //given
        Product savedProduct = productRepository.save(new Product("p1", 1000, "product1"));
        String code = savedProduct.getCode() + " fail";

        //when
        //then
        assertThatThrownBy(() -> productRepository.findByCode(code).orElseThrow(() -> new RuntimeException(Product.class.getSimpleName() + "가 존재하지 않습니다")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(Product.class.getSimpleName() + "가 존재하지 않습니다");
    }
}