package com.alesandro.ejercicio15m.controller;

import com.alesandro.ejercicio15m.dao.DaoAeropuerto;
import com.alesandro.ejercicio15m.dao.DaoAvion;
import com.alesandro.ejercicio15m.model.Aeropuerto;
import com.alesandro.ejercicio15m.model.Avion;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana de añadir aviones
 */
public class AniadirAvionController implements Initializable {
    @FXML // fx:id="cbAeropuerto"
    private ComboBox<Aeropuerto> cbAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="rbActivado"
    private RadioButton rbActivado; // Value injected by FXMLLoader

    @FXML // fx:id="rbDesactivado"
    private RadioButton rbDesactivado; // Value injected by FXMLLoader

    @FXML // fx:id="rbGroup"
    private ToggleGroup rbGroup; // Value injected by FXMLLoader

    @FXML // fx:id="txtAsientos"
    private TextField txtAsientos; // Value injected by FXMLLoader

    @FXML // fx:id="txtModelos"
    private TextField txtModelos; // Value injected by FXMLLoader

    @FXML // fx:id="txtVelMax"
    private TextField txtVelMax; // Value injected by FXMLLoader

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
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtAsientos.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Verifica y procesa los datos introducidos
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        int num_asientos = 0;
        int vel_max = 0;
        if (txtModelos.getText().isEmpty()) {
            error = "El modelo del avión no puede estar vacío";
        }
        if (txtAsientos.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El número de asientos del avión no puede estar vacío";
        } else {
            try {
                num_asientos = Integer.parseInt(txtAsientos.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "El número de asientos del avión tiene que ser un número entero";
            }
        }
        if (txtVelMax.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "La velocidad máxima del avión no puede estar vacío";
        } else {
            try {
                vel_max = Integer.parseInt(txtVelMax.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "La velocidad máxima del avión tiene que ser un número entero";
            }
        }
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Avion avion = new Avion();
            avion.setModelo(txtModelos.getText());
            avion.setNumero_asientos(num_asientos);
            avion.setVelocidad_maxima(vel_max);
            avion.setActivado(rbActivado.isSelected());
            avion.setAeropuerto(cbAeropuerto.getSelectionModel().getSelectedItem());
            ObservableList<Avion> aviones = DaoAvion.cargarListado();
            if (aviones.contains(avion)) {
                alerta("Este modelo ya existe en el aeropuerto. Elige otro modelo u otro aeropuerto");
            } else {
                int resultado = DaoAvion.insertar(avion);
                if (resultado == -1) {
                    alerta("Ha habido un error insertando el avión. Por favor inténtelo de nuevo");
                } else {
                    confirmacion("Avión insertado correctamente!");
                    Stage stage = (Stage)txtAsientos.getScene().getWindow();
                    stage.close();
                }
            }
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
