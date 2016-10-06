package me.gking2224.budgetms;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import me.gking2224.budgetms.db.DatabaseConfiguration;
import me.gking2224.budgetms.db.EmbeddedDatabaseConfiguration;
import me.gking2224.common.test.TestConfiguration;

@ComponentScan({"me.gking2224.budgetms.model", "me.gking2224.budgetms.service"})
@Import({DatabaseConfiguration.class, EmbeddedDatabaseConfiguration.class, TestConfiguration.class})
public class BudgetsTestConfiguration {

}
