package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Doctor;
import com.ahmedAndkhalil.miniprojetjavafx.models.Nurse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.ahmedAndkhalil.miniprojetjavafx.models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DoctorDashboard implements Initializable {

    @FXML
    private TableColumn<Patient, String> adresseColumn;

    @FXML
    private Button ajouter;

    @FXML
    private Button consulter;

    @FXML
    private TableColumn<Patient, String> diagnosticColumn;

    @FXML
    private TextArea diagnosticField;

    @FXML
    private TableView<Patient> displayPatientTable;

    @FXML
    private TableColumn<Patient, String> docteurColumn;

    @FXML
    private TableColumn<Patient, String> idColumn;

    @FXML
    private TableColumn<Patient, String> nomColumn;

    @FXML
    private TableColumn<Patient, String> numeroLitColumn;

    @FXML
    private TableColumn<Patient, String> numeroSalleColumn;

    @FXML
    private TableColumn<Patient, String> ordonnanceColumn;

    @FXML
    private TextArea ordonnanceField;

    @FXML
    private TableColumn<Patient, String> prenomColumn;

    @FXML
    private TableColumn<Patient, String> serviceColumn;

    @FXML
    private TableColumn<Patient, String> telephoneColumn;

    @FXML
    void AddOrdonnance(ActionEvent event) {
        String addQuery = "INSERT INTO ordonnance Values(?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = statement.getConnection().prepareStatement(addQuery);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, docteurColumn.getText());
            preparedStatement.setString(3, ordonnanceField.getText());
            preparedStatement.setString(4, nomColumn.getText());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, idColumn.toString());
            preparedStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ordonnance");
            alert.setHeaderText("Ajout d'ordonnance");
            alert.setContentText("Ordonnance ajoutée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String updateQuery = "UPDATE patient SET iDordonnance = ? WHERE ordonnance.idPatient = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(updateQuery);
            String query = "SELECT * FROM ordonnance";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String idPatient = resultSet.getString("idPatient");
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, idPatient);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Statement statement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @FXML
    void getAllPatients(ActionEvent event) {
        displayPatientTable.getItems().clear();
        String query = "SELECT * FROM patient";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("numero");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                String telephone = resultSet.getString("telephone");
                String service = resultSet.getString("service");
                String numeroLit = resultSet.getString("numeroLit");
                String numeroSalle = resultSet.getString("numeroSalle");
                String docteur = resultSet.getString("docteur");
                String diagnostic = resultSet.getString("diagnostic");
                String ordonnance = resultSet.getString("iDordonnance");
                String query2 = "SELECT * FROM services WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(query2);
                preparedStatement.setString(1, service);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                while (resultSet2.next()) {
                    service = resultSet2.getString("nom");
                }
                String query3 = "SELECT * FROM doctors WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(query3);
                preparedStatement.setString(1, docteur);
                ResultSet resultSet3 = preparedStatement.executeQuery();
                while (resultSet3.next()) {
                    docteur = resultSet3.getString("firstname") + " " + resultSet3.getString("lastname");
                }
                String query4 = "SELECT * FROM ordonnance WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(query4);
                preparedStatement.setString(1, ordonnance);
                ResultSet resultSet4 = preparedStatement.executeQuery();
                while (resultSet4.next()) {
                    ordonnance = resultSet4.getString("listeMedicament");
                }
                idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
                prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
                telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
                serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
                numeroLitColumn.setCellValueFactory(cellData -> cellData.getValue().numeroLitProperty());
                numeroSalleColumn.setCellValueFactory(cellData -> cellData.getValue().numeroSalleProperty());
                docteurColumn.setCellValueFactory(cellData -> cellData.getValue().docteurProperty());
                diagnosticColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosticProperty());
                ordonnanceColumn.setCellValueFactory(cellData -> cellData.getValue().ordonnanceProperty());
                displayPatientTable.getItems().add(new Patient(id, nom, prenom, adresse, telephone, service, numeroLit, numeroSalle, docteur, diagnostic, ordonnance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseConnection connection = new DatabaseConnection();
            statement = connection.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        displayPatientTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                diagnosticField.setText(newValue.getDiagnostic());

                String ordo = newValue.getiDordonnance();
                String query = "SELECT * FROM ordonnance WHERE id = '" + ordo + "'";
                try {
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        String listeMedicament = resultSet.getString("listeMedicament");
                        ordonnanceField.setText(listeMedicament);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
