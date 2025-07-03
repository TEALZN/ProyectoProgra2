/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

/**
 *
 * @author Zailyn Tencio
 */
import com.tallerparaiso.registro.tallerparaiso.Domain.Cliente;
import java.util.List;
import java.util.Optional;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.function.Function;

public class XmlClienteDAO extends XmlGenericDAO<Cliente, Integer> {

    public XmlClienteDAO(String rutaXml, String backupDir) {
        super(rutaXml, backupDir);
    }

    @Override
    protected String getRootTag() {
        return "Clientes";
    }

    @Override
    protected String getItemTag() {
        return "Cliente";
    }

    @Override
    protected Element toElement(Cliente cliente, Document doc) {
        Element e = new Element(getItemTag()).setAttribute("id", String.valueOf(cliente.getId()));
        e.addContent(new Element("numeroIdentificacion").setText(cliente.getNumeroIdentificacion()));
        e.addContent(new Element("nombre").setText(cliente.getNombre()));
        e.addContent(new Element("primerApellido").setText(cliente.getPrimerApellido()));
        e.addContent(new Element("segundoApellido").setText(cliente.getSegundoApellido()));
        e.addContent(new Element("telefonoFijo").setText(cliente.getTelefonoFijo()));
        e.addContent(new Element("telefonoCelular").setText(cliente.getTelefonoCelular()));
        e.addContent(new Element("direccion").setText(cliente.getDireccion()));
        e.addContent(new Element("email").setText(cliente.getEmail()));
        return e;
    }

    @Override
    protected Cliente toEntity(Element e) {
        Cliente cliente = new Cliente();
        cliente.setId(Integer.parseInt(e.getAttributeValue("id")));
        cliente.setNumeroIdentificacion(e.getChildText("numeroIdentificacion"));
        cliente.setNombre(e.getChildText("nombre"));
        cliente.setPrimerApellido(e.getChildText("primerApellido"));
        cliente.setSegundoApellido(e.getChildText("segundoApellido"));
        cliente.setTelefonoFijo(e.getChildText("telefonoFijo"));
        cliente.setTelefonoCelular(e.getChildText("telefonoCelular"));
        cliente.setDireccion(e.getChildText("direccion"));
        cliente.setEmail(e.getChildText("email"));
        return cliente;
    }

    @Override
    protected Integer extractId(Element e) {
        return Integer.valueOf(e.getAttributeValue("id"));
    }

    @Override
    protected Function<Cliente, Integer> idFunction() {
        return Cliente::getId;
    }

    @Override
    protected void setEntityId(Cliente c, int id) {
        c.setId(id);
    }
}
