package com.example.ecommerceprototype.dam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAM {

    //Database info
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/semesteropg";
    private static final String DB_user = "postgres";
    private static final String DB_Password = "Supermand1";

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

    // add "sale" logo to an existing picture and update the database
    public void addSaleLogo(Asset asset) throws Exception {
        // vi starter med at indlæse filen
        File toBeWatermarked = new File(asset.filepath);

        BufferedImage originaltBillede = null;
        try {
            originaltBillede = ImageIO.read(toBeWatermarked);
        } catch (Exception e) {
            throw new Exception("Filen kunne ikke indlæses" + e.getMessage());
        }

        // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke til
        BufferedImage watermarkedImage = new BufferedImage(originaltBillede.getWidth(), originaltBillede.getHeight(), BufferedImage.TYPE_INT_RGB);

        // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

        // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
        g2d.drawImage(originaltBillede, 0, 0, null);

        BufferedImage logo = null;

        try {
            logo = ImageIO.read(new File("sale.png"));
        } catch (Exception e){
            throw new Exception("logo kunne ikke indlæses " + e.getMessage());
        }

        // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        int logoMargin = 12;
        int logoX = watermarkedImage.getWidth() - logoWidth - logoMargin;
        int logoY = watermarkedImage.getHeight() - logoHeight - logoMargin;
        g2d.drawImage(logo, logoX, logoY, null);

        // vi gemmer nu det nye billede som en fil

        String newFilePath = asset.filepath + ".watermarked.jpg";
        try {
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("Billedet med vandmærke kunne ikke gemmes: " + e.getMessage());
        }

        // vi opdaterer nu databasen så det passer
        String sql = "UPDATE assets SET file_path = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, asset.filepath);
            pstmt.setInt(2, asset.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("filepath kunne ikke opdateres i databasen " + e.getMessage());
        }

        // til allersidst opdaterer vi dette assets eksisterende opgave filepath med den nye og vandmærkede fil
        asset.filepath = newFilePath;
    }

    // add a copyright © to an existing picture and update the database
    public void addCopyrightLogo(Asset asset) throws Exception {
        // vi starter med at indlæse filen
        File toBeWatermarked = new File(asset.filepath);

        BufferedImage originaltBillede = null;
        try {
            originaltBillede = ImageIO.read(toBeWatermarked);
        } catch (Exception e) {
            throw new Exception("Filen kunne ikke indlæses" + e.getMessage());
        }

        // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke til
        BufferedImage watermarkedImage = new BufferedImage(originaltBillede.getWidth(), originaltBillede.getHeight(), BufferedImage.TYPE_INT_RGB);

        // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

        // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
        g2d.drawImage(originaltBillede, 0, 0, null);

        BufferedImage logo = null;

        try {
            logo = ImageIO.read(new File("copyrightbillede.png"));
        } catch (Exception e){
            throw new Exception("logo kunne ikke indlæses " + e.getMessage());
        }

        // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        int logoMargin = 12;
        int logoX = watermarkedImage.getWidth() - logoWidth - logoMargin;
        int logoY = watermarkedImage.getHeight() - logoHeight - logoMargin;
        g2d.drawImage(logo, logoX, logoY, null);

        // vi gemmer nu det nye billede som en fil

        String newFilePath = asset.filepath + ".watermarked.jpg";
        try {
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("Billedet med vandmærke kunne ikke gemmes: " + e.getMessage());
        }

        // vi opdaterer nu databasen så det passer
        String sql = "UPDATE assets SET file_path = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, asset.filepath);
            pstmt.setInt(2, asset.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("filepath kunne ikke opdateres i databasen." +
                    " Copyright logo kunne ikke tilføjes " + e.getMessage());
        }

        // til allersidst opdaterer vi dette assets eksisterende opgave filepath med den nye og vandmærkede fil
        asset.filepath = newFilePath;
    }

    // add a custom text-based watermark to an existing picture and update the database
    public void addWatermark(String mark, Asset asset) throws Exception {

        // vi starter med at indlæse filen
        File toBeWatermarked = new File(asset.filepath);

        BufferedImage originaltBillede = null;
        try {
            originaltBillede = ImageIO.read(toBeWatermarked);
        } catch (Exception e) {
            throw new Exception("Filen kunne ikke indlæses" + e.getMessage());
        }

        // nu opretter vi et BufferedImage object til det billede som vi ønsker at tilføje et vandmærke til
        BufferedImage watermarkedImage = new BufferedImage(originaltBillede.getWidth(), originaltBillede.getHeight(), BufferedImage.TYPE_INT_RGB);

        // derefter opretter vi et Graphics2D objekt af billedet vi ønsker at vandmærke
        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

        // vi tilføjer og tegner nu det originale billede ovenpå det nye billede
        g2d.drawImage(originaltBillede, 0, 0, null);

        // herfter må vi tegne vandmærket ovenpå det nye billede
        Font font = new Font("Purisa", Font.PLAIN, 48);
        Color color = Color.WHITE;
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(mark, 10, 50);

        // vi gemmer nu billedet med vandmærket som en ny fil
        String newFilePath = asset.filepath + ".watermarked.jpg";

        try{
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("The watermarked photo couldn't be saved " + e.getMessage());
        }

        // til allersidst opdaterer vi dette assets eksisterende opgave filepath med den nye og vandmærkede fil
        asset.filepath = newFilePath;
    }



}


