package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


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


    @Column(name = "fournisseur", nullable = false)
        private String fournisseur;



        // Constructors, getters, and setters
        public BonDeCommande() {
        }

    public BonDeCommande(int id, String numeroCommande, LocalDate dateCommande, String fournisseur) {
        this.id = id;
        this.numeroCommande = numeroCommande;
        this.dateCommande = dateCommande;
        this.fournisseur = fournisseur;
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

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
}

