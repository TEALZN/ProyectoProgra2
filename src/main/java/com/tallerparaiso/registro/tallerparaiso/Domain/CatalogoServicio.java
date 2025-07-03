/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.Domain;

/**
 *
 * @author Jose Solano
 */
import java.math.BigDecimal;
import java.util.Objects;

public class CatalogoServicio {

    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal costoManoObraEstandar;
    private boolean activo = true;

    public CatalogoServicio() {
    }

    public CatalogoServicio(int id, String nombre, String descripcion, BigDecimal costoManoObraEstandar) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoManoObraEstandar = costoManoObraEstandar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoManoObraEstandar() {
        return costoManoObraEstandar;
    }

    public void setCostoManoObraEstandar(BigDecimal costo) {
        this.costoManoObraEstandar = costo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean hayStock(int cantidad) {
        return activo;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogoServicio)) {
            return false;
        }
        CatalogoServicio c = (CatalogoServicio) o;
        return id == c.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
