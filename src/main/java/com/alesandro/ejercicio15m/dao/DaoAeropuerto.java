package com.alesandro.ejercicio15m.dao;

import com.alesandro.ejercicio15m.db.DBConnect;
import com.alesandro.ejercicio15m.model.Aeropuerto;
import com.alesandro.ejercicio15m.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecuta las consultas para la tabla Aeropuertos
 */
public class DaoAeropuerto {
    /**
     * Metodo que busca un aeropuerto por medio de su id
     *
     * @param id id del aeropuerto a buscar
     * @return aeropuerto o null
     */
    public static Aeropuerto getAeropuerto(int id) {
        DBConnect connection;
        Aeropuerto aeropuerto = null;
        try {
            connection = new DBConnect();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos WHERE id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_aeropuerto = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = DaoDireccion.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                aeropuerto = new Aeropuerto(id_aeropuerto,nombre,anio_inauguracion,capacidad,direccion,imagen);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return aeropuerto;
    }

    /**
     * Metodo que carga los datos de la tabla Aeropuertos y los devuelve para usarlos en un listado de aeropuertos
     *
     * @return listado de aeropuertos para cargar en un tableview
     */
    public static ObservableList<Aeropuerto> cargarListado() {
        DBConnect connection;
        ObservableList<Aeropuerto> airportList = FXCollections.observableArrayList();
        try{
            connection = new DBConnect();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = DaoDireccion.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                Aeropuerto airport = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
                airportList.add(airport);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return airportList;
    }

    /**
     * Metodo que modifica los datos de un aeropuerto en la BD
     *
     * @param aeropuerto		Instancia del aeropuerto con datos nuevos
     * @param aeropuertoNuevo Nuevos datos del aeropuerto a modificar
     * @return			true/false
     */
    public static boolean modificar(Aeropuerto aeropuerto, Aeropuerto aeropuertoNuevo) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "UPDATE aeropuertos SET nombre = ?,anio_inauguracion = ?,capacidad = ?,id_direccion = ?,imagen = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, aeropuertoNuevo.getNombre());
            pstmt.setInt(2, aeropuertoNuevo.getAnio_inauguracion());
            pstmt.setInt(3, aeropuertoNuevo.getCapacidad());
            pstmt.setInt(4, aeropuertoNuevo.getDireccion().getId());
            pstmt.setBlob(5, aeropuertoNuevo.getImagen());
            pstmt.setInt(6, aeropuerto.getId());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizada aeropuerto");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo un aeropuerto en la BD
     *
     * @param aeropuerto		Instancia del modelo aeropuerto con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Aeropuerto aeropuerto) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');
            String consulta = "INSERT INTO aeropuertos (nombre,anio_inauguracion,capacidad,id_direccion,imagen) VALUES (?,?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, aeropuerto.getNombre());
            pstmt.setInt(2, aeropuerto.getAnio_inauguracion());
            pstmt.setInt(3, aeropuerto.getCapacidad());
            pstmt.setInt(4, aeropuerto.getDireccion().getId());
            pstmt.setBlob(5, aeropuerto.getImagen());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en aeropuerto");
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
     * Elimina un aeropuerto en función del modelo Aeropuerto que le hayamos pasado
     *
     * @param aeropuerto Aeropuerto a eliminar
     * @return a boolean
     */
    public  static boolean eliminar(Aeropuerto aeropuerto){
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "DELETE FROM aeropuertos WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, aeropuerto.getId());
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
