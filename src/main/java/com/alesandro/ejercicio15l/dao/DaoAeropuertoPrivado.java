package com.alesandro.ejercicio15l.dao;

import com.alesandro.ejercicio15l.db.DBConnect;
import com.alesandro.ejercicio15l.model.AeropuertoPrivado;
import com.alesandro.ejercicio15l.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAeropuertoPrivado {
    /**
     * Metodo que carga los datos de la tabla AeropuertoPrivado y los devuelve para usarlos en un listado de personas
     *
     * @return listado de personas para cargar en un tableview
     */
    public static ObservableList<AeropuertoPrivado> cargarListado() {
        DBConnect connection;
        ObservableList<AeropuertoPrivado> airportList = FXCollections.observableArrayList();
        try{
            connection = new DBConnect();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,numero_socios FROM aeropuertos,aeropuertos_privados WHERE id=id_aeropuerto";
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
                int numero_socios = rs.getInt("numero_socios");
                AeropuertoPrivado airport = new AeropuertoPrivado(id,nombre,anio_inauguracion,capacidad,direccion,imagen,numero_socios);
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
     * Metodo que modifica los datos de una persona en la BD
     *
     * @param p		Instancia de la persona con datos nuevos
     * @param pNueva Nuevos datos de la persona a modificar
     * @return			true/false
     */
    public static boolean modificar(AeropuertoPrivado p, AeropuertoPrivado pNueva) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            // UPDATE `DNI`.`PAISES` SET `pais` = 'BulgariaK' WHERE (`pais` = 'Bulgaria');

            String consulta = "UPDATE AeropuertoPrivado SET nombre = ?,apellidos = ?,edad = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);

            pstmt.setString(1, pNueva.getNombre());
            pstmt.setString(2, pNueva.getApellidos());
            pstmt.setInt(3, pNueva.getEdad());
            pstmt.setInt(4, p.getId());

            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Actualizada personas");
            //if (pstmt != null)
            pstmt.close();
            //if (connection != null)
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo una persona en la BD
     *
     * @param persona		Instancia del modelo persona con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(AeropuertoPrivado persona) {
        DBConnect connection;
        PreparedStatement pstmt;

        try {
            connection = new DBConnect();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');

            String consulta = "INSERT INTO AeropuertoPrivado (nombre,apellidos,edad) VALUES (?,?,?) ";
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
     * Elimina una persona en función del modelo AeropuertoPrivado que le hayamos pasado
     *
     * @param personaAEliminar AeropuertoPrivado a eliminar
     * @return a boolean
     */
    public  static boolean eliminar(AeropuertoPrivado personaAEliminar){

        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            //DELETE FROM `DNI`.`dni` WHERE (`dni` = 'asdasd');
            String consulta = "DELETE FROM AeropuertoPrivado WHERE (id = ?)";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, personaAEliminar.getId());
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
