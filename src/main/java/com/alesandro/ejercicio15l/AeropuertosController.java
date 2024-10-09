package com.alesandro.ejercicio15l;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AeropuertosController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}