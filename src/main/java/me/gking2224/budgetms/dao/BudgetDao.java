package me.gking2224.budgetms.dao;

import java.util.List;

import me.gking2224.budgetms.jpa.Budget;

public interface BudgetDao {

    Budget createBudget(Budget budget);

    List<Budget> findAllBudgets();

    Budget updateBudget(Budget budget);

    void deleteBudget(Long id);

    Budget findBudgetById(Long id);

}
