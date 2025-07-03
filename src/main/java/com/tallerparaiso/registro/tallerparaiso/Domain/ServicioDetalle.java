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

public class ServicioDetalle extends DetalleOrden {

    private int servicioId;
    private String descripcionServicio;
    private BigDecimal costoManoObra;

    public ServicioDetalle() {
    }

    public ServicioDetalle(int id, int ordenId, int servicioId, String descripcionServicio, BigDecimal costoManoObra, int cantidad, String observaciones) {
        super(id, ordenId, cantidad, observaciones);
        this.servicioId = servicioId;
        this.descripcionServicio = descripcionServicio;
        this.costoManoObra = costoManoObra;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public BigDecimal getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(BigDecimal costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    @Override
    public BigDecimal calcularSubTotal() {
        return costoManoObra.multiply(BigDecimal.valueOf(cantidad));
    }

    @Override
    public String getTipo() {
        return "SERVICIO";
    }

    @Override
    public String toString() {
        return descripcionServicio + " x" + cantidad;
    }
}
