package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.utils.JwtTokenUtil;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableScheduling
@EnableTransactionManagement
@ComponentScan({"ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistence"})
@Configuration
public class WebConfig {

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.postgresql.Driver.class);

        dataSource.setUrl("jdbc:postgresql://localhost:5432/paw"); //LOCAL

        dataSource.setUsername("postgres"); //LOCAL
        dataSource.setPassword("root"); //LOCAL
//
//        dataSource.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2021b-3"); //aca va la dir del servidor
//
//        dataSource.setUsername("paw-2021b-3");
//        dataSource.setPassword("hqpefZ04Y");

        return dataSource;
    }

    @Bean //https://stackoverflow.com/questions/37958145/autowired-gives-null-value-in-custom-constraint-validator
    public ValidatorFactory validatorFactory(final AutowireCapableBeanFactory autowireCapableBeanFactory){
        return Validation.byProvider(HibernateValidator.class).configure()
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();
    }

    @Bean
    public JwtTokenUtil jwtUtil(@Value("classpath:secret") Resource resource) throws IOException {
        return new JwtTokenUtil(resource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);

        return messageSource;
    }
}