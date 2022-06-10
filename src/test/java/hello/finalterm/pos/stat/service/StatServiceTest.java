package hello.finalterm.pos.stat.service;

import hello.finalterm.pos.product.entity.Product;
import hello.finalterm.pos.product.repository.ProductRepository;
import hello.finalterm.pos.sale.dto.SaleCreateDto;
import hello.finalterm.pos.sale.service.SaleService;
import hello.finalterm.pos.stat.dto.TopSellingProductNameAndQuantity;
import hello.finalterm.pos.user.entity.User;
import hello.finalterm.pos.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class StatServiceTest {

    @Autowired
    StatService statService;

    @Autowired
    SaleService saleService;

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
        product2 = productRepository.findByCode("p2").orElseThrow(RuntimeException::new);
    }

    @Test
    public void totalSellingCountDateTest() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2022, 3, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 1);

        //when
        Long totalSellingCount = statService.totalSellingCount(startDate, endDate);

        //then
        assertThat(totalSellingCount).isEqualTo(25);
    }

    @Test
    public void totalSellingCountWeekTest() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2022, 3, 1);
        LocalDate endDate = startDate.plusDays(6);

        //when
        Long totalSellingCount = statService.totalSellingCount(startDate, endDate);

        //then
        assertThat(totalSellingCount).isEqualTo(45);
    }

    @Test
    public void totalSellingCountMonthTest() throws Exception {
        //given
        int year = 2022;
        int month = 4;
        YearMonth stand = YearMonth.of(year, month);
        LocalDate startDate = stand.atDay(1);
        LocalDate endDate = stand.atEndOfMonth();

        //when
        Long totalSellingCount = statService.totalSellingCount(startDate, endDate);

        //then
        assertThat(totalSellingCount).isEqualTo(45);
    }

    @Test
    public void totalIncomeDateTest() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2022, 3, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 1);

        //when
        Long totalIncomeDate = statService.totalIncome(startDate, endDate);
        int result = 5 * 1 * product1.getPrice() + 10 * 2 * product2.getPrice();

        //then
        assertThat(totalIncomeDate).isEqualTo(result);
    }

    @Test
    public void totalIncomeWeekTest() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 3, 1);
        LocalDate endDate = startDate.plusDays(6);

        //when
        Long totalIncomeWeek = statService.totalIncome(startDate, endDate);
        int result = 5 * 5 * product1.getPrice() + 10 * 2 * product2.getPrice();

        //then
        assertThat(totalIncomeWeek).isEqualTo(result);
    }

    @Test
    public void totalIncomeMonthTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto = new SaleCreateDto(5, LocalDateTime.of(2022, 4, 10, 23, 10, 10), "p2");
        saleService.saleProduct(user1.getId(), saleCreateDto);

        int year = 2022;
        int month = 4;
        YearMonth stand = YearMonth.of(year, month);
        LocalDate startDate = stand.atDay(1);
        LocalDate endDate = stand.atEndOfMonth();

        //when
        Long totalIncomeWeek = statService.totalIncome(startDate, endDate);
        int result = 5 * 5 * product1.getPrice() + 10 * 2 * product2.getPrice() + saleCreateDto.getSaleQuantity() * product2.getPrice();

        //then
        assertThat(totalIncomeWeek).isEqualTo(result);
    }

    @Test
    public void findTopSellingProductDateTest() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2022, 3, 1);
        LocalDate endDate = LocalDate.of(2022, 3, 1);

        //when
        List<TopSellingProductNameAndQuantity> topSellingProducts = statService.findTopSellingProduct(startDate, endDate);
        topSellingProducts.forEach(topSellingProduct -> log.info("topSellingProduct={}",topSellingProduct));

        //then
        assertThat(topSellingProducts.size()).isEqualTo(1);
        assertThat(topSellingProducts)
                .extracting("productName")
                .contains(product2.getName());
        assertThat(topSellingProducts)
                .extracting("totalSellingQuantity")
                .contains(10 * 2);
    }

    @Test
    public void findTopSellingProductMonthAndDuplicateTest() throws Exception {
        //given
        SaleCreateDto saleCreateDto = new SaleCreateDto(5, LocalDateTime.of(2022, 3, 10, 23, 10, 10), "p2");
        saleService.saleProduct(user1.getId(), saleCreateDto);

        int year = 2022;
        int month = 3;
        YearMonth stand = YearMonth.of(year, month);
        LocalDate startDate = stand.atDay(1);
        LocalDate endDate = stand.atEndOfMonth();

        //when
        List<TopSellingProductNameAndQuantity> topSellingProducts = statService.findTopSellingProduct(startDate, endDate);
        topSellingProducts.forEach(topSellingProduct -> log.info("topSellingProduct={}",topSellingProduct));

        //then
        assertThat(topSellingProducts.size()).isEqualTo(2);
        assertThat(topSellingProducts)
                .extracting("productName")
                .contains(product1.getName(), product2.getName());
        assertThat(topSellingProducts)
                .extracting("totalSellingQuantity")
                .contains(25, 25);
    }

    @Test
    public void findTopSellingProductProductNotSoldTest() throws Exception {
        //given

        int year = 2022;
        int month = 5;
        YearMonth stand = YearMonth.of(year, month);
        LocalDate startDate = stand.atDay(1);
        LocalDate endDate = stand.atEndOfMonth();

        //when
        List<TopSellingProductNameAndQuantity> topSellingProducts = statService.findTopSellingProduct(startDate, endDate);
        topSellingProducts.forEach(topSellingProduct -> log.info("topSellingProduct={}",topSellingProduct));

        //then
        assertThat(topSellingProducts.size()).isEqualTo(1);
        assertThat(topSellingProducts)
                .extracting("productName")
                .contains("없음");
        assertThat(topSellingProducts)
                .extracting("totalSellingQuantity")
                .contains(0);
    }
}