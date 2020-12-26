package com.team9.motors.repository;

import com.team9.motors.model.Product;
import com.team9.motors.model.Supplier;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
	public Supplier findSupplierByName(String name);
	
//	@Query("Select s from Supplier as s where s.name like CONCAT('%',:k,'%') ")
//    public Supplier findSupplierByName(@Param("k") String keyword);
}
