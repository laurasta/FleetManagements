package com.example.drivingsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {
    public static Connection connectToDB(){
        Connection conn = null;
        String url = "jdbc:postgresql://localhost:5432/DriverSystem";
        String user = "postgres";
        String pass = "pervazninkai";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public static void disconnectFromDB(Connection connection, Statement statement){
        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
