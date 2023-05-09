package com.example.ecommerceprototype.pim.sql_helpers;

import com.example.ecommerceprototype.pim.PIMResourceRoot;
import com.example.ecommerceprototype.pim.exceptions.sql.SQLDatabaseNotFoundException;

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

    public static Properties loadProperties(String filename, Properties defaultProperties) throws IOException {
        String path = SQLConnection.FOLDER_PATH + filename;
        InputStream inputStream = PIMResourceRoot.class.getResourceAsStream(path);

        if (inputStream == null) throw new IOException(String.format("""
        Could not find file: %s
        
        Look in src/main/resources/com/example/ecommerceprototype/pim/credentials/README.md, for how to setup credentials.
        
        """, PIMResourceRoot.getPathString(filename)));

        return loadProperties(inputStream, defaultProperties);
    }

    public static Properties loadProperties(String filename) throws IOException {
        return SQLConnection.loadProperties(filename, SQLConnection.getDefaultProperties());
    }

    public static Properties loadMainProperties() throws IOException {
        return SQLConnection.loadProperties("main.credentials");
    }


    public static Properties loadTestProperties() throws IOException {
        Properties mainProperties = SQLConnection.loadMainProperties();
        Properties testProperties = SQLConnection.loadProperties("test.credentials", mainProperties);

        if (testProperties.getProperty("database").equals(mainProperties.getProperty("database"))) {
            throw new IllegalArgumentException("Test connection cannot point to the same database as the main connection");
        }

        return testProperties;
    }


    public static Connection getConnectionFromProperties(Properties properties) throws SQLException {
        String url = properties.getProperty("host") + properties.getProperty("database");

        DriverManager.registerDriver(new org.postgresql.Driver());
        try {
            return DriverManager.getConnection(
                    url,
                    properties.getProperty("username"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            throw SQLExceptionParser.parse(e); // Rethrow exception as a more descriptive exception.
        }
    }

    // Returns a connection to the postgres database, based on the credentials in the properties.
    private static Connection getConnectionForDefaultDB(Properties properties) throws SQLException {
        Properties adminProperties = new Properties(properties);
        adminProperties.setProperty("database", "postgres");
        return getConnectionFromProperties(adminProperties);
    }

    public static boolean createDatabase(Connection connection, String name) throws SQLException {
        // Need to bypass SQL Injections preventions, because otherwise it won't work.
        // Should be low risk, as no user input is passed in.
        PreparedStatement statement = connection.prepareStatement("CREATE DATABASE " + name);
        return SQLExceptionParser.executePreparedStatement(statement);
    }

    public static boolean createDatabase(Properties properties) throws SQLException {
        // Wrap in try-with resource, so connection is automatically closed again.
        try (Connection connection = getConnectionForDefaultDB(properties)) {
            return createDatabase(connection, properties.getProperty("database"));
        }
    }

    public static boolean dropDatabase(Connection connection, String name, boolean withForce) throws SQLException {
        if (name.equals("postgres")) throw new IllegalArgumentException("Cannot DROP protected database " + name);
        PreparedStatement statement;
        if (withForce) {
            statement = connection.prepareStatement(String.format("DROP DATABASE %s WITH (FORCE)", name));
        } else {
            statement = connection.prepareStatement(String.format("DROP DATABASE %s", name));
        }
        return SQLExceptionParser.executePreparedStatement(statement);
    }
    /*
        Drop the database which the credentials in the properties points to.
     */
    public static boolean dropDatabase(Properties properties, boolean withForce) throws SQLException {
        // Wrap in try-with resource, so connection is automatically closed again.
        try (Connection connection = getConnectionForDefaultDB(properties)) {
            return dropDatabase(connection, properties.getProperty("database"), withForce);
        }
    }


    public static boolean isDatabaseInPropertiesPresent(Properties properties) throws SQLException {
        // Wrap in try-with resource, so connection is automatically closed again.
        try (Connection connection = getConnectionFromProperties(properties)) {
            // If is able to connect to database, then database is present
            return true;
        } catch (SQLDatabaseNotFoundException e) {
            // If throwing SQLDatabaseNotFoundException, then return false.
            return false;
            // Do not catch any other SQLRelated errors.
        }
    }

    public static Connection getMainConnection() throws IOException, SQLException {
        return getConnectionFromProperties(loadMainProperties());
    }

    public static Connection getTestConnection() throws IOException, SQLException {
        return getConnectionFromProperties(loadTestProperties());
    }


    // If database is not present in the system, then create the database. And execute initializer.
    public static Connection getConnectionFromPropertiesInitializeIfNeeded(Properties properties, SQLInitializer initializer) throws SQLException, IOException {
        Connection connection;
        try {
            connection = getConnectionFromProperties(properties);
        } catch (SQLDatabaseNotFoundException e) {
            createDatabase(properties);
            connection = getConnectionFromProperties(properties);
            initializer.initialize(connection);
        }
        return connection;
    }


    public static Connection getMainConnectionInitializeIfNeeded(SQLInitializer initializer) throws SQLException, IOException {
        return getConnectionFromPropertiesInitializeIfNeeded(loadMainProperties(), initializer);
    }

    /*
        Get main connection and run default initializer if needed
     */
    public static Connection getMainConnectionInitializeIfNeeded() throws SQLException, IOException {
        return getMainConnectionInitializeIfNeeded(new SQLConnectionMainInitializer());
    }

    public static Connection getTestConnectionInitializeIfNeeded(SQLInitializer initializer) throws SQLException, IOException {
        return getConnectionFromPropertiesInitializeIfNeeded(loadTestProperties(), initializer);
    }



    /*
        Get test connection and run default initializer if needed
     */
    public static Connection getTestConnectionInitializeIfNeeded() throws SQLException, IOException {
        return getTestConnectionInitializeIfNeeded(new SQLConnectionTestInitializer());
    }
}