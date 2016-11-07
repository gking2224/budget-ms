package me.gking2224.budgetms.db.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.BudgetServiceTestInitializer;
import me.gking2224.budgetms.BudgetsTestConfiguration;
import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.model.Role;
import me.gking2224.budgetms.model.RoleAllocation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name="budgetms", classes=BudgetsTestConfiguration.class, initializers={BudgetServiceTestInitializer.class})
@Transactional
@Rollback
@Sql({"../../SingleBudget.sql"})
public class BudgetDaoTest {

    @Autowired
    protected BudgetDao budgetDao;
    
    
    @Test
    public void testFindAll() {
        
        List<Budget> bb = budgetDao.findAll();
        
        assertNotNull(bb);
        assertTrue(bb.size() > 0);
    }
    
    @Test
    public void testSave() {
        String newName = "Updated name";
        
        Budget b = budgetDao.findById(1L);
        
        assertNotNull(b);
        assertNotEquals(newName, b.getName());
        
        b.setName(newName);

        budgetDao.save(b);
        Budget updated = budgetDao.findById(1L);
        
        assertNotNull(updated);
        assertEquals(newName, updated.getName());
    }
    
    @Test
    public void testSaveAddNewRole() {
        long newLocation = 11L;
        String newRoleName = "newRole";
        
        Budget b = budgetDao.findById(1L);
        
        Role newRole = new Role();
        newRole.setName(newRoleName);
        newRole.setLocationId(newLocation);
        newRole.setFtes(Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1).stream()
                .map(i->new BigDecimal((double)i))
                .collect(Collectors.toList()));
        
        Set<Role> roles = b.getRoles();
        int numRoles = roles.size();
        roles.add(newRole);

        budgetDao.save(b);
        Budget updated = budgetDao.findById(1L);
        
        assertNotNull(updated);
        assertNotNull(updated.getRoles());
        assertEquals(numRoles + 1, updated.getRoles().size());
    }
    
    @Test
    public void testSaveModifyExistingRole() {
        String newRoleName = "newRole";
        
        Budget b = budgetDao.findById(1L);
        
        Role role = b.getRoles().stream().filter(r -> r.getId() == 1L).findFirst().get();
        role.setName(newRoleName);
        role.setFtes(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3).stream()
                .map(i->new BigDecimal((double)i))
                .collect(Collectors.toList()));

        budgetDao.save(b);
        Budget updated = budgetDao.findById(1L);
        
        assertNotNull(updated);
        assertNotNull(updated.getRoles());
        Role updatedRole = updated.getRoles().stream().filter(r -> r.getId() == 1L).findFirst().get();
        assertEquals(newRoleName, updatedRole.getName());
    }
    
    @Test
    public void testSaveAddRoleAllocation() {
        Long resourceId = 3L;
        
        Budget b = budgetDao.findById(1L);
        Role role = b.getRoles().stream().filter(r -> r.getId() == 1L).findFirst().get();

        // check doesn't already exist
        Optional<RoleAllocation> existingAlloc = role.getAllocations().stream()
                .filter(it -> it.getResourceId() == resourceId)
                .findAny();
        assertFalse(existingAlloc.isPresent());

        // get num allocations before
        int numAllocs = role.getAllocations().size();

        // add new allocation and update
        RoleAllocation ra = new RoleAllocation();
        ra.setResourceId(resourceId);
        ra.setActuals(Arrays.asList(1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d).stream()
                .map(i -> new BigDecimal(i))
                .collect(Collectors.toList()));
        ra.setForecast(Arrays.asList(1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d).stream()
                .map(i -> new BigDecimal(i))
                .collect(Collectors.toList()));
        role.addAllocation(ra);
        budgetDao.save(b);
        
        Budget updated = budgetDao.findById(1L);
        
        assertNotNull(updated);
        assertNotNull(updated.getRoles());
        role = updated.getRoles().stream().filter(r -> r.getId() == 1L).findFirst().get();
        assertEquals(numAllocs + 1, role.getAllocations().size());
        
        ra = role.getAllocations().stream().filter(it -> it.getResourceId() == resourceId).findFirst().get();
        assertEquals(resourceId, ra.getResourceId());
    }
}
