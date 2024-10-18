package com.alesandro.ejercicio15m.dao;

import com.alesandro.ejercicio15m.db.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alesandro.ejercicio15m.model.Usuario;

/**
 * Clase donde se ejecuta las consultas para la tabla Usuarios
 */
public class DaoUsuario {
    /**
     * Metodo que busca un usuario por medio de su nombre de usuario
     *
     * @param usuario nombre de usuario a buscar
     * @return usuario o null
     */
    public static Usuario getUsuario(String usuario) {
        DBConnect connection;
        Usuario user = null;
        try {
            connection = new DBConnect();
            String consulta = "SELECT usuario,password FROM usuarios WHERE usuario = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom_usuario = rs.getString("usuario");
                String password = rs.getString("password");
                user = new Usuario(nom_usuario, password);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return user;
    }
}
