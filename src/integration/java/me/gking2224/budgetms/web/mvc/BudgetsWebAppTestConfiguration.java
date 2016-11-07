package me.gking2224.budgetms.web.mvc;


import org.springframework.context.annotation.Import;

import me.gking2224.budgetms.BudgetsTestConfiguration;
import me.gking2224.common.test.WebAppTestConfigurer;

@Import({BudgetsTestConfiguration.class})
@org.springframework.test.context.web.WebAppConfiguration
public class BudgetsWebAppTestConfiguration extends WebAppTestConfigurer {
}
