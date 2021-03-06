package me.gking2224.budgetms.db.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={"me.gking2224.budgetms"}, entityManagerFactoryRef="entityManagerFactory")
public class JpaConfiguration {
    
    @Bean("jpaModelBase") String jpaModelBase() {
        return "me.gking2224.budgetms.model";
    }
}
