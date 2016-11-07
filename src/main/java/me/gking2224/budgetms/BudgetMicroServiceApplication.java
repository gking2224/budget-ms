package me.gking2224.budgetms;


import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import me.gking2224.budgetms.db.DatabaseConfiguration;
import me.gking2224.budgetms.db.EmbeddedDatabaseConfiguration;
import me.gking2224.budgetms.jms.MessagingConfiguration;
import me.gking2224.budgetms.security.SecurityConfiguration;
import me.gking2224.budgetms.web.WebAppConfiguration;
import me.gking2224.common.AbstractMicroServiceApplication;
import me.gking2224.common.CommonConfiguration;

@Configuration
@ComponentScan(basePackages={"me.gking2224.budgetms.service", "me.gking2224.budgetms.model"})
@Import({
    CommonConfiguration.class,
    WebAppConfiguration.class,
    DatabaseConfiguration.class, EmbeddedDatabaseConfiguration.class,
    SecurityConfiguration.class,
    MessagingConfiguration.class
})
public class BudgetMicroServiceApplication extends AbstractMicroServiceApplication {

    public static void main(String[] args) throws ParseException {
        SpringApplication app = new Builder(args)
                .appPrefix("budgetms")
                .applicationClass(BudgetMicroServiceApplication.class)
                .build();
        app.run(args);
    }
}
