package com.alveole.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "bon_de_livraison")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BonDeLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numero_livraison", nullable = false)
    private String numeroLivraison;

    @Column(name = "numero_commande", nullable = false)
    private String numeroCommande;

    @Column(name = "date_livraison", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateLivraison;

    @Column(name = "client", nullable = false)
    private String client;

    // Constructors, getters, and setters


    public BonDeLivraison() {
    }

    public BonDeLivraison(int id, String numeroLivraison, String numeroCommande, LocalDate dateLivraison, String client) {
        this.id = id;
        this.numeroLivraison = numeroLivraison;
        this.numeroCommande = numeroCommande;
        this.dateLivraison = dateLivraison;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroLivraison() {
        return numeroLivraison;
    }

    public void setNumeroLivraison(String numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}

