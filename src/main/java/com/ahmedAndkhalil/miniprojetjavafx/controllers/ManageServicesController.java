package com.ahmedAndkhalil.miniprojetjavafx.controllers;

import com.ahmedAndkhalil.miniprojetjavafx.database.DatabaseConnection;
import com.ahmedAndkhalil.miniprojetjavafx.models.Doctor;
import com.ahmedAndkhalil.miniprojetjavafx.models.Salle;
import com.ahmedAndkhalil.miniprojetjavafx.models.Service;
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
import java.util.ResourceBundle;

public class ManageServicesController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button addRoomToServiceButton;

    @FXML
    private TableColumn<Salle, String> bedsColumn;

    @FXML
    private TableColumn<Service, String> blocColumn;

    @FXML
    private TextField blocFieldUpdate;

    @FXML
    private Button consultButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button displayRoomsButton;

    @FXML
    private TableView<Salle> displayRoomsTable;

    @FXML
    private TableView<Service> displayServicesTable;

    @FXML
    private TableColumn<Service, String> idColumn;

    @FXML
    private TextField idFieldUpdate;

    @FXML
    private Button manageDoctors;

    @FXML
    private Button manageNurses;

    @FXML
    private Button manageRooms;

    @FXML
    private Button manageServices;

    @FXML
    private TableColumn<Service, String> managerColumn;

    @FXML
    private ComboBox<Doctor> managerDropdown;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TextField nameFieldUpdate;

    @FXML
    private ComboBox<Salle> roomsDropdown;

    @FXML
    private TableColumn<Salle, String> roomsIdColumn;

    @FXML
    private TableColumn<Salle, String> roomsManagerColumn;

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

        displayServicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayRoomsTable.getItems().clear();
                idFieldUpdate.setText(newSelection.getId());
                nameFieldUpdate.setText(newSelection.getNom());
                blocFieldUpdate.setText(newSelection.getBloc());
                String query = "SELECT * FROM salle WHERE service = ?";
                try {
                    preparedStatement = statement.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, newSelection.getId());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String id = resultSet.getString("numero");
                        String beds = resultSet.getString("nombreLits");
                        String manager = resultSet.getString("surveillant");
                        String getManager = "SELECT * FROM nurses WHERE id = ?";
                        preparedStatement = statement.getConnection().prepareStatement(getManager);
                        preparedStatement.setString(1, manager);
                        ResultSet resultSet1 = preparedStatement.executeQuery();
                        while (resultSet1.next()) {
                            manager = resultSet1.getString("lastName") + " " + resultSet1.getString("firstName");
                        }
                        roomsIdColumn.setCellValueFactory(celldata -> celldata.getValue().idProperty());
                        bedsColumn.setCellValueFactory(celldata -> celldata.getValue().nbrLitsProperty());
                        roomsManagerColumn.setCellValueFactory(celldata -> celldata.getValue().infirmierProperty());
                        displayRoomsTable.getItems().add(new Salle(id, beds, manager));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String query = "SELECT * FROM services";

        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("nom");
                String bloc = resultSet.getString("bloc");
                String manager = resultSet.getString("directeur");
                String getManager = "SELECT * FROM doctors WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(getManager);
                preparedStatement.setString(1, manager);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    manager = resultSet1.getString("lastName") + " " + resultSet1.getString("firstName");
                }
                idColumn.setCellValueFactory(celldata -> celldata.getValue().idProperty());
                nameColumn.setCellValueFactory(celldata -> celldata.getValue().nomProperty());
                blocColumn.setCellValueFactory(celldata -> celldata.getValue().blocProperty());
                managerColumn.setCellValueFactory(celldata -> celldata.getValue().docteurProperty());
                displayServicesTable.getItems().add(new Service(id, name, bloc, manager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String query2 = "SELECT * FROM doctors";

        try {
            resultSet = statement.executeQuery(query2);
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getString("id"), resultSet.getString("lastName"), resultSet.getString("firstName"), resultSet.getString("address"), resultSet.getString("date"), resultSet.getString("phone"), resultSet.getString("specialite"));
                managerDropdown.getItems().add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String getNullRooms = "SELECT * FROM salle WHERE service IS NULL";

        try {
            resultSet = statement.executeQuery(getNullRooms);
            while (resultSet.next()) {
                Salle salle = new Salle(resultSet.getString("numero"), resultSet.getString("nombreLits"), resultSet.getString("surveillant"));
                roomsDropdown.getItems().add(salle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteButton.setOnAction(actionEvent -> {
            int selectedIndex = displayServicesTable.getSelectionModel().getSelectedIndex();
            Service service = displayServicesTable.getItems().get(selectedIndex);
            String query1 = "DELETE FROM services WHERE id = ?";
            String query5 = "UPDATE salle SET service = NULL WHERE service = ?";
            try {
                preparedStatement = statement.getConnection().prepareStatement(query1);
                preparedStatement.setString(1, service.getId());
                preparedStatement.executeUpdate();
                preparedStatement = statement.getConnection().prepareStatement(query5);
                preparedStatement.setString(1, service.getId());
                preparedStatement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Service deleted successfully");
                alert.showAndWait();
                displayServicesTable.getItems().remove(selectedIndex);
                refreshTableRoomsTable();
                refreshRoomsDropdown();
                idFieldUpdate.setText("");
                nameFieldUpdate.setText("");
                blocFieldUpdate.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void addRoomToService() {
        int selectedIndex = displayServicesTable.getSelectionModel().getSelectedIndex();
        Service service = displayServicesTable.getItems().get(selectedIndex);
        Salle salle = roomsDropdown.getSelectionModel().getSelectedItem();
        String query = "UPDATE salle SET service = ? WHERE numero = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, service.getId());
            preparedStatement.setString(2, salle.getId());
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Room added to service successfully");
            alert.showAndWait();
            refreshTableRoomsTable();
            refreshRoomsDropdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshTableRoomsTable() {
        displayRoomsTable.getItems().clear();
        String query = "SELECT * FROM salle WHERE service = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, idFieldUpdate.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("numero");
                String beds = resultSet.getString("nombreLits");
                String manager = resultSet.getString("surveillant");
                String getManager = "SELECT * FROM nurses WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(getManager);
                preparedStatement.setString(1, manager);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    manager = resultSet1.getString("lastName") + " " + resultSet1.getString("firstName");
                }
                roomsIdColumn.setCellValueFactory(celldata -> celldata.getValue().idProperty());
                bedsColumn.setCellValueFactory(celldata -> celldata.getValue().nbrLitsProperty());
                roomsManagerColumn.setCellValueFactory(celldata -> celldata.getValue().infirmierProperty());
                displayRoomsTable.getItems().add(new Salle(id, beds, manager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshTableServicesTable() {
        displayServicesTable.getItems().clear();
        String query = "SELECT * FROM services";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("nom");
                String bloc = resultSet.getString("bloc");
                String manager = resultSet.getString("directeur");
                String getManager = "SELECT * FROM doctors WHERE id = ?";
                preparedStatement = statement.getConnection().prepareStatement(getManager);
                preparedStatement.setString(1, manager);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    manager = resultSet1.getString("lastName") + " " + resultSet1.getString("firstName");
                }
                idColumn.setCellValueFactory(celldata -> celldata.getValue().idProperty());
                nameColumn.setCellValueFactory(celldata -> celldata.getValue().nomProperty());
                blocColumn.setCellValueFactory(celldata -> celldata.getValue().blocProperty());
                managerColumn.setCellValueFactory(celldata -> celldata.getValue().docteurProperty());
                displayServicesTable.getItems().add(new Service(id, name, bloc, manager));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshRoomsDropdown() {
        String getNullRooms = "SELECT * FROM salle WHERE service IS NULL";
        roomsDropdown.getItems().clear();
        try {
            resultSet = statement.executeQuery(getNullRooms);
            while (resultSet.next()) {
                Salle salle = new Salle(resultSet.getString("numero"), resultSet.getString("nombreLits"), resultSet.getString("surveillant"));
                roomsDropdown.getItems().add(salle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editServiceAction() {
        String query = "UPDATE services SET nom = ?, bloc = ?, directeur = ? WHERE id = ?";
        try {
            preparedStatement = statement.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nameFieldUpdate.getText());
            preparedStatement.setString(2, blocFieldUpdate.getText());
            preparedStatement.setString(3, managerDropdown.getSelectionModel().getSelectedItem().getId());
            preparedStatement.setString(4, idFieldUpdate.getText());
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Service modifié avec succès");
            alert.showAndWait();
            refreshTableServicesTable();
            refreshTableRoomsTable();
            refreshRoomsDropdown();
            idFieldUpdate.setText("");
            nameFieldUpdate.setText("");
            blocFieldUpdate.setText("");
            managerDropdown.getSelectionModel().clearSelection();
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
}
