package me.gking2224.budgetms.db.dao;

import java.util.List;

import me.gking2224.budgetms.model.Budget;

public interface BudgetDao {

    Budget createBudget(Budget budget);

    List<Budget> findAll();

    Budget update(Budget budget);

    void deleteBudget(Long id);

    Budget findById(Long id);

}
