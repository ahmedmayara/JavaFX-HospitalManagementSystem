package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController {

    @FXML
    private Button addDoctor;

    @FXML
    private Button addButton;

    @FXML
    private TextField adressField;

    @FXML
    private Button clearButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField firstameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField phoneField;

    Statement statement;

    PreparedStatement preparedStatement;

    ResultSet resultSet;


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
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            statement = connectNow.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String firstname = firstameField.getText();
        String lastname = lastnameField.getText();
        String adress = adressField.getText();
        String phone = phoneField.getText();
        String date = dateField.getValue().toString();

        try {
            preparedStatement = statement.getConnection().prepareStatement("INSERT INTO doctors (firstName, lastName, address, phone, date) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, adress);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, date);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Doctor added successfully");
            alert.setContentText("Doctor added successfully");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

