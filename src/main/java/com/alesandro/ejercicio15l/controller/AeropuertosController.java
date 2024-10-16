package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.AeropuertosApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.alesandro.ejercicio15l.model.*;
import com.alesandro.ejercicio15l.dao.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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

    @FXML // fx:id="filtroNombre"
    private TextField filtroNombre; // Value injected by FXMLLoader

    @FXML // fx:id="menuEditarAeropuerto"
    private MenuItem menuEditarAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="menuInfoAeropuerto"
    private MenuItem menuInfoAeropuerto; // Value injected by FXMLLoader

    @FXML // fx:id="tabla"
    private TableView tabla; // Value injected by FXMLLoader

    private ObservableList<Aeropuerto> masterData = FXCollections.observableArrayList();
    private ObservableList<Aeropuerto> filteredData = FXCollections.observableArrayList();

    /**
     * Función que se ejecuta cuando se carga la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Carga inicial
        cargarPublicos();
        // RadioButtons
        rbGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldBtn, Toggle newBtn) -> {
            if (newBtn.equals(rbPublicos)) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        });
        // Filtro nombre
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Añadir" de aeropuertos. Abre un menú para añadir un aeropuerto nuevo
     *
     * @param event
     */
    @FXML
    void aniadirAeropuerto(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(AeropuertosController.class.getResource("/fxml/DatosAeropuerto.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(AeropuertosApplication.class.getResourceAsStream("/imagenes/avion.png")));
            stage.setTitle("AVIONES - AÑADIR AEROPUERTO");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
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
     * Función que carga los aeropuertos públicos en la lista
     */
    public void cargarPublicos() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        // Cargar columnas
        TableColumn<AeropuertoPublico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));
        TableColumn<AeropuertoPublico, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));
        TableColumn<AeropuertoPublico, String> colPais = new TableColumn<>("País");
        colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));
        TableColumn<AeropuertoPublico, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));
        TableColumn<AeropuertoPublico, String> colCalle = new TableColumn<>("Calle");
        colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));
        TableColumn<AeropuertoPublico, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));
        TableColumn<AeropuertoPublico, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));
        TableColumn<AeropuertoPublico, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));
        TableColumn<AeropuertoPublico, Integer> colFinanciacion = new TableColumn<>("Financiación");
        colFinanciacion.setCellValueFactory(new PropertyValueFactory("financiacion"));
        TableColumn<AeropuertoPublico, Integer> colTrabajadores = new TableColumn<>("Nº Trabajadores");
        colTrabajadores.setCellValueFactory(new PropertyValueFactory("num_trabajadores"));
        tabla.getColumns().addAll(colId,colNombre,colPais,colCiudad,colCalle,colNumero,colAnio,colCapacidad,colFinanciacion,colTrabajadores);
        // Cargar filas
        ObservableList<AeropuertoPublico> aeropuertos = DaoAeropuertoPublico.cargarListado();
        tabla.getItems().addAll(aeropuertos);
        // Listener
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AeropuertoPublico>() {
            @Override
            public void changed(ObservableValue<? extends AeropuertoPublico> observableValue, AeropuertoPublico oldValue, AeropuertoPublico newValue) {
                if (newValue != null) {
                    deshabilitarMenus(false);
                } else {
                    deshabilitarMenus(true);
                }
            }
        });
    }

    /**
     * Función que carga los aeropuertos privados en la lista
     */
    public void cargarPrivados() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        // Cargar columnas
        TableColumn<AeropuertoPrivado, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getId()));
        TableColumn<AeropuertoPrivado, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getNombre()));
        TableColumn<AeropuertoPrivado, String> colPais = new TableColumn<>("País");
        colPais.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getPais()));
        TableColumn<AeropuertoPrivado, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCiudad()));
        TableColumn<AeropuertoPrivado, String> colCalle = new TableColumn<>("Calle");
        colCalle.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getCalle()));
        TableColumn<AeropuertoPrivado, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getDireccion().getNumero()));
        TableColumn<AeropuertoPrivado, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getAnio_inauguracion()));
        TableColumn<AeropuertoPrivado, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getAeropuerto().getCapacidad()));
        TableColumn<AeropuertoPrivado, Integer> colSocios = new TableColumn<>("Nº Socios");
        colSocios.setCellValueFactory(new PropertyValueFactory("numero_socios"));
        tabla.getColumns().addAll(colId,colNombre,colPais,colCiudad,colCalle,colNumero,colAnio,colCapacidad,colSocios);
        // Cargar filas
        ObservableList<AeropuertoPrivado> aeropuertos = DaoAeropuertoPrivado.cargarListado();
        tabla.getItems().addAll(aeropuertos);
        // Listener
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AeropuertoPrivado>() {
            @Override
            public void changed(ObservableValue<? extends AeropuertoPrivado> observableValue, AeropuertoPrivado oldValue, AeropuertoPrivado newValue) {
                if (newValue != null) {
                    deshabilitarMenus(false);
                } else {
                    deshabilitarMenus(true);
                }
            }
        });
    }

    /**
     * Función que habilita o deshabilita los menus de aeropuertos
     *
     * @param deshabilitado true/false
     */
    public void deshabilitarMenus(boolean deshabilitado) {
        menuEditarAeropuerto.setDisable(deshabilitado);
        menuBorrarAeropuerto.setDisable(deshabilitado);
        menuInfoAeropuerto.setDisable(deshabilitado);
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