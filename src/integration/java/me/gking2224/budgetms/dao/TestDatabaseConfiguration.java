package me.gking2224.budgetms.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

//@Configuration
//@Profile("xxx")
class TestDatabaseConfiguration extends DatabaseConfiguration {

    @Value("${jdbc.driverClassName}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;
    
    @Value("${jdbc.username}")
    private String jdbcUsername;
    
    @Value("${jdbc.password}")
    private String jdbcPassword;
    
    /**
     * Property placeholder configurer needed to process @Value annotations
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
       PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
       p.setLocation(new ClassPathResource("test-database.properties"));
       return p;
    }
    
    @Bean(name="dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        System.out.println("Creating database with url: "+getJdbcUrl());
        ds.setUrl(getJdbcUrl());
        ds.setUsername(jdbcUsername);
        ds.setDriverClassName(jdbcDriver);
        ds.setPassword(jdbcPassword);
        return ds;
    }
    
    protected String getJdbcUrl() {
        System.out.println("get url (subclass): "+jdbcUrl);
        return jdbcUrl;
    }
    protected void setJdbcUrl(String jdbcUrl) {
        System.out.println("set url (subclass): "+jdbcUrl);
        this.jdbcUrl = jdbcUrl;
    }
}
