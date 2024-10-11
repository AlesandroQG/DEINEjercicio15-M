package com.alesandro.ejercicio15l.model;

import java.util.Objects;

/**
 * Clase Aeropuerto Privado
 */
public class AeropuertoPrivado {
    private int id_aeropuerto;
    private int numero_socios;

    /**
     * Constructor con parámetros de aeropuerto privado
     *
     * @param id_aeropuerto del aeropuerto
     * @param numero_socios del aeropuerto
     */
    public AeropuertoPrivado(int id_aeropuerto, int numero_socios) {
        this.id_aeropuerto = id_aeropuerto;
        this.numero_socios = numero_socios;
    }

    /**
     * Constructor vacío de aeropuerto privado
     */
    public AeropuertoPrivado() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPrivado that = (AeropuertoPrivado) o;
        return id_aeropuerto == that.id_aeropuerto && numero_socios == that.numero_socios;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_aeropuerto, numero_socios);
    }

}
