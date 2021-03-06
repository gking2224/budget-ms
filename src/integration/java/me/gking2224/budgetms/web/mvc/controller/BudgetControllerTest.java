package me.gking2224.budgetms.web.mvc.controller;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static me.gking2224.budgetms.web.mvc.BudgetController.BUDGETS;
import static me.gking2224.common.utils.test.JsonMvcTestHelper.doGet;
import static me.gking2224.common.utils.test.JsonMvcTestHelper.doPost;
import static me.gking2224.common.utils.test.JsonMvcTestHelper.doPut;
import static me.gking2224.common.utils.test.JsonMvcTestHelper.responseContent;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import me.gking2224.budgetms.BudgetServiceTestInitializer;
import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.web.mvc.BudgetsWebAppTestConfiguration;
import me.gking2224.common.utils.JsonUtil;
import me.gking2224.common.utils.test.JsonMvcTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name="budgetms", classes=BudgetsWebAppTestConfiguration.class, initializers={BudgetServiceTestInitializer.class})
@Transactional
@ActiveProfiles("web")
@SpringBootTest()
@Rollback
@Sql("../../../SingleBudget.sql")
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
    public void testGetAllBudgets() throws Exception {
        doGet(this.mockMvc, BUDGETS, JsonMvcTestHelper::expectOK)
            .andDo(JsonMvcTestHelper::responseContent)
            .andExpect(jsonPath("$.length()").value(greaterThan(0)))
            .andDo((m)->assertEquals("/budgets/1", json.getFilterValue(responseContent(m), "$.[?(@._id == '1')].location")));
    }

    @Test
    public void testNewBudget() throws Exception {
        long projectId = 1L;
        Budget b = new Budget("Budget", projectId, 2016);
        ResultActions result = doPost(this.mockMvc, b, BUDGETS, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$._id").isNotEmpty())
            .andExpect(jsonPath("$.projectId").value(hasToString(valueOf(projectId))))
            .andExpect(jsonPath("$.location").isNotEmpty());
    }

    @Test
    public void testGetSingleBudget() throws Exception {
        long id = 1L;
        String address = format("%s/%s", BUDGETS, id);
        ResultActions result = doGet(this.mockMvc, address, JsonMvcTestHelper::expectOK);
        result
            .andExpect(jsonPath("$._id").value(hasToString(valueOf(id))))
            .andExpect(jsonPath("$.location").value(equalTo(address)));
    }

    @Test
    public void testUpdateBudget() throws Exception {
        String newName = "Budget.x";
        long projectId = 1L;
        long id = 1L;
        Budget b = new Budget(newName, projectId, 2016);
        
        doPut(this.mockMvc, b, format("%s/%s", BUDGETS, id), JsonMvcTestHelper::expectOK)
            .andExpect(jsonPath("$._id").value(hasToString(valueOf(id))))
            .andExpect(jsonPath("$.name").value(equalTo(newName)))
            .andExpect(jsonPath("$.location").isNotEmpty());
    }
}