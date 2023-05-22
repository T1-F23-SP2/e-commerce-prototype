package com.example.ecommerceprototype.pim.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestMain extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(TestMain.class.getResource("pim-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
		stage.setTitle("PIM GUI");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
