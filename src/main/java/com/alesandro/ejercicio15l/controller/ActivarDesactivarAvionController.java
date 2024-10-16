package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.model.Aeropuerto;
import com.alesandro.ejercicio15l.model.Avion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ActivarDesactivarAvionController implements Initializable {
    @FXML // fx:id="cbAeropuerto"
    private ComboBox<Aeropuerto> cbAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="cbAvion"
    private ComboBox<Avion> cbAvion; // Value injected by FXMLLoader

    @FXML // fx:id="rbActivado"
    private RadioButton rbActivado; // Value injected by FXMLLoader

    @FXML // fx:id="rbDesactivado"
    private RadioButton rbDesactivado; // Value injected by FXMLLoader

    @FXML // fx:id="rbGroup"
    private ToggleGroup rbGroup; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
