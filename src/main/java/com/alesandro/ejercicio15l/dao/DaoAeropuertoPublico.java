package com.alesandro.ejercicio15l.dao;

import com.alesandro.ejercicio15l.db.DBConnect;
import com.alesandro.ejercicio15l.model.AeropuertoPublico;
import com.alesandro.ejercicio15l.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAeropuertoPublico {
    /**
     * Metodo que carga los datos de la tabla AeropuertoPublico y los devuelve para usarlos en un listado de aeropuertos
     *
     * @return listado de aeropuertos públicos para cargar en un tableview
     */
    public static ObservableList<AeropuertoPublico> cargarListado() {
        DBConnect connection;
        ObservableList<AeropuertoPublico> airportList = FXCollections.observableArrayList();
        try{
            connection = new DBConnect();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,financiacion,num_trabajadores FROM aeropuertos,aeropuertos_publicos WHERE id=id_aeropuerto";
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
                double financiacion = rs.getDouble("financiacion");
                int num_trabajadores = rs.getInt("num_trabajadores");
                AeropuertoPublico airport = new AeropuertoPublico(id,nombre,anio_inauguracion,capacidad,direccion,imagen,financiacion,num_trabajadores);
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
    public static boolean modificar(AeropuertoPublico aeropuerto, AeropuertoPublico aeropuertoNuevo) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "UPDATE aeropuertos_publicos SET nombre = ?,apellidos = ?,edad = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, pNueva.getNombre());
            pstmt.setString(2, pNueva.getApellidos());
            pstmt.setInt(3, pNueva.getEdad());
            pstmt.setInt(4, p.getId());
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
    public  static int insertar(AeropuertoPublico persona) {
        DBConnect connection;
        PreparedStatement pstmt;

        try {
            connection = new DBConnect();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');

            String consulta = "INSERT INTO AeropuertoPublico (nombre,apellidos,edad) VALUES (?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellidos());
            pstmt.setInt(3, persona.getEdad());

            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)

            //if (connection != null)
            System.out.println("Nueva entrada en  persona");
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
     * Elimina un aeropuerto en función del modelo AeropuertoPublico que le hayamos pasado
     *
     * @param aeropuerto aeropuerto a eliminar
     * @return a boolean
     */
    public  static boolean eliminar(AeropuertoPublico aeropuerto){
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "DELETE FROM aeropuertos_publicos WHERE (id = ?)";
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
