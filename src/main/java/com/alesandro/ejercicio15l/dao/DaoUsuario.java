package com.alesandro.ejercicio15l.dao;

import com.alesandro.ejercicio15l.db.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alesandro.ejercicio15l.model.Usuario;

public class DaoUsuario {
    /**
     * Metodo que carga los datos de la tabla Persona y los devuelve para usarlos en un listado de personas
     *
     * @param usuario nombre de usuario a buscar
     * @return persona o null
     */
    public static Usuario getUsuario(String usuario) {
        DBConnect connection;
        Usuario user = null;
        try {
            connection = new DBConnect();
            String consulta = "SELECT usuario,password FROM usuarios";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom_usuario = rs.getString("usuario");
                String password = rs.getString("password");
                if (!nom_usuario.isBlank()) {
                    user = new Usuario(nom_usuario, password);
                }
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
