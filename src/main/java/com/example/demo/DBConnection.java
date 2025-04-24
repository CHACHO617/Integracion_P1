package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=laboratorio;encrypt=true;trustServerCertificate=true";
    private static final String USER = "emeri";
    private static final String PASSWORD = "Chacho123!"; // replace with your SQL Server password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
