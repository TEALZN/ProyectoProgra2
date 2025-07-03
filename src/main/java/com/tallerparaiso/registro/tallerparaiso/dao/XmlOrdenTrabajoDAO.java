package com.tallerparaiso.registro.tallerparaiso.dao;

import com.tallerparaiso.registro.tallerparaiso.Domain.EstadoOrden;
import com.tallerparaiso.registro.tallerparaiso.Domain.OrdenTrabajo;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Element;

public class XmlOrdenTrabajoDAO extends XmlGenericDAO<OrdenTrabajo, Integer> {

    public XmlOrdenTrabajoDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "OrdenesTrabajo";
    }

    @Override
    protected String getItemTag() {
        return "OrdenTrabajo";
    }

    @Override
    protected Element toElement(OrdenTrabajo ordenTrabajo, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(ordenTrabajo.getId()));
        e.addContent(new Element("vehiculoPlaca").setText(ordenTrabajo.getVehiculoPlaca()));
        e.addContent(new Element("descripcionSolicitud").setText(ordenTrabajo.getDescripcionSolicitud()));
        e.addContent(new Element("fechaIngreso").setText(ordenTrabajo.getFechaIngreso().toString()));
        e.addContent(new Element("fechaDevolucionAproximada").setText(ordenTrabajo.getFechaDevolucionAproximada().toString()));
        e.addContent(new Element("estado").setText(ordenTrabajo.getEstado().name()));
        e.addContent(new Element("observaciones").setText(ordenTrabajo.getObservaciones()));

        Element dets = new Element("Detalles");
        ordenTrabajo.getDetalles().forEach(d -> dets.addContent(new XmlDetalleOrdenDAO(rutaXml, backupManager.backupDir).toElement(d, doc))
        );
        e.addContent(dets);

        Element eHistorial = new Element("HistorialEstados");
        ordenTrabajo.getHistorialEstados().forEach(h -> {
            Element he = new Element("HistorialEstado");
            he.addContent(new Element("fechaCambio").setText(h.getFechaCambio().toString()));
            he.addContent(new Element("estadoAnterior").setText(h.getEstadoAnterior().name()));
            he.addContent(new Element("estadoNuevo").setText(h.getEstadoNuevo().name()));
            he.addContent(new Element("observaciones").setText(h.getObservaciones()));
            eHistorial.addContent(he);
        });
        e.addContent(eHistorial);

        return e;
    }

    @Override
    protected OrdenTrabajo toEntity(Element e) {
        return OrdenTrabajo.fromElement(e);
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<OrdenTrabajo, Integer> idFunction() {
        return OrdenTrabajo::getId;
    }

    @Override
    protected void setEntityId(OrdenTrabajo o, int id) {
        o.setId(id);
    }
}
