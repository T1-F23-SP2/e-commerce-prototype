package com.example.ecommerceprototype.dam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAM {

    //Database info
    private static final String DB_URL = "jdbc:postgresql://192.168.0.64:5432/db";
    private static final String DB_user = "postgres";
    private static final String DB_Password = "MyPassw0rd";

    //List of assets
    private List<DigitalAsset> assetList = new ArrayList<>();



    /*
        er det nødvendigt at have en liste som programmet tjekker igennem. er database ikke godt nok?
     */
    public boolean addDigitalAsset(DigitalAsset asset){
        //Checks if asset already exists in the manager's collection
        if (assetList.contains(asset)){
            return false;
        }

        //Checks if asset already exists in the SQLDatabase
        if (checkDatabase(asset)){
            return false;
        }

        //Adds asset to manager's collection
        assetList.add(asset);

        //Adds asset to SQLDatabase
        addToDataBase(asset);

        return true;
    }



    private boolean checkDatabase(DigitalAsset asset) {
        boolean exists = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            //connection to DB
            con = DriverManager.getConnection(DB_URL, DB_user, DB_Password);

            //Prepare the query to check if the asset exists
            stmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM digital_assets WHERE filename = ?"); //More options available....!!!!
            stmt.setString(1, asset.getFilename());

            //Execute the query and get the result
            result = stmt.executeQuery();

            //If the query has more than 0 results the file exists
            if (result.next()){
                exists = result.getInt(1)>0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //closing all the resources
            try {
                result.close();
            } catch (Exception e) {}

            try { stmt.close();
            } catch (Exception e) {}

            try { con.close();
            } catch (Exception e) {}
        }
        return exists;
    }


    public void addToDataBase(DigitalAsset asset) {
        try (Connection con = DriverManager.getConnection(DB_URL,DB_user,DB_Password)){
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO digital_assets(filename, filepath, filetype, filesize, uuid, date_added) VALUES(?,?,?,?,?,?)");
            stmt.setString(1, asset.getFilename());
            stmt.setString(2, asset.getFilepath());
            stmt.setString(3, asset.getFiletype());
            stmt.setInt(4, asset.getFilesize());
            stmt.setString(5, asset.getUuid());
            stmt.setDate(6, new java.sql.Date(asset.getDate_added().getTime()));
            stmt.executeUpdate();


            //Assigning the given id from the database to id in manager
            PreparedStatement stmt2 = con.prepareStatement(
                    "SELECT id FROM digital_assets WHERE filename = ?");
            stmt2.setString(1, asset.getFilename());

            ResultSet result = stmt2.executeQuery();
            while (result.next()) {
                asset.setId(result.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


