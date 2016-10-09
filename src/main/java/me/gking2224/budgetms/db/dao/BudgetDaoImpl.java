package me.gking2224.budgetms.db.dao;

import java.util.List;

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
    public Budget create(Budget budget) {
        Budget saved = update(budget);
        return saved;
    }

    @Override
    public List<Budget> findAll() {
        List<Budget> budgets = budgetRepository.findAll();
        return budgets;
    }

    @Override
    public Budget update(final Budget b) {

        Budget toSave = b;
        if (b.getId() != null) {
            toSave = budgetRepository.findOne(b.getId());
            toSave.updateFrom(b);
        }
        else {
            final Budget parent = toSave;
            toSave.getRoles().forEach(r -> {
                r.setBudget(parent);
                final Role parentRole = r;
                r.getAllocations().forEach(ra -> {
                    ra.setRole(parentRole);
                });
            });
        }
        
        Budget saved = budgetRepository.save(toSave);
        return saved;
    }

    protected Role updateRole(Budget existingBudget, Role r) {

        Role role = r;
        
        role.getAllocations().forEach(ra -> {
            ra.setRole(r);
            if (ra.getId() == null) {
                role.addAllocation(ra);
            }
        });

        return role;
    }

    @Override
    public void delete(Long id) {
        
        budgetRepository.delete(id);
    }

    @Override
    public Budget findById(Long id) {
        
        Budget budget = budgetRepository.findOne(id);
        Hibernate.initialize(budget.getRoles());
        
        return budget;
    }

    @Override
    public List<Budget> findByProjectId(Long projectId) {
        return budgetRepository.findByProjectId(projectId);
    }

    @Override
    public List<Budget> findByResourceId(Long resourceId) {
        return budgetRepository.findByRoles_Allocations_ResourceId(resourceId);
    }
}
