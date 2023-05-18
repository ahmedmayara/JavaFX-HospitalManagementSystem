package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Nurse extends Employe{
    private final SimpleStringProperty grade;
    private final SimpleStringProperty salaire;

    public Nurse(String id, String nom, String prenom, String adresse, String dateNaissance, String telephone, String grade, String salaire) {
        super(id, nom, prenom, adresse, dateNaissance, telephone);
        this.grade = new SimpleStringProperty(grade);
        this.salaire = new SimpleStringProperty(salaire);
    }

    public String getGrade() {
        return grade.get();
    }

    public SimpleStringProperty gradeProperty() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public String getSalaire() {
        return salaire.get();
    }

    public SimpleStringProperty salaireProperty() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire.set(salaire);
    }
}
