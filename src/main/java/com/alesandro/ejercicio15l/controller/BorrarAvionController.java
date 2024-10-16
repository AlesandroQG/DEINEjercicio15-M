package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.model.Aeropuerto;
import com.alesandro.ejercicio15l.model.Avion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class BorrarAvionController implements Initializable {
    @FXML // fx:id="cbAeropuerto"
    private ComboBox<Aeropuerto> cbAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="cbAvion"
    private ComboBox<Avion> cbAvion; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
