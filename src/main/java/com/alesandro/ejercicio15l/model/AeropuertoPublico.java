package com.alesandro.ejercicio15l.model;

import java.util.Objects;

/**
 * Clase Aeropuerto Público
 */
public class AeropuertoPublico {
    private int id_aeropuerto;
    private double financiacion;
    private int num_trabajadores;

    /**
     * Constructor con parámetros de aeropuerto público
     *
     * @param id_aeropuerto del aeropuerto
     * @param financiacion del aeropuerto
     * @param num_trabajadores del aeropuerto
     */
    public AeropuertoPublico(int id_aeropuerto, double financiacion, int num_trabajadores) {
        this.id_aeropuerto = id_aeropuerto;
        this.financiacion = financiacion;
        this.num_trabajadores = num_trabajadores;
    }

    /**
     * Constructor vacío de aeropuerto público
     */
    public AeropuertoPublico() {}

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
     * Getter para el id del aeropuerto
     *
     * @return id del aeropuerto
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPublico that = (AeropuertoPublico) o;
        return id_aeropuerto == that.id_aeropuerto && Double.compare(financiacion, that.financiacion) == 0 && num_trabajadores == that.num_trabajadores;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_aeropuerto, financiacion, num_trabajadores);
    }

}
