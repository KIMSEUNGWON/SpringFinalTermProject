package hello.finalterm.pos.sale.service;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.repository.ProductRepository;
import hello.finalterm.pos.sale.dto.SaleCreateDto;
import hello.finalterm.pos.sale.dto.SaleDto;
import hello.finalterm.pos.sale.entity.Sale;
import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.dto.StockInDto;
import hello.finalterm.pos.stock.service.StockService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Slf4j
class SaleServiceTest {

    @Autowired
    SaleService saleService;

    @Autowired
    StockService stockService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    User user1;
    Product product1, product2;
    StockDto stockProduct1, stockProduct2;

    @BeforeEach
    public void init() {
        user1 = userRepository.findByEmail("a@a.com").orElseThrow(RuntimeException::new);
        product1 = productRepository.findByCode("p1").orElseThrow(RuntimeException::new);
        stockProduct1 = stockService.inStock(user1.getId(), new StockInDto(10, LocalDateTime.of(2022, 5, 1, 21, 10, 10), product1.getId()));
        product2 = productRepository.save(new Product("p2", 2000, "product2"));
//        stockProduct2 = stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));
    }

    @Test
    public void saleProductTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto =
                new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode());

        //when
        SaleDto saleDto = saleService.saleProduct(user1.getId(), saleCreateDto);

        //then
        assertThat(saleDto.getSaleQuantity()).isEqualTo(saleCreateDto.getSaleQuantity());
        assertThat(saleDto.getSaleDateTime()).isEqualTo(saleCreateDto.getSaleDateTime());
        assertThat(saleDto.getProductId()).isEqualTo(product1.getId());
        assertThat(saleDto.getUserId()).isEqualTo(user1.getId());
        assertThat(saleDto.getProductName()).isEqualTo(product1.getName());
        assertThat(saleDto.getUserName()).isEqualTo(user1.getName());
    }

    @Test
    public void saleProductUserNotExistTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto =
                new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode());

        //when
        //then
        assertThatThrownBy(() -> saleService.saleProduct(user1.getId() + 100L, saleCreateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록되지 않은 유저입니다.");
    }

    @Test
    public void saleProductProductNotExistTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto =
                new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode() + " fail");

        //when
        //then
        assertThatThrownBy(() -> saleService.saleProduct(user1.getId(), saleCreateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록되지 않은 제품입니다.");
    }

    @Test
    public void saleProductProductNotStockTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto =
                new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product2.getCode());

        //when
        //then
        assertThatThrownBy(() -> saleService.saleProduct(user1.getId(), saleCreateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("제품이 입고되지 않았습니다.");
    }

    @Test
    public void saleProductProductStockNotEnoughTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto =
                new SaleCreateDto(20, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode());

        //when
        //then
        assertThatThrownBy(() -> saleService.saleProduct(user1.getId(), saleCreateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("제품 재고가 충분하지 않습니다.");
    }

    @Test
    public void findAllSaleByProductIdTest() throws Exception {
        //given
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));

        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product2.getCode()));

        //when
        List<Sale> allSaleByProductId = saleService.findAllSaleByProductId(product1.getId());

        //then
        assertThat(allSaleByProductId.size()).isEqualTo(2);
        assertThat(allSaleByProductId)
                .extracting("saleQuantity")
                .containsAll(Collections.singleton(1));
        assertThat(allSaleByProductId)
                .extracting("saleDateTime")
                .containsAll(Collections.singleton(LocalDateTime.of(2022, 6, 1, 21, 10, 10)));
        assertThat(allSaleByProductId)
                .extracting("product_id")
                .containsAll(Collections.singleton(product1.getId()));
        assertThat(allSaleByProductId)
                .extracting("user_id")
                .containsAll(Collections.singleton(user1.getId()));
    }

    @Test
    public void findAllSaleByProductIdEmptyTest() throws Exception {
        //given
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));

        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product2.getCode()));

        //when
        List<Sale> allSaleByProductId = saleService.findAllSaleByProductId(product1.getId() + 100L);

        //then
        assertThat(allSaleByProductId.size()).isEqualTo(0);
    }

    @Test
    public void findSaleListJoinProductTest() throws Exception {
        //given
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));

        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product2.getCode()));

        //when
        List<SaleDto> allSaleDto = saleService.findSaleListJoinProduct();

        //then
        assertThat(allSaleDto.size()).isEqualTo(3);
        assertThat(allSaleDto)
                .extracting("saleQuantity")
                .contains(1);
        assertThat(allSaleDto)
                .extracting("saleDateTime")
                .contains(LocalDateTime.of(2022, 6, 1, 21, 10, 10));
        assertThat(allSaleDto)
                .extracting("productId")
                .contains(product1.getId(), product2.getId());
        assertThat(allSaleDto)
                .extracting("userId")
                .containsAll(Collections.singleton(user1.getId()));
        assertThat(allSaleDto)
                .extracting("productName")
                .contains(product1.getName(), product2.getName());
        assertThat(allSaleDto)
                .extracting("productPrice")
                .contains(product1.getPrice(), product2.getPrice());
        assertThat(allSaleDto)
                .extracting("userName")
                .containsAll(Collections.singleton(user1.getName()));
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product1.getCode()));

        stockService.inStock(user1.getId(), new StockInDto(20, LocalDateTime.of(2022, 4, 1, 21, 10, 10), product2.getId()));
        saleService.saleProduct(user1.getId(), new SaleCreateDto(1, LocalDateTime.of(2022, 6, 1, 21, 10, 10), product2.getCode()));

        //when
        List<Sale> allSale = saleService.findAllSale();

        //then
        assertThat(allSale.size()).isEqualTo(3);
        assertThat(allSale)
                .extracting("saleQuantity")
                .contains(1);
        assertThat(allSale)
                .extracting("saleDateTime")
                .contains(LocalDateTime.of(2022, 6, 1, 21, 10, 10));
        assertThat(allSale)
                .extracting("product_id")
                .contains(product1.getId(), product2.getId());
        assertThat(allSale)
                .extracting("user_id")
                .containsAll(Collections.singleton(user1.getId()));
    }
}