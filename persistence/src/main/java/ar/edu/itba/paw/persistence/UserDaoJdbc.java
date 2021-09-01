package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<User> ROW_MAPPER =
            (resultSet, rowNum) -> new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("email"),
                    resultSet.getString("password"));

    @Autowired
    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("users")
                .usingGeneratedKeyColumns("userId");

    }

    @Override
    public User get(Integer id) {
        return null;
    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public Optional<User> findById(long id) {

        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE userId = ?", new Object[]{id}, ROW_MAPPER);

        return users.stream().findFirst();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User register(String email, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        int userId = jdbcInsert.execute(data);

        return new User(userId, email, password);
    }
}
