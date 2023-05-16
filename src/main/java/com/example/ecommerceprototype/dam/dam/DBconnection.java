package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    public Connection conn;

    public Connection getConn() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            conn = DriverManager.getConnection(Constants.DB_URL2, Constants.DB_USER, Constants.DB_PASSWORD);
            if (conn != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }

        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        }
        return conn;
    }




}
