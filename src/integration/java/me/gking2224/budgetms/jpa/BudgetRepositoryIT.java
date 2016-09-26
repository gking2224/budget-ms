package me.gking2224.budgetms.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.mvc.WebAppTestConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(classes=WebAppTestConfigurer.class)
@TestPropertySource("/test.properties")
@Transactional()
@EnableJpaRepositories
@Rollback
public class BudgetRepositoryIT {

    @Autowired
    protected BudgetRepository repository;
    
    @Test
    public void testSave() {
        String name = "budget1";
        
        Budget b = new Budget(name);
        Budget saved = repository.save(b);
        assertNotNull(saved);
    }
    
    @Test
    @Sql
    public void testFindOne() {
        
        Budget mm = repository.findOne(100L);
        
        assertNotNull(mm);
        assertEquals("Test Model", mm.getName());
    }
}
