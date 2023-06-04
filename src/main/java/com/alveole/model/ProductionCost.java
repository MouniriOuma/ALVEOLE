package com.alveole.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "productionCost")
public class ProductionCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "cost")
    private float cost;

    public ProductionCost() {
    }

    public ProductionCost(int id, Date date, float cost) {
        this.id = id;
        this.date = date;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
