module com.ahmed.miniprojetjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.ahmed.miniprojetjavafx to javafx.fxml;
    opens com.ahmed.miniprojetjavafx.controllers to javafx.fxml;
    exports com.ahmed.miniprojetjavafx;
    exports com.ahmed.miniprojetjavafx.controllers;
    exports com.ahmed.miniprojetjavafx.database;
}