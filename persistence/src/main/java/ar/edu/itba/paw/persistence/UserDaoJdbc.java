package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<User> ROW_MAPPER =
            (resultSet, rowNum) -> new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    Locations.values()[resultSet.getInt("location")],
                    resultSet.getLong("picture"),
                    UserType.values()[resultSet.getInt("type")]);

    @Autowired
    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("account")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<User> list() {
        return jdbcTemplate.query("SELECT * from account", ROW_MAPPER);
    }

    @Override
    public Optional<User> findById(long id) {

        List<User> users = jdbcTemplate.query("SELECT * FROM account WHERE id = ?", new Object[]{id}, ROW_MAPPER);

        return users.stream().findFirst();
    }

    @Override
    public Optional<User> register(String email, String password, String firstName, String lastName, Locations location, Long img, UserType type) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("location", location.ordinal());
        data.put("picture", img);
        data.put("type", type.ordinal());
        int userId = jdbcInsert.execute(data);

        return Optional.of(new User(userId, email, password, firstName, lastName, location, img, type));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM account WHERE email = ?", new Object[]{email}, ROW_MAPPER);
        return users.stream().findFirst();
    }

    @Override
    public void update(long id, String firstName, String lastName, Locations location, int type) {
        jdbcTemplate.update("UPDATE account\n" +
                "SET first_name = ?,\n" +
                "    last_name  = ?,\n" +
                "    location   = ?,\n" +
                "    type       = ?\n" +
                "WHERE id = ?;", firstName, lastName, location.ordinal(), type, id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE\n" +
                "FROM account\n" +
                "WHERE id = ?;", id);
    }

    @Override
    public void updatePassword(long id, String passwordHash) {
        jdbcTemplate.update("UPDATE account\n" +
                "SET password = ?\n" +
                "where id = ?;", passwordHash, id);
    }
}
