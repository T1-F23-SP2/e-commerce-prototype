package com.example.ecommerceprototype.dam;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAM {

    //Database info
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db";
    private static final String DB_user = "postgres";
    private static final String DB_Password = "MyPassw0rd";

    private static DAM instance;
    private Connection connection = null;

    DAM() {
        initializePostgresqlDatabase();
    }

    public static DAM getInstance() {
        if (instance == null) {
            instance = new DAM();
        }
        return instance;
    }

    //Connection to Database
    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(DB_URL, DB_user, DB_Password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    //Creates a list from the database of all the assets
    public List<Asset> getAssetsList() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM digital_assets");
            ResultSet sqlReturnValues = stmt.executeQuery();
            int rowcount = 0;
            List<Asset> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()) {
                returnValue.add(new Asset(
                        sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2),
                        sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4),
                        sqlReturnValues.getInt(5),
                        sqlReturnValues.getString(6),
                        sqlReturnValues.getBoolean(7),
                        sqlReturnValues.getDate(8)));
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Uploade assets
    public boolean uploadAsset(Asset asset) {
        //Checks if asset already exists in the database
        if (getAssetsList().contains(asset)) {
            return false;
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO digital_assets(filename, filepath, filetype, filesize, uuid, isWatermarked date_added) VALUES(?,?,?,?,?,?,?)");
            stmt.setString(1, asset.getFilename());
            stmt.setString(2, asset.getFilepath());
            stmt.setString(3, asset.getFiletype());
            stmt.setInt(4, asset.getFilesize());
            stmt.setString(5, asset.getUuid());
            stmt.setBoolean(6, asset.getIsWatermarked());
            stmt.setDate(7, new java.sql.Date(asset.getDate_added().getTime()));
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //Delete asset
    public boolean deleteAsset(int id) {
        if (getAssetsList().contains(id)) {
            try {
                PreparedStatement stmt = connection.prepareStatement(
                        "DELETE FROM digital_assets WHERE id = ?");
                stmt.setInt(1, id);
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }


    //Search engine
    public Asset getAsset(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM digital_assets WHERE id = ?");
            stmt.setInt(1, id);
            return getAsset(stmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Asset getAsset(String uuid) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM digital_assets WHERE uuid = ?");
            stmt.setString(1, uuid);
            return getAsset(stmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Made to simplify duplication of code in search methods/getAsset methods
    private Asset getAsset(PreparedStatement stmt) throws SQLException {
        ResultSet sqlReturnValues = stmt.executeQuery();
        if (!sqlReturnValues.next()) {
            return null;
        }
        return new Asset(
                sqlReturnValues.getInt(1),
                sqlReturnValues.getString(2),
                sqlReturnValues.getString(3),
                sqlReturnValues.getString(4),
                sqlReturnValues.getInt(5),
                sqlReturnValues.getString(6),
                sqlReturnValues.getBoolean(7),
                sqlReturnValues.getDate(8)
        );
    }

}


