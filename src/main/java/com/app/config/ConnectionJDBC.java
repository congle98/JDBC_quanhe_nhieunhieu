package com.app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionJDBC {
    private static String URL = "jdbc:mysql://localhost:3306/bookmanagermanymany";
    private static String USER = "root";
    private static String PASSWORD = "034266145";
    private static Connection connection;

    private ConnectionJDBC(){

    }
    public static Connection getConnection(){
        if(connection==null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection =  DriverManager.getConnection(URL,USER,PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}
