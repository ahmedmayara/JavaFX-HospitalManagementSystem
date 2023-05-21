package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Doctor;
import com.ahmedAndkhalil.miniprojetjavafx.models.Salle;
import com.ahmedAndkhalil.miniprojetjavafx.models.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InfermierController implements Initializable {
    @FXML
    private Button addPatientButton;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<Doctor> doctorDropdown;

    @FXML
    private ComboBox<Service> serviceDropdown;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<Salle> roomDropdown;

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextArea diagnosticTextArea;

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

        String getDoctorsQuery = "SELECT * FROM doctors";

        try {
            resultSet = statement.executeQuery(getDoctorsQuery);
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getString("id"), resultSet.getString("lastName"), resultSet.getString("firstName"), resultSet.getString("address"), resultSet.getString("date"), resultSet.getString("phone"), resultSet.getString("specialite"));
                doctorDropdown.getItems().add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String getRoomsQuery = "SELECT * FROM salle";

        try {
            resultSet = statement.executeQuery(getRoomsQuery);
            while (resultSet.next()) {
                Salle salle = new Salle(resultSet.getString("numero"), resultSet.getString("nombreLits"), resultSet.getString("surveillant"));
                roomDropdown.getItems().add(salle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String getServicesQuery = "SELECT * FROM services";

        try {
            resultSet = statement.executeQuery(getServicesQuery);
            while (resultSet.next()) {
                Service service = new Service(resultSet.getString("id"), resultSet.getString("nom"), resultSet.getString("bloc"), resultSet.getString("directeur"));
                serviceDropdown.getItems().add(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        clearButton.setOnAction(actionEvent -> {
            firstnameField.setText("");
            lastnameField.setText("");
            roomNumberField.setText("");
            doctorDropdown.getSelectionModel().clearSelection();
            roomDropdown.getSelectionModel().clearSelection();
        });

        addPatientButton.setOnAction(actionEvent -> {
            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty() || roomNumberField.getText().isEmpty() || doctorDropdown.getSelectionModel().isEmpty() || roomDropdown.getSelectionModel().isEmpty() || serviceDropdown.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur d'ajout d'un patient");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
            } else if (roomDropdown.getSelectionModel().getSelectedItem().nbrLitsProperty().equals(0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur d'ajout d'un patient");
                alert.setContentText("La salle est pleine");
                alert.showAndWait();
            } else {
                String addQuery = "INSERT INTO patient (nom, prenom, adresse, telephone, service, numeroLit, numeroSalle, docteur, diagnostic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    preparedStatement = statement.getConnection().prepareStatement(addQuery);
                    preparedStatement.setString(1, lastnameField.getText());
                    preparedStatement.setString(2, firstnameField.getText());
                    preparedStatement.setString(3, addressField.getText());
                    preparedStatement.setString(4, phoneField.getText());
                    preparedStatement.setString(5, serviceDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.setString(6, roomNumberField.getText());
                    preparedStatement.setString(7, roomDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.setString(8, doctorDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.setString(9, diagnosticTextArea.getText());
                    preparedStatement.executeUpdate();

                    String updateRoomQuery = "UPDATE salle SET nombreLits = nombreLits - 1 WHERE numero = ?";
                    preparedStatement = statement.getConnection().prepareStatement(updateRoomQuery);
                    preparedStatement.setString(1, roomDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText("Patient ajouté avec succès");
                    alert.setContentText("Le patient a été ajouté avec succès");
                    alert.showAndWait();

                    firstnameField.setText("");
                    lastnameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                    roomNumberField.setText("");
                    diagnosticTextArea.setText("");
                    doctorDropdown.getSelectionModel().clearSelection();
                    roomDropdown.getSelectionModel().clearSelection();
                    serviceDropdown.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
