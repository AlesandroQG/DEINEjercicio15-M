package com.alesandro.ejercicio15l.controller;

import com.alesandro.ejercicio15l.dao.DaoUsuario;
import com.alesandro.ejercicio15l.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Clase que controla los eventos de la ventana
 */
public class LoginController {
    @FXML // fx:id="txtPassword"
    private TextField txtPassword; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsuario"
    private TextField txtUsuario; // Value injected by FXMLLoader

    /**
     * Función que se ejecuta cuando se pulsa el botón "Login"
     *
     * @param event
     */
    @FXML
    void login(ActionEvent event) {
        String error = "";
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        if (usuario.isEmpty()) {
            error = "El campo usuario no puede estar vacío";
        }
        if (password.isEmpty()) {
            if (!error.isEmpty()) {
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
            } else {
                if (password.equals(user.getPassword())) {
                    System.out.println("Correcto!");
                } else {
                    alerta("Contraseña incorrecta");
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