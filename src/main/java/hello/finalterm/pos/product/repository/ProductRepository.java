package hello.finalterm.pos.product.repository;

import hello.finalterm.pos.product.entity.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Product> rowMapper = (rs, rowNum) ->
            new Product(
                    rs.getLong("ID"),
                    rs.getString("CODE"),
                    rs.getInt("PRICE"),
                    rs.getString("NAME")
            );

    public Product save(Product product) {
        String sql = "insert into PRODUCT(CODE, PRICE, NAME) values(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, product.getCode());
                    ps.setInt(2, product.getPrice());
                    ps.setString(3, product.getName());
                    return ps;
                }, keyHolder);

        return new Product(keyHolder.getKey().longValue(), product.getCode(), product.getPrice(), product.getName());
    }

    public List<Product> findAll() {
        String sql = "select * from PRODUCT";

        List<Product> result = jdbcTemplate.query(sql,rowMapper);
        return result;
    }

    public Optional<Product> findByCode(String code) {
        String sql = "select * from PRODUCT where CODE = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, code));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Product> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from PRODUCT where ID = ?", rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
