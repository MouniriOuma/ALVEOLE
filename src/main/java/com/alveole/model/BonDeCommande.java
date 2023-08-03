package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "bon_de_commande")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BonDeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "numero_commande", nullable = false)
    private String numeroCommande;

    @Column(name = "date_commande", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCommande;


    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "total_HT", nullable = false)
    private BigDecimal totalHT;

    @OneToMany(mappedBy = "bonDeCommande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BonDeCommandeDetails> bonDeCommandeDetails;



    // Constructors, getters, and setters
    public BonDeCommande() {
    }

    public BonDeCommande(int id, String numeroCommande, LocalDate dateCommande, String client, BigDecimal totalHT, List<BonDeCommandeDetails> bonDeCommandeDetails) {
        this.id = id;
        this.numeroCommande = numeroCommande;
        this.dateCommande = dateCommande;
        this.client = client;
        this.totalHT = totalHT;
        this.bonDeCommandeDetails = bonDeCommandeDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<BonDeCommandeDetails> getBonDeCommandeDetails() {
        return bonDeCommandeDetails;
    }

    public void setBonDeCommandeDetails(List<BonDeCommandeDetails> bonDeCommandeDetails) {
        this.bonDeCommandeDetails = bonDeCommandeDetails;
    }
}
