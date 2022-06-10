package hello.finalterm.pos.user.repository;

import hello.finalterm.pos.user.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
            new User(
                    rs.getLong("ID"),
                    rs.getString("EMAIL"),
                    rs.getString("PASSWORD"),
                    rs.getString("NAME"),
                    rs.getString("USERROLE"),
                    rs.getTimestamp("REGISTERDATETIME").toLocalDateTime()
            );

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User save(User user) {
        String sql = "insert into USER(EMAIL, PASSWORD, NAME, USERROLE, REGISTERDATETIME) values(?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getName());
                    ps.setString(4, user.getUserRole());
                    ps.setTimestamp(5, Timestamp.valueOf(user.getRegisterDateTime()));
                    return ps;
                }, keyHolder);

        return new User(keyHolder.getKey().longValue(), user.getEmail(), user.getPassword(), user.getName(), user.getUserRole(), user.getRegisterDateTime());
    }

    public List<User> findAll() {
        List<User> results = jdbcTemplate.query("select * from USER",rowMapper);
        return results;
    }

    public Optional<User> findByEmail(String email) {
        System.out.println("email = " + email);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from USER where EMAIL = ?", rowMapper, email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        System.out.println("id = " + id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from USER where ID = ?", rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
