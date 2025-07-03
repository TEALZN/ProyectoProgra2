/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

/**
 *
 * @author Jose Solano
 */
import com.tallerparaiso.registro.tallerparaiso.Domain.CatalogoServicio;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XmlCatalogoServicioDAO extends XmlGenericDAO<CatalogoServicio, Integer> {

    public XmlCatalogoServicioDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "Servicios";
    }

    @Override
    protected String getItemTag() {
        return "Servicio";
    }

    @Override
    protected Element toElement(CatalogoServicio catalogoServicio, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(catalogoServicio.getId()));
        e.addContent(new Element("nombre").setText(catalogoServicio.getNombre()));
        e.addContent(new Element("descripcion").setText(catalogoServicio.getDescripcion()));
        e.addContent(new Element("costoManoObraEstandar")
                .setText(catalogoServicio.getCostoManoObraEstandar().toString()));
        e.addContent(new Element("activo").setText(String.valueOf(catalogoServicio.isActivo())));
        return e;
    }

    @Override
    protected CatalogoServicio toEntity(Element e) {
        CatalogoServicio catalogoServicio = new CatalogoServicio();
        catalogoServicio.setId(Integer.parseInt(e.getAttributeValue("id")));
        catalogoServicio.setNombre(e.getChildText("nombre"));
        catalogoServicio.setDescripcion(e.getChildText("descripcion"));
        catalogoServicio.setCostoManoObraEstandar(new BigDecimal(e.getChildText("costoManoObraEstandar")));
        catalogoServicio.setActivo(Boolean.parseBoolean(e.getChildText("activo")));
        return catalogoServicio;
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<CatalogoServicio, Integer> idFunction() {
        return CatalogoServicio::getId;
    }

    @Override
    protected void setEntityId(CatalogoServicio catalogoServicio, int id) {
        catalogoServicio.setId(id);
    }
}
