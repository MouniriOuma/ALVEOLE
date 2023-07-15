package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "facture")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "numero_facture", nullable = false)
    private String numeroFacture;

    @Column(name = "numero_commande", nullable = false)
    private String numeroCommande;

    @Column(name = "date_facture", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFacture;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "total_HT", nullable = false)
    private BigDecimal totalHT;

    @Column(name = "total_TVA", nullable = false)
    private BigDecimal totalTVA;

    @Column(name = "total_TTC", nullable = false)
    private BigDecimal totalTTC;


    // Constructors
    public Facture() {
    }

    public Facture(int id, String numeroFacture, String numeroCommande, LocalDate dateFacture, String client, BigDecimal totalHT, BigDecimal totalTVA, BigDecimal totalTTC) {
        this.id = id;
        this.numeroFacture = numeroFacture;
        this.numeroCommande = numeroCommande;
        this.dateFacture = dateFacture;
        this.client = client;
        this.totalHT = totalHT;
        this.totalTVA = totalTVA;
        this.totalTTC = totalTTC;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getTotalTVA() {
        return totalTVA;
    }

    public void setTotalTVA(BigDecimal totalTVA) {
        this.totalTVA = totalTVA;
    }

    public BigDecimal getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }
}

