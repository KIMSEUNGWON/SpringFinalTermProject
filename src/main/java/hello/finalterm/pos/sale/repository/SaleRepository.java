package hello.finalterm.pos.sale.repository;

import hello.finalterm.pos.sale.dto.SaleDto;
import hello.finalterm.pos.sale.entity.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

public class SaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public SaleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Sale> rowMapper = (rs, rowNum) ->
            new Sale(
                    rs.getLong("ID"),
                    rs.getInt("SALEQUANTITY"),
                    rs.getTimestamp("SALEDATETIME").toLocalDateTime(),
                    rs.getLong("PRODUCTID"),
                    rs.getLong("USERID")
            );

    private final RowMapper<SaleDto> rowMapperDto = (rs, rowNum) ->
            new SaleDto(
                    rs.getLong("S.ID"),
                    rs.getInt("S.SALEQUANTITY"),
                    rs.getTimestamp("S.SALEDATETIME").toLocalDateTime(),
                    rs.getLong("P.ID"),
                    rs.getLong("U.ID"),
                    rs.getString("P.NAME"),
                    rs.getInt("P.PRICE"),
                    rs.getString("U.NAME")
            );

    public Sale save(Sale sale) {
        String sql = "insert into Sale(SALEQUANTITY, SALEDATETIME, PRODUCTID, USERID) values(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, sale.getSaleQuantity());
                    ps.setTimestamp(2, Timestamp.valueOf(sale.getSaleDateTime()));
                    ps.setLong(3, sale.getProduct_id());
                    ps.setLong(4, sale.getUser_id());
                    return ps;
                }, keyHolder);

        return new Sale(keyHolder.getKey().longValue(), sale.getSaleQuantity(), sale.getSaleDateTime(), sale.getProduct_id(), sale.getUser_id());
    }

    public List<Sale> findAll() {
        String sql = "select * from SALE";

        List<Sale> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    public List<Sale> findAllByProductId(Long productId) {
        String sql = "select * from SALE where PRODUCTID = ?";

        List<Sale> result = jdbcTemplate.query(sql, rowMapper, productId);
        return result;
    }

    public List<SaleDto> findAllJoinProduct() {
        String sql = "select S.*, P.*, U.* from SALE as S JOIN PRODUCT as P ON S.PRODUCTID = P.ID LEFT JOIN USER as U ON S.USERID = U.ID";

        List<SaleDto> result = jdbcTemplate.query(sql, rowMapperDto);
        return result;
    }

}