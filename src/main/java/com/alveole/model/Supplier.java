package com.alveole.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "contact", length = 25)
    private String contact;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "supplied_product", length = 50)
    private String suppliedProduct;

    @Column(name = "cin", length = 50)
    private String cin;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "ice")
    private int ice;

    @Column(name = "business_name", length = 50)
    private String businessName;
    
    
    
    
    // Default constructor
    
    public Supplier() {
        
    }
        
    
    // Constructors
    
    public Supplier(int supplierId, String address, String contact, String email, String suppliedProduct, String cin,
			String firstName, String lastName, int ice, String businessName) {
		super();
		this.supplierId = supplierId;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.suppliedProduct = suppliedProduct;
		this.cin = cin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ice = ice;
		this.businessName = businessName;
	}
    
    
    // getters, and setters

	public int getSupplierId() {
		return supplierId;
	}	

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuppliedProduct() {
		return suppliedProduct;
	}

	public void setSuppliedProduct(String suppliedProduct) {
		this.suppliedProduct = suppliedProduct;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getIce() {
		return ice;
	}

	public void setIce(int ice) {
		this.ice = ice;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

    
    
}

