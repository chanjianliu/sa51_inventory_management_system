package com.team9.motors.repository;

import com.team9.motors.model.StockUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockUsageRepository extends JpaRepository<StockUsage, Integer> {
    public StockUsage findByCustomerName(String name);
    public StockUsage findByCarId(int CarId);
}
