package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employe {
    private SimpleStringProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty adresse;
    private final SimpleStringProperty dateNaissance;
    private final SimpleStringProperty telephone;

    public Employe(String id, String nom, String prenom, String adresse, String dateNaissance, String telephone) {
        this.id = new SimpleStringProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.adresse = new SimpleStringProperty(adresse);
        this.dateNaissance = new SimpleStringProperty(dateNaissance);
        this.telephone = new SimpleStringProperty(telephone);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getDateNaissance() {
        return dateNaissance.get();
    }

    public SimpleStringProperty dateNaissanceProperty() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance.set(dateNaissance);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    @Override
    public String toString() {
        return nom.get() + " " + prenom.get();
    }
}
