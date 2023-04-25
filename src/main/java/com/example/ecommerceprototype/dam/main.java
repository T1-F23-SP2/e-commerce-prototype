package com.example.ecommerceprototype.dam;
import com.example.ecommerceprototype.dam.FileSystem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.util.*;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Date;

public class main extends Application {

    public void start(Stage stage) throws IOException {
        DAM test = new DAM();
        Application test2 = new GUI();
        Application.launch();
    }
}