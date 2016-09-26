package me.gking2224.budgetms.controller;

import static me.gking2224.budgetms.controller.BudgetController.BUDGETS;
import static me.gking2224.common.utils.JsonMvcTestHelper.doGet;
import static me.gking2224.common.utils.JsonMvcTestHelper.doPost;
import static me.gking2224.common.utils.JsonMvcTestHelper.responseContent;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import me.gking2224.budgetms.jpa.Budget;
import me.gking2224.budgetms.mvc.WebAppTestConfigurer;
import me.gking2224.common.utils.JsonMvcTestHelper;
import me.gking2224.common.utils.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=WebAppTestConfigurer.class)
@Transactional
@Rollback
@Sql
public class BudgetControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    private JsonUtil json;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        json = new JsonUtil();
    }

    @Test
    public void testGetBudgets() throws Exception {
        ResultActions result = doGet(this.mockMvc, BUDGETS, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.length()").value(greaterThan(0)))
            .andDo((m)->assertEquals("/budgets/100", json.getFilterValue(responseContent(m), "$.[?(@.name=='Model Type')].location")));
    }

    @Test
    public void testNewBudget() throws Exception {
        Budget mt = new Budget(100L, "Budget");
        ResultActions result = doPost(this.mockMvc, mt, BUDGETS, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.location").isNotEmpty());
    }
}