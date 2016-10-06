package me.gking2224.budgetms.db.jpa;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;

import me.gking2224.common.db.EntityManagerFactoryBeanBuilder;

@Configuration
@EnableJpaRepositories(basePackages={"me.gking2224.budgetms"}, entityManagerFactoryRef="entityManagerFactory")
public class JpaConfiguration {
    
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(
            @Qualifier("hibernateProperties") Properties hibernateProperties,
            DataSource dataSource,
            JpaVendorAdapter vendorAdapter
    ) {
        return new EntityManagerFactoryBeanBuilder()
                .packages("me.gking2224.budgetms.model")
                .properties(hibernateProperties)
                .dataSource(dataSource)
                .vendorAdapter(vendorAdapter)
                .build();
    }
    
    @Bean(name="transactionManager")
    public PlatformTransactionManager getTransactionManager(
            EntityManagerFactory emf,
            DataSource dataSource,
            JpaDialect jpaDialect
    ) {
        JpaTransactionManager jpatm = new JpaTransactionManager();
        jpatm.setEntityManagerFactory(emf);
        jpatm.setDataSource(dataSource);
        jpatm.setJpaDialect(new HibernateJpaDialect());
        return jpatm;
    }
}
