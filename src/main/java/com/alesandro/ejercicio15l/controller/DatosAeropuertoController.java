package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.model.Aeropuerto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana datos de aeropuerto
 */
public class DatosAeropuertoController extends GridPane implements Initializable {
    private Aeropuerto aeropuerto;
    private FXMLLoader fxmlLoader;

    @FXML // fx:id="lblParam1"
    private Label lblParam1; // Value injected by FXMLLoader

    @FXML // fx:id="lblParam2"
    private Label lblParam2; // Value injected by FXMLLoader

    @FXML // fx:id="rbPrivado"
    private RadioButton rbPrivado; // Value injected by FXMLLoader

    @FXML // fx:id="rbPublico"
    private RadioButton rbPublico; // Value injected by FXMLLoader

    @FXML // fx:id="rbTipo"
    private ToggleGroup rbTipo; // Value injected by FXMLLoader

    @FXML // fx:id="txtAniioInauguracion"
    private TextField txtAniioInauguracion; // Value injected by FXMLLoader

    @FXML // fx:id="txtCalle"
    private TextField txtCalle; // Value injected by FXMLLoader

    @FXML // fx:id="txtCapacidad"
    private TextField txtCapacidad; // Value injected by FXMLLoader

    @FXML // fx:id="txtCiudad"
    private TextField txtCiudad; // Value injected by FXMLLoader

    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumero"
    private TextField txtNumero; // Value injected by FXMLLoader

    @FXML // fx:id="txtPais"
    private TextField txtPais; // Value injected by FXMLLoader

    @FXML // fx:id="txtParam1"
    private TextField txtParam1; // Value injected by FXMLLoader

    @FXML // fx:id="txtParam2"
    private TextField txtParam2; // Value injected by FXMLLoader

    public DatosAeropuertoController(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DatosAeropuerto.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DatosAeropuertoController() {
        this.aeropuerto = null;
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DatosAeropuerto.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    /**
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (aeropuerto == null) {
            //
        }
    }

    @FXML
    void guardar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtNombre.getScene().getWindow();
        stage.close();
    }

}
