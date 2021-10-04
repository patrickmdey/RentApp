package ar.edu.itba.paw;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence" })
@Configuration
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource db = new SimpleDriverDataSource();

        db.setDriverClass(JDBCDriver.class);
        db.setUrl("jdbc:hsqldb:mem:paw");
        db.setUsername("ha");
        db.setPassword("");

        return db;
    }

}
