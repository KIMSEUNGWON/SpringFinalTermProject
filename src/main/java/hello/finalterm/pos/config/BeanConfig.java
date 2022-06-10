package hello.finalterm.pos.config;

import hello.finalterm.pos.product.repository.ProductRepository;
import hello.finalterm.pos.product.service.ProductService;
import hello.finalterm.pos.sale.repository.SaleRepository;
import hello.finalterm.pos.sale.service.SaleService;
import hello.finalterm.pos.stat.service.StatService;
import hello.finalterm.pos.stock.repository.StockRepository;
import hello.finalterm.pos.stock.service.StockService;
import hello.finalterm.pos.user.repository.UserRepository;
import hello.finalterm.pos.user.service.UserService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8&serverTimezone=Asia/Seoul");
        ds.setUsername("root");
        ds.setPassword("1234");

        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        return ds;
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(dataSource());
    }

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository());
    }

    @Bean
    public StockRepository stockRepository() {
        return new StockRepository(dataSource());
    }

    @Bean
    public StockService stockService() {
        return new StockService(stockRepository(), productService(), userService());
    }

    @Bean
    public SaleRepository saleRepository() {
        return new SaleRepository(dataSource());
    }

    @Bean
    public SaleService saleService() {
        return new SaleService(saleRepository(), stockService(), productService(), userService());
    }

    @Bean
    public StatService statService() {
        return new StatService(saleService());
    }
}
