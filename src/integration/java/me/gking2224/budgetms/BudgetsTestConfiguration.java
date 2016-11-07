package me.gking2224.budgetms;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import me.gking2224.budgetms.db.DatabaseConfiguration;
import me.gking2224.budgetms.db.EmbeddedDatabaseConfiguration;
import me.gking2224.budgetms.db.jpa.JpaConfiguration;
import me.gking2224.budgetms.web.WebAppConfiguration;
import me.gking2224.common.test.CommonTestConfiguration;

@Import({CommonTestConfiguration.class, DatabaseConfiguration.class, EmbeddedDatabaseConfiguration.class, JpaConfiguration.class, WebAppConfiguration.class})
@ComponentScan({"me.gking2224.budgetms.model", "me.gking2224.budgetms.service"})
public class BudgetsTestConfiguration {

}
