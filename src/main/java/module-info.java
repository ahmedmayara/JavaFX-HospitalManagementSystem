module com.ahmed.miniprojetjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.ahmedAndkhalil.miniprojetjavafx to javafx.fxml;
    opens com.ahmedAndkhalil.miniprojetjavafx.controllers to javafx.fxml;
    exports com.ahmedAndkhalil.miniprojetjavafx;
    exports com.ahmedAndkhalil.miniprojetjavafx.controllers;
    exports com.ahmedAndkhalil.miniprojetjavafx.database;
}