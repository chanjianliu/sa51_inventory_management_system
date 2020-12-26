package com.team9.motors.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StockUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique=true, nullable = false)
    private int carId;
    @Column(nullable = false)
    private String customerName;
    @OneToMany(mappedBy = "stockUsage")
    private List<StockUsageInventory> usageOfTheCustomer = new ArrayList<StockUsageInventory>();

    public StockUsage() {
        super();
    }

    public StockUsage(String customerName) {
        super();
        this.customerName = customerName;
    }
	
    public StockUsage(int carId, String customerName) {
		super();
		this.carId = carId;
		this.customerName = customerName;
	}

	public StockUsage(int carId) {
        super();
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addStockUsageInventory(StockUsageInventory item) {
        this.usageOfTheCustomer.add(item);
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<StockUsageInventory> getUsageOfTheCustomer() {
        return usageOfTheCustomer;
    }

    public void setUsageOfTheCustomer(List<StockUsageInventory> usageOfTheCustomer) {
        this.usageOfTheCustomer = usageOfTheCustomer;
    }
}