package me.gking2224.budgetms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.db.dao.BudgetDao;
import me.gking2224.budgetms.model.Budget;
import me.gking2224.common.db.dao.CrudDao;
import me.gking2224.common.service.AbstractCrudServiceImpl;

@Component
@Transactional(readOnly=true)
public class BudgetServiceImpl extends AbstractCrudServiceImpl<Budget, Long> implements BudgetService {


    @Autowired
    private BudgetDao dao;

    public BudgetServiceImpl() {
    }

    @Override
    public List<Budget> findByProjectId(final Long projectId) {
        return dao.findByProjectId(projectId);
    }

    @Override
    public List<Budget> findByResourceId(Long resourceId) {
        return dao.findByResourceId(resourceId);
    }

    @Override
    protected CrudDao<Budget, Long> getDao() {
        return dao;
    }

}
