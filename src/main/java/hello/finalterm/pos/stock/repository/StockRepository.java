package hello.finalterm.pos.stock.repository;

import hello.finalterm.pos.stock.dto.StockDto;
import hello.finalterm.pos.stock.entity.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Stock> rowMapper = (rs, rowNum) ->
        new Stock(
                rs.getLong("ID"),
                rs.getInt("STOCKQUANTITY"),
                rs.getTimestamp("STOCKDATETIME").toLocalDateTime(),
                rs.getLong("PRODUCTID"),
                rs.getLong("USERID")
        );

    private final RowMapper<StockDto> rowMapperDto = (rs, rowNum) ->
            new StockDto(
                rs.getLong("S.ID"),
                rs.getInt("S.STOCKQUANTITY"),
                rs.getTimestamp("S.STOCKDATETIME").toLocalDateTime(),
                rs.getString("P.CODE"),
                rs.getLong("P.ID"),
                rs.getLong("U.ID"),
                rs.getString("P.NAME"),
                rs.getString("U.NAME")
            );

    public Stock save(Stock stock) {
        String sql = "insert into STOCK(STOCKQUANTITY, STOCKDATETIME, PRODUCTID, USERID) values(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, stock.getStockQuantity());
                    ps.setTimestamp(2, Timestamp.valueOf(stock.getStockDateTime()));
                    ps.setLong(3, stock.getProduct_id());
                    ps.setLong(4, stock.getUser_id());
                    return ps;
                }, keyHolder);

        return new Stock(keyHolder.getKey().longValue(), stock.getStockQuantity(), stock.getStockDateTime(), stock.getProduct_id(), stock.getUser_id());
    }

    public List<Stock> findAll() {
        String sql = "select * from STOCK";

        List<Stock> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    public List<StockDto> findAllJoinProduct() {
        String sql = "select S.*, P.*, U.* from STOCK as S JOIN PRODUCT as P ON S.PRODUCTID = P.ID LEFT JOIN USER as U ON S.USERID = U.ID";

        List<StockDto> result = jdbcTemplate.query(sql, rowMapperDto);
        return result;
    }

    public List<Stock> findAllHadIn() {
        String sql = "select * from STOCK where STOCKQUANTITY > 0";

        List<Stock> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    public List<Stock> findAllOuted() {
        String sql = "select * from STOCK where STOCKQUANTITY < 0";

        List<Stock> result = jdbcTemplate.query(sql, rowMapper);
        return result;
    }

    public List<Stock> findAllByProductId(Long productId) {
        String sql = "select * from STOCK where PRODUCTID = ?";

        List<Stock> result = jdbcTemplate.query(sql, rowMapper, productId);
        return result;
    }

}