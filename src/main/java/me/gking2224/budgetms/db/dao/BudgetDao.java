package me.gking2224.budgetms.db.dao;

import java.util.List;

import me.gking2224.budgetms.model.Budget;
import me.gking2224.common.db.dao.CrudDao;

public interface BudgetDao extends CrudDao<Budget, Long>{

    List<Budget> findByProjectId(Long projectId);

    List<Budget> findByResourceId(Long resourceId);

}
