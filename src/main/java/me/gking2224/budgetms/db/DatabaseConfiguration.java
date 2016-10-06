package me.gking2224.budgetms.db;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan({"me.gking2224.budgetms.db"})
@Profile("!embedded")
@Import(me.gking2224.common.db.CommonDatabaseConfiguration.class)
public class DatabaseConfiguration {

}
