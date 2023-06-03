package com.alveole.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredientId")
    private int ingredientId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "stockQuantity")
    private float stockQuantity;

    @Column(name = "unitPrice")
    private float unitPrice;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    @Column(name = "unitOfMeasurement", length = 50)
    private String unitOfMeasurement;

    @Column(name = "maxQuantity")
    private float maxQuantity;

    
    
    // Default constructor
    
    public Ingredient() {
    	
    };
    
    //constructor

	public Ingredient(int ingredientId, String name, float stockQuantity, float unitPrice, String description, Supplier supplier, String unitOfMeasurement, float maxQuantity) {
		this.ingredientId = ingredientId;
		this.name = name;
		this.stockQuantity = stockQuantity;
		this.unitPrice = unitPrice;
		this.description = description;
		this.supplier = supplier;
		this.unitOfMeasurement = unitOfMeasurement;
		this.maxQuantity = maxQuantity;
	}


	//getters and setters


	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(float stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public float getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(float maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
}

