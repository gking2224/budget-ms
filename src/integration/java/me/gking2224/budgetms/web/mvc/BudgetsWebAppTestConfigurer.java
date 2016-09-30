package me.gking2224.budgetms.web.mvc;


import org.springframework.context.annotation.ComponentScan;

import me.gking2224.common.utils.test.WebAppTestConfigurer;

@ComponentScan({"me.gking2224.budgetms", "me.gking2224.common"})
public class BudgetsWebAppTestConfigurer extends WebAppTestConfigurer {
}
