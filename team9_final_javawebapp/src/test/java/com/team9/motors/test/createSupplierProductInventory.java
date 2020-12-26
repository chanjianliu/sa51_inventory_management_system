package com.team9.motors.test;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team9.motors.interfacemethods.SupplierInterface;
import com.team9.motors.model.Inventory;
import com.team9.motors.model.Product;
import com.team9.motors.model.ProductState;
import com.team9.motors.model.StockUsage;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.model.Supplier;
import com.team9.motors.repository.InventoryRepository;
import com.team9.motors.repository.ProductRepository;
import com.team9.motors.repository.StockUsageInventoryRepository;
import com.team9.motors.repository.StockUsageRepository;
import com.team9.motors.repository.SupplierRepository;
import com.team9.motors.repository.UserRepository;
import com.team9.motors.service.SupplierImplementation;


@SpringBootTest
class createSupplierProductInventory {

	@Autowired
	private SupplierRepository srepo;
	@Autowired
	private ProductRepository prepo;
	@Autowired
	private UserRepository urepo;
	@Autowired
	private SupplierInterface sservice;
	@Autowired
	public void setSupplierInterface(SupplierImplementation sImpl) {
		this.sservice = sImpl;
	}
	@Autowired
	private StockUsageRepository surepo;
	@Autowired
	private StockUsageInventoryRepository suirepo;
	@Autowired
	private InventoryRepository irepo;

	@Test
	void conTextLoad() {
		Supplier s1 = new Supplier("Toyota Car Co. LTD",
				"8 Jalan Lembah Kallang #01-01, Min Ghee Building, Singapore 339564", "toyata@gmail.com", "89776243");
		Supplier s2 = new Supplier("Mazida Car Co. LTD", "6 Tannery Lane #01-05B Sindo Building, Singapore 347805",
				"mazida@gmail.com", "89770043");
		Supplier s3 = new Supplier("Mercedes-Benz Car Co. LTD", "7 Soon Lee Street #05-05 iSpace, Singapore 627608",
				"benz@gmail.com", "89744043");
		Supplier s4 = new Supplier("Honda Car Co. LTD",
				"50 Serangoon North Avenue 4 #02-19 First Centre, Singapore 555856", "honda@gmail.com", "88744000");
		Supplier s5 = new Supplier("BMW Car Co. LTD", "48 Toh Guan Road East #04-126 Enterprise Hub, Singapore 608586",
				"bmw@gmail.com", "88744001");
		srepo.save(s1);
		srepo.save(s2);
		srepo.save(s3);
		srepo.save(s4);
		srepo.save(s5);
		
		// Inventory
		Inventory i11 = new Inventory(11, ProductState.InStock, 13);
		Inventory i12 = new Inventory(12, ProductState.InStock, 13);
		Inventory i13 = new Inventory(13, ProductState.InStock, 13);
		
		Inventory i21 = new Inventory(21, ProductState.InStock, 13);
		Inventory i22 = new Inventory(22, ProductState.InStock, 13);
		
		Inventory i31 = new Inventory(31, ProductState.InStock, 13);
		Inventory i32 = new Inventory(32, ProductState.InStock, 13);
		Inventory i33 = new Inventory(33, ProductState.InStock, 13);
		Inventory i34 = new Inventory(34, ProductState.InStock, 13);
		
		//Product
		Product p11 = new Product("Toyata Steer Wheel", "Toyata", LocalDate.now(), "silver", 3000, 3100, 3300, 3200,
				"This is a wheel", "round", "Wheel", 4, 8);p11.setSupId(s1.getId());p11.setSupplier(s1);p11.setInventory(i11);
		Product p12 = new Product("Toyata Car seat", "Toyata", LocalDate.now(), "black", 1000, 1100, 1300, 1200,
				"This is a seat", "notround", "Seat", 4, 8);p12.setSupId(s1.getId());p12.setSupplier(s1);p12.setInventory(i12);
		Product p13 = new Product("Toyata Car Front Light", "Toyata", LocalDate.now(), "black", 1000, 1100, 1300, 1200,
				"This is a seat", "notround", "Light", 4, 8);p13.setSupId(s1.getId());p13.setSupplier(s1);p13.setInventory(i13);
				
		Product p21 = new Product("Mazida Steer wheel", "Mazida", LocalDate.now(), "silver", 3000, 3100, 3300, 3200,
				"This is a wheel", "round", "Wheel", 4, 8);p21.setSupId(s2.getId());p21.setSupplier(s2);p21.setInventory(i21);
		Product p22 = new Product("Mazida Headlight", "Mazida", LocalDate.now(), "silver", 1000, 1100, 1300, 1200,
				"This is a light", "notround", "Light", 4, 8);p22.setSupId(s2.getId());p22.setSupplier(s2);p22.setInventory(i22);
				
		Product p31 = new Product("Benz Steer wheel", "Mercedes-Benz", LocalDate.now(), "silver", 5000, 5100, 5300, 5200,
				"This is a wheel", "round", "Wheel", 4, 8);p31.setSupId(s3.getId());p31.setSupplier(s3);p31.setInventory(i31);
		Product p32 = new Product("Benz Car children seat", "Mercedes-Benz", LocalDate.now(), "yellow", 1000, 1100, 1300,
				1200, "This is aseat", "notround", "Seat",  4, 8);p32.setSupId(s3.getId());p32.setSupplier(s3);p32.setInventory(i32);		
		Product p33 = new Product("Benz Machine Oil", "Mercedes-Benz", LocalDate.now(), "silver", 5000, 5100, 5300, 5200,
				"This is a wheel", "round", "Oil", 4, 8);p33.setSupId(s3.getId());p33.setSupplier(s3);p33.setInventory(i33);
		Product p34 = new Product("Benz Car Water", "Mercedes-Benz", LocalDate.now(), "yellow", 1000, 1100, 1300,
				1200, "This is aseat", "notround", "Water",  4, 8);p34.setSupId(s3.getId());p34.setSupplier(s3);p34.setInventory(i34);
		
		// Save
		prepo.save(p11);
		prepo.save(p12);
		prepo.save(p21);
		prepo.save(p22);
		prepo.save(p31);
		prepo.save(p32);
		prepo.save(p33);
		prepo.save(p34);
		
		//Adding customer and usage records
		StockUsage su1 = new StockUsage(1, "Jerry");
		surepo.save(su1);

		int pid11 = i11.getId();
		StockUsageInventory sui1 = new StockUsageInventory(pid11, 10, LocalDate.now());
		sui1.setInventory(i11);
		sui1.setStockUsage(su1);
		i11.getStockUsageInventory().add(sui1);
		su1.getUsageOfTheCustomer().add(sui1);
		suirepo.save(sui1);
	}
	
//	@Test
//	void conTextLoad_Customer() {
//		Customer c1 = new Customer();
//	}

}
