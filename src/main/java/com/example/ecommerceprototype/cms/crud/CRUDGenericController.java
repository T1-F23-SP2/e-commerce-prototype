package com.example.ecommerceprototype.cms.crud;

import com.example.ecommerceprototype.cms.CMS;
import com.example.ecommerceprototype.cms.FXMLLoadFailedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.URL;
import java.util.*;

public class CRUDGenericController implements Initializable {

    private String fileName;
    private float size;
    private boolean bold;
    private String contents;
    private String style = "System";
    private String font;
    private String fontText;

    private final List<String> styles = Arrays.asList("System", "Arial", "Impact");

    ObservableList<String> options = FXCollections.observableArrayList("System", "Arial", "Impact");
    ObservableList<String> components = FXCollections.observableArrayList();

    public File file;



    @FXML
    private Label fileLabel;
    @FXML
    private TextField IDField;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField contentField;
    @FXML
    private Label testLabel;
    @FXML
    private RadioButton boldRadio;
    @FXML
    private ComboBox<String> fontBox;
    @FXML
    private ComboBox<String> compBox;
    @FXML
    private VBox vBox;

    @FXML
    public void updateTestLabel(){
        double v = Double.parseDouble(sizeField.getText());
        font = "SYSTEM";
        if (boldRadio.isSelected()){
            //sets whether it is bold
            testLabel.setFont(Font.font(font, FontWeight.BOLD, v));
        }else{
            //sets the font equal to the user input
            testLabel.setFont(Font.font(font, v));
        }
        //sets the text equal to the user input
        testLabel.setText(contentField.getText());
    }


    public void updateFXML() throws IOException {
        fileName = compBox.getValue();
        String sizeInput = sizeField.getText();
        file = new File("src/main/resources/com/example/ecommerceprototype/cms/"+ fileName);
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        String id = IDField.getText();
        Scanner s = new Scanner(file);

        System.out.println("file loaded");
        String[] fontText;
        while (s.hasNextLine()){
            String tempLine = s.nextLine();
            String tempLine2 = tempLine;
            if (tempLine.contains("Button")){
                if (tempLine.contains("fx:id")){
                    tempLine2 = s.nextLine();
                    if (!tempLine2.contains("<font>")){
                        String[] tempArray = tempLine.split("/");
                        tempLine = tempArray[0]+tempArray[1];
                        lines.add(tempLine);
                        lines.add("<font>");
                        lines.add("<Font name=\"SYSTEM\" size=\"12\" />");
                        lines.add("</font></Button>");
                    }else {
                        lines.add(tempLine);
                    }
                    lines.add(tempLine2);
                }else {
                    lines.add(tempLine);
                }
            } else if (tempLine.contains("Label")) {
                if (tempLine.contains("fx:id")){
                    tempLine2 = s.nextLine();
                    if (!tempLine2.contains("<font>")){
                        String[] tempArray = tempLine.split("/");
                        tempLine = tempArray[0]+tempArray[1];
                        lines.add(tempLine);
                        lines.add("<font>");
                        lines.add("<Font name=\"SYSTEM\" size=\"12\" />");
                        lines.add("</font></Label>");
                    }else {
                        lines.add(tempLine);
                    }
                    lines.add(tempLine2);
                }else {
                    lines.add(tempLine);
                }
            } else{
                lines.add(tempLine);
            }

            System.out.println("additional line added");
        }
        for (int i = 0; i<lines.size(); i++) {
            System.out.println("line " + i + "added");
            if (lines.get(i).contains(id)) {
                System.out.println("found id");
                i=i+2;
                if (lines.get(i).contains("<Font")) {
                    line = lines.get(i);
                    fontText = line.split(" ");
                    for (int j = 0; j < fontText.length; j++) {
                        if (fontText[j].contains("size")) {
                            System.out.println(fontText[j]);
                            fontText[j] = "size=\"" + sizeInput + "\"";
                            System.out.println("size has been updated");
                        }
                        if (fontText[j].contains("name")) {
                            fontText[j] = "name=\"" + font + "\"";
                        }

                    }
                    String tempString = "";
                    for (int j = 0; j < fontText.length; j++) {
                        tempString = tempString + fontText[j] + " ";
                    }
                    lines.set(i, tempString);
                    //debugging
                    /*
                    for (int k = 0; k<fontText.length; k++){
                        System.out.println(fontText[k]);
                    }
                     */
                }else {

                }
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        //Writes the new contents back into the file
        for (int i = 0; i< lines.size(); i++){
            bw.write(lines.get(i));
            bw.newLine();
        }
        s.close();
        bw.close();
    }
    private void updateFontData(String comp, String id) throws FileNotFoundException {
        File file = new File("src/main/resources/com/example/ecommerceprototype/cms/"+ comp +".fxml");
        Scanner s = new Scanner(file);
        List<String> lines = new ArrayList<>();

        while (s.hasNextLine()){
            lines.add(s.nextLine());
        }
        for (int i = 0; i<lines.size();i++){
            String[] fontText;
            String[] contentText;
            if (lines.get(i).contains(id)){
                contentText = lines.get(i).split(" ");
                for (int j = 0; j<contentText.length; j++){
                    if (contentText[j].contains("text")){
                        String[] text = contentText[j].split("\"");
                        contents = text[1];
                    }
                }
                fontText = lines.get(i+2).split(" ");
                for (int j = 0; j<fontText.length; j++){
                    if (fontText[j].contains("size")){
                        String[] sizeText = fontText[j].split("\"");
                        size = Float.parseFloat(sizeText[1]);
                    } else if (fontText[j].contains("name")) {
                        String[] sizeText = fontText[j].split("\"");
                        style = sizeText[1];
                    } else if (fontText[j].contains("Bold")) {
                        bold=true;
                    }
                }
            }
        }
    }
/*
    public static void main(String[] args) throws IOException {
        CRUDGenericController c = new CRUDGenericController();
        c.updateFXML();
    }

 */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fontBox.setItems(options);
        System.out.println(fontBox.getValue());
        components.addAll(CMS.getInstance().getComponentList());
        compBox.setItems(components);
        fontBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue);
            fontText = newValue;
        });
        compBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            vBox.getChildren().clear();
            IDField.setText("");
            fontBox.setValue(null);
            sizeField.setText("");
            List<Node> nodes;
            try {
                Pane pane = CMS.getInstance().loadComponent((newValue.split("\\."))[0]);
                nodes = CMS.getInstance().getNodeList(pane);
            } catch (FXMLLoadFailedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i<nodes.size(); i++){
                int index = i;
                Button b = new Button(nodes.get(i).getId());

                b.setOnAction(event -> {
                    String tempId = nodes.get(index).getId();
                    IDField.setText(tempId);
                    try {
                        updateFontData(newValue.split("\\.")[0], nodes.get(index).getId());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    switch(style) {
                        case "System":
                            fontBox.setValue(style);
                            break;
                        case "Impact":
                            fontBox.setValue(style);
                            break;
                        case "Arial":
                            fontBox.setValue(style);
                            break;
                        default:
                            fontBox.setValue(null);
                    }
                    String tempSize = String.valueOf(size);
                    sizeField.setText(tempSize);
                    String tempContent = contents;
                    contentField.setText(tempContent);
                });
                vBox.getChildren().add(b);
            }
        });
    }
}
