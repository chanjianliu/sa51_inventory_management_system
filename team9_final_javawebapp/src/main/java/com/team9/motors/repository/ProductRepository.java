package com.team9.motors.repository;

import com.team9.motors.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findByName(String name);
    public List<Product> findByColour(String color);
    public List<Product> findByBrand(String brand);
    public List<Product> findByDescriptionContaining(String keyword);
    
    //find by supplier name
    @Query("select p from Product p where p.supplier.name = :SupplierName")
    public List<Product> findBySupplier(String SupplierName);
    
    //find by supplierId
    @Query("select p from Product p where p.supplier.id = :id")
	public List<Product> findBySupplierId(int id);

    @Query("Select p from Product as p where p.name like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductByName(@Param("k") String keyword);

    @Query("Select p from Product as p where p.brand like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductByBrand(@Param("k") String keyword);

    @Query("Select p from Product as p where p.colour like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductByColour(@Param("k") String keyword);

    @Query("Select p from Product as p where p.category like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductByCategory(@Param("k") String keyword);

    @Query("Select p from Product as p where p.description like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductByDescription(@Param("k") String keyword);
    
    @Query("Select p from Product as p where p.supplier.name like CONCAT('%',:k,'%') ")
    public ArrayList<Product> SearchProductBySupplier(@Param("k") String keyword);
    
    @Query("Select p from Product p where p.supplier.id = :id")
    public List<Product> reorderReport(int id);
}