package me.gking2224.budgetms.web.mvc;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import me.gking2224.budgetms.db.DatabaseConfiguration;
import me.gking2224.budgetms.db.EmbeddedDatabaseConfiguration;
import me.gking2224.budgetms.web.WebAppConfiguration;
import me.gking2224.common.test.TestConfiguration;
import me.gking2224.common.test.WebAppTestConfigurer;

@ComponentScan({"me.gking2224.budgetms.model", "me.gking2224.budgetms.service"})
@Import({WebAppConfiguration.class, DatabaseConfiguration.class, EmbeddedDatabaseConfiguration.class, TestConfiguration.class})
public class BudgetsWebAppTestConfiguration extends WebAppTestConfigurer {
}
