package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.dao.DaoUsuario;
import com.alesandro.ejercicio15l.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana login
 */
public class LoginController implements Initializable {
    @FXML // fx:id="txtPassword"
    private TextField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsuario"
    private TextField txtUsuario; // Value injected by FXMLLoader

    /**
     * TODO: Delete this
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsuario.setText("admin");
        txtPassword.setText("admin");
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Login". Válida los datos y carga la ventana de aeropuertos
     *
     * @param event
     */
    @FXML
    void login(ActionEvent event) {
        String error = "";
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        if (usuario.isBlank()) {
            error = "El campo usuario no puede estar vacío";
        }
        if (password.isEmpty()) {
            if (!error.isBlank()) {
                error += "\n";
            }
            error += "El campo password no puede estar vacío";
        }
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Usuario user = DaoUsuario.getUsuario(usuario);
            if (user == null) {
                alerta("Usuario no valido");
                txtUsuario.setText("");
                txtPassword.setText("");
            } else {
                if (password.equals(user.getPassword())) {
                    System.out.println("Login correcto");
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxml/Aeropuertos.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("AVIONES - AEROPUERTOS");
                        stage.show();
                        Stage actual = (Stage) txtUsuario.getScene().getWindow();
                        actual.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        alerta("Error abriendo ventana, por favor inténtelo de nuevo");
                    }
                } else {
                    alerta("Contraseña incorrecta");
                    txtPassword.setText("");
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

}