package com.team9.motors.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int shelfLocation;
    private ProductState productState;
    @Column(nullable = false)
    private int quantity;
    
    private int damagedQuantity;

    @OneToOne(mappedBy = "inventory")
    private Product product;

    @OneToMany(mappedBy = "inventory")
    private List<StockUsageInventory> stockUsageInventory = new ArrayList<StockUsageInventory>();

    public Inventory() {
        super();
    }

    public Inventory(int shelfLocation, ProductState productState, int quantity, Product product) {
        super();
        this.shelfLocation = shelfLocation;
        this.productState = productState;
        this.quantity = quantity;
        this.product = product;
    }
    
    public Inventory(int shelfLocation, ProductState productState, int quantity, Product product, int damagedQuantity) {
        super();
        this.shelfLocation = shelfLocation;
        this.productState = productState;
        this.quantity = quantity;
        this.product = product;
        this.damagedQuantity = damagedQuantity;
    }
    
    public Inventory(int shelfLocation, ProductState productState, int quantity) {
        super();
        this.shelfLocation = shelfLocation;
        this.productState = productState;
        this.quantity = quantity;
    }

    public List<StockUsageInventory> getStockUsageInventory() {
        return stockUsageInventory;
    }

    public void setStockUsageInventory(List<StockUsageInventory> stockUsageInventory) {
        this.stockUsageInventory = stockUsageInventory;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getShelfLocation() {
        return shelfLocation;
    }
    public void setShelfLocation(int shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public ProductState getProductState() {
        return productState;
    }
    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

	public int getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(int damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}
    
    
}
