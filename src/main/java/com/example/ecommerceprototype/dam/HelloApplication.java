package com.example.ecommerceprototype.dam;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.print.attribute.standard.Media;
import java.io.File;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class HelloApplication extends Application {

    private final FileSystem fileSystem = new FileSystem();
    private final Map<String, Asset> fileListAssets = new HashMap<>();

    @Override
    public void start(Stage window) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        window.setTitle("DAM login");
        window.setScene(scene);
        window.show();
    }
    public static void main (String[] args){
        launch();
    }
}



