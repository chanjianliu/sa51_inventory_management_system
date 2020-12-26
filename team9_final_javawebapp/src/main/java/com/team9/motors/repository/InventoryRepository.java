package com.team9.motors.repository;

import com.team9.motors.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    public Inventory findInventoryById(int id);
}
