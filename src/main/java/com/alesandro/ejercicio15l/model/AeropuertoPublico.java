package com.alesandro.ejercicio15l.model;

import java.sql.Blob;

/**
 * Clase Aeropuerto Público
 */
public class AeropuertoPublico extends Aeropuerto {
    private double financiacion;
    private int num_trabajadores;

    /**
     * Constructor con parámetros de aeropuerto público
     *
     * @param id del aeropuerto
     * @param nombre del aeropuerto
     * @param anio_inauguracion del aeropuerto
     * @param capacidad del aeropuerto
     * @param direccion del aeropuerto
     * @param imagen del aeropuerto
     * @param financiacion del aeropuerto
     * @param num_trabajadores del aeropuerto
     */
    public AeropuertoPublico(int id, String nombre, int anio_inauguracion, int capacidad, Direccion direccion, Blob imagen, double financiacion, int num_trabajadores) {
        super(id, nombre, anio_inauguracion, capacidad, direccion, imagen);
        this.financiacion = financiacion;
        this.num_trabajadores = num_trabajadores;
    }

    /**
     * Constructor vacío de aeropuerto público
     */
    public AeropuertoPublico() {}

    /**
     * Getter para la financiación del aeropuerto
     *
     * @return financiación del aeropuerto
     */
    public double getFinanciacion() {
        return financiacion;
    }

    /**
     * Setter para la financiación del aeropuerto
     *
     * @param financiacion nueva financiación del aeropuerto
     */
    public void setFinanciacion(double financiacion) {
        this.financiacion = financiacion;
    }

    /**
     * Getter para el número de trabajadores del aeropuerto
     *
     * @return número de trabajadores del aeropuerto
     */
    public int getNum_trabajadores() {
        return num_trabajadores;
    }

    /**
     * Setter para el número de trabajadores del aeropuerto
     *
     * @param num_trabajadores nuevo número de trabajadores del aeropuerto
     */
    public void setNum_trabajadores(int num_trabajadores) {
        this.num_trabajadores = num_trabajadores;
    }

}
