/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

import com.tallerparaiso.registro.tallerparaiso.Domain.CatalogoRepuesto;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Jose Solano
 */
public class XmlCatalogoRepuestoDAO extends XmlGenericDAO<CatalogoRepuesto, Integer> {

    public XmlCatalogoRepuestoDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "Repuestos";
    }

    @Override
    protected String getItemTag() {
        return "Repuesto";
    }

    @Override
    protected Element toElement(CatalogoRepuesto catalogoRepuesto, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(catalogoRepuesto.getId()));
        e.addContent(new Element("nombre").setText(catalogoRepuesto.getNombre()));
        e.addContent(new Element("precioUnitarioBase").setText(catalogoRepuesto.getPrecioUnitarioBase().toString()));
        e.addContent(new Element("stockActual").setText(String.valueOf(catalogoRepuesto.getStockActual())));
        e.addContent(new Element("proveedor").setText(catalogoRepuesto.getProveedor()));
        e.addContent(new Element("activo").setText(String.valueOf(catalogoRepuesto.isActivo())));
        return e;
    }

    @Override
    protected CatalogoRepuesto toEntity(Element e) {
        CatalogoRepuesto catalogoRepuesto = new CatalogoRepuesto();
        catalogoRepuesto.setId(Integer.parseInt(e.getAttributeValue("id")));
        catalogoRepuesto.setNombre(e.getChildText("nombre"));
        catalogoRepuesto.setPrecioUnitarioBase(new BigDecimal(e.getChildText("precioUnitarioBase")));
        catalogoRepuesto.setStockActual(Integer.parseInt(e.getChildText("stockActual")));
        catalogoRepuesto.setProveedor(e.getChildText("proveedor"));
        catalogoRepuesto.setActivo(Boolean.parseBoolean(e.getChildText("activo")));
        return catalogoRepuesto;
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<CatalogoRepuesto, Integer> idFunction() {
        return CatalogoRepuesto::getId;
    }

    @Override
    protected void setEntityId(CatalogoRepuesto r, int id) {
        r.setId(id);
    }

    public Optional<CatalogoRepuesto> buscarPorNombre(String nombre) throws Exception {
        return leerTodos().stream().filter(r -> r.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }

    public boolean hayStock(int id, int cant) throws Exception {
        return leerPorId(id).map(r -> r.getStockActual() >= cant).orElse(false);
    }

    public void reducirStock(int id, int cant) throws Exception {
        CatalogoRepuesto catalogoRepuesto = leerPorId(id).orElseThrow();
        if (catalogoRepuesto.getStockActual() < cant) {
            throw new IllegalStateException("Stock insuficiente");
        }
        catalogoRepuesto.setStockActual(catalogoRepuesto.getStockActual() - cant);
        actualizar(catalogoRepuesto);
    }

    public void aumentarStock(int id, int cant) throws Exception {
        CatalogoRepuesto catalogoRepuesto = leerPorId(id).orElseThrow();
        catalogoRepuesto.setStockActual(catalogoRepuesto.getStockActual() + cant);
        actualizar(catalogoRepuesto);
    }
}
