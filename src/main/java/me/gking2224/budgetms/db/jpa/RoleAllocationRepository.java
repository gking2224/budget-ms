package me.gking2224.budgetms.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import me.gking2224.budgetms.model.RoleAllocation;

public interface RoleAllocationRepository extends JpaRepository<RoleAllocation, Long>{

}
