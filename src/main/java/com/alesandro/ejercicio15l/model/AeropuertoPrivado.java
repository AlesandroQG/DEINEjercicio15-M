package com.alesandro.ejercicio15l.model;

import java.sql.Blob;

/**
 * Clase Aeropuerto Privado
 */
public class AeropuertoPrivado extends Aeropuerto {
    private int numero_socios;

    /**
     * Constructor con parámetros de aeropuerto privado
     *
     * @param id del aeropuerto
     * @param nombre del aeropuerto
     * @param anio_inauguracion del aeropuerto
     * @param capacidad del aeropuerto
     * @param direccion del aeropuerto
     * @param imagen del aeropuerto
     * @param numero_socios del aeropuerto
     */
    public AeropuertoPrivado(int id, String nombre, int anio_inauguracion, int capacidad, Direccion direccion, Blob imagen, int numero_socios) {
        super(id, nombre, anio_inauguracion, capacidad, direccion, imagen);
        this.numero_socios = numero_socios;
    }

    /**
     * Constructor vacío de aeropuerto privado
     */
    public AeropuertoPrivado() {}


    /**
     * Getter para el número de socios del aeropuerto
     *
     * @return número de socios del aeropuerto
     */
    public int getNumero_socios() {
        return numero_socios;
    }

    /**
     * Setter para el número de socios del aeropuerto
     *
     * @param numero_socios nuevo número de socios del aeropuerto
     */
    public void setNumero_socios(int numero_socios) {
        this.numero_socios = numero_socios;
    }

}
