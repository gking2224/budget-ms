package me.gking2224.budgetms.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.db.jpa.BudgetRepository;
import me.gking2224.budgetms.db.jpa.RoleAllocationRepository;
import me.gking2224.budgetms.db.jpa.RoleRepository;
import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.model.Role;
import me.gking2224.common.db.AbstractDaoImpl;

@Component
@Transactional(readOnly=true)
public class BudgetDaoImpl extends AbstractDaoImpl<Budget, Long> implements BudgetDao {
    
    @Autowired
    protected BudgetRepository repository;
    
    @Autowired
    protected RoleRepository roleRepository;
    
    @Autowired
    protected RoleAllocationRepository roleAllocationRepository;
    
    public BudgetDaoImpl() {
    }

    @Override
    public Budget save(final Budget b) {

        Budget toSave = b;
        if (b.getId() != null) {
            toSave = repository.findOne(b.getId());
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
        
        Budget saved = repository.save(toSave);
        return saved;
    }

    @Override
    public List<Budget> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    @Override
    public List<Budget> findByResourceId(Long resourceId) {
        return repository.findByRoles_Allocations_ResourceId(resourceId);
    }

    @Override
    protected JpaRepository<Budget, Long> getRepository() {
        return repository;
    }
}
