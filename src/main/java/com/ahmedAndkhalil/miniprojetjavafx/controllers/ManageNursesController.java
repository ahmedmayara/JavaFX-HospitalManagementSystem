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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageNursesController implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Nurse, String> addressColumn;

    @FXML
    private TextField adressFieldUpdate;

    @FXML
    private Button consultButton;

    @FXML
    private TableColumn<Nurse, String> dateColumn;

    @FXML
    private DatePicker dateFieldUpdate;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Nurse> displayNursesTable;

    @FXML
    private TableColumn<Nurse, String> firstNameColumn;

    @FXML
    private TextField firstameFieldUpdate;

    @FXML
    private TableColumn<Nurse, String> gradeColumn;

    @FXML
    private TableColumn<Nurse, String> idColumn;

    @FXML
    private TextField idFieldUpdate;

    @FXML
    private TableColumn<Nurse, String> lastnameColumn;

    @FXML
    private TextField lastnameFieldUpdate;

    @FXML
    private Button manageDoctors;

    @FXML
    private Button manageNurses;

    @FXML
    private Button manageRooms;

    @FXML
    private Button manageServices;

    @FXML
    private TableColumn<Nurse, String> phoneColumn;

    @FXML
    private TextField phoneFieldUpdate;

    @FXML
    private TableColumn<Nurse, String> salaireColumn;

    @FXML
    private Button selectButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button addNurseButton;

    @FXML
    private TextField adressField;

    @FXML
    private Button clearButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField firstameField;

    @FXML
    private TextField gradeField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField salaireField;

    @FXML
    private TextField salaireFieldUpdate;

    @FXML
    private TextField gradeFieldUpdate;



    Statement statement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;

    @FXML
    public void showAddNurseView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/addNurse.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Nurse");
            stage.setScene(new Scene(root));
            stage.show();
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
    public void showManageNursesView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/manageNurses.fxml"));
            Stage stage = (Stage) manageNurses.getScene().getWindow();
            stage.setTitle("Hospital Management System - Manage Nurses");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
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
    }



    @FXML
    public void addNurseAction() {
        String query = "INSERT INTO nurses (id, firstname, lastname, address, phone, date, salaire, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, firstameField.getText());
            preparedStatement.setString(3, lastnameField.getText());
            preparedStatement.setString(4, adressField.getText());
            preparedStatement.setString(5, phoneField.getText());
            preparedStatement.setString(6, dateField.getValue().toString());
            preparedStatement.setString(7, salaireField.getText());
            preparedStatement.setString(8, gradeField.getText());
            preparedStatement.execute();
            firstameField.clear();
            lastnameField.clear();
            adressField.clear();
            phoneField.clear();
            dateField.getEditor().clear();
            salaireField.clear();
            gradeField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajouter une infirmière");
            alert.setHeaderText("Information");
            alert.setContentText("L'infirmière a été ajoutée avec succès");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void getAllNurses() {
        displayNursesTable.getItems().clear();
        String query = "SELECT * FROM nurses";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String date = resultSet.getString("date");
                String salaire = resultSet.getString("salaire");
                String grade = resultSet.getString("grade");
                idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
                lastnameColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
                addressColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
                phoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
                dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateNaissanceProperty());
                gradeColumn.setCellValueFactory(cellData -> cellData.getValue().salaireProperty());
                salaireColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
                displayNursesTable.getItems().add(new Nurse(id, firstname, lastname, address, phone, date, salaire, grade));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteNurse() {
        String query = "DELETE FROM nurses WHERE id = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, displayNursesTable.getSelectionModel().getSelectedItem().getId());
            preparedStatement.execute();
            displayNursesTable.getItems().remove(displayNursesTable.getSelectionModel().getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supprimer une infirmière");
            alert.setHeaderText("Information");
            alert.setContentText("L'infirmière a été supprimée avec succès");
            alert.showAndWait();
            displayNursesTable.getItems().clear();
            getAllNurses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setFields() {
        int nurseToEdit = displayNursesTable.getSelectionModel().getSelectedIndex();
        Nurse nurse = displayNursesTable.getItems().get(nurseToEdit);
        idFieldUpdate.setText(nurse.getId());
        firstameFieldUpdate.setText(nurse.getPrenom());
        lastnameFieldUpdate.setText(nurse.getNom());
        adressFieldUpdate.setText(nurse.getAdresse());
        dateFieldUpdate.setValue(LocalDate.parse(nurse.getTelephone()));
        phoneFieldUpdate.setText(nurse.getDateNaissance());
        salaireFieldUpdate.setText(nurse.getSalaire());
        gradeFieldUpdate.setText(nurse.getGrade());
    }

    @FXML
    public void editNurseAction() {
        String query = "UPDATE nurses SET firstName = ?, lastName = ?, address = ?, phone = ?, date = ?, salaire = ?, grade = ? WHERE id = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, firstameFieldUpdate.getText());
            preparedStatement.setString(2, lastnameFieldUpdate.getText());
            preparedStatement.setString(3, adressFieldUpdate.getText());
            preparedStatement.setString(4, phoneFieldUpdate.getText());
            preparedStatement.setString(5, dateFieldUpdate.getValue().toString());
            preparedStatement.setString(6, salaireFieldUpdate.getText());
            preparedStatement.setString(7, gradeFieldUpdate.getText());
            preparedStatement.setString(8, idFieldUpdate.getText());
            preparedStatement.execute();
            firstameFieldUpdate.clear();
            lastnameFieldUpdate.clear();
            adressFieldUpdate.clear();
            phoneFieldUpdate.clear();
            dateFieldUpdate.getEditor().clear();
            salaireFieldUpdate.clear();
            gradeFieldUpdate.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("infirmier modifié");
            alert.setHeaderText("Information");
            alert.setContentText("infirmier modifié avec succès");
            alert.showAndWait();
            displayNursesTable.getItems().clear();
            getAllNurses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
