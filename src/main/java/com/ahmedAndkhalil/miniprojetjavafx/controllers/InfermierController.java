package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Doctor;
import com.ahmedAndkhalil.miniprojetjavafx.models.Salle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private ComboBox<Salle> roomDropdown;

    @FXML
    private TextField roomNumberField;

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

        clearButton.setOnAction(actionEvent -> {
            firstnameField.setText("");
            lastnameField.setText("");
            roomNumberField.setText("");
            doctorDropdown.getSelectionModel().clearSelection();
            roomDropdown.getSelectionModel().clearSelection();
        });

        addPatientButton.setOnAction(actionEvent -> {
            if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || roomNumberField.getText().isEmpty() || doctorDropdown.getSelectionModel().isEmpty() || roomDropdown.getSelectionModel().isEmpty()) {
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
                String addQuery = "INSERT INTO patient (nom, prenom, numeroLit, numeroSalle, docteur, diagnostic) VALUES (?, ?, ?, ?, ?, ?)";
                try {
                    preparedStatement = statement.getConnection().prepareStatement(addQuery);
                    preparedStatement.setString(1, lastnameField.getText());
                    preparedStatement.setString(2, firstnameField.getText());
                    preparedStatement.setString(3, roomNumberField.getText());
                    preparedStatement.setString(4, roomDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.setString(5, doctorDropdown.getSelectionModel().getSelectedItem().idProperty().getValue());
                    preparedStatement.setString(6, "Aucun");
                    preparedStatement.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Ajoût d'un patient");
                    alert.setContentText("Le patient a été ajouté avec succès");
                    alert.showAndWait();
                    firstnameField.setText("");
                    lastnameField.setText("");
                    roomNumberField.setText("");
                    doctorDropdown.getSelectionModel().clearSelection();
                    roomDropdown.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
