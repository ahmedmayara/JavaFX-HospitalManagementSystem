package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Doctor extends Employe {
    private final SimpleStringProperty specialite;
    private Service service;

    public Doctor(String nom, String prenom, String adresse, String dateNaissance, String telephone, String specialite, Service service) {
        super(nom, prenom, adresse, dateNaissance, telephone);
        this.specialite = new SimpleStringProperty(specialite);
        this.service = service;
    }

    public String getSpecialite() {
        return specialite.get();
    }

    public SimpleStringProperty specialiteProperty() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite.set(specialite);
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
