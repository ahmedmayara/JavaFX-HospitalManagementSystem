package com.ahmedAndkhalil.miniprojetjavafx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private SimpleIntegerProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty bloc;
    private Doctor directeur;

    private List<Salle> salles = new ArrayList<>();

    public Service(String nom, String bloc, Doctor directeur) {
        this.nom = new SimpleStringProperty(nom);
        this.bloc = new SimpleStringProperty(bloc);
        this.directeur = directeur;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
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

    public Doctor getDirecteur() {
        return directeur;
    }

    public void setDirecteur(Doctor directeur) {
        this.directeur = directeur;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }
}
