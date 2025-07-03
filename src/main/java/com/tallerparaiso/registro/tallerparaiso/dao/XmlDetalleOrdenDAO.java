/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

/**
 *
 * @author Zailyn Tencio
 */
import com.tallerparaiso.registro.tallerparaiso.Domain.DetalleOrden;
import com.tallerparaiso.registro.tallerparaiso.Domain.RepuestoDetalle;
import com.tallerparaiso.registro.tallerparaiso.Domain.ServicioDetalle;
import org.jdom2.Document;
import org.jdom2.Element;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class XmlDetalleOrdenDAO extends XmlGenericDAO<DetalleOrden, Integer> {

    public XmlDetalleOrdenDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "DetallesOrden";
    }

    @Override
    protected String getItemTag() {
        return "Detalle";
    }

    @Override
    protected Element toElement(DetalleOrden detalleOrden, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(detalleOrden.getId())).setAttribute("tipo", detalleOrden.getTipo());
        e.addContent(new Element("ordenId").setText(String.valueOf(detalleOrden.getOrdenId())));
        e.addContent(new Element("cantidad").setText(String.valueOf(detalleOrden.getCantidad())));
        e.addContent(new Element("observaciones").setText(detalleOrden.getObservaciones()));

        if (detalleOrden instanceof ServicioDetalle) {
            ServicioDetalle servicioDetalle = (ServicioDetalle) detalleOrden;
            e.addContent(new Element("servicioId").setText(String.valueOf(servicioDetalle.getServicioId())));
            e.addContent(new Element("descripcionServicio").setText(servicioDetalle.getDescripcionServicio()));
            e.addContent(new Element("costoManoObra").setText(servicioDetalle.getCostoManoObra().toString()));
        } else if (detalleOrden instanceof RepuestoDetalle) {
            RepuestoDetalle r = (RepuestoDetalle) detalleOrden;
            e.addContent(new Element("repuestoId").setText(String.valueOf(r.getRepuestoId())));
            e.addContent(new Element("nombreRepuesto").setText(r.getNombreRepuesto()));
            e.addContent(new Element("precioUnitario").setText(r.getPrecioUnitario().toString()));
            e.addContent(new Element("pedido").setText(String.valueOf(r.isPedido())));
        }
        return e;
    }

    @Override
    protected DetalleOrden toEntity(Element e) {
        String tipo = e.getAttributeValue("tipo");
        int id = Integer.parseInt(e.getAttributeValue("id"));
        int ordenId = Integer.parseInt(e.getChildText("ordenId"));
        int cantidad = Integer.parseInt(e.getChildText("cantidad"));
        String obs = e.getChildText("observaciones");

        if ("SERVICIO".equals(tipo)) {
            ServicioDetalle servicioDetalle = new ServicioDetalle();
            servicioDetalle.setId(id);
            servicioDetalle.setOrdenId(ordenId);
            servicioDetalle.setCantidad(cantidad);
            servicioDetalle.setObservaciones(obs);
            servicioDetalle.setServicioId(Integer.parseInt(e.getChildText("servicioId")));
            servicioDetalle.setDescripcionServicio(e.getChildText("descripcionServicio"));
            servicioDetalle.setCostoManoObra(new BigDecimal(e.getChildText("costoManoObra")));
            return servicioDetalle;
        } else {
            RepuestoDetalle repuestoDetalle = new RepuestoDetalle();
            repuestoDetalle.setId(id);
            repuestoDetalle.setOrdenId(ordenId);
            repuestoDetalle.setCantidad(cantidad);
            repuestoDetalle.setObservaciones(obs);
            repuestoDetalle.setRepuestoId(Integer.parseInt(e.getChildText("repuestoId")));
            repuestoDetalle.setNombreRepuesto(e.getChildText("nombreRepuesto"));
            repuestoDetalle.setPrecioUnitario(new BigDecimal(e.getChildText("precioUnitario")));
            repuestoDetalle.setPedido(Boolean.parseBoolean(e.getChildText("pedido")));
            return repuestoDetalle;
        }
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<DetalleOrden, Integer> idFunction() {
        return DetalleOrden::getId;
    }

    @Override
    protected void setEntityId(DetalleOrden d, int id) {
        d.setId(id);
    }
}
