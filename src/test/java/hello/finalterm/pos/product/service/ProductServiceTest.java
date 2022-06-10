package hello.finalterm.pos.product.service;

import hello.finalterm.pos.product.dto.ProductCreateDto;
import hello.finalterm.pos.product.dto.ProductDto;
import hello.finalterm.pos.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void createProductTest() throws Exception {
        //given
        ProductCreateDto productCreateDto =
                new ProductCreateDto("p1", 1000, "product1");

        //when
        ProductDto productDto = productService.createProduct(productCreateDto);

        //then
        assertThat(productDto.getCode()).isEqualTo(productCreateDto.getCode());
        assertThat(productDto.getPrice()).isEqualTo(productCreateDto.getPrice());
        assertThat(productDto.getName()).isEqualTo(productCreateDto.getName());
    }

    @Test
    public void createProductDuplicateFailTest() throws Exception {
        //given
        ProductCreateDto productCreateDto =
                new ProductCreateDto("p1", 1000, "product1");

        //when
        productService.createProduct(productCreateDto);

        //then
        assertThatThrownBy(() -> productService.createProduct(productCreateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(Product.class.getSimpleName() + "가 이미 등록되어 있습니다.");
    }
}