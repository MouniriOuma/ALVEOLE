package com.alveole.model;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "WaterElec")
public class WaterElec {
	
	@Id
	@Column(name = "bill_num")
    private int bill_Num;

	@Column(name = "water_elec")
    @Enumerated(EnumType.STRING)
    private WaterOrElec water_elec;
	
	@Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "cost")
    private float cost;

	public WaterElec(){

	}
	public WaterElec(int bill_Num, WaterOrElec water_elec, Date date, float cost) {
		this.bill_Num = bill_Num;
		this.water_elec = water_elec;
		this.date = date;
		this.cost = cost;
	}

	public int getBill_Num() {
		return bill_Num;
	}

	public void setBill_Num(int bill_Num) {
		this.bill_Num = bill_Num;
	}

	public WaterOrElec getWater_elec() {
		return water_elec;
	}

	public void setWater_elec(WaterOrElec water_elec) {
		this.water_elec = water_elec;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
