package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Nurse;
import com.ahmedAndkhalil.miniprojetjavafx.models.Salle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ManageRoomsController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Salle, String> bedsColumn;

    @FXML
    private TextField bedsFieldUpdate;

    @FXML
    private Button consultButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Salle> displayRoomsTable;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Salle, String> endColumn;

    @FXML
    private TextField endFieldUpdate;

    @FXML
    private TableColumn<Salle, String> idColumn;

    @FXML
    private TextField idFieldUpdate;

    @FXML
    private ComboBox<Nurse> infirmierDropdown;

    @FXML
    private Button manageDoctors;

    @FXML
    private Button manageNurses;

    @FXML
    private Button manageRooms;

    @FXML
    private Button manageServices;

    @FXML
    private TableColumn<Salle, String> startColumn;

    @FXML
    private TextField startFieldUpdate;

    @FXML
    private TableColumn<Salle, String> surveillantColumn;

    @FXML
    private Button updateButton;

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

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        bedsColumn.setCellValueFactory(cellData -> cellData.getValue().nbrLitsProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().heureDebutProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().heureFinProperty());
        surveillantColumn.setCellValueFactory(cellData -> cellData.getValue().infirmierProperty());

        displayRoomsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idFieldUpdate.setText(newValue.getId());
                bedsFieldUpdate.setText(newValue.getNbrLits());
                startFieldUpdate.setText(newValue.getHeureDebut());
                endFieldUpdate.setText(newValue.getHeureFin());
            }
        });

        String query = "SELECT * FROM nurses";

        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                infirmierDropdown.getItems().add(new Nurse(resultSet.getString("id"), resultSet.getString("lastName"), resultSet.getString("firstName"), resultSet.getString("address"), resultSet.getString("date"), resultSet.getString("phone"), resultSet.getString("grade"), resultSet.getString("salaire")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        infirmierDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue.getId());
            }
        });
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

    @FXML
    public void getAllRooms() {
        displayRoomsTable.getItems().clear();
        String query = "SELECT * FROM salle, nurses WHERE nurses.id = salle.surveillant";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("numero");
                String beds = resultSet.getString("nombreLits");
                String start = resultSet.getString("heureDebut");
                String end = resultSet.getString("heureFin");
                String nurse = resultSet.getString("lastName") + " " + resultSet.getString("firstName");
                displayRoomsTable.getItems().add(new Salle(id, beds, start, end, nurse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDeleteRoom() {
        String query = "DELETE FROM salle WHERE numero = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            String id = displayRoomsTable.getSelectionModel().getSelectedItem().getId();
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Suppression de la salle");
            alert.setContentText("La salle a été supprimée avec succès");
            alert.showAndWait();
            getAllRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
