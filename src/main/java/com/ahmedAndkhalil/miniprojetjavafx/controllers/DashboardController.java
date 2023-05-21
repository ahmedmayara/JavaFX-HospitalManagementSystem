package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label totalDoctors;

    @FXML
    private Label totalNurses;

    @FXML
    private Label totalRooms;

    @FXML
    private Label totalServices;

    @FXML
    private Button manageDoctors;

    @FXML
    private Button manageNurses;

    @FXML
    private Button manageRooms;

    @FXML
    private Button manageServices;
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

        String totalDoctorsQuery = "SELECT COUNT(*) FROM doctors";
        String totalNursesQuery = "SELECT COUNT(*) FROM nurses";
        String totalRoomsQuery = "SELECT COUNT(*) FROM salle";
        String totalServicesQuery = "SELECT COUNT(*) FROM services";

        try {
            resultSet = statement.executeQuery(totalDoctorsQuery);
            while (resultSet.next()) {
                totalDoctors.setText(resultSet.getString(1));
            }
            resultSet = statement.executeQuery(totalNursesQuery);
            while (resultSet.next()) {
                totalNurses.setText(resultSet.getString(1));
            }
            resultSet = statement.executeQuery(totalRoomsQuery);
            while (resultSet.next()) {
                totalRooms.setText(resultSet.getString(1));
            }
            resultSet = statement.executeQuery(totalServicesQuery);
            while (resultSet.next()) {
                totalServices.setText(resultSet.getString(1));
            }
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

    @FXML
    public void showManageRoomsView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndkhalil/miniprojetjavafx/manageRooms.fxml"));
            Stage stage = (Stage) manageRooms.getScene().getWindow();
            stage.setTitle("Hospital Management System - Manage Rooms");
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
}
