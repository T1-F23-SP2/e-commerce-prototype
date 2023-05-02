package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.PIMResourceRoot;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/*
    A set of helper functions, to aid in managing sql connections
 */
public abstract class SQLConnection {
    private static final String FOLDER_PATH = "credentials/";
    private static Properties defaultProperties;

    private static Properties getDefaultProperties() {
        if (SQLConnection.defaultProperties == null) {
            Properties properties = new Properties();
            properties.setProperty("host", "jdbc:postgresql://localhost:5432");
            properties.setProperty("database", "e_commerce_pim_db");
            properties.setProperty("username", "postgres");
            properties.setProperty("password", "");
            SQLConnection.defaultProperties = properties;
        }
        return SQLConnection.defaultProperties;
    }

    public static Properties loadProperties(InputStream inputStream, Properties defaultProperties) throws IOException {
        Properties properties = new Properties(defaultProperties);
        properties.load(inputStream);

        // Check host has optional url prefix
        String prefix = "jdbc:postgresql://";
        if (!properties.getProperty("host").startsWith(prefix)) {
            properties.setProperty("host", prefix + properties.getProperty("host"));
        }

        // Check host has optional url suffix
        String suffix = "/";
        if (!properties.getProperty("host").endsWith(suffix)) {
            properties.setProperty("host", properties.getProperty("host") + suffix);
        }


        return properties;
    }

    public static Properties loadProperties(InputStream inputStream) throws IOException {
        return SQLConnection.loadProperties(inputStream, SQLConnection.getDefaultProperties());
    }

    public static Properties loadMainProperties() throws IOException {
        return SQLConnection.loadProperties(PIMResourceRoot.class.getResourceAsStream(SQLConnection.FOLDER_PATH + "main.credentials"));
    }


    public static Properties loadTestProperties() throws IOException {
        Properties mainProperties = SQLConnection.loadMainProperties();
        Properties testProperties = SQLConnection.loadProperties(PIMResourceRoot.class.getResourceAsStream(SQLConnection.FOLDER_PATH + "test.credentials"), mainProperties);

        if (testProperties.getProperty("database").equals(mainProperties.getProperty("database"))) {
            throw new IllegalArgumentException("Test connection cannot point to the same database as the main connection");
        }

        return testProperties;
    }


    public static Connection getConnectionFromProperties(Properties properties) throws SQLException {
        String url = properties.getProperty("host") + properties.getProperty("database");

        DriverManager.registerDriver(new org.postgresql.Driver());

        return DriverManager.getConnection(
                url,
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }

    private static Connection getAdminConnection(Properties properties) throws SQLException {
        Properties adminProperties = new Properties(properties);
        adminProperties.setProperty("database", "postgres");
        return getConnectionFromProperties(adminProperties);
    }

    public static boolean createDatabase(Connection connection, String name) throws SQLException {
        // Need to bypass SQL Injections preventions, because otherwise it won't work.
        // Should be low risk, as no user input is passed in.
        PreparedStatement statement = connection.prepareStatement("CREATE DATABASE " + name);
        return statement.execute();
    }

    public static boolean createDatabase(Properties properties) throws SQLException {
        return createDatabase(getAdminConnection(properties), properties.getProperty("database"));
    }

    public static boolean dropDatabase(Connection connection, String name, boolean withForce) throws SQLException {
        if (name.equals("postgres")) throw new IllegalArgumentException("Cannot DROP protected database " + name);
        PreparedStatement statement;
        if (withForce) {
            statement = connection.prepareStatement(String.format("DROP DATABASE %s WITH (FORCE)", name));
        } else {
            statement = connection.prepareStatement(String.format("DROP DATABASE %s", name));
        }
        return statement.execute();
    }

    public static boolean dropDatabase(Properties properties, boolean withForce) throws SQLException {
        return dropDatabase(getAdminConnection(properties), properties.getProperty("database"), withForce);
    }



    public static Connection getMainConnection() throws IOException, SQLException {
        return getConnectionFromProperties(loadMainProperties());
    }

    public static Connection getTestConnection() throws IOException, SQLException {
        return getConnectionFromProperties(loadTestProperties());
    }



}