package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleStringProperty;

public class Salle {
    private SimpleStringProperty id;
    private SimpleStringProperty nbrLits;
    private SimpleStringProperty heureDebut;
    private SimpleStringProperty heureFin;
    private SimpleStringProperty infirmier;

    public Salle(String id, String nbrLits, String heureDebut, String heureFin, String infirmier) {
        this.id = new SimpleStringProperty(id);
        this.nbrLits = new SimpleStringProperty(nbrLits);
        this.heureDebut = new SimpleStringProperty(heureDebut);
        this.heureFin = new SimpleStringProperty(heureFin);
        this.infirmier = new SimpleStringProperty(infirmier);
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

    public String getNbrLits() {
        return nbrLits.get();
    }

    public SimpleStringProperty nbrLitsProperty() {
        return nbrLits;
    }

    public void setNbrLits(String nbrLits) {
        this.nbrLits.set(nbrLits);
    }

    public String getHeureDebut() {
        return heureDebut.get();
    }

    public SimpleStringProperty heureDebutProperty() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut.set(heureDebut);
    }

    public String getHeureFin() {
        return heureFin.get();
    }

    public SimpleStringProperty heureFinProperty() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin.set(heureFin);
    }

    public String getInfirmier() {
        return infirmier.get();
    }

    public SimpleStringProperty infirmierProperty() {
        return infirmier;
    }

    public void setInfirmier(String infirmier) {
        this.infirmier.set(infirmier);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nbrLits=" + nbrLits +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", infirmier=" + infirmier +
                '}';
    }
}
