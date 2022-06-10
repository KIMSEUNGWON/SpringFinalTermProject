package hello.finalterm.pos.stock.service;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.repository.ProductRepository;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.dto.StockInDto;
import hello.finalterm.pos.stock.entity.Stock;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    User user1;
    Product product1, product2;

    @BeforeEach
    public void init() {
        user1 = userRepository.findByEmail("a@a.com").orElseThrow(RuntimeException::new);
        product1 = productRepository.findByCode("p1").orElseThrow(RuntimeException::new);
        product2 = productRepository.save(new Product("p2", 2000, "product2"));
    }

    @Test
    public void inStockTest() throws Exception {
        //given
        StockInDto stockInDto =
                new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId());

        //when
        StockDto stockDto = stockService.inStock(user1.getId(), stockInDto);

        //then
        assertThat(stockDto.getStockQuantity()).isEqualTo(stockInDto.getStockQuantity());
        assertThat(stockDto.getStockDateTime()).isEqualTo(stockInDto.getStockDateTime());
        assertThat(stockDto.getProductCode()).isEqualTo(product1.getCode());
        assertThat(stockDto.getProductId()).isEqualTo(product1.getId());
        assertThat(stockDto.getUserId()).isEqualTo(user1.getId());
        assertThat(stockDto.getProductName()).isEqualTo(product1.getName());
        assertThat(stockDto.getUserName()).isEqualTo(user1.getName());
    }

    @Test
    public void inStockUserNotExistFailTest() throws Exception {
        //given
        StockInDto stockInDto =
                new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId());

        //when
        //then
        assertThatThrownBy(() -> stockService.inStock(user1.getId() + 100L, stockInDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록되지 않은 유저입니다.");
    }

    @Test
    public void inStockProductNotExistFailTest() throws Exception {
        //given
        StockInDto stockInDto =
                new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId() + 100L);

        //when
        //then
        assertThatThrownBy(() -> stockService.inStock(user1.getId(), stockInDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록되지 않은 제품입니다.");
    }

    @Test
    public void isProductInStockTest() throws Exception {
        //given
        StockInDto stockInDto =
                new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId());
        stockService.inStock(user1.getId(), stockInDto);

        //when
        boolean isProductInStock = stockService.isProductInStock(product1.getId());

        //then
        assertThat(isProductInStock).isTrue();
    }

    @Test
    public void isNotProductInStockTest() throws Exception {
        //given
        StockInDto stockInDto =
                new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId());
        stockService.inStock(user1.getId(), stockInDto);

        //when
        boolean isProductInStock = stockService.isProductInStock(product1.getId() + 100L);

        //then
        assertThat(isProductInStock).isFalse();
    }

    @Test
    public void findAllStockByProductIdTest() throws Exception {
        //given
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));

        //when
        List<Stock> allStockByProductId = stockService.findAllStockByProductId(product1.getId());

        //then
        assertThat(allStockByProductId.size()).isEqualTo(2);
        assertThat(allStockByProductId)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(10));
        assertThat(allStockByProductId)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 21, 10, 10)));
        assertThat(allStockByProductId)
                .extracting("product_id")
                .containsAll(Collections.singleton(product1.getId()));
        assertThat(allStockByProductId)
                .extracting("user_id")
                .containsAll(Collections.singleton(user1.getId()));
    }

    @Test
    public void findAllStockByProductIdEmptyTest() throws Exception {
        //given
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));

        //when
        List<Stock> allStockByProductId = stockService.findAllStockByProductId(product1.getId() + 100L);

        //then
        assertThat(allStockByProductId.size()).isEqualTo(0);
    }

    @Test
    public void findAllStockTest() throws Exception {
        //given
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));

        //when
        List<Stock> allStock = stockService.findStockList();

        //then
        assertThat(allStock.size()).isEqualTo(3);
        assertThat(allStock)
                .extracting("stockQuantity")
                .contains(10, 20);
        assertThat(allStock)
                .extracting("stockDateTime")
                .contains(LocalDateTime.of(2022, 5, 1, 21, 10, 10), LocalDateTime.of(2022, 4, 1, 21, 10, 10));
        assertThat(allStock)
                .extracting("product_id")
                .contains(product1.getId(), product2.getId());
        assertThat(allStock)
                .extracting("user_id")
                .contains(user1.getId());
    }

    @Test
    public void getTotalProductStockQuantityTest() throws Exception {
        //given
        StockDto stockDto = stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        StockDto stockDto1 = stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));

        //when
        long totalProductStockQuantity = stockService.getTotalProductStockQuantity(product1.getId());

        //then
        assertThat(totalProductStockQuantity).isEqualTo(stockDto.getStockQuantity() + stockDto1.getStockQuantity());
    }
}