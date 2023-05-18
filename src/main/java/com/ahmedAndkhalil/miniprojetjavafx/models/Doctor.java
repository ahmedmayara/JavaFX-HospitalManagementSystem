package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Doctor extends Employe {
    private final SimpleStringProperty specialite;

    public Doctor(String id, String nom, String prenom, String adresse, String dateNaissance, String telephone, String specialite) {
        super(id, nom, prenom, adresse, dateNaissance, telephone);
        this.specialite = new SimpleStringProperty(specialite);
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
}