package me.gking2224.budgetms.dao;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jmx.export.annotation.ManagedResource;

@Configuration()
//@ManagedResource(
//        log=true,
//        logFile="jmx.log",
//        currencyTimeLimit=15,
//        persistPolicy="OnUpdate",
//        persistPeriod=200,
//        persistLocation="foo",
//        persistName="bar")
public class DatabaseConfiguration {

    @Value("${jdbc.driverClassName}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;
    
    @Value("${jdbc.username}")
    private String jdbcUsername;
    
    @Value("${jdbc.password}")
    private String jdbcPassword;
    
    @Value("${databaseName}")
    private String databaseName;
    
    @Value("${autoCommit}")
    private boolean autoCommit;
    
    @Value("${initSql}")
    private String initSql;
    
    @Value("${dbcpInitialSize}")
    private int dbcpInitialSize;
    
    @Autowired @Qualifier("datasourceProperties")
    private Properties datasourceProperties;

    @Bean(name="datasourceProperties")
    public Properties getDatasourceProperties() throws IOException {
        Properties dsProps = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/datasource.properties"));
        
        return dsProps;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jt = new JdbcTemplate(getDataSource());
        return jt;
    }
//    @Bean(name="transactionManager")
//    public DataSourceTransactionManager getTransactionManager() {
//        DataSourceTransactionManager tm = new DataSourceTransactionManager();
//        tm.setDataSource(getDataSource());
//        return tm;
//    }
    
    @Bean(name="dataSource")
    public DataSource getDataSource() {

//        BasicDataSource ds = new BasicDataSource();
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setConnectionProperties(datasourceProperties);
        ds.setUsername(jdbcUsername);
        ds.setDriverClassName(jdbcDriver);
        ds.setUrl(jdbcUrl);
//        ds.setDefaultAutoCommit(autoCommit);
//        ds.setConnectionInitSqls(Arrays.asList(initSql));
//        ds.setEnableAutoCommitOnReturn(autoCommit);
//        ds.setInitialSize(dbcpInitialSize);
        ds.setPassword(jdbcPassword);
        return ds;
    }
    protected String getJdbcUrl() {
        System.out.println("get url (superclass): "+jdbcUrl);
        return jdbcUrl;
    }
    protected void setJdbcUrl(String jdbcUrl) {
        System.out.println("set url (superclass): "+jdbcUrl);
        this.jdbcUrl = jdbcUrl;
    }
    public boolean getAutoCommit() {
        return autoCommit;
    }

//    @ManagedAttribute(description="jdbcDriver", currencyTimeLimit=15)
    public String getJdbcDriver() {
        return jdbcDriver;
    }

//    @ManagedAttribute(description="jdbcUsername", currencyTimeLimit=15)
    public String getJdbcUsername() {
        return jdbcUsername;
    }

//    @ManagedAttribute(description="jdbcPassword", currencyTimeLimit=15)
    public String getJdbcPassword() {
        return jdbcPassword;
    }

//    @ManagedAttribute(description="databaseName", currencyTimeLimit=15)
    public String getDatabaseName() {
        return databaseName;
    }
    
    
}
