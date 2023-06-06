package com.alveole.model;


import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "ice")
    private int ice;

    @Column(name = "raison_social", length = 50)
    private String raisonSocial;

    @Column(name = "adresse", length = 100)
    private String adresse;

    @Column(name = "contact", length = 25)
    private String contact;

    @Column(name = "adresse_email", length = 100)
    private String adresseEmail;

    // Constructors, getters, and setters

    public Client() {
    }

    public Client(int ice, String raisonSocial, String adresse, String contact, String adresseEmail) {
        this.ice = ice;
        this.raisonSocial = raisonSocial;
        this.adresse = adresse;
        this.contact = contact;
        this.adresseEmail = adresseEmail;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
}
