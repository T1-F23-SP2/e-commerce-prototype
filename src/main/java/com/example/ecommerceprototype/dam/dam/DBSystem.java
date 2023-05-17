package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Constants;

import java.sql.*;

public class DBSystem {

    private static DBSystem instance;
    private Connection connection = null;

    DBSystem() {
        initializePostgresqlDatabase();
    }

    public static DBSystem getInstance() {
        if (instance == null) {
            instance = new DBSystem();
        }
        return instance;
    }

    //Connection to Database
    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(Constants.DB_URL2, Constants.DB_USER, Constants.DB_PASSWORD);
            if (connection != null) {
                System.out.println("Connection established");
            }
            else {
                System.out.println("Connection failed");
            }

        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }


    public void addAsset (Asset asset)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_asset(?,?,?,?,?,?)");

            statement.setString(1, asset.getType().getValue());
            statement.setString(2, asset.getName());
            statement.setString(3, asset.getPath());
            statement.setString(4, asset.getFormat());
            statement.setString(5, asset.getCategory().getValue());
            statement.setString(6, asset.getUuid());

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    int assetID = resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAsset (int assetID_in)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT delete_asset(?)");

            statement.setInt(1, assetID_in);

            statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int addTag (String tagName_in) {
        int tagID = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_tag(?)");

            statement.setString(1, tagName_in);

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    tagID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tagID;
    }

    public void addTagAssignment (int assetID_in, int tagID_in)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_tag_assignment(?,?)");

            statement.setInt(1,assetID_in);
            statement.setInt(2,tagID_in);

            statement.executeQuery();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllTags ()
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT get_all_tags()");

            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    int tag_id = resultSet.getInt(1);
                    String tag_name = resultSet.getString(2);

                    System.out.println("Tag ID: " + tag_id + ", Tag Name: " + tag_name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
