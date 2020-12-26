package com.team9.motors.interfacemethods;

import com.team9.motors.model.Inventory;

import java.util.List;

public interface InventoryInterface {
    public boolean saveInventory(Inventory inventory);
    public List<Inventory> findAllInventorys();
    public Inventory findInventoryById(Integer id);
    public void deleteInventory(Inventory inventory);

}
