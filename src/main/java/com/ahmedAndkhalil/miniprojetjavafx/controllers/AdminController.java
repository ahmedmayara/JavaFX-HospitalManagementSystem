package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Doctor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TableColumn<Doctor, String> addressColumn;

    @FXML
    private TableColumn<Doctor, String> specialiteColumn;

    @FXML
    private TableColumn<Doctor, String> dateColumn;

    @FXML
    private TableView<Doctor> displayDoctorsTable;

    @FXML
    private TableColumn<Doctor, String> firstNameColumn;

    @FXML
    private TableColumn<Doctor, String> idColumn;

    @FXML
    private TableColumn<Doctor, String> lastnameColumn;

    @FXML
    private TableColumn<Doctor, String> phoneColumn;

    @FXML
    private Button manageDoctors;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button consultButton;

    @FXML
    private TextField firstameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField adressField;

    @FXML
    private TextField phoneField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField lastnameFieldUpdate;

    @FXML
    private TextField firstameFieldUpdate;

    @FXML
    private TextField adressFieldUpdate;

    @FXML
    private TextField spacialiteFieldUpdate;

    @FXML
    private TextField phoneFieldUpdate;

    @FXML
    private DatePicker dateFieldUpdate;

    @FXML
    private TextField idFieldUpdate;

    @FXML
    private Button updateButton;

    @FXML
    private Button manageServices;

    @FXML
    private Button manageNurses;

    @FXML
    private Button manageRooms;

    @FXML
    private TextField speacialiteField;


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

    }

    @FXML
    public void handleManageDoctors() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/manageDoctors.fxml"));
            Stage stage = (Stage) manageDoctors.getScene().getWindow();
            stage.setTitle("Hospital Management System - Manage Doctors");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showManageServicesView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/manageServices.fxml"));
            Stage stage = (Stage) manageServices.getScene().getWindow();
            stage.setTitle("Hospital Management System - Manage Services");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddDoctor() throws IOException {
        // Open the add doctor file in a new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/addDoctor.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Doctor");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void addDoctorAction() {
        String query = "INSERT INTO doctors VALUES (?,?,?,?,?,?,?)";

        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, firstameField.getText());
            preparedStatement.setString(3, lastnameField.getText());
            preparedStatement.setString(4, adressField.getText());
            preparedStatement.setString(5, phoneField.getText());
            preparedStatement.setString(6, dateField.getValue().toString());
            preparedStatement.setString(7, speacialiteField.getText());
            preparedStatement.execute();
            firstameField.clear();
            lastnameField.clear();
            adressField.clear();
            phoneField.clear();
            dateField.getEditor().clear();
            speacialiteField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Docteur ajouté");
            alert.setHeaderText("Information");
            alert.setContentText("Docteur ajouté avec succès");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void getAllDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String date = resultSet.getString("date");
                String specialite = resultSet.getString("specialite");
                idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
                lastnameColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
                addressColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
                dateColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
                phoneColumn.setCellValueFactory(cellData -> cellData.getValue().dateNaissanceProperty());
                specialiteColumn.setCellValueFactory(cellData -> cellData.getValue().specialiteProperty());
                displayDoctorsTable.getItems().add(new Doctor(id, firstName, lastName, address, phone, date, specialite));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteDoctor() {
        String query = "DELETE FROM doctors WHERE id = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, displayDoctorsTable.getSelectionModel().getSelectedItem().getId());
            preparedStatement.execute();
            displayDoctorsTable.getItems().remove(displayDoctorsTable.getSelectionModel().getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Docteur supprimé");
            alert.setHeaderText("Information");
            alert.setContentText("Docteur supprimé avec succès");
            alert.showAndWait();
            displayDoctorsTable.getItems().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setFields () {
        int doctorToEdit = displayDoctorsTable.getSelectionModel().getSelectedIndex();
        Doctor doctor = displayDoctorsTable.getItems().get(doctorToEdit);
        idFieldUpdate.setText(doctor.getId());
        firstameFieldUpdate.setText(doctor.getPrenom());
        lastnameFieldUpdate.setText(doctor.getNom());
        adressFieldUpdate.setText(doctor.getAdresse());
        dateFieldUpdate.setValue(LocalDate.parse(doctor.getTelephone()));
        phoneFieldUpdate.setText(doctor.getDateNaissance());
        spacialiteFieldUpdate.setText(doctor.getSpecialite());
    }

    @FXML
    public void editDoctorAction() {
        String query = "UPDATE doctors SET firstName = ?, lastName = ?, address = ?, phone = ?, date = ?, specialite = ? WHERE id = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, firstameFieldUpdate.getText());
            preparedStatement.setString(2, lastnameFieldUpdate.getText());
            preparedStatement.setString(3, adressFieldUpdate.getText());
            preparedStatement.setString(4, phoneFieldUpdate.getText());
            preparedStatement.setString(5, dateFieldUpdate.getValue().toString());
            preparedStatement.setString(6, spacialiteFieldUpdate.getText());
            preparedStatement.setString(7, idFieldUpdate.getText());
            preparedStatement.execute();
            firstameFieldUpdate.clear();
            lastnameFieldUpdate.clear();
            adressFieldUpdate.clear();
            phoneFieldUpdate.clear();
            dateFieldUpdate.getEditor().clear();
            spacialiteFieldUpdate.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Docteur modifié");
            alert.setHeaderText("Information");
            alert.setContentText("Docteur modifié avec succès");
            alert.showAndWait();
            displayDoctorsTable.getItems().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

