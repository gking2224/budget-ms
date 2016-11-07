package me.gking2224.budgetms.db.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.BudgetServiceTestInitializer;
import me.gking2224.budgetms.BudgetsTestConfiguration;
import me.gking2224.budgetms.model.Budget;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name="budgetms", classes=BudgetsTestConfiguration.class, initializers={BudgetServiceTestInitializer.class})
@Transactional
@Rollback
public class BudgetRepositoryIT {

    @Autowired
    protected BudgetRepository repository;
    
    @Test
    public void testSave() {
        String name = "budget1";
        Long projectId = 1L;
        
        Budget b = new Budget(name, projectId, 2016);
        Budget saved = repository.save(b);
        assertNotNull(saved);
    }
    
    @Test
    @Sql("../../SingleBudget.sql")
    public void testFindOne() {
        
        Budget b = repository.findOne(1L);
        
        assertNotNull(b);
        assertEquals("Test Budget", b.getName());
    }
}
