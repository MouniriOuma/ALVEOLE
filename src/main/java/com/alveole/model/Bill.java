package com.alveole.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "bill_number")
    private int billNumber;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "amount")
    private float amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BillStatus status;
    
    
    // Default constructor
    
    public Bill() {
    
    }
    
    //constructor

     public Bill(int billNumber, Supplier supplier, Date date, float amount, BillStatus status) {
		super();
		this.billNumber = billNumber;
		this.supplier = supplier;
		this.date = date;
		this.amount = amount;
		this.status = status;
	}

	// Getters and setters

    

	public int getBillNumber() {
        return billNumber;
    }

	public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	
    
    
   
}
