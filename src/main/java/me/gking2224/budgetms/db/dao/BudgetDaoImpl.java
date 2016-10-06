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
    public Budget createBudget(Budget budget) {
//        Budget saved = budgetRepository.save(budget);
//        budget.getRoles().forEach(r -> r.setBudget(saved));
//        saved.setRoles(budget.getRoles().stream()
//                .map(roleRepository::save)
//                .collect(Collectors.toSet()));
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
        
        Budget saved = budgetRepository.save(toSave);
        return saved;
    }

    protected Role updateRole(Budget existingBudget, Role r) {

        Role role = r;
//        if (r.getId() != null) {
//            Role existing = roleRepository.findOne(r.getId());
//    
//            if (existing != null) {
//                Map<Long, RoleAllocation> existingAllocations = existing.getAllocationsById();
//                Map<Long, RoleAllocation> allocations = r.getAllocationsById();
//                existingAllocations.keySet().forEach(i -> {
//                    if (!allocations.containsKey(i)) {
//                        RoleAllocation toDelete = existingAllocations.get(i);
//                        existing.setAllocations(existing.getAllocations().stream()
//                                .filter(a -> a.getId() != i)
//                                .collect(Collectors.toSet()));
//                    }
//                });
//                roleRepository.save(existing);
//            }
//        }
        
        role.getAllocations().forEach(ra -> {
            ra.setRole(r);
            if (ra.getId() == null) {
                role.addAllocation(ra);
            }
        });

        return role;
    }

    @Override
    public void deleteBudget(Long id) {
        
        budgetRepository.delete(id);
    }

    @Override
    public Budget findById(Long id) {
        
        Budget budget = budgetRepository.findOne(id);
        Hibernate.initialize(budget.getRoles());
        
        return budget;
    }
}
