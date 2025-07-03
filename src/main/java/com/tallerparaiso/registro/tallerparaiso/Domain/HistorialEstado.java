/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.Domain;

/**
 *
 * @author Jose Solano
 */
import java.time.LocalDateTime;
import java.util.Objects;
import org.jdom2.Element;

public class HistorialEstado {

    private LocalDateTime fechaCambio;
    private EstadoOrden estadoAnterior;
    private EstadoOrden estadoNuevo;
    private String observaciones;

    public HistorialEstado(LocalDateTime fechaCambio, EstadoOrden estadoAnterior, EstadoOrden estadoNuevo, String observaciones) {
        this.fechaCambio = fechaCambio;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public EstadoOrden getEstadoAnterior() {
        return estadoAnterior;
    }

    public EstadoOrden getEstadoNuevo() {
        return estadoNuevo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s (%s)",
                fechaCambio, estadoAnterior, estadoNuevo, observaciones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistorialEstado)) {
            return false;
        }
        HistorialEstado that = (HistorialEstado) o;
        return Objects.equals(fechaCambio, that.fechaCambio)
                && estadoAnterior == that.estadoAnterior
                && estadoNuevo == that.estadoNuevo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaCambio, estadoAnterior, estadoNuevo);
    }


     // Factory para deserializar HistorialEstado desde XML.

    public static HistorialEstado fromElement(Element e) {
        LocalDateTime fecha = LocalDateTime.parse(e.getChildText("fechaCambio"));
        EstadoOrden ant = EstadoOrden.valueOf(e.getChildText("estadoAnterior"));
        EstadoOrden neu = EstadoOrden.valueOf(e.getChildText("estadoNuevo"));
        String obs = e.getChildText("observaciones");
        return new HistorialEstado(fecha, ant, neu, obs);
    }
}
