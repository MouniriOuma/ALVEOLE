package com.alveole.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;

    @Column(name = "product_name", length = 50)
    private String productName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "quantity")
    private int quantity;
    
    
    
    // Default constructor
    
    public Product() {
        
    }
    
    //constructor

    public Product(int productId, String productName, String description, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
    }

    
    
    // Getters and setters
    
    
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    

}
