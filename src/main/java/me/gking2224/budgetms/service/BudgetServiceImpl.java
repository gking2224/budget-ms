package me.gking2224.budgetms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.gking2224.budgetms.dao.BudgetDao;
import me.gking2224.budgetms.jpa.Budget;

@Component
@Transactional(readOnly=true)
public class BudgetServiceImpl implements BudgetService {


    @Autowired
    private BudgetDao dao;

    public BudgetServiceImpl() {
    }

    @Override
    @Transactional(readOnly=false)
    public Budget createBudget(Budget budget) {
        return dao.createBudget(budget);
    }

    @Override
    public List<Budget> findAllBudgets() {
        return dao.findAllBudgets();
    }

    @Override
    @Transactional(readOnly=false)
    public Budget updateBudget(Budget budget) {
        return dao.updateBudget(budget);
    }

    @Override
    @Transactional(readOnly=false)
    public void deleteBudget(Long id) {
        dao.deleteBudget(id);
    }

    @Override
    public Budget findBudgetById(Long id) {
        return dao.findBudgetById(id);
    }

}
