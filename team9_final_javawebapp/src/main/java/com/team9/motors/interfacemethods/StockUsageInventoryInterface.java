package com.team9.motors.interfacemethods;

import com.team9.motors.model.StockUsageInventory;

import java.util.List;

public interface StockUsageInventoryInterface {
    public void saveStockUsageInventory(StockUsageInventory sui);
    public void deleteStockUsageInventory(StockUsageInventory sui);
    public List<StockUsageInventory> listAllStockUsageInventories();
    public StockUsageInventory findStockUsageInventoryById(Integer id);
}
