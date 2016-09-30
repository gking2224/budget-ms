package me.gking2224.budgetms.db.jpa;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Configuration
@EnableJpaRepositories(basePackages={"me.gking2224.budgetms"}, entityManagerFactoryRef="entityManagerFactory")
public class JpaConfiguration {
    
    @Autowired(required=true)
    protected DataSource dataSource;
    
    @Autowired(required=true) @Qualifier("hibernateProperties")
    protected Properties hibernateProperties;
    
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(getDataSource());
        emfb.setPackagesToScan("me.gking2224.budgetms");
        emfb.setJpaProperties(hibernateProperties);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(vendorAdapter);
        emfb.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        return emfb;
    }

    @Bean
    public TransactionTemplate getTransactionTemplate(PlatformTransactionManager ptm) {
        TransactionTemplate tt = new TransactionTemplate();
        tt.setTransactionManager(ptm);
        return tt;
    }
    
    @Bean(name="transactionManager")
    public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpatm = new JpaTransactionManager();
        jpatm.setEntityManagerFactory(emf);
        jpatm.setDataSource(dataSource);
        jpatm.setJpaDialect(new HibernateJpaDialect());
        return jpatm;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
