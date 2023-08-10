package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "numero_livraison", nullable = false)
    private String numeroLivraison;

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

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<FactureDetails> factureDetails;


    // Constructors
    public Facture() {
    }

    public Facture(int id, String numeroFacture, String numeroCommande, String numeroLivraison, LocalDate dateFacture, String client, BigDecimal totalHT, BigDecimal totalTVA, BigDecimal totalTTC, List<FactureDetails> factureDetails) {
        this.id = id;
        this.numeroFacture = numeroFacture;
        this.numeroCommande = numeroCommande;
        this.numeroLivraison = numeroLivraison;
        this.dateFacture = dateFacture;
        this.client = client;
        this.totalHT = totalHT;
        this.totalTVA = totalTVA;
        this.totalTTC = totalTTC;
        this.factureDetails = factureDetails;
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

    public String getNumeroLivraison() {
        return numeroLivraison;
    }

    public void setNumeroLivraison(String numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
    }

    public List<FactureDetails> getFactureDetails() {
        return factureDetails;
    }

    public void setFactureDetails(List<FactureDetails> factureDetails) {
        this.factureDetails = factureDetails;
    }
}

