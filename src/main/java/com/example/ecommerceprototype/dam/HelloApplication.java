package com.example.ecommerceprototype.dam;
import javafx.application.Application;
import javafx.collections.FXCollections;
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
import java.nio.file.Paths;
import java.util.*;

public class HelloApplication extends Application {

    private final FileSystem fileSystem = new FileSystem();
    private final Map<String, Asset> fileListAssets = new HashMap<>();

    @Override
    public void start(Stage window) {

        DAM test2 = new DAM();
    }

    public static void main (String[]args){
        launch(HelloApplication.class);
    }
}



