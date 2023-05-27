package com.alveole.model;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "WaterElec")
public class WaterElec {
	
	@Id
	@Column(name = "bill_num")
    private int billNum;

	@Column(name = "water_elec")
    @Enumerated(EnumType.STRING)
    private WaterOrElec status;
	
	@Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    @Column(name = "cost")
    private float cost;

    
    public WaterElec() {
        
    }
    
    
	public WaterElec(int billNum, WaterOrElec status, Date updateDate, float cost) {
		super();
		this.billNum = billNum;
		this.status = status;
		this.updateDate = updateDate;
		this.cost = cost;
	}


	public int getBillNum() {
		return billNum;
	}


	public void setBillNum(int billNum) {
		this.billNum = billNum;
	}


	public WaterOrElec getStatus() {
		return status;
	}


	public void setStatus(WaterOrElec status) {
		this.status = status;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public float getCost() {
		return cost;
	}


	public void setCoast(float cost) {
		this.cost = cost;
	}
    
    
    
}
