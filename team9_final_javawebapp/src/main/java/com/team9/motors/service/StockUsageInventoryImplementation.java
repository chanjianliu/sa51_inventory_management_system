package com.team9.motors.service;

import com.team9.motors.interfacemethods.StockUsageInventoryInterface;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.repository.StockUsageInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StockUsageInventoryImplementation implements StockUsageInventoryInterface {

    @Autowired
    StockUsageInventoryRepository suirepo;

    @Override
    @Transactional
    public void saveStockUsageInventory(StockUsageInventory sui) {
        suirepo.save(sui);
    }

    @Override
    @Transactional
    public void deleteStockUsageInventory(StockUsageInventory sui) {
        suirepo.delete(sui);
    }

    @Override
    @Transactional
    public List<StockUsageInventory> listAllStockUsageInventories() {
        return suirepo.findAll();
    }

    @Override
    @Transactional
    public StockUsageInventory findStockUsageInventoryById(Integer id) {
        return suirepo.findById(id).get();
    }


}
