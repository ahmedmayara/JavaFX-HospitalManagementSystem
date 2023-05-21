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

public class AddRoomController implements Initializable {
    @FXML
    private TextField addBedsField;

    @FXML
    private TextField addEndField;

    @FXML
    private Button addRoomButton;

    @FXML
    private TextField addStartField;

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

        addRoomButton.setOnAction(actionEvent -> {
            String addQuery = "INSERT INTO salle VALUES (?, ?, ?, ?, ?, ?)";
            try {
                preparedStatement = statement.getConnection().prepareStatement(addQuery);
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, addBedsField.getText());
                preparedStatement.setString(3, addStartField.getText());
                preparedStatement.setString(4, addEndField.getText());
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, null);
                preparedStatement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Ajoût d'une salle");
                alert.setContentText("La salle a été ajoutée avec succès");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
