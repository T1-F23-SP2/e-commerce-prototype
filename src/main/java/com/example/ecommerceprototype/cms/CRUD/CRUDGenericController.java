package com.example.ecommerceprototype.cms.CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CRUDGenericController {

    private String fileName;
    private double size;

    public File file;


    public String font;
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
        fileName = "CRUDTesting";
        String sizeInput = "69.0"; //sizeField.getText();
        file = new File("src/main/resources/com/example/ecommerceprototype/cms/"+ fileName +".fxml");
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        String id = "l1"; //IDField.getText();
        Scanner s = new Scanner(file);

        System.out.println("file loaded");
        String[] fontText;
        while (s.hasNextLine()){
            String tempLine = s.nextLine();
            lines.add(tempLine);
            if (tempLine.contains("fx:id")){
                tempLine = s.nextLine();
                if (!tempLine.contains("<font>")){
                    lines.add("<font>");
                    lines.add("<Font name=\"SYSTEM\" size=\"12\" />");
                    lines.add("</font>");
                }
                lines.add(tempLine);
            }

            System.out.println("additional line added");
        }
        for (int i = 0; i<lines.size(); i++) {
            System.out.println("line " + i + "added");
            if (lines.get(i).contains(id)) {
                System.out.println("found id");
                int l=i;
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

    public static void main(String[] args) throws IOException {
        CRUDGenericController c = new CRUDGenericController();
        c.updateFXML();
    }

}
