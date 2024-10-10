package com.alesandro.ejercicio15l.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import com.alesandro.ejercicio15l.model.*;
import com.alesandro.ejercicio15l.dao.*;

/**
 * Clase que controla los eventos de la ventana de aeropuertos
 */
public class AeropuertosController implements Initializable {
    @FXML // fx:id="menuBorrarAeropuerto"
    private MenuItem menuBorrarAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="rbGroup"
    private ToggleGroup rbGroup; // Value injected by FXMLLoader

    @FXML // fx:id="rbPublicos"
    private RadioButton rbPublicos; // Value injected by FXMLLoader

    @FXML // fx:id="rbPrivados"
    private RadioButton rbPrivados; // Value injected by FXMLLoader

    @FXML // fx:id="menuEditarAeropuerto"
    private MenuItem menuEditarAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="menuInfoAeropuerto"
    private MenuItem menuInfoAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="tabla"
    private TableView<?> tabla; // Value injected by FXMLLoader

    @FXML // fx:id="colId"
    private TableColumn<?, ?> colId; // Value injected by FXMLLoader

    @FXML // fx:id="colNombre"
    private TableColumn<?, ?> colNombre; // Value injected by FXMLLoader

    @FXML // fx:id="colPais"
    private TableColumn<?, ?> colPais; // Value injected by FXMLLoader

    @FXML // fx:id="colCiudad"
    private TableColumn<?, ?> colCiudad; // Value injected by FXMLLoader

    @FXML // fx:id="colCalle"
    private TableColumn<?, ?> colCalle; // Value injected by FXMLLoader

    @FXML // fx:id="colNumero"
    private TableColumn<?, ?> colNumero; // Value injected by FXMLLoader

    @FXML // fx:id="colAnio"
    private TableColumn<?, ?> colAnio; // Value injected by FXMLLoader

    @FXML // fx:id="colCapacidad"
    private TableColumn<?, ?> colCapacidad; // Value injected by FXMLLoader

    @FXML // fx:id="colNSocios"
    private TableColumn<?, ?> colNSocios; // Value injected by FXMLLoader

    /**
     * Función que se ejecuta cuando se carga la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    @FXML
    void aniadirAeropuerto(ActionEvent event) {

    }

    @FXML
    void editarAeropuerto(ActionEvent event) {

    }

    @FXML
    void borrarAeropuerto(ActionEvent event) {

    }

    @FXML
    void infoAeropuerto(ActionEvent event) {

    }

    @FXML
    void aniadirAvion(ActionEvent event) {

    }

    @FXML
    void activarDesactivarAvion(ActionEvent event) {

    }

    @FXML
    void borrarAvion(ActionEvent event) {

    }

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Función que muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

}