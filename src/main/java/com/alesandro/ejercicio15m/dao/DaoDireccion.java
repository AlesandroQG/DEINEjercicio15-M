package com.alesandro.ejercicio15m.dao;

import com.alesandro.ejercicio15m.db.DBConnect;
import com.alesandro.ejercicio15m.model.Direccion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecuta las consultas para la tabla Direcciones
 */
public class DaoDireccion {
    /**
     * Metodo que obtiene los datos de la tabla Dirección
     *
     * @param id de la dirección a buscar
     * @return dirección o null
     */
    public static Direccion getDireccion(int id) {
        DBConnect connection;
        Direccion direccion = null;
        try {
            connection = new DBConnect();
            String consulta = "SELECT id,pais,ciudad,calle,numero FROM direcciones WHERE id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String pais = rs.getString("pais");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                direccion = new Direccion(id, pais, ciudad, calle, numero);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return direccion;
    }

    /**
     * Metodo que modifica los datos de una dirección en la BD
     *
     * @param direccion Instancia de la dirección con datos nuevos
     * @param direccionNueva Nuevos datos de la dirección a modificar
     * @return true/false
     */
    public static boolean modificar(Direccion direccion, Direccion direccionNueva) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "UPDATE direcciones SET pais = ?,ciudad = ?,calle = ?,numero = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, direccionNueva.getPais());
            pstmt.setString(2, direccionNueva.getCalle());
            pstmt.setString(3, direccionNueva.getCalle());
            pstmt.setInt(4, direccionNueva.getNumero());
            pstmt.setInt(5, direccion.getId());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizada dirección");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo una dirección en la BD
     *
     * @param direccion		Instancia del modelo dirección con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Direccion direccion) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "INSERT INTO direcciones (pais,ciudad,calle,numero) VALUES (?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, direccion.getPais());
            pstmt.setString(2, direccion.getCiudad());
            pstmt.setString(3, direccion.getCalle());
            pstmt.setInt(4, direccion.getNumero());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en dirección");
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pstmt.close();
                    connection.closeConnection();
                    return id;
                }
            }
            pstmt.close();
            connection.closeConnection();
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Elimina una dirección en función del modelo Dirección que le hayamos pasado
     *
     * @param direccion Dirección a eliminar
     * @return a boolean
     */
    public  static boolean eliminar(Direccion direccion){
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "DELETE FROM direcciones WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, direccion.getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConnection();
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
