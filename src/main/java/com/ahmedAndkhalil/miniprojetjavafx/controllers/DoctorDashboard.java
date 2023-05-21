package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

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
    private TableColumn<Patient, String> telephoneColumn;

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
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseConnection connection = new DatabaseConnection();
            statement = connection.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String getAllPatientsQuery = "SELECT * FROM patient";

        try {
            resultSet = statement.executeQuery(getAllPatientsQuery);
            while (resultSet.next()) {
                String id = resultSet.getString("numero");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                String numeroTelephone = resultSet.getString("telephone");
                String service = resultSet.getString("service");
                String getServiceQuery = "SELECT * FROM services WHERE id = " + service;
                preparedStatement = statement.getConnection().prepareStatement(getServiceQuery);
                ResultSet serviceResult = preparedStatement.executeQuery();
                while (serviceResult.next()) {
                    service = serviceResult.getString("nom");
                }
                String numeroLit = resultSet.getString("numeroLit");
                String numeroSalle = resultSet.getString("numeroSalle");
                String docteur = resultSet.getString("docteur");
                String getDoctorQuery = "SELECT * FROM doctors WHERE id = " + docteur;
                preparedStatement = statement.getConnection().prepareStatement(getDoctorQuery);
                ResultSet doctorResult = preparedStatement.executeQuery();
                while (doctorResult.next()) {
                    docteur = doctorResult.getString("lastname") + " " + doctorResult.getString("firstname");
                }
                String diagnostic = resultSet.getString("diagnostic");
                String ordonnance = resultSet.getString("ordonnance");
                idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
                prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
                telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
                serviceColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
                numeroLitColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
                adresseColumn.setCellValueFactory(cellData -> cellData.getValue().numeroLitProperty());
                numeroSalleColumn.setCellValueFactory(cellData -> cellData.getValue().numeroSalleProperty());
                docteurColumn.setCellValueFactory(cellData -> cellData.getValue().docteurProperty());
                diagnosticColumn.setCellValueFactory(cellData -> cellData.getValue().diagnosticProperty());
                ordonnanceColumn.setCellValueFactory(cellData -> cellData.getValue().ordonnanceProperty());
                displayPatientTable.getItems().add(new Patient(id, nom, prenom, adresse, numeroTelephone, service, numeroLit, numeroSalle, docteur, diagnostic, ordonnance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        displayPatientTable.getSelectionModel().selectedItemProperty().addListener((observableValue, patient, t1) -> {
            diagnosticField.setText(t1.getDiagnostic());
            ordonnanceField.setText(t1.getOrdonnance());
        });

        ajouter.setOnAction(actionEvent -> {
            String insertQuery = "INSERT INTO ordonnance (id, nomDocteur, listeMedicament, nomPatient, dateVisite, idPatient) VALUES (?, ?, ?, ?, ?, ?)";
            String updateQuery = "UPDATE patient SET diagnostic = ?, ordonnance = ? WHERE numero = ?";
            try {
                preparedStatement = statement.getConnection().prepareStatement(insertQuery);
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, docteurColumn.getCellData(displayPatientTable.getSelectionModel().getSelectedIndex()));
                preparedStatement.setString(3, ordonnanceField.getText());
                preparedStatement.setString(4, nomColumn.getCellData(displayPatientTable.getSelectionModel().getSelectedIndex()));
                preparedStatement.setString(5, new Date().toString());
                preparedStatement.setString(6, idColumn.getCellData(displayPatientTable.getSelectionModel().getSelectedIndex()));
                preparedStatement.execute();
                preparedStatement = statement.getConnection().prepareStatement(updateQuery);
                preparedStatement.setString(1, diagnosticField.getText());
                preparedStatement.setString(2, ordonnanceField.getText());
                preparedStatement.setString(3, idColumn.getCellData(displayPatientTable.getSelectionModel().getSelectedIndex()));
                preparedStatement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Ordonnance ajoutée");
                alert.setContentText("L'ordonnance a été ajoutée avec succès");
                alert.showAndWait();
                ordonnanceField.setText("");
                diagnosticField.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
