package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.dao.DaoAeropuerto;
import com.alesandro.ejercicio15l.dao.DaoAvion;
import com.alesandro.ejercicio15l.model.Aeropuerto;
import com.alesandro.ejercicio15l.model.AeropuertoPublico;
import com.alesandro.ejercicio15l.model.Avion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana de borrar aviones
 */
public class BorrarAvionController implements Initializable {
    @FXML // fx:id="cbAeropuerto"
    private ComboBox<Aeropuerto> cbAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="cbAvion"
    private ComboBox<Avion> cbAvion; // Value injected by FXMLLoader

    /**
     * Función que se ejecuta cuando se carga la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Aeropuertos
        ObservableList<Aeropuerto> aeropuertos = DaoAeropuerto.cargarListado();
        cbAeropuerto.setItems(aeropuertos);
        cbAeropuerto.getSelectionModel().select(0);
        cbAeropuerto.valueProperty().addListener(new ChangeListener<Aeropuerto>() {
            @Override
            public void changed(ObservableValue<? extends Aeropuerto> observableValue, Aeropuerto oldValue, Aeropuerto newValue) {
                cambioAeropuerto(newValue);
            }
        });
        cambioAeropuerto(cbAeropuerto.getSelectionModel().getSelectedItem());
    }

    /**
     * Función que carga los aviones de un aeropuerto cuando este se cambia
     *
     * @param aeropuerto nuevo aeropuerto seleccionado
     */
    public void cambioAeropuerto(Aeropuerto aeropuerto) {
        if (aeropuerto != null) {
            ObservableList<Avion> aviones = DaoAvion.cargarListado(aeropuerto);
            cbAvion.setItems(aviones);
            cbAvion.getSelectionModel().select(0);
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Elimina el avión
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        Avion avion = cbAvion.getSelectionModel().getSelectedItem();
        boolean resultado = DaoAvion.eliminar(avion);
        if (resultado) {
            confirmacion("Avión eliminado correctamente");
            Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
            stage.close();
        } else {
            alerta("Ha habido un error eliminado el avión. Por favor inténtelo de nuevo");
        }
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
