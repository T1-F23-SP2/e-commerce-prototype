package com.example.ecommerceprototype.dam.dam;

import com.example.ecommerceprototype.dam.constants.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
                System.out.println("Connection established DB");
            } else {
                System.out.println("Connection failed");
            }

        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    public Connection getConn() {
        return connection;
    }


    public int addAsset(Asset asset) {
        int assetID = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_asset(?,?,?,?,?,?)");

            statement.setString(1, asset.getType().getValue());
            statement.setString(2, asset.getName());
            statement.setString(3, asset.getPath());
            statement.setString(4, asset.getFormat());
            statement.setString(5, asset.getCategory().getValue());
            statement.setString(6, asset.getUuid());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assetID = resultSet.getInt(1);

                }
            }
        } catch (Exception e)
        {
            System.out.println("Something went wrong! No asset added!");
        }
        return assetID;
    }


    public boolean deleteAsset(int assetID_in) {
        boolean assetDeleted = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT delete_asset(?)");

            statement.setInt(1, assetID_in);

            statement.executeQuery();

            assetDeleted = true;

        } catch (SQLException e) {
            System.out.println("something wrong with 'deleteAsset' in DBSystem");
        }

        return assetDeleted;
    }


    public int addTag(String tagName_in) {
        tagName_in.toLowerCase();

        int tagID = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_tag(?)");

            statement.setString(1, tagName_in);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tagID = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("something wrong with 'addTag' in DBSystem");
        }
        return tagID;
    }

    public boolean addTagAssignment(int assetID_in, int tagID_in) {
        boolean tagAssigned = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT add_tag_assignment(?,?)");

            statement.setInt(1, assetID_in);
            statement.setInt(2, tagID_in);

            statement.executeQuery();
            tagAssigned = true;

        } catch (Exception e) {
            System.out.println("something wrong with 'addTagAssignment' in DBSystem");
        }
        return tagAssigned;
    }

    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT tag_name FROM Tags");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String tag = resultSet.getString("tag_name");
                tags.add(tag);
            }
        } catch (SQLException ex) {
            System.out.println("something wrong with 'getAllTags' in DBSystem");
        }

        return tags;
    }


    public List<String> getTagsForAssetID(int assetid_in) {
        List<String> tags = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT t.tag_name FROM Tags t INNER JOIN Tag_Assignments ta ON t.tag_id = ta.tag_id WHERE ta.asset_id = ?");

            statement.setInt(1, assetid_in);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String tag = resultSet.getString("tag_name");
                tags.add(tag);
            }
        } catch (SQLException ex) {
            System.out.println("something wrong with 'getTagsForAssetID' in DBSystem");
        }

        return tags;
    }

    public boolean deleteTagAssignment(int assetID_in, String tagName_in) {
        boolean tagDeleted = false;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT delete_tag_assignment(?,?)");
            statement.setString(1, tagName_in);
            statement.setInt(2, assetID_in);

            statement.executeQuery();
            tagDeleted = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tagDeleted;
    }


    public List<String> getURLsByUUID (String uuid)
    {
        List<String> paths = new ArrayList<>();
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT get_paths_by_uuid(?)");
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            while(result.next())
            {
                String path = result.getString(1);
                paths.add(path);
            }

        } catch (SQLException e) {
            System.out.println("something wrong with 'getURLByUUID' in DBSystem");
        }
        return paths;
    }

}
