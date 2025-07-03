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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.jdom2.Element;

public class OrdenTrabajo {

    private int id;
    private String vehiculoPlaca;
    private String descripcionSolicitud;
    private LocalDate fechaIngreso;
    private LocalDate fechaDevolucionAproximada;
    private EstadoOrden estado;
    private String observaciones;
    private List<DetalleOrden> detalles = new ArrayList<>();
    private List<HistorialEstado> historialEstados = new ArrayList<>();

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(int id, String vehiculoPlaca, String descripcionSolicitud, LocalDate fechaIngreso, LocalDate fechaDevolucionAproximada, EstadoOrden estado, String observaciones) {
        this.id = id;
        this.vehiculoPlaca = vehiculoPlaca;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.fechaDevolucionAproximada = fechaDevolucionAproximada;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehiculoPlaca() {
        return vehiculoPlaca;
    }

    public void setVehiculoPlaca(String vehiculoPlaca) {
        this.vehiculoPlaca = vehiculoPlaca;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaDevolucionAproximada() {
        return fechaDevolucionAproximada;
    }

    public void setFechaDevolucionAproximada(LocalDate fechaDevolucionAproximada) {
        this.fechaDevolucionAproximada = fechaDevolucionAproximada;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<DetalleOrden> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrden> detalles) {
        this.detalles = detalles;
    }

    public List<HistorialEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<HistorialEstado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public void agregarDetalle(DetalleOrden d) {
        if (puedeAgregarDetalle()) {
            detalles.add(d);
        }
    }

    public boolean eliminarDetalle(int detalleId) {
        return detalles.removeIf(d -> d.getId() == detalleId);
    }

    public boolean actualizarDetalle(DetalleOrden nuevo) {
        for (int i = 0; i < detalles.size(); i++) {
            if (detalles.get(i).getId() == nuevo.getId()) {
                detalles.set(i, nuevo);
                return true;
            }
        }
        return false;
    }

    public BigDecimal calcularCostoTotal() {
        return detalles.stream()
                .map(DetalleOrden::calcularSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void cambiarEstado(EstadoOrden nuevo) {
        if (puedeCambiarEstado(nuevo)) {
            historialEstados.add(new HistorialEstado(
                    LocalDateTime.now(), this.estado, nuevo, this.observaciones));
            this.estado = nuevo;
        }
    }

    public boolean puedeAgregarDetalle() {
        return estado == EstadoOrden.DIAGNOSTICO
                || estado == EstadoOrden.EN_REPARACION;
    }

    public boolean puedeCambiarEstado(EstadoOrden nuevo) {
        switch (this.estado) {
            case DIAGNOSTICO:
                return EnumSet.of(EstadoOrden.EN_REPARACION, EstadoOrden.CANCELADO)
                        .contains(nuevo);
            case EN_REPARACION:
                return EnumSet.of(EstadoOrden.LISTO_ENTREGA, EstadoOrden.CANCELADO)
                        .contains(nuevo);
            case LISTO_ENTREGA:
                return nuevo == EstadoOrden.ENTREGADO;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return String.format("Orden #%d [%s]", id, estado);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo that = (OrdenTrabajo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    
     // Deserializa una OrdenTrabajo desde un Element JDOM. Se usa en
     // XmlOrdenTrabajoDAO.toEntity().
     
    public static OrdenTrabajo fromElement(Element e) {
        OrdenTrabajo o = new OrdenTrabajo();
        o.setId(Integer.parseInt(e.getAttributeValue("id")));
        o.setVehiculoPlaca(e.getChildText("vehiculoPlaca"));
        o.setDescripcionSolicitud(e.getChildText("descripcionSolicitud"));
        o.setFechaIngreso(LocalDate.parse(e.getChildText("fechaIngreso")));
        o.setFechaDevolucionAproximada(LocalDate.parse(e.getChildText("fechaDevolucionAproximada")));
        o.setEstado(EstadoOrden.valueOf(e.getChildText("estado")));
        o.setObservaciones(e.getChildText("observaciones"));

        // Detalles
        Element dets = e.getChild("Detalles");
        if (dets != null) {
            for (Element de : dets.getChildren("Detalle")) {
                DetalleOrden d = DetalleOrden.fromElement(de);
                o.getDetalles().add(d);
            }
        }

        // HistorialEstados
        Element hist = e.getChild("HistorialEstados");
        if (hist != null) {
            for (Element he : hist.getChildren("HistorialEstado")) {
                HistorialEstado h = HistorialEstado.fromElement(he);
                o.getHistorialEstados().add(h);
            }
        }

        return o;
    }
}
