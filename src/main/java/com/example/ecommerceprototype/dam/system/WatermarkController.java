package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import com.example.ecommerceprototype.dam.dam.DAMSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class WatermarkController {

    private int id;
    private String filename;
    private String type;
    private String category;
    private String uuid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @FXML
    private Button addWatermarkButton;

    @FXML
    private Button addSaleLogoButton;

    @FXML
    private Button addCopyrightLogoButton;

    @FXML
    private Button addTextWatermarkButton;

    @FXML
    private Label saleLabel;

    @FXML
    private Label copyrightLabel;

    @FXML
    private Label watermarkLabel;

    @FXML
    private Label textLabel;

    @FXML
    private TextField watermarkText;


    DAMSystem dam = DAMSystem.getInstance();


    public void watermark() {
        String filename_in = getFilename();
        String type_in = getType();
        String category_in = getCategory();
        String uuid_in = getUuid();

        if (dam.watermark(filename_in, type_in, category_in, uuid_in)) {
            File selectedFile = new File("./data/" + filename_in);



            try {
                BufferedImage originalImage  = ImageIO.read(selectedFile);

                int originalWidth = originalImage.getWidth();
                int originalHeight = originalImage.getHeight();

                BufferedImage watermarkedImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

                g2d.drawImage(originalImage, 0, 0, null);

                BufferedImage logo = ImageIO.read(new File("src/main/resources/com/example/ecommerceprototype/dam/images/sdu_logo.png"));


                double scaleFactor = Math.min((double) originalWidth / logo.getWidth(), (double) originalHeight / logo.getHeight());

                int scaledWidth = (int) (logo.getWidth() * scaleFactor);
                int scaledHeight = (int) (logo.getHeight() * scaleFactor);
                Image scaledLogo = logo.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);

                int desiredWidth = (int) (originalWidth * 0.1);  // Adjust the value as needed for smaller or bigger watermark
                int desiredHeight = (int) (originalHeight * 0.1);  // Adjust the value as needed for smaller or bigger watermark
                Image sizedLogo = scaledLogo.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

                int logoWidth = logo.getWidth();
                int logoHeight = logo.getHeight();
                int logoMargin = 12;
                int logoX = originalWidth - desiredWidth - logoMargin;
                int logoY = originalHeight - desiredHeight - logoMargin;
                g2d.drawImage(sizedLogo, logoX, logoY, null);

                String nameWatermarked = ".watermarked.jpg";

                String newFilePath = selectedFile.getPath() + nameWatermarked;


                File watermarkedFile;
                ImageIO.write(watermarkedImage, "jpg", watermarkedFile = new File(newFilePath));

                Type type = switch (type_in) {
                    case "Product Image" -> Type.PRODUCT_IMAGE;
                    case "Product File" -> Type.PRODUCT_FILE;
                    case "Image" -> Type.IMAGE;
                    case "File" -> Type.FILE;
                    default -> null;
                };

                Category cat = extractCategory(category_in);

                String fileFormat = extractFileFormat(filename_in);

                String name = filename_in + nameWatermarked;

                dam.addAsset(name, type, cat, fileFormat, uuid_in, newFilePath);

                selectedFile.delete();

                watermarkedFile.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            watermarkLabel.setVisible(true);
        }
    }


    public void addSaleLogo() {
        String filename_in = getFilename();
        String type_in = getType();
        String category_in = getCategory();
        String uuid_in = getUuid();


        if (dam.watermark(filename_in, type_in, category_in, uuid_in)) {
            File selectedFile = new File("./data/" + filename_in);

            BufferedImage originalImage = null;

            try {
                originalImage = ImageIO.read(selectedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            g2d.drawImage(originalImage, 0, 0, null);

            BufferedImage logo = null;

            try {
                logo = ImageIO.read(new File("src/main/resources/com/example/ecommerceprototype/dam/images/sale.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            double scaleFactor = Math.min((double) originalWidth / logo.getWidth(), (double) originalHeight / logo.getHeight());

            int scaledWidth = (int) (logo.getWidth() * scaleFactor);
            int scaledHeight = (int) (logo.getHeight() * scaleFactor);
            Image scaledLogo = logo.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);

            int desiredWidth = (int) (originalWidth * 0.1);  // Adjust the value as needed
            int desiredHeight = (int) (originalHeight * 0.1);  // Adjust the value as needed
            Image sizedLogo = scaledLogo.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();
            int logoMargin = 12;
            int logoX = originalWidth - desiredWidth - logoMargin;
            int logoY = originalHeight - desiredHeight - logoMargin;
            g2d.drawImage(sizedLogo, logoX, logoY, null);

            String nameWatermarked = ".logomarked.jpg";
            String newFilePath = selectedFile.getPath() + nameWatermarked;

            try {
                File watermarkedFile;
                ImageIO.write(watermarkedImage, "jpg", watermarkedFile = new File(newFilePath));

                Type type = switch (type_in) {
                    case "Product Image" -> Type.PRODUCT_IMAGE;
                    case "Product File" -> Type.PRODUCT_FILE;
                    case "Image" -> Type.IMAGE;
                    case "File" -> Type.FILE;
                    default -> null;

                };

                Category cat = extractCategory(category_in);

                String fileFormat = extractFileFormat(filename_in);

                String name = filename_in + nameWatermarked;

                dam.addAsset(name, type, cat, fileFormat, uuid_in, newFilePath);

                selectedFile.delete();

                watermarkedFile.delete();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            saleLabel.setVisible(true);
        }
    }


    public void addCopyrightLogo() {
        String filename_in = getFilename();
        String type_in = getType();
        String category_in = getCategory();
        String uuid_in = getUuid();

        if (dam.watermark(filename_in, type_in, category_in, uuid_in)) {
            File selectedFile = new File("./data/" + filename_in);

            BufferedImage originalImage = null;

            try {
                originalImage = ImageIO.read(selectedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

            g2d.drawImage(originalImage, 0, 0, null);

            BufferedImage logo = null;

            try {
                logo = ImageIO.read(new File("src/main/resources/com/example/ecommerceprototype/dam/images/copyrightbillede.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // vi bliver nu nødt til at tegne logoet ovenpå det nye billede

            double scaleFactor = Math.min((double) originalWidth / logo.getWidth(), (double) originalHeight / logo.getHeight());

            int scaledWidth = (int) (logo.getWidth() * scaleFactor);
            int scaledHeight = (int) (logo.getHeight() * scaleFactor);
            Image scaledLogo = logo.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);

            int desiredWidth = (int) (originalWidth * 0.1);  // Adjust the value as needed
            int desiredHeight = (int) (originalHeight * 0.1);  // Adjust the value as needed
            Image sizedLogo = scaledLogo.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();
            int logoMargin = 12;
            int logoX = originalWidth - desiredWidth - logoMargin;
            int logoY = originalHeight - desiredHeight - logoMargin;
            g2d.drawImage(sizedLogo, logoX, logoY, null);

            // vi gemmer nu det nye billede som en fil
            String nameWatermarked = ".logomarked.jpg";
            String newFilePath = selectedFile.getPath() + nameWatermarked;

            try {
                File watermarkedFile;
                ImageIO.write(watermarkedImage, "jpg", watermarkedFile = new File(newFilePath));

                Type type = switch (type_in) {
                    case "Product Image" -> Type.PRODUCT_IMAGE;
                    case "Product File" -> Type.PRODUCT_FILE;
                    case "Image" -> Type.IMAGE;
                    case "File" -> Type.FILE;
                    default -> null;

                };

                Category cat = extractCategory(category_in);

                String fileFormat = extractFileFormat(filename_in);

                String name = filename_in + nameWatermarked;

                dam.addAsset(name, type, cat, fileFormat, uuid_in, newFilePath);

                selectedFile.delete();

                watermarkedFile.delete();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            copyrightLabel.setVisible(true);
        }
    }


    public void addTextWatermark() {

        String mark = watermarkText.getText();

        if (!mark.isBlank()) {
            String filename_in = getFilename();
            String type_in = getType();
            String category_in = getCategory();
            String uuid_in = getUuid();

            if (dam.watermark(filename_in, type_in, category_in, uuid_in)) {
                File selectedFile = new File("./data/" + filename_in);

                BufferedImage originalImage = null;

                try {
                    originalImage = ImageIO.read(selectedFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                BufferedImage watermarkedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();

                g2d.drawImage(originalImage, 0, 0, null);

                Font font = new Font("Purisa", Font.PLAIN, 48);
                Color color = Color.WHITE;
                g2d.setFont(font);
                g2d.setColor(color);
                g2d.drawString(mark, 10, 50);

                String nameWatermarked = ".logomarked.jpg";
                String newFilePath = selectedFile.getPath() + nameWatermarked;


                try {
                    File watermarkedFile;
                    ImageIO.write(watermarkedImage, "jpg", watermarkedFile = new File(newFilePath));

                    Type type = switch (type_in) {
                        case "Product Image" -> Type.PRODUCT_IMAGE;
                        case "Product File" -> Type.PRODUCT_FILE;
                        case "Image" -> Type.IMAGE;
                        case "File" -> Type.FILE;
                        default -> null;

                    };

                    Category cat = extractCategory(category_in);

                    String fileFormat = extractFileFormat(filename_in);

                    String name = filename_in + nameWatermarked;

                    dam.addAsset(name, type, cat, fileFormat, uuid_in, newFilePath);

                    selectedFile.delete();

                    watermarkedFile.delete();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                textLabel.setVisible(true);
            }
        }
    }


    private Category extractCategory(String cat_in) {
        Category cat = Category.valueOf(cat_in.toUpperCase());
        return cat;
    }

    private String extractFileFormat(String name_in) {
        String formatString = name_in.substring(name_in.lastIndexOf(".") + 1);

        return formatString;
    }


}
