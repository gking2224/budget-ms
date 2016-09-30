package me.gking2224.budgetms.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import me.gking2224.budgetms.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long>{

    @Modifying
    @Override
    @Query(value = "delete raf, rf, ra, r, b from budget as b "+
            "inner join role as r on r.budget_id = b.budget_id "+
            "inner join role_allocation as ra on ra.role_id = r.role_id "+
            "inner join role_fte rf on rf.role_id = r.role_id "+
            "inner join role_allocation_fte raf on raf.role_allocation_id = ra.role_allocation_id "+
            "where b.budget_id= ?1", nativeQuery=true)
    void delete(Long id);
}
