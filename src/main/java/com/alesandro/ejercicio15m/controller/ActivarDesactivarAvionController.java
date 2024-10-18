package com.alesandro.ejercicio15m.controller;

import com.alesandro.ejercicio15m.dao.DaoAeropuerto;
import com.alesandro.ejercicio15m.dao.DaoAvion;
import com.alesandro.ejercicio15m.model.Aeropuerto;
import com.alesandro.ejercicio15m.model.Avion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana de activar o desactivar aviones
 */
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

    /**
     * Función que se ejecuta cuando se carga la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        cbAvion.valueProperty().addListener(new ChangeListener<Avion>() {
            @Override
            public void changed(ObservableValue<? extends Avion> observableValue, Avion oldValue, Avion newValue) {
                cambioAvion(newValue);
            }
        });
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
     * Función que cambia el estado de los radiobuttons cuando un avión es seleccionado
     *
     * @param avion nuevo avión seleccionado
     */
    public void cambioAvion(Avion avion) {
        if (avion != null) {
            boolean activado = avion.isActivado();
            if (activado) {
                rbActivado.setSelected(true);
                rbDesactivado.setSelected(false);
            } else {
                rbActivado.setSelected(false);
                rbDesactivado.setSelected(true);
            }
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
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Actualizada el avión
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        boolean activado = rbActivado.isSelected();
        Avion avion = cbAvion.getSelectionModel().getSelectedItem();
        Avion avionNuevo = new Avion(avion.getId(),avion.getModelo(),avion.getNumero_asientos(),avion.getVelocidad_maxima(),activado,avion.getAeropuerto());
        boolean resultado = DaoAvion.modificar(avion, avionNuevo);
        if (resultado) {
            confirmacion("Avión modificado correctamente");
            Stage stage = (Stage)cbAeropuerto.getScene().getWindow();
            stage.close();
        } else {
            alerta("Ha habido un error actualizando el avión. Por favor intentalo de nuevo");
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
