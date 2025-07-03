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

public class RepuestoDetalle extends DetalleOrden {

    private int repuestoId;
    private String nombreRepuesto;
    private BigDecimal precioUnitario;
    private boolean pedido;

    public RepuestoDetalle() {
    }

    public RepuestoDetalle(int id, int ordenId, int repuestoId, String nombreRepuesto, BigDecimal precioUnitario, int cantidad, boolean pedido, String observaciones) {
        super(id, ordenId, cantidad, observaciones);
        this.repuestoId = repuestoId;
        this.nombreRepuesto = nombreRepuesto;
        this.precioUnitario = precioUnitario;
        this.pedido = pedido;
    }

    public int getRepuestoId() {
        return repuestoId;
    }

    public void setRepuestoId(int repuestoId) {
        this.repuestoId = repuestoId;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public boolean isPedido() {
        return pedido;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }

    @Override
    public BigDecimal calcularSubTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    @Override
    public String getTipo() {
        return "REPUESTO";
    }

    @Override
    public String toString() {
        return nombreRepuesto + " x" + cantidad;
    }
}
