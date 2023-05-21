package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Ordonnance {
    private  final SimpleStringProperty idOrdonnance;
    private  final SimpleStringProperty nomDocteur;
    private  final SimpleStringProperty listeMedicaments;
    private final SimpleStringProperty nomPatient;
    private final SimpleStringProperty dateVisite;
    private final SimpleStringProperty idPatient;

    public Ordonnance(SimpleStringProperty idOrdonnance, SimpleStringProperty nomDocteur, SimpleStringProperty listeMedicaments, SimpleStringProperty nomPatient, SimpleStringProperty dateVisite, SimpleStringProperty idPatient) {
        this.idOrdonnance = idOrdonnance;
        this.nomDocteur = nomDocteur;
        this.listeMedicaments = listeMedicaments;
        this.nomPatient = nomPatient;
        this.dateVisite = dateVisite;
        this.idPatient = idPatient;
    }

    public String getIdOrdonnance() {
        return idOrdonnance.get();
    }

    public SimpleStringProperty idOrdonnanceProperty() {
        return idOrdonnance;
    }

    public void setIdOrdonnance(String idOrdonnance) {
        this.idOrdonnance.set(idOrdonnance);
    }

    public String getNomDocteur() {
        return nomDocteur.get();
    }

    public SimpleStringProperty nomDocteurProperty() {
        return nomDocteur;
    }

    public void setNomDocteur(String nomDocteur) {
        this.nomDocteur.set(nomDocteur);
    }

    public String getListeMedicaments() {
        return listeMedicaments.get();
    }

    public SimpleStringProperty listeMedicamentsProperty() {
        return listeMedicaments;
    }

    public void setListeMedicaments(String listeMedicaments) {
        this.listeMedicaments.set(listeMedicaments);
    }

    public String getNomPatient() {
        return nomPatient.get();
    }

    public SimpleStringProperty nomPatientProperty() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient.set(nomPatient);
    }

    public String getDateVisite() {
        return dateVisite.get();
    }

    public SimpleStringProperty dateVisiteProperty() {
        return dateVisite;
    }

    public void setDateVisite(String dateVisite) {
        this.dateVisite.set(dateVisite);
    }

    public String getIdPatient() {
        return idPatient.get();
    }

    public SimpleStringProperty idPatientProperty() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient.set(idPatient);
    }

    @Override
    public String toString() {
        return "Ordonnance{" +
                "idOrdonnance=" + idOrdonnance +
                ", nomDocteur=" + nomDocteur +
                ", listeMedicaments=" + listeMedicaments +
                ", nomPatient=" + nomPatient +
                ", dateVisite=" + dateVisite +
                ", idPatient=" + idPatient +
                '}';
    }
}
