package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private void initializePostgresqlDatabase()
    {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(Constants.DB_URL2, Constants.DB_USER, Constants.DB_PASSWORD);
            if (connection != null) {
                System.out.println("Connection established DB");
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

    public Connection getConn()
    {
        return connection;
    }


    public void addAsset(Asset asset)
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



    public void deleteAsset(int assetID_in)
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


    public int addTag(String tagName_in)
    {
        tagName_in.toLowerCase();

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

    public void addTagAssignment(int assetID_in, int tagID_in)
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

    public List<String> getAllTags ()
    {
        List<String> tags = null;

        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT tag_name FROM Tags");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String tag = resultSet.getString("tag_name");
                tags.add(tag);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

      return tags;
    }


    public List<String> getTagsForAssetID (int assetid_in)
    {
        List<String> tags = new ArrayList<>();
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT t.tag_name FROM Tags t INNER JOIN Tag_Assignments ta ON t.tag_id = ta.tag_id WHERE ta.asset_id = ?");

            statement.setInt(1,assetid_in);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String tag = resultSet.getString("tag_name");
                tags.add(tag);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return tags;
    }


}
