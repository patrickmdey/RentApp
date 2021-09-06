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
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("location"),
                    resultSet.getInt("type"));

    @Autowired
    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("account")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> get(long id) {
        return null;
    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public Optional<User> findById(long id) {

        List<User> users = jdbcTemplate.query("SELECT * FROM account WHERE id = ?", new Object[]{id}, ROW_MAPPER);

        return users.stream().findFirst();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> register(String email, String password, String firstName, String lastName, String location, int type) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("location", location);
        data.put("type", type);
        int userId = jdbcInsert.execute(data);

        return Optional.of(new User(userId, email, password, firstName, lastName, location, type));
    }
}
