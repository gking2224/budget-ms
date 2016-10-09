package me.gking2224.budgetms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.db.dao.BudgetDao;
import me.gking2224.budgetms.model.Budget;

@Component
@Transactional(readOnly=true)
public class BudgetServiceImpl implements BudgetService {


    @Autowired
    private BudgetDao dao;

    public BudgetServiceImpl() {
    }

    @Override
    @Transactional(readOnly=false)
    public Budget create(final Budget budget) {
        return dao.create(budget);
    }

    @Override
    public List<Budget> findAllBudgets() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly=false)
    public Budget update(final Budget budget) {
        return dao.update(budget);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(final Long id) {
        dao.delete(id);
    }

    @Override
    public Budget findBudgetById(final Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Budget> findByProjectId(final Long projectId) {
        return dao.findByProjectId(projectId);
    }

    @Override
    public List<Budget> findByResourceId(Long resourceId) {
        return dao.findByResourceId(resourceId);
    }

}
