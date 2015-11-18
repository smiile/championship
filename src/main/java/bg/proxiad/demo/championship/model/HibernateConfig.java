package bg.proxiad.demo.championship.model;

import java.util.Properties;
import static org.hibernate.cfg.AvailableSettings.DEFAULT_BATCH_FETCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.DEFAULT_SCHEMA;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_REFLECTION_OPTIMIZER;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.HSQLDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackageClasses = HibernateConfig.class, useDefaultFilters = false, includeFilters = @Filter(Repository.class))
public class HibernateConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateConfig.class);

    @Value("${database.schema}")
    private String dbSchema;

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public SessionFactory sessionFactory() {

        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());

        builder.scanPackages("bg.proxiad.demo.championship.model").addProperties(getHibernateProperties());

        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put(FORMAT_SQL, "true");
        prop.put(SHOW_SQL, "true");
        prop.put(DIALECT, HSQLDialect.class.getName());
        prop.put(HBM2DDL_AUTO, "drop-create");
        prop.put(USE_SECOND_LEVEL_CACHE, "false");
        prop.put(USE_QUERY_CACHE, "false");
        prop.put(DEFAULT_SCHEMA, dbSchema);
        prop.put(USE_REFLECTION_OPTIMIZER, "false");
        prop.put(DEFAULT_BATCH_FETCH_SIZE, "30");
        return prop;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:hsql://localhost/championshipdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
