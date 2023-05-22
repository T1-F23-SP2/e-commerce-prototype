package com.example.ecommerceprototype.dam.system;

import com.example.ecommerceprototype.dam.constants.Category;
import com.example.ecommerceprototype.dam.constants.Type;
import com.example.ecommerceprototype.dam.dam.DAMSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResizeImagesController implements Initializable {

    private int id;
    private String filename ;
    private String type;
    private String category;
    private String uuid;

    DAMSystem dam = DAMSystem.getInstance();


    @FXML
    private Button resizeImageButton;

    @FXML
    private TextField targetWidthField;

    @FXML
    private TextField targetHeightField;


    @FXML
    private Label resizedLabel;





    public void resizeImage()
    {
        int targetWidth = Integer.parseInt(targetWidthField.getText());
        int targetHeight = Integer.parseInt(targetWidthField.getText());

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

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics2D = resizedImage.createGraphics();

            graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            graphics2D.dispose();


            // vi gemmer nu det nye billede som en fil
            String nameResized = ".resized.jpg";
            String newFilePath = selectedFile.getPath() + nameResized;

            try {
                File resizedFile;
                ImageIO.write(resizedImage, "jpg", resizedFile = new File(newFilePath));


                Type type = switch (type_in) {
                    case "Product Image" -> Type.PRODUCT_IMAGE;
                    case "Product File" -> Type.PRODUCT_FILE;
                    case "Image" -> Type.IMAGE;
                    case "File" -> Type.FILE;
                    default -> null;

                };

                Category cat = extractCategory(category_in);

                String fileFormat = extractFileFormat(filename_in);

                String name = filename_in + nameResized;

                dam.addAsset(name, type, cat, fileFormat, uuid_in, newFilePath);

                selectedFile.delete();

                resizedFile.delete();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            resizedLabel.setVisible(true);
        }

    }
    @FXML
    private void checkInputHeight(KeyEvent event)
    {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]"))
        {
            event.consume();
            targetHeightField.setStyle("-fx-border-color: red");
        } else {
            targetHeightField.setStyle("-fx-border-color: blue");
        }
    }
    @FXML
    private void checkInputWidth(KeyEvent event)
    {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]"))
        {
            event.consume();
            targetWidthField.setStyle("-fx-border-color: red");
        } else {
            targetWidthField.setStyle("-fx-border-color: blue");
        }
    }


    private Category extractCategory(String cat_in)
    {
        Category cat = Category.valueOf(cat_in.toUpperCase());
        return cat;
    }

    private String extractFileFormat(String name_in)
    {
        String formatString = name_in.substring(name_in.lastIndexOf(".") + 1);

        return formatString;
    }


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetWidthField.addEventFilter(KeyEvent.KEY_PRESSED, this::checkInputWidth);
        targetHeightField.addEventFilter(KeyEvent.KEY_PRESSED, this::checkInputHeight);
    }
}
