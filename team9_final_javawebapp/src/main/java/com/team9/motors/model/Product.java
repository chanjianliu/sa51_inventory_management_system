package com.team9.motors.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	private String brand;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dom;
	private String colour;
	// @Min(1)
	@Column(nullable = false)
	private double oriPrice;
	// @Min(1)
	private double wholesalePrice; // lower price
	// @Min(1)
	private double retailPrice; // highest price
	// @Min(1)
	private double partnerPrice; // 2nd highest
	private String description;
	private String dimension;
	private String category;
	private int supId; // supplier's id
	// @Min(1)
//	@Column(nullable = false)
	private int reorderLevel; // reorder level
	// @Min(1)
//	@Column(nullable = false)
	private int minReorderQuantity; // minimum reorder quantity
	@ManyToOne(cascade = CascadeType.MERGE)
	private Supplier supplier;
	@OneToOne(cascade = CascadeType.ALL)
	private Inventory inventory;

	public Product() {
		super();
	}

	public Product(String name, String brand, LocalDate dom, String colour, double oriPrice, double wholesalePrice,
			double retailPrice, double partnerPrice, String description, String dimension, String category,
			int reorderLevel, int minReorderQuantity, Supplier supplier) {
		super();
		this.name = name;
		this.brand = brand;
		this.dom = dom;
		this.colour = colour;
		this.oriPrice = oriPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.description = description;
		this.dimension = dimension;
		this.category = category;
		this.reorderLevel = reorderLevel;
		this.minReorderQuantity = minReorderQuantity;
		this.supplier = supplier;
		this.inventory = new Inventory();
	}

	public Product(String name, String brand, LocalDate dom, String colour, double oriPrice, double wholesalePrice,
			double retailPrice, double partnerPrice, String description, String dimension, String category,
			int reorderLevel, int minReorderQuantity) {
		super();
		this.name = name;
		this.brand = brand;
		this.dom = dom;
		this.colour = colour;
		this.oriPrice = oriPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.description = description;
		this.dimension = dimension;
		this.category = category;
		this.reorderLevel = reorderLevel;
		this.minReorderQuantity = minReorderQuantity;
		this.supplier = new Supplier();
		this.inventory = new Inventory();
	}
	
	public Product(String name, String brand, LocalDate dom, String colour, double oriPrice, double wholesalePrice,
			double retailPrice, double partnerPrice, String description, String dimension, String category, 
			int supplierId, int reorderLevel, int minReorderQuantity, Supplier supplier, Inventory inventory) {
		super();
		this.name = name;
		this.brand = brand;
		this.dom = dom;
		this.colour = colour;
		this.oriPrice = oriPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.description = description;
		this.dimension = dimension;
		this.category = category;
		this.reorderLevel = reorderLevel;
		this.minReorderQuantity = minReorderQuantity;
		this.supId = supplierId;
		this.supplier = supplier;
		this.inventory = inventory;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDate getDom() {
		return dom;
	}

	public void setDom(LocalDate dom) {
		this.dom = dom;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getOriPrice() {
		return oriPrice;
	}

	public void setOriPrice(double oriPrice) {
		this.oriPrice = oriPrice;
	}

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getPartnerPrice() {
		return partnerPrice;
	}

	public void setPartnerPrice(double partnerPrice) {
		this.partnerPrice = partnerPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public int getMinReorderQuantity() {
		return minReorderQuantity;
	}

	public void setMinReorderQuantity(int minReorderQuantity) {
		this.minReorderQuantity = minReorderQuantity;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	// FOR RE-ORDER REPORT

	public int CalculateOrdQty(int Qty, int ReOrderQty, int MinOrdQty) {

		if (Qty < ReOrderQty) {
			if ((ReOrderQty - Qty) < MinOrdQty) {
				return MinOrdQty;
			} else {
				return (ReOrderQty - Qty);
			}
		} else
			return 0;
	}

	public double CalculatePrice(int OrdQty, double oriPrice) {
		return Math.round(OrdQty * oriPrice * 100.0) / 100.0;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", qty=" + inventory.getQuantity() + "Unit.Price=" + oriPrice + ", ReorderQty="
				+ reorderLevel + ", Min.ord.qty=" + minReorderQuantity + ", Ord.Qty="
				+ CalculateOrdQty(inventory.getQuantity(), reorderLevel, minReorderQuantity) + ",Price = "
				+ CalculatePrice(CalculateOrdQty(inventory.getQuantity(), reorderLevel, minReorderQuantity), oriPrice)
				+ " ]";
	}

	public String toString1() {
		return " id " + " qty " + " Unit.Price " + " Reorder qty " + " Min.ord.qty " + " Ord.Qty " + " Price ";
	}

	public String toString2() {
		
		int orderQuantity = CalculateOrdQty(inventory.getQuantity(), reorderLevel, minReorderQuantity);
		
		return " " + id + "   " + inventory.getQuantity() + "    " + oriPrice + "        " + reorderLevel
				+ "           " + minReorderQuantity + "            "
				+ orderQuantity + "       "
				+ CalculatePrice(orderQuantity, oriPrice);
	}

	public double getPrice() {
		int orderQuantity = CalculateOrdQty(inventory.getQuantity(), reorderLevel, minReorderQuantity);
		
		return CalculatePrice(orderQuantity, oriPrice);
	}

}