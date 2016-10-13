package me.gking2224.budgetms.service;

import java.util.List;

import me.gking2224.budgetms.model.Budget;
import me.gking2224.common.service.CrudService;

public interface BudgetService extends CrudService<Budget, Long> {

    List<Budget> findByProjectId(Long projectId);

    List<Budget> findByResourceId(Long resourceId);
}
