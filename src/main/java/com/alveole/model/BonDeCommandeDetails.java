package com.alveole.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bon_de_commande_details")
public class BonDeCommandeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_de_commande_id", nullable = false)
    private BonDeCommande bonDeCommande;

    @Column(name = "produit", nullable = false)
    private String produit;

    @Column(name = "quantite", nullable = false)
    private int quantite;

    @Column(name = "prix_unitaire", nullable = false)
    private BigDecimal prixUnitaire;

    @Column(name = "total_HT", nullable = false)
    private BigDecimal totalHT;

    // Constructors, getters, and setters

    public BonDeCommandeDetails() {
    }

    public BonDeCommandeDetails(int id, BonDeCommande bonDeCommande, String produit, int quantite, BigDecimal prixUnitaire, BigDecimal totalHT) {
        this.id = id;
        this.bonDeCommande = bonDeCommande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.totalHT = totalHT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BonDeCommande getBonDeCommande() {
        return bonDeCommande;
    }

    public void setBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }
}
