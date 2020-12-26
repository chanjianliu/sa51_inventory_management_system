package com.team9.motors.service;

import com.team9.motors.interfacemethods.CatalogueInterface;
import com.team9.motors.model.Inventory;
import com.team9.motors.model.Product;
import com.team9.motors.model.StockUsage;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.repository.InventoryRepository;
import com.team9.motors.repository.ProductRepository;
import com.team9.motors.repository.StockUsageInventoryRepository;
import com.team9.motors.repository.StockUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogueImplementation implements CatalogueInterface {

    @Autowired
    InventoryRepository irepo;
    @Autowired
    ProductRepository prepo;
    @Autowired
    StockUsageRepository surepo;
    @Autowired
    StockUsageInventoryRepository sirepo;

    @Override
    public List<StockUsageInventory> findStockUsageInventoryByRegistrationDateBetween(LocalDate startDate, LocalDate endDate){
        return sirepo.findByRegistrationDateBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public List<Inventory> findPartsByName(String name) {
        List<Product> pros = prepo.findByName(name);
        List<Inventory> invList = new ArrayList<>();
        for(Product pro : pros) {
            Inventory inv = irepo.getOne(pro.getId());
            invList.add(inv);
        }
        return invList;

    }

    @Override
    @Transactional
    public List<Inventory> findPartsByBrand(String name) {
        List<Product> pros = prepo.findByBrand(name);
        List<Inventory> invList = new ArrayList<>();
        for(Product pro : pros) {
            Inventory inv = irepo.getOne(pro.getId());
            invList.add(inv);
        }
        return invList;
    }

    @Override
    @Transactional
    public List<Inventory> findPartsByColor(String color) {
        List<Product> pros = prepo.findByColour(color);
        List<Inventory> invList = new ArrayList<>();
        for(Product pro : pros) {
            Inventory inv = irepo.getOne(pro.getId());
            invList.add(inv);
        }
        return invList;
    }

    @Override
    @Transactional
    public List<Inventory> findPartsBySupplier(String supplierName) {
        List<Product> pros = prepo.findBySupplier(supplierName);
        List<Inventory> invList = new ArrayList<>();
        for(Product pro : pros) {
            Inventory inv = irepo.getOne(pro.getId());
            invList.add(inv);
        }
        return invList;
    }

    @Override
    @Transactional
    public List<Inventory> FilteringPartByDescription(String keyword) {
        List<Product> pros = prepo.findByDescriptionContaining(keyword);
        List<Inventory> invList = new ArrayList<>();
        for(Product pro : pros) {
            Inventory inv = irepo.getOne(pro.getId());
            invList.add(inv);
        }
        return invList;
    }

    @Override
    @Transactional
    public Inventory findPartById(int id) {
        return irepo.findInventoryById(id);
    }

    @Override
    @Transactional
    public void saveInventory(Inventory inventory) {
        irepo.save(inventory);
    }

    @Override
    @Transactional
    public void saveStockUsage(StockUsage su) {
        surepo.save(su);

    }

    @Override
    @Transactional
    public void saveStockUsageInventory(StockUsageInventory si) {
        sirepo.save(si);

    }

    @Override
    @Transactional
    public void deleteStockUsageInventory(StockUsageInventory si) {
        sirepo.delete(si);
    }

    @Override
    @Transactional
    public void deleteStockUsage(StockUsage su) {
        surepo.delete(su);
    }

    @Override
    @Transactional
    public List<Inventory> listAllInventories() {
        return irepo.findAll();
    }

    @Override
    @Transactional
    public List<StockUsageInventory> listAllStockUsageInventories() {
        return sirepo.findAll();
    }

    @Override
    @Transactional
    public List<StockUsage> listAllStockUsages() {
        return surepo.findAll();
    }

    @Override
    @Transactional
    public List<StockUsageInventory> listUsageByCustomerId(int id) {
        return surepo.findById(id).get().getUsageOfTheCustomer();
    }

    @Override
    @Transactional
    public List<StockUsageInventory> listUsageByCarId(int id) {
        return surepo.findByCarId(id).getUsageOfTheCustomer();
    }

    @Override
    @Transactional
    public StockUsage findCustomerById(int id) {
        return surepo.findById(id).get();
    }

    @Override
    @Transactional
    public StockUsageInventory findUsageById(int id) {
        return sirepo.findById(id).get();
    }
    
    @Override
	@Transactional
	public void usageReport(LocalDate startDate, LocalDate endDate) {
		BufferedWriter bw = null;
		try {
			List<StockUsageInventory> mycontent = sirepo.findByRegistrationDateBetween(startDate, endDate);
			if (mycontent.size() == 0) {
				return;
			}
			File file = new File("D:\\reorderreport.dat");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);

			bw = new BufferedWriter(fw);
			bw.write("\t\t\t\t\t\t\tInventory Reorder Report from : " + startDate + " to " + endDate);
			bw.newLine();
			bw.write("\t\t\t\t\t\t-------------------------------------------------------------");
			bw.newLine();
			bw.write("===============================================================================================================");
			
			bw.newLine();

			for (StockUsageInventory p : mycontent) {
                if(mycontent.get(0) == p)
                {
                    bw.newLine();
                    bw.write(p.toStringTitle());
                    bw.newLine();
                }
                
					bw.newLine();
					bw.write(p.toStringContent());
					bw.newLine();

			}
			bw.newLine();
			bw.write("===============================================================================================================");
			bw.newLine();
			bw.write("===============================================================================================================");
			bw.newLine();
			bw.write("\t\t\t\t\t\t\t\t\t\t\tEnd Of Report");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				System.out.println("Error in closing the BufferedWriter" + ex);
			}
		}
	}
}

