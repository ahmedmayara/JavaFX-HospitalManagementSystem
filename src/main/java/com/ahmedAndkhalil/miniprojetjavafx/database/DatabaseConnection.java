package com.ahmedAndkhalil.miniprojetjavafx.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection connection;

    public Connection getConnection() {
        String databaseName = "miniprojetjavafx";
        String databaseUser = "root";
        String databasePassword = "";
        String databaseUrl = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
