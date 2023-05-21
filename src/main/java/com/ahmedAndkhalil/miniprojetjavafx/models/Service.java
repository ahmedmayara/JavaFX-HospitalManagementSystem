package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private SimpleStringProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty bloc;
    private final SimpleStringProperty docteur;

    public Service(String id, String nom, String bloc, String docteur) {
        this.id = new SimpleStringProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.bloc = new SimpleStringProperty(bloc);
        this.docteur = new SimpleStringProperty(docteur);
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

    public String getBloc() {
        return bloc.get();
    }

    public SimpleStringProperty blocProperty() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc.set(bloc);
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

    @Override
    public String toString() {
        return "Service " + nom.get() + " " + bloc.get();
    }
}
