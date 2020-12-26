package com.team9.motors.interfacemethods;

import com.team9.motors.model.Inventory;
import com.team9.motors.model.StockUsage;
import com.team9.motors.model.StockUsageInventory;

import java.time.LocalDate;
import java.util.List;

public interface CatalogueInterface {

    public List<StockUsageInventory> findStockUsageInventoryByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    public List<Inventory> findPartsByName(String name);
    public List<Inventory> findPartsByColor(String color);
    public List<Inventory> findPartsByBrand(String name);
    public List<Inventory> findPartsBySupplier(String supplierName);
    public List<Inventory> FilteringPartByDescription(String keyword);
    public Inventory findPartById(int id);

    public void saveInventory(Inventory inventory);
    public void saveStockUsage(StockUsage su);
    public void saveStockUsageInventory(StockUsageInventory si);

    public void deleteStockUsageInventory(StockUsageInventory si);
    public void deleteStockUsage(StockUsage su);

    public List<Inventory> listAllInventories();

    public List<StockUsageInventory> listAllStockUsageInventories();
    public List<StockUsageInventory> listUsageByCustomerId(int id);
    public List<StockUsageInventory> listUsageByCarId(int id);

    public StockUsageInventory findUsageById(int id);
    public List<StockUsage> listAllStockUsages();
    public StockUsage findCustomerById(int id);
    
    public void usageReport(LocalDate startDate, LocalDate endDate);
}
