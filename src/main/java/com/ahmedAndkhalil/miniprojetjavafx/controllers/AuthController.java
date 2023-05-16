package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;

public class AuthController {

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private PasswordField passwordRegisterField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameRegisterField;

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public void handleLogin() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else {
            Connection connection = databaseConnection.getConnection();

            String checkLoginAndRoleQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            try {
                var preparedStatement = connection.prepareStatement(checkLoginAndRoleQuery);
                preparedStatement.setString(1, usernameField.getText());
                preparedStatement.setString(2, passwordField.getText());
                var resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    if (role.equals("ADMIN")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndKhalil/miniprojetjavafx/admin.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setTitle("Hospital Management System - Admin");
                        stage.setScene(new Scene(fxmlLoader.load()));
                        stage.show();
                    } else if (role.equals("DOCTEUR")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndKhalil/miniprojetjavafx/doctor-dashboard.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setTitle("Hospital Management System - Doctor");
                        stage.setScene(new Scene(fxmlLoader.load()));
                        stage.show();
                    } else if (role.equals("INFERMIER")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmedAndKhalil/miniprojetjavafx/patient.fxml"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setTitle("Hospital Management System - Patient");
                        stage.setScene(new Scene(fxmlLoader.load()));
                        stage.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid credentials");
                    alert.setContentText("Please check your credentials");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCancel() {
        Platform.exit();
    }

    public void handleRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmed/miniprojetjavafx/register.fxml"));
            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setTitle("Hospital Management System - Register");
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleRegisterAction() {
            if (usernameRegisterField.getText().isEmpty() || passwordRegisterField.getText().isEmpty() || firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty fields");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            } else {
                try {
                    Connection connection = databaseConnection.getConnection();
                    String insertQuery = "INSERT INTO users (username, password, firstname, lastname) VALUES (?, ?, ?, ?)";
                    var preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, usernameRegisterField.getText());
                    preparedStatement.setString(2, passwordRegisterField.getText());
                    preparedStatement.setString(3, firstnameField.getText());
                    preparedStatement.setString(4, lastnameField.getText());
                    preparedStatement.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Registration successful");
                    alert.setContentText("You can now login");
                    alert.showAndWait();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ahmed/miniprojetjavafx/login.fxml"));
                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.setTitle("Hospital Management System - Login");
                    stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}
