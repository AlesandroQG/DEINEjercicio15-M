package com.alesandro.ejercicio15l.model;

import java.util.Objects;

/**
 * Clase Avión
 */
public class Avion {
    private int id;
    private String modelo;
    private int numero_asientos;
    private int velocidad_maxima;
    private boolean activado;
    private int id_aeropuerto;

    /**
     * Constructor con parámetros del avión
     *
     * @param id
     * @param modelo
     * @param numero_asientos
     * @param velocidad_maxima
     * @param activado
     * @param id_aeropuerto
     */
    public Avion(int id, String modelo, int numero_asientos, int velocidad_maxima, boolean activado, int id_aeropuerto) {
        this.id = id;
        this.modelo = modelo;
        this.numero_asientos = numero_asientos;
        this.velocidad_maxima = velocidad_maxima;
        this.activado = activado;
        this.id_aeropuerto = id_aeropuerto;
    }

    /**
     * Constructor vacío del avión
     */
    public Avion() {}

    /**
     * Getter para el id del avión
     *
     * @return id del avión
     */
    public int getId() {
        return id;
    }

    /**
     * Setter para el id del avión
     *
     * @param id nuevo id del avión
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter para módelo id del avión
     *
     * @return módelo del avión
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Setter para módelo id del avión
     *
     * @param modelo nuevo módelo del avión
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Getter para el número de asientos del avión
     *
     * @return número de asientos del avión
     */
    public int getNumero_asientos() {
        return numero_asientos;
    }

    /**
     * Setter para el número de asientos del avión
     *
     * @param numero_asientos nuevo número de asientos del avión
     */
    public void setNumero_asientos(int numero_asientos) {
        this.numero_asientos = numero_asientos;
    }

    /**
     * Getter para la velocidad máxima del avión
     *
     * @return velocidad máxima del avión
     */
    public int getVelocidad_maxima() {
        return velocidad_maxima;
    }

    /**
     * Setter para la velocidad máxima del avión
     *
     * @param velocidad_maxima nueva velocidad máxima del avión
     */
    public void setVelocidad_maxima(int velocidad_maxima) {
        this.velocidad_maxima = velocidad_maxima;
    }

    /**
     * Getter para si el avión está activado o no
     *
     * @return si el avión está activado o no
     */
    public boolean isActivado() {
        return activado;
    }

    /**
     * Setter para si el avión está activado o no
     *
     * @param activado nuevo valor para si el avión está activado o no
     */
    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    /**
     * Getter para el id del aeropuerto
     *
     * @return id del aeropuerto
     */
    public int getId_aeropuerto() {
        return id_aeropuerto;
    }

    /**
     * Setter para el id del aeropuerto
     *
     * @param id_aeropuerto nuevo id del aeropuerto
     */
    public void setId_aeropuerto(int id_aeropuerto) {
        this.id_aeropuerto = id_aeropuerto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return id == avion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
