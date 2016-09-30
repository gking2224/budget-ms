package me.gking2224.budgetms.db.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.db.jpa.BudgetRepository;
import me.gking2224.budgetms.db.jpa.RoleAllocationRepository;
import me.gking2224.budgetms.db.jpa.RoleRepository;
import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.model.Role;
import me.gking2224.common.db.AbstractDaoImpl;

@Component
@Transactional
public class BudgetDaoImpl extends AbstractDaoImpl<Budget> implements BudgetDao {
    
    @Autowired
    protected BudgetRepository budgetRepository;
    
    @Autowired
    protected RoleRepository roleRepository;
    
    @Autowired
    protected RoleAllocationRepository roleAllocationRepository;
    
    public BudgetDaoImpl() {
    }

    @Override
    public Budget createBudget(Budget budget) {
        Budget saved = budgetRepository.save(budget);
        budget.getRoles().forEach(r -> r.setBudget(saved));
        saved.setRoles(
                budget.getRoles().stream().map(roleRepository::save).collect(Collectors.toSet()));
        return saved;
    }

    @Override
    public List<Budget> findAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        budgets.forEach( b -> {
            getEntityManager().detach(b);
            b.setRoles(null);
        });
        return budgets;
    }

    @Override
    public Budget updateBudget(Budget budget) {
        
        Budget existing = budgetRepository.findOne(budget.getId());
        existing.getRoles().clear();
        
        budget.getRoles().forEach(r -> {
            r.setBudget(existing);
        });
        budget.setRoles(
                budget.getRoles().stream().map(this::updateRole).collect(Collectors.toSet()));
        Budget saved = budgetRepository.save(budget);
        return saved;
    }
    
    protected Role updateRole(Role role) {
        
        Role existing = roleRepository.findOne(role.getId());
        existing.getAllocations().clear();
        
        role.getAllocations().forEach(r -> {
            r.setRole(existing);
        });
        role.setAllocations(
                role.getAllocations().stream().map(roleAllocationRepository::save).collect(Collectors.toSet()));
        Role saved = roleRepository.save(role);
        return saved;
    }

    @Override
    public void deleteBudget(Long id) {
        
        budgetRepository.delete(id);
    }

    @Override
    public Budget findBudgetById(Long id) {
        
        Budget budget = budgetRepository.findOne(id);
        Hibernate.initialize(budget.getRoles());
//        budget.getRoles().forEach( r -> {
//            Hibernate.initialize(r.getFtes());
//            Hibernate.initialize(r.getAllocations());
//        });
        
        getEntityManager().detach(budget);
        return budget;
    }
}
