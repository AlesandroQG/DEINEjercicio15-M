package com.alesandro.ejercicio15m.dao;

import com.alesandro.ejercicio15m.db.DBConnect;
import com.alesandro.ejercicio15m.model.Aeropuerto;
import com.alesandro.ejercicio15m.model.Avion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase donde se ejecuta las consultas para la tabla Aviones
 */
public class DaoAvion {
    /**
     * Metodo que busca un avión por medio de su id
     *
     * @param id id del avión a buscar
     * @return avión o null
     */
    public static Avion getAvion(int id) {
        DBConnect connection;
        Avion avion = null;
        try {
            connection = new DBConnect();
            String consulta = "SELECT id,modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto FROM aviones WHERE id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_avion = rs.getInt("id");
                String modelo = rs.getString("modelo");
                int numero_asientos = rs.getInt("numero_asientos");
                int velocidad_maxima = rs.getInt("velocidad_maxima");
                boolean activado = rs.getBoolean("activado");
                int id_aeropuerto = rs.getInt("id_aeropuerto");
                Aeropuerto aeropuerto = DaoAeropuerto.getAeropuerto(id_aeropuerto);
                avion = new Avion(id_avion,modelo,numero_asientos,velocidad_maxima,activado,aeropuerto);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return avion;
    }

    /**
     * Metodo que carga los datos de la tabla Avión y los devuelve para usarlos en un listado de aviones
     *
     * @return listado de aviones para cargar en un tableview
     */
    public static ObservableList<Avion> cargarListado(Aeropuerto aeropuerto) {
        DBConnect connection;
        ObservableList<Avion> airplaneList = FXCollections.observableArrayList();
        try{
            connection = new DBConnect();
            String consulta = "SELECT id,modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto FROM aviones WHERE id_aeropuerto = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1,aeropuerto.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String modelo = rs.getString("modelo");
                int numero_asientos = rs.getInt("numero_asientos");
                int velocidad_maxima = rs.getInt("velocidad_maxima");
                boolean activado = rs.getBoolean("activado");
                int id_aeropuerto = rs.getInt("id_aeropuerto");
                Aeropuerto aeropuerto_db = DaoAeropuerto.getAeropuerto(id_aeropuerto);
                Avion avion = new Avion(id,modelo,numero_asientos,velocidad_maxima,activado,aeropuerto_db);
                airplaneList.add(avion);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return airplaneList;
    }

    /**
     * Metodo que carga los datos de la tabla Avión y los devuelve para usarlos en un listado de aviones
     *
     * @return listado de aviones para cargar en un tableview
     */
    public static ObservableList<Avion> cargarListado() {
        DBConnect connection;
        ObservableList<Avion> airplaneList = FXCollections.observableArrayList();
        try{
            connection = new DBConnect();
            String consulta = "SELECT id,modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto FROM aviones";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String modelo = rs.getString("modelo");
                int numero_asientos = rs.getInt("numero_asientos");
                int velocidad_maxima = rs.getInt("velocidad_maxima");
                boolean activado = rs.getBoolean("activado");
                int id_aeropuerto = rs.getInt("id_aeropuerto");
                Aeropuerto aeropuerto = DaoAeropuerto.getAeropuerto(id_aeropuerto);
                Avion avion = new Avion(id,modelo,numero_asientos,velocidad_maxima,activado,aeropuerto);
                airplaneList.add(avion);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return airplaneList;
    }

    /**
     * Metodo que modifica los datos de un avion en la BD
     *
     * @param avion		Instancia del avion con datos nuevos
     * @param avionNuevo Nuevos datos del avion a modificar
     * @return			true/false
     */
    public static boolean modificar(Avion avion, Avion avionNuevo) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "UPDATE aviones SET modelo = ?,numero_asientos = ?,velocidad_maxima = ?,activado = ?,id_aeropuerto = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, avionNuevo.getModelo());
            pstmt.setInt(2, avionNuevo.getNumero_asientos());
            pstmt.setInt(3, avionNuevo.getVelocidad_maxima());
            pstmt.setBoolean(4, avionNuevo.isActivado());
            pstmt.setInt(5, avionNuevo.getAeropuerto().getId());
            pstmt.setInt(6, avion.getId());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Actualizada avion");
            pstmt.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo que CREA un nuevo un avion en la BD
     *
     * @param avion		Instancia del modelo avion con datos nuevos
     * @return			id/-1
     */
    public  static int insertar(Avion avion) {
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');
            String consulta = "INSERT INTO aviones (modelo,numero_asientos,velocidad_maxima,activado,id_aeropuerto) VALUES (?,?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, avion.getModelo());
            pstmt.setInt(2, avion.getNumero_asientos());
            pstmt.setInt(3, avion.getVelocidad_maxima());
            pstmt.setBoolean(4, avion.isActivado());
            pstmt.setInt(5, avion.getAeropuerto().getId());
            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Nueva entrada en avion");
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
     * Elimina un avion en función del modelo Avion que le hayamos pasado
     *
     * @param avion Avion a eliminar
     * @return a boolean
     */
    public  static boolean eliminar(Avion avion){
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "DELETE FROM aviones WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, avion.getId());
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

    /**
     * Elimina un avion en función del modelo Avion que le hayamos pasado
     *
     * @param aeropuerto Aeropuerto a eliminar
     * @return a boolean
     */
    public  static boolean eliminarPorAeropuerto(Aeropuerto aeropuerto){
        DBConnect connection;
        PreparedStatement pstmt;
        try {
            connection = new DBConnect();
            String consulta = "DELETE FROM aviones WHERE id_aeropuerto = ?";
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
