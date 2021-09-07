package ar.edu.itba.paw;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserType;
import ar.edu.itba.paw.persistence.UserDaoJdbc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJdbcTest {

    private static final String EMAIL = "email";

    private static final String PASSWORD = "password";

    private static final String LOCATION = "location";

    private static final String FIRST_NAME = "first name";

    private static final String LAST_NAME = "last name";

    private static final UserType TYPE = UserType.Owner;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDaoJdbc userDaoJdbc;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "account");
    }

    @Test
    public void testRegister() {
        final User user = userDaoJdbc.register(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, LOCATION, TYPE.ordinal()).get();

        Assert.assertNotNull(user);
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
        Assert.assertEquals(LOCATION, user.getLocation());
        Assert.assertEquals(TYPE, user.getType());

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "account"));

    }


}
