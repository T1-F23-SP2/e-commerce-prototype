package com.example.ecommerceprototype.cms.crud.article;

import com.example.ecommerceprototype.cms.ArticleManager;
import com.example.ecommerceprototype.cms.CMS;
<<<<<<< HEAD
import com.example.ecommerceprototype.cms.FXMLLoadFailedException;
=======
import com.example.ecommerceprototype.cms.exceptions.FXMLLoadFailedException;
>>>>>>> origin/main
import com.example.ecommerceprototype.cms.crud.CRUDHubApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CRUDArticleController implements Initializable {
    @FXML
    private Button add_Button, delete_Button, save_Button, discardChange_Button, back_Button;
    @FXML
    private TextField titel_TextField, author_TextField;
    @FXML
    private TextArea articleText_TextArea;
    @FXML
    private ListView<String> articleList_ListView;
    private ObservableList<String> items;
    private ArticleManager articleManager;
    private File file;
    private void addArticle(ActionEvent add){
        file = new File("src/main/resources/com/example/ecommerceprototype/cms/Articles/Article"+articleManager.getArticleCount()+".txt");
        try {
            if(file.createNewFile()){
                try (PrintWriter pw = new PrintWriter(file)){
                    pw.println("Unnamed Article"+(articleManager.getArticleCount()-1));
                    pw.println("");
                    pw.println("" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM-yyyy")));
                    pw.println("");
                }catch (IOException e){
                    throw new RuntimeException();
                }
                listUpdater();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void removeArticle(ActionEvent remove){
        if (articleList_ListView.getSelectionModel().getSelectedItem() == null)
            return;
        File fileSelected = articleManager.getArticle(articleList_ListView.getSelectionModel().getSelectedItem());
        if (fileSelected.exists()){
            if (fileSelected.delete()){
                items.remove(fileSelected);
                listUpdater();
            }
        }
    }
    private void discardChange(ActionEvent discard){
        listHandler(null);
    }
    private void saveArticle(ActionEvent save){
        String articleName = articleList_ListView.getSelectionModel().getSelectedItem();
        if (articleName != null){
            //articleList_ListView.getSelectionModel().select(articleName);
            try (PrintWriter pw = new PrintWriter(articleManager.getArticle(articleName))) {
                //PrintWriter pw = new PrintWriter(articleManager.getArticle(articleName));
                pw.println(titel_TextField.getText());
                pw.println(author_TextField.getText());
                pw.println("" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM-yyyy")));
                pw.println(articleText_TextArea.getText());
                //pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            listUpdater();
        }
        articleList_ListView.requestFocus();
    }
    private void listHandler(MouseEvent listSelect){
        if (articleList_ListView.getSelectionModel().getSelectedItem() == null)
            return;
        file = articleManager.getArticle(articleList_ListView.getSelectionModel().getSelectedItem());
        try (Scanner sc = new Scanner(file)) {
            titel_TextField.setText(sc.nextLine());
            author_TextField.setText(sc.nextLine());
            sc.nextLine(); // skips Date line
            articleText_TextArea.setText("");
            while (sc.hasNextLine()){
                articleText_TextArea.appendText(sc.nextLine()+"\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void backHandler(ActionEvent event){
        try {
            Pane pane = CMS.getInstance().loadComponent("CRUDHub", true);
            CRUDHubApplication.setStage(new Scene(pane, 600, 400));
        } catch (FXMLLoadFailedException e) {
            throw new RuntimeException(e);
        }
    }

    private void listUpdater(){
        items = FXCollections.observableList(articleManager.getArticleNames());
        articleList_ListView.setItems(items);
        articleList_ListView.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleManager = CMS.articles;
        items = FXCollections.observableList(articleManager.getArticleNames());
        articleList_ListView.setItems(items);

        //articleList_ListView.setCellFactory(TextFieldListCell.forListView());
        articleList_ListView.setOnMouseClicked(this::listHandler);

        Image addImage = new Image(String.valueOf(CRUDArticleController.class.getResource("iconImages/add-button.png")));
        ImageView addButton_ImageView = new ImageView(addImage);
        addButton_ImageView.fitHeightProperty().bind(add_Button.heightProperty());
        addButton_ImageView.fitWidthProperty().bind(add_Button.widthProperty());
        addButton_ImageView.setPreserveRatio(true);
        add_Button.setGraphic(addButton_ImageView);

        Image removeImage = new Image(String.valueOf(CRUDArticleController.class.getResource("iconImages/remove-button.png")));
        ImageView deleteButton_ImageView = new ImageView(removeImage);
        deleteButton_ImageView.fitHeightProperty().bind(delete_Button.heightProperty());
        deleteButton_ImageView.fitWidthProperty().bind(delete_Button.widthProperty());
        deleteButton_ImageView.setPreserveRatio(true);
        delete_Button.setGraphic(deleteButton_ImageView);

        Image revertImage = new Image(String.valueOf(CRUDArticleController.class.getResource("iconImages/revert-button.png")));
        ImageView revertButton_ImageView = new ImageView(revertImage);
        revertButton_ImageView.fitHeightProperty().bind(discardChange_Button.heightProperty());
        revertButton_ImageView.fitWidthProperty().bind(discardChange_Button.widthProperty());
        revertButton_ImageView.setPreserveRatio(true);
        discardChange_Button.setGraphic(revertButton_ImageView);

        Image saveImage = new Image(String.valueOf(CRUDArticleController.class.getResource("iconImages/save-button.png")));
        ImageView saveButton_ImageView = new ImageView(saveImage);
        saveButton_ImageView.fitHeightProperty().bind(save_Button.heightProperty());
        saveButton_ImageView.fitWidthProperty().bind(save_Button.widthProperty());
        saveButton_ImageView.setPreserveRatio(true);
        save_Button.setGraphic(saveButton_ImageView);

        add_Button.setOnAction(this::addArticle);
        delete_Button.setOnAction(this::removeArticle);
        discardChange_Button.setOnAction(this::discardChange);
        save_Button.setOnAction(this::saveArticle);

        back_Button.setOnAction(this::backHandler);
    }
}
