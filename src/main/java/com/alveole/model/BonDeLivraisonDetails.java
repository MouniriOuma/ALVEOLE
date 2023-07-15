package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bon_de_livraison_details")
public class BonDeLivraisonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_de_livraison_id", nullable = false)
    private BonDeLivraison bonDeLivraison;

    @Column(name = "produit", nullable = false)
    private String produit;

    @Column(name = "quantite_commande", nullable = false)
    private int quantiteCommande;

    @Column(name = "prix_unitaire", nullable = false)
    private BigDecimal prixUnitaire;

    @Column(name = "total_HT", nullable = false)
    private BigDecimal totalHT;

    // Constructors, getters, and setters


    public BonDeLivraisonDetails() {
    }

    public BonDeLivraisonDetails(int id, BonDeLivraison bonDeLivraison, String produit, int quantiteCommande, BigDecimal prixUnitaire, BigDecimal totalHT) {
        this.id = id;
        this.bonDeLivraison = bonDeLivraison;
        this.produit = produit;
        this.quantiteCommande = quantiteCommande;
        this.prixUnitaire = prixUnitaire;
        this.totalHT = totalHT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BonDeLivraison getBonDeLivraison() {
        return bonDeLivraison;
    }

    public void setBonDeLivraison(BonDeLivraison bonDeLivraison) {
        this.bonDeLivraison = bonDeLivraison;
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

    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }
}

