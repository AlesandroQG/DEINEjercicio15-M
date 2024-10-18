package com.alesandro.ejercicio15m.controller;

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
import java.util.Optional;
import java.util.ResourceBundle;

import com.alesandro.ejercicio15m.model.*;
import com.alesandro.ejercicio15m.dao.*;
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

    private ObservableList masterData = FXCollections.observableArrayList();
    private ObservableList filteredData = FXCollections.observableArrayList();

    /**
     * Función que se ejecuta cuando se carga la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listener
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observableValue, Object oldValue, Object newValue) {
                if (newValue != null) {
                    deshabilitarMenus(false);
                } else {
                    deshabilitarMenus(true);
                }
            }
        });
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
        filtroNombre.setOnKeyTyped(keyEvent -> filtrar());
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DatosAeropuerto.fxml"));
            DatosAeropuertoController controlador = new DatosAeropuertoController();
            fxmlLoader.setController(controlador);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/avion.png")));
            stage.setTitle("AVIONES - AÑADIR AEROPUERTO");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (rbPublicos.isSelected()) {
                cargarPublicos();
            } else {
                cargarPrivados();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Editar" de aeropuertos. Abre un menú para editar el aeropuerto seleccionado
     *
     * @param event
     */
    @FXML
    void editarAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            alerta("Selecciona un aeropuerto antes de editarlo");
        } else {
            try {
                Window ventana = rbPrivados.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DatosAeropuerto.fxml"));
                DatosAeropuertoController controlador = new DatosAeropuertoController(aeropuerto);
                fxmlLoader.setController(controlador);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/avion.png")));
                stage.setTitle("AVIONES - EDITAR AEROPUERTO");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if (rbPublicos.isSelected()) {
                    cargarPublicos();
                } else {
                    cargarPrivados();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                alerta("Error abriendo ventana, por favor inténtelo de nuevo");
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Borrar" de aeropuertos. Elimina el aeropuerto seleccionado
     *
     * @param event
     */
    @FXML
    void borrarAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            alerta("Selecciona un aeropuerto antes de eliminarlo");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(tabla.getScene().getWindow());
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estás seguro que quieres eliminar ese aeropuerto? Esto también eliminara los aviones en este aeropuerto.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (aeropuerto instanceof AeropuertoPublico) {
                    // Aeropuerto Público
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                    ObservableList<Avion> aviones = DaoAvion.cargarListado(aeropuertoPublico.getAeropuerto());
                    for (Avion avion:aviones) {
                        if (!DaoAvion.eliminar(avion)) {
                            alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPublico.getAeropuerto();
                    if (DaoAeropuertoPublico.eliminar(aeropuertoPublico)) {
                        if (DaoAeropuerto.eliminar(airport)) {
                            cargarPublicos();
                            confirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                        }
                    } else {
                        alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                    }
                } else {
                    // Aeropuerto Privado
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    ObservableList<Avion> aviones = DaoAvion.cargarListado(aeropuertoPrivado.getAeropuerto());
                    for (Avion avion:aviones) {
                        if (!DaoAvion.eliminar(avion)) {
                            alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                            return;
                        }
                    }
                    Aeropuerto airport = aeropuertoPrivado.getAeropuerto();
                    if (DaoAeropuertoPrivado.eliminar(aeropuertoPrivado)) {
                        if (DaoAeropuerto.eliminar(airport)) {
                            cargarPrivados();
                            confirmacion("Aeropuerto eliminado correctamente");
                        } else {
                            alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                        }
                    } else {
                        alerta("No se pudo eliminar ese aeropuerto. Inténtelo de nuevo");
                    }
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Mostrar Info" de aeropuertos. Abre un menú para ver los datos del aeropuerto
     *
     * @param event
     */
    @FXML
    void infoAeropuerto(ActionEvent event) {
        Object aeropuerto = tabla.getSelectionModel().getSelectedItem();
        if (aeropuerto == null) {
            alerta("Selecciona un aeropuerto antes de ver su información");
        } else {
            String info = "";
            if (aeropuerto instanceof AeropuertoPublico) {
                // Aeropuerto Público
                AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                Aeropuerto airport = aeropuertoPublico.getAeropuerto();
                info = "Nombre: " + airport.getNombre();
                info += "\nPaís:" + airport.getDireccion().getPais();
                info += "\nDirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad();
                info += "\nAño de inauguración: " + airport.getAnio_inauguracion();
                info += "\nCapacidad: " + airport.getCapacidad();
                info += "\nAviones:";
                ObservableList<Avion> aviones = DaoAvion.cargarListado(airport);
                for (Avion avion:aviones) {
                    info += "\n\tModelo: " + avion.getModelo();
                    info += "\n\tNúmero de asientos: " + avion.getNumero_asientos();
                    info += "\n\tVelocidad máxima: " + avion.getVelocidad_maxima();
                    if (avion.isActivado()) {
                        info += "\n\tActivado";
                    } else {
                        info+="\n\tDesactivado";
                    }
                }
                info += "\nPúblico";
                info += "\nFinanciación: " + aeropuertoPublico.getFinanciacion();
                info += "\nNúmero de trabajadores: " + aeropuertoPublico.getNum_trabajadores();
            } else {
                // Aeropuerto Privado
                AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                Aeropuerto airport = aeropuertoPrivado.getAeropuerto();
                info = "Nombre: " + airport.getNombre();
                info += "\nPaís:" + airport.getDireccion().getPais();
                info += "\nDirección: C\\ " + airport.getDireccion().getCalle() + " " + airport.getDireccion().getNumero() + ", " + airport.getDireccion().getCiudad();
                info += "\nAño de inauguración: " + airport.getAnio_inauguracion();
                info += "\nCapacidad: " + airport.getCapacidad();
                info += "\nAviones:";
                ObservableList<Avion> aviones = DaoAvion.cargarListado(airport);
                for (Avion avion:aviones) {
                    info += "\n\tModelo: " + avion.getModelo();
                    info += "\n\tNúmero de asientos: " + avion.getNumero_asientos();
                    info += "\n\tVelocidad máxima: " + avion.getVelocidad_maxima();
                    if (avion.isActivado()) {
                        info += "\n\tActivado";
                    } else {
                        info += "\n\tDesactivado";
                    }
                }
                info += "\nPrivado";
                info += "\nNúmero de socios: " + aeropuertoPrivado.getNumero_socios();
            }
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Información");
            alerta.setContentText(info);
            alerta.showAndWait();
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Añadir" de aviones. Abre un menú para añadir un avión nuevo
     *
     * @param event
     */
    @FXML
    void aniadirAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AniadirAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/avion.png")));
            stage.setTitle("AVIONES - AÑADIR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Activar/Desactivar" de aviones. Abre un menú para activar o desactivar un avión
     *
     * @param event
     */
    @FXML
    void activarDesactivarAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ActivarDesactivarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/avion.png")));
            stage.setTitle("AVIONES - ACTIVAR/DESACTIVAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Borrar" de aviones. Abre un menú para borrar un avión
     *
     * @param event
     */
    @FXML
    void borrarAvion(ActionEvent event) {
        try {
            Window ventana = rbPrivados.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BorrarAvion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/avion.png")));
            stage.setTitle("AVIONES - BORRAR AVIÓN");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que carga los aeropuertos públicos en la lista
     */
    public void cargarPublicos() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        masterData.clear();
        filteredData.clear();
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
        masterData.setAll(aeropuertos);
        tabla.setItems(aeropuertos);
    }

    /**
     * Función que carga los aeropuertos privados en la lista
     */
    public void cargarPrivados() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        masterData.clear();
        filteredData.clear();
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
        masterData.setAll(aeropuertos);
        tabla.setItems(aeropuertos);
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
     * Función que filtra la tabla de aeropuertos por nombre
     */
    public void filtrar() {
        String valor = filtroNombre.getText();
        valor = valor.toLowerCase();
        if (valor.isEmpty()) {
            tabla.setItems(masterData);
        } else {
            filteredData.clear();
            if (masterData.getFirst() instanceof AeropuertoPublico) {
                for (Object aeropuerto : masterData) {
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                    String nombre = aeropuertoPublico.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.contains(valor)) {
                        filteredData.add(aeropuertoPublico);
                    }
                }
            } else {
                for (Object aeropuerto : masterData) {
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                    String nombre = aeropuertoPrivado.getAeropuerto().getNombre();
                    nombre = nombre.toLowerCase();
                    if (nombre.startsWith(valor)) {
                        filteredData.add(aeropuertoPrivado);
                    }
                }
            }
            tabla.setItems(filteredData);
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