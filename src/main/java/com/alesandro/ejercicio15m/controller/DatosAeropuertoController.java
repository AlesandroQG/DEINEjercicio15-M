package com.alesandro.ejercicio15m.controller;

import com.alesandro.ejercicio15m.dao.DaoAeropuerto;
import com.alesandro.ejercicio15m.dao.DaoAeropuertoPrivado;
import com.alesandro.ejercicio15m.dao.DaoAeropuertoPublico;
import com.alesandro.ejercicio15m.dao.DaoDireccion;
import com.alesandro.ejercicio15m.model.Aeropuerto;
import com.alesandro.ejercicio15m.model.AeropuertoPrivado;
import com.alesandro.ejercicio15m.model.AeropuertoPublico;
import com.alesandro.ejercicio15m.model.Direccion;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana datos de aeropuerto
 */
public class DatosAeropuertoController implements Initializable {
    private Object aeropuerto;
    private Aeropuerto aeropuerto_obj;
    private Blob imagenAeropuerto;

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

    @FXML // fx:id="txtAnioInauguracion"
    private TextField txtAnioInauguracion; // Value injected by FXMLLoader

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

    @FXML // fx:id="imagenView"
    private ImageView imagenView; // Value injected by FXMLLoader

    @FXML // fx:id="btnGuardar"
    private Button btnGuardar; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancelar"
    private Button btnCancelar; // Value injected by FXMLLoader

    /**
     * Constructor que define el aeropuerto a editar (si aplicable)
     *
     * @param aeropuerto a editar
     */
    public DatosAeropuertoController(Object aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Constructor para añadir un nuevo aeropuerto
     */
    public DatosAeropuertoController() {
        this.aeropuerto = null;
    }

    /**
     * Setter para el aeropuerto a editar (si aplicable)
     *
     * @param aeropuerto a editar
     */
    public void setAeropuerto(Object aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnGuardar.setDefaultButton(true);
        btnCancelar.setCancelButton(true);
        this.imagenAeropuerto = null;
        // Listener RadioButtons
        rbTipo.selectedToggleProperty().addListener(this::cambioTipo);
        // Carga inicial
        if (aeropuerto == null) {
            // Null -> Añadir Aeropuerto
            System.out.println("Null");
        } else {
            // Not Null -> Editar Aeropuerto
            System.out.println("Not Null");
            Aeropuerto airport;
            if (aeropuerto instanceof AeropuertoPublico) {
                // Aeropuerto Público
                AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) aeropuerto;
                airport = aeropuertoPublico.getAeropuerto();
                txtParam1.setText(aeropuertoPublico.getFinanciacion() + "");
                txtParam2.setText(aeropuertoPublico.getNum_trabajadores() + "");
            } else {
                // Aeropuerto Privado
                AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) aeropuerto;
                airport = aeropuertoPrivado.getAeropuerto();
                rbTipo.selectToggle(rbPrivado);
                txtParam1.setText(aeropuertoPrivado.getNumero_socios() + "");
            }
            this.aeropuerto_obj = airport;
            rbPublico.setDisable(true);
            rbPrivado.setDisable(true);
            // Rellena datos aeropuerto
            txtNombre.setText(airport.getNombre());
            txtPais.setText(airport.getDireccion().getPais());
            txtCiudad.setText(airport.getDireccion().getCiudad());
            txtCalle.setText(airport.getDireccion().getCalle());
            txtNumero.setText(airport.getDireccion().getNumero() + "");
            txtAnioInauguracion.setText(airport.getAnio_inauguracion() + "");
            txtCapacidad.setText(airport.getCapacidad() + "");
            if (airport.getImagen() != null) {
                System.out.println("Has image");
                this.imagenAeropuerto = airport.getImagen();
                InputStream imagen = null;
                try {
                    imagen = airport.getImagen().getBinaryStream();
                    imagenView.setImage(new Image(imagen));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Seleccionar". Abre un FileChooser para seleccionar una imagen
     *
     * @param event
     */
    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen de aeropuerto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png"));
        File file = fileChooser.showOpenDialog(null);
        try {
            InputStream imagen = new FileInputStream(file);
            Blob blob = DaoAeropuerto.convertFileToBlob(file);
            this.imagenAeropuerto = blob;
            imagenView.setImage(new Image(imagen));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Válida y procesa los datos del aeropuerto
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = "Campo nombre no puede estar vacío";
        }
        if (txtPais.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo país no puede estar vacío";
        }
        if (txtCiudad.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo ciudad no puede estar vacío";
        }
        if (txtCalle.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo calle no puede estar vacío";
        }
        if (txtNumero.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo número no puede estar vacío";
        } else {
            try {
                int numero = Integer.parseInt(txtNumero.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo número tiene que ser numérico";
            }
        }
        if (txtAnioInauguracion.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo año de inauguración no puede estar vacío";
        } else {
            try {
                int anio_inauguracion = Integer.parseInt(txtAnioInauguracion.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo año de inauguración tiene que ser numérico";
            }
        }
        if (txtCapacidad.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "Campo capacidad no puede estar vacío";
        } else {
            try {
                int capacidad = Integer.parseInt(txtCapacidad.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo capacidad tiene que ser numérico";
            }
        }
        if (rbPublico.isSelected()) {
            // Aeropuerto Público
            if (txtParam1.getText().isEmpty()) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo financiación no puede estar vacío";
            } else {
                if (!txtParam1.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) {
                    if (!error.isEmpty()) {
                        error += "\n";
                    }
                    error += "Campo financiación tiene que ser decimal";
                }
            }
            if (txtParam2.getText().isEmpty()) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo número de trabajadores no puede estar vacío";
            } else {
                try {
                    int capacidad = Integer.parseInt(txtParam2.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    if (!error.isEmpty()) {
                        error += "\n";
                    }
                    error += "Campo número de trabajadores tiene que ser numérico";
                }
            }
        } else {
            // Aeropuerto Privado
            if (txtParam1.getText().isEmpty()) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "Campo número de socios no puede estar vacío";
            } else {
                try {
                    int capacidad = Integer.parseInt(txtParam1.getText());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    if (!error.isEmpty()) {
                        error += "\n";
                    }
                    error += "Campo número de socios tiene que ser numérico";
                }
            }
        }
        // Fin verificación de datos
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            // Correcto
            boolean resultado;
            if (this.aeropuerto == null) {
                resultado = crearAeropuerto();
            } else {
                resultado = modificarAeropuerto();
            }
            if (resultado) {
                Stage stage = (Stage)txtNombre.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * FUnción que crea un aeropuerto nuevo en la base de datos
     *
     * @return true/false
     */
    public boolean crearAeropuerto() {
        Direccion direccion = new Direccion();
        direccion.setPais(txtPais.getText());
        direccion.setCiudad(txtCiudad.getText());
        direccion.setCalle(txtCalle.getText());
        direccion.setNumero(Integer.parseInt(txtNumero.getText()));
        int id_direccion = DaoDireccion.insertar(direccion);
        if (id_direccion == -1) {
            alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
            return false;
        } else {
            direccion.setId(id_direccion);
            Aeropuerto airport = new Aeropuerto();
            airport.setNombre(txtNombre.getText());
            airport.setDireccion(direccion);
            airport.setAnio_inauguracion(Integer.parseInt(txtAnioInauguracion.getText()));
            airport.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            airport.setImagen(imagenAeropuerto);
            int id_aeropuerto = DaoAeropuerto.insertar(airport);
            if (id_aeropuerto == -1) {
                alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                return false;
            } else {
                airport.setId(id_aeropuerto);
                if (rbPublico.isSelected()) {
                    // Aeropuerto Público
                    AeropuertoPublico aeropuertoPublico = new AeropuertoPublico(airport,new BigDecimal(txtParam1.getText()),Integer.parseInt(txtParam2.getText()));
                    if (!DaoAeropuertoPublico.insertar(aeropuertoPublico)) {
                        alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                        return false;
                    }
                } else {
                    // Aeropuerto Privado
                    AeropuertoPrivado aeropuertoPrivado = new AeropuertoPrivado(airport,Integer.parseInt(txtParam1.getText()));
                    if (!DaoAeropuertoPrivado.insertar(aeropuertoPrivado)) {
                        alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                        return false;
                    }
                }
                confirmacion("Aeropuerto creado correctamente");
                return true;
            }
        }
    }

    /**
     * Función que modifica un aeropuerto en la base de datos
     *
     * @return true/false
     */
    public boolean modificarAeropuerto() {
        Aeropuerto airport = new Aeropuerto();
        Direccion direccion = new Direccion();
        direccion.setId(aeropuerto_obj.getDireccion().getId());
        direccion.setPais(txtPais.getText());
        direccion.setCiudad(txtCiudad.getText());
        direccion.setCalle(txtCalle.getText());
        direccion.setNumero(Integer.parseInt(txtNumero.getText()));
        if (!DaoDireccion.modificar(this.aeropuerto_obj.getDireccion(),direccion)) {
            alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
            return false;
        } else {
            airport.setDireccion(direccion);
            airport.setNombre(txtNombre.getText());
            airport.setAnio_inauguracion(Integer.parseInt(txtAnioInauguracion.getText()));
            airport.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
            airport.setImagen(imagenAeropuerto);
            if (!DaoAeropuerto.modificar(aeropuerto_obj,airport)) {
                alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                return false;
            } else {
                if (this.aeropuerto instanceof AeropuertoPublico) {
                    // Aeropuerto Público
                    AeropuertoPublico aeropuertoPublico = (AeropuertoPublico) this.aeropuerto;
                    AeropuertoPublico newAirport = new AeropuertoPublico(airport,new BigDecimal(txtParam1.getText()),Integer.parseInt(txtParam2.getText()));
                    if (!DaoAeropuertoPublico.modificar(aeropuertoPublico,newAirport)) {
                        alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                        return false;
                    }
                } else {
                    // Aeropuerto Privado
                    AeropuertoPrivado aeropuertoPrivado = (AeropuertoPrivado) this.aeropuerto;
                    AeropuertoPrivado newAirport = new AeropuertoPrivado(airport,Integer.parseInt(txtParam1.getText()));
                    if (!DaoAeropuertoPrivado.modificar(aeropuertoPrivado,newAirport)) {
                        alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                        return false;
                    }
                }
                confirmacion("Aeropuerto actualizado correctamente");
                return true;
            }
        }
    }

    /**
     * Función que se ejecuta cuando se cambia los RadioButtons
     *
     * @param newBtn
     */
    public void cambioTipo(ObservableValue<? extends Toggle> observableValue, Toggle oldBtn, Toggle newBtn) {
        if (newBtn.equals(rbPublico)) {
            // Aeropuerto Público
            lblParam1.setText("Financiación:");
            lblParam2.setText("Número de trabajadores:");
            txtParam2.setVisible(true);
        } else {
            // Aeropuerto Privado
            lblParam1.setText("Número de socios:");
            lblParam2.setText(null);
            txtParam2.setVisible(false);
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtNombre.getScene().getWindow();
        stage.close();
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
