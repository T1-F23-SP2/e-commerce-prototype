package com.example.ecommerceprototype.dam;

import java.io.File;
import java.util.Date;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Asset {
    private int id;
    private String filename;
    private String filepath;
    private String filetype;
    private int filesize;
    private String uuid;
    private boolean isWatermarked;
    private Date date_added;


//Constructor

    public Asset(String filename, String filepath, String filetype,
                 int filesize, String uuid, boolean isWatermarked, Date date_added) {
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.filesize = filesize;
        this.uuid = uuid;
        this.isWatermarked = isWatermarked;
        this.date_added = date_added;
    }

    public Asset(int id, String filename, String filepath, String filetype,
                 int filesize, String uuid, boolean isWatermarked, Date date_added) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.filetype = filetype;
        this.filesize = filesize;
        this.uuid = uuid;
        this.isWatermarked = isWatermarked;
        this.date_added = date_added;
    }


    //Getters and setters

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean getIsWatermarked() {
        return isWatermarked;
    }

    public void setWatermarked(boolean watermarked) {
        isWatermarked = watermarked;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public void addWatermark(String mark) throws Exception {

        // vi starter med at indlæse filen
        File toBeWatermarked = new File(this.filepath);

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
        String newFilePath = this.filepath + ".watermarked.jpg";

        try{
            ImageIO.write(watermarkedImage, "jpg", new File(watermarkedFilePath));
        } catch (Exception e) {
            throw new Exception("Det vandmærkede billede kunne ikke gemmes " + e.getMessage());
        }

        // til allersidst opdaterer vi dette assets eksisterende opgave filepath med den nye og vandmærkede fil
        this.filepath = newFilePath;
    }

    public void addCopyrightLogo() throws Exception {
        // vi starter med at indlæse filen
        File toBeWatermarked = new File(this.filepath);

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

        String newFilePath = this.filepath + ".watermarked.jpg";
        try {
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("Billedet med vandmærke kunne ikke gemmes: " + e.getMessage());
        }
    }

    public void addSaleLogo() throws Exception {
        // vi starter med at indlæse filen
        File toBeWatermarked = new File(this.filepath);

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

        String newFilePath = this.filepath + ".watermarked.jpg";
        try {
            ImageIO.write(watermarkedImage, "jpg", new File(newFilePath));
        } catch (Exception e) {
            throw new Exception("Billedet med vandmærke kunne ikke gemmes: " + e.getMessage());
        }
    }

}
