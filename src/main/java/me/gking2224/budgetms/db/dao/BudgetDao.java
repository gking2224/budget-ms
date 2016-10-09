package me.gking2224.budgetms.db.dao;

import java.util.List;

import me.gking2224.budgetms.model.Budget;

public interface BudgetDao {

    Budget create(Budget budget);

    List<Budget> findAll();

    Budget update(Budget budget);

    void delete(Long id);

    Budget findById(Long id);

    List<Budget> findByProjectId(Long projectId);

    List<Budget> findByResourceId(Long resourceId);

}
