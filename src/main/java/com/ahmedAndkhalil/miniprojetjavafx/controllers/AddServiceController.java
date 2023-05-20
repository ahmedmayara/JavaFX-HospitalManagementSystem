package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddServiceController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField blocNameField;

    @FXML
    private TextField serviceNameField;
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

        addButton.setOnAction(actionEvent -> {
            String addQuery = "INSERT INTO services VALUES (?, ?, ?, ?)";
            try {
                preparedStatement = statement.getConnection().prepareStatement(addQuery);
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, serviceNameField.getText());
                preparedStatement.setString(3, blocNameField.getText());
                preparedStatement.setString(4, null);
                preparedStatement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Ajoût d'un service");
                alert.setContentText("Le service a été ajouté avec succès");
                alert.showAndWait();
                serviceNameField.setText("");
                blocNameField.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
