package com.tallerparaiso.registro.tallerparaiso.dao;

import com.tallerparaiso.registro.tallerparaiso.Domain.Vehiculo;
import java.util.function.Function;
import org.jdom2.Document;
import org.jdom2.Element;

public class XmlVehiculoDAO extends XmlGenericDAO<Vehiculo, Integer> {

    public XmlVehiculoDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "Vehiculos";
    }

    @Override
    protected String getItemTag() {
        return "Vehiculo";
    }

    @Override
    protected Element toElement(Vehiculo vehiculo, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(vehiculo.getId()));
        e.addContent(new Element("placa").setText(vehiculo.getPlaca()));
        e.addContent(new Element("color").setText(vehiculo.getColor()));
        e.addContent(new Element("marca").setText(vehiculo.getMarca()));
        e.addContent(new Element("estilo").setText(vehiculo.getEstilo()));
        e.addContent(new Element("anio").setText(String.valueOf(vehiculo.getAnio())));
        e.addContent(new Element("vin").setText(vehiculo.getVin()));
        e.addContent(new Element("cilindraje").setText(vehiculo.getCilindraje()));
        e.addContent(new Element("clienteId").setText(String.valueOf(vehiculo.getClienteId())));
        return e;
    }

    @Override
    protected Vehiculo toEntity(Element e) {
        return new Vehiculo(
                Integer.parseInt(e.getAttributeValue("id")),
                e.getChildText("placa"),
                e.getChildText("color"),
                e.getChildText("marca"),
                e.getChildText("estilo"),
                Integer.parseInt(e.getChildText("anio")),
                e.getChildText("vin"),
                e.getChildText("cilindraje"),
                Integer.parseInt(e.getChildText("clienteId"))
        );
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<Vehiculo, Integer> idFunction() {
        return Vehiculo::getId;
    }

    @Override
    protected void setEntityId(Vehiculo v, int id) {
        v.setId(id);
    }
}
