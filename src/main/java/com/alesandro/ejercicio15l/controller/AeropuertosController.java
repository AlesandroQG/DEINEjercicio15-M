package com.alesandro.ejercicio15l.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Clase que controla los eventos de la ventana
 */
public class AeropuertosController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}