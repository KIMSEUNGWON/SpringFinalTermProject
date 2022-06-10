package hello.finalterm.pos.stock.repository;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.repository.ProductRepository;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.entity.Stock;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class StockRepositoryTest {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));

        Stock stock =
                new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId());

        //when
        Stock savedStock = stockRepository.save(stock);

        //then
        assertThat(savedStock.getStockQuantity()).isEqualTo(stock.getStockQuantity());
        assertThat(savedStock.getStockDateTime()).isEqualTo(stock.getStockDateTime());
        assertThat(savedStock.getProduct_id()).isEqualTo(product.getId());
        assertThat(savedStock.getUser_id()).isEqualTo(user.getId());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));
        }

        //when
        List<Stock> stocks = stockRepository.findAll();

        //then
        assertThat(stocks.size()).isEqualTo(5);
        assertThat(stocks)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(10));
        assertThat(stocks)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 20, 50, 10)));
        assertThat(stocks)
                .extracting("product_id")
                .containsAll(Collections.singleton(product.getId()));
        assertThat(stocks)
                .extracting("user_id")
                .containsAll(Collections.singleton(user.getId()));
    }

    @Test
    public void findAllJoinProductTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        }

        stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));
        stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        //when
        List<StockDto> stocks = stockRepository.findAllJoinProduct();

        //then
        for (StockDto stock : stocks) {
            log.info("stock={}", stock);
        }
        assertThat(stocks.size()).isEqualTo(7);
        assertThat(stocks)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(10));
        assertThat(stocks)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 20, 50, 10)));
        assertThat(stocks)
                .extracting("productCode")
                .containsAll(Collections.singleton(product.getCode()));
        assertThat(stocks)
                .extracting("productId")
                .containsAll(Collections.singleton(product.getId()));
        assertThat(stocks)
                .extracting("userId")
                .containsAll(Collections.singleton(user.getId()));
        assertThat(stocks)
                .extracting("productName")
                .containsAll(Collections.singleton(product.getName()));
        assertThat(stocks)
                .extracting("userName")
                .containsAll(Collections.singleton(user.getName()));
    }


    @Test
    public void findAllHadInTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        }

        stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));
        stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        //when
        List<Stock> stocks = stockRepository.findAllHadIn();

        //then
        assertThat(stocks.size()).isEqualTo(7);
        assertThat(stocks)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(10));
        assertThat(stocks)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 20, 50, 10)));
        assertThat(stocks)
                .extracting("product_id")
                .containsAll(Collections.singleton(product.getId()));
        assertThat(stocks)
                .extracting("user_id")
                .containsAll(Collections.singleton(user.getId()));
    }

    @Test
    public void findAllOutedTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        }

        stockRepository.save(new Stock(-10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));
        stockRepository.save(new Stock(-10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product.getId(), user.getId()));

        //when
        List<Stock> stocks = stockRepository.findAllOuted();

        //then
        assertThat(stocks.size()).isEqualTo(2);
        assertThat(stocks)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(-10));
        assertThat(stocks)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 20, 50, 10)));
        assertThat(stocks)
                .extracting("product_id")
                .containsAll(Collections.singleton(product.getId()));
        assertThat(stocks)
                .extracting("user_id")
                .containsAll(Collections.singleton(user.getId()));
    }

    @Test
    public void findAllByProductIdTest() throws Exception {
        //given
        User user = userRepository.findByEmail("a@a.com").orElseThrow(() -> new RuntimeException(User.class.getSimpleName()));
        Product product1 = productRepository.findByCode("p1").orElseThrow(() -> new RuntimeException(Product.class.getSimpleName()));
        Product product2 = productRepository.save(new Product("p2", 20, "product2"));
        Product product3 = productRepository.save(new Product("p3", 30, "product3"));

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product1.getId(), user.getId()));
            stockRepository.save(new Stock(20, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product2.getId(), user.getId()));
            stockRepository.save(new Stock(30, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product3.getId(), user.getId()));
        }

        for (int i = 0; i < 5; i++) {
            stockRepository.save(new Stock(-10, LocalDateTime.of(2022, 5, 1, 20, 50, 10), product1.getId(), user.getId()));
        }

        //when
        List<Stock> stocks = stockRepository.findAllByProductId(product1.getId());

        //then
        assertThat(stocks.size()).isEqualTo(10);
        assertThat(stocks)
                .extracting("stockQuantity")
                .containsAll(Collections.singleton(10));
        assertThat(stocks)
                .extracting("stockDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 5, 1, 20, 50, 10)));
        assertThat(stocks)
                .extracting("product_id")
                .containsAll(Collections.singleton(product1.getId()));
        assertThat(stocks)
                .extracting("user_id")
                .containsAll(Collections.singleton(user.getId()));
    }
}