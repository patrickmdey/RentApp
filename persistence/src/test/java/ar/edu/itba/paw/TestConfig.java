package ar.edu.itba.paw;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence" })
@Configuration
public class TestConfig {

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Value("classpath:hsqldb.sql")
    private Resource hsqldb;

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource db = new SimpleDriverDataSource();

        db.setDriverClass(JDBCDriver.class);
        db.setUrl("jdbc:hsqldb:mem:paw");
        db.setUsername("ha");
        db.setPassword("");

        return db;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(hsqldb);
        dbp.addScript(schemaSql);
        return dbp;
    }


}
