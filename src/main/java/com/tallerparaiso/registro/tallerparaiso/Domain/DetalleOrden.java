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
import org.jdom2.Element;

public abstract class DetalleOrden {

    protected int id;
    protected int ordenId;
    protected int cantidad;
    protected String observaciones;

    public DetalleOrden() {
    }

    public DetalleOrden(int id, int ordenId, int cantidad, String observaciones) {
        this.id = id;
        this.ordenId = ordenId;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(int ordenId) {
        this.ordenId = ordenId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public abstract BigDecimal calcularSubTotal();

    public abstract String getTipo();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleOrden)) {
            return false;
        }
        DetalleOrden that = (DetalleOrden) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static DetalleOrden fromElement(Element e) {
        String tipo = e.getAttributeValue("tipo");
        int id = Integer.parseInt(e.getAttributeValue("id"));
        int ordenId = Integer.parseInt(e.getChildText("ordenId"));
        int cantidad = Integer.parseInt(e.getChildText("cantidad"));
        String obs = e.getChildText("observaciones");

        switch (tipo) {
            case "SERVICIO":
                ServicioDetalle s = new ServicioDetalle();
                s.setId(id);
                s.setOrdenId(ordenId);
                s.setCantidad(cantidad);
                s.setObservaciones(obs);
                s.setServicioId(Integer.parseInt(e.getChildText("servicioId")));
                s.setDescripcionServicio(e.getChildText("descripcionServicio"));
                s.setCostoManoObra(new BigDecimal(e.getChildText("costoManoObra")));
                return s;

            case "REPUESTO":
                RepuestoDetalle r = new RepuestoDetalle();
                r.setId(id);
                r.setOrdenId(ordenId);
                r.setCantidad(cantidad);
                r.setObservaciones(obs);
                r.setRepuestoId(Integer.parseInt(e.getChildText("repuestoId")));
                r.setNombreRepuesto(e.getChildText("nombreRepuesto"));
                r.setPrecioUnitario(new BigDecimal(e.getChildText("precioUnitario")));
                r.setPedido(Boolean.parseBoolean(e.getChildText("pedido")));
                return r;

            default:
                throw new IllegalArgumentException("Tipo de DetalleOrden desconocido: " + tipo);
        }
    }
}
