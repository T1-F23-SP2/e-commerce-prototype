package com.example.ecommerceprototype.cms;

public class FXMLLoadFailedException extends Exception{
    public FXMLLoadFailedException() {
        super("FXML file could not be loaded! The file probably contains errors.");
    }

    public FXMLLoadFailedException(String errorMessage) {
        super("FXML file could not be loaded! The file probably contains errors." + errorMessage);
    }
}
