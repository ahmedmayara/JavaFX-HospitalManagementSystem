package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Doctor extends Employe {

    public Doctor(String id, String nom, String prenom, String adresse, String dateNaissance, String telephone) {
        super(id, nom, prenom, adresse, dateNaissance, telephone);
    }
}
