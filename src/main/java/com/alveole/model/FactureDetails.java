package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "facture_details")
public class FactureDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facture_id")
    @JsonBackReference
    private Facture facture;

    @Column(name = "produit", nullable = false)
    private String produit;

    @Column(name = "quantite_commande", nullable = false)
    private int quantiteCommande;

    @Column(name = "prix_unitaire", nullable = false)
    private BigDecimal prixUnitaire;


    // Constructors

    public FactureDetails() {
    }

    public FactureDetails(int id, Facture facture, String produit, int quantiteCommande, BigDecimal prixUnitaire) {
        this.id = id;
        this.facture = facture;
        this.produit = produit;
        this.quantiteCommande = quantiteCommande;
        this.prixUnitaire = prixUnitaire;
    }

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(int quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}

