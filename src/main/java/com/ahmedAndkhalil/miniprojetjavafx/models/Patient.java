package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Patient {
    private final SimpleStringProperty numero;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty numeroLit;
    private final SimpleStringProperty adresse;

    private final SimpleStringProperty telephone;

    private final SimpleStringProperty service;
    private final SimpleStringProperty numeroSalle;
    private final SimpleStringProperty docteur;
    private final SimpleStringProperty diagnostic;

    public Patient(SimpleStringProperty numero, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty numeroLit, SimpleStringProperty adresse, SimpleStringProperty telephone, SimpleStringProperty service, SimpleStringProperty numeroSalle, SimpleStringProperty docteur, SimpleStringProperty diagnostic) {
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroLit = numeroLit;
        this.adresse = adresse;
        this.telephone = telephone;
        this.service = service;
        this.numeroSalle = numeroSalle;
        this.docteur = docteur;
        this.diagnostic = diagnostic;
    }

    public String getNumero() {
        return numero.get();
    }

    public SimpleStringProperty numeroProperty() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero.set(numero);
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

    public String getNumeroLit() {
        return numeroLit.get();
    }

    public SimpleStringProperty numeroLitProperty() {
        return numeroLit;
    }

    public void setNumeroLit(String numeroLit) {
        this.numeroLit.set(numeroLit);
    }

    public String getNumeroSalle() {
        return numeroSalle.get();
    }

    public SimpleStringProperty numeroSalleProperty() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle.set(numeroSalle);
    }

    public String getDocteur() {
        return docteur.get();
    }

    public SimpleStringProperty docteurProperty() {
        return docteur;
    }

    public void setDocteur(String docteur) {
        this.docteur.set(docteur);
    }

    public String getDiagnostic() {
        return diagnostic.get();
    }

    public SimpleStringProperty diagnosticProperty() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic.set(diagnostic);
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

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getService() {
        return service.get();
    }

    public SimpleStringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }
}
