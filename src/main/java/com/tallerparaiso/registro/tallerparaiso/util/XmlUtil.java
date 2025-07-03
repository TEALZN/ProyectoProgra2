/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.util;

/**
 *
 * @author Zailyn Tencio
 */
import jakarta.faces.validator.Validator;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class XmlUtil {
    private XmlUtil() { /* utilitario */ }

    private static final Map<String, Lock> locks = new ConcurrentHashMap<>();

    public static Lock getLock(String rutaXml) {
        return locks.computeIfAbsent(rutaXml, k -> new ReentrantLock());
    }

    public static Document cargarDocumento(String rutaXml) throws Exception {
        return loadDocument(rutaXml);
    }

    public static Document loadDocument(String rutaXml) throws Exception {
        File file = new File(rutaXml);
        if (!file.exists()) {
            return new Document(new Element("Data"));
        }
        SAXBuilder sax = new SAXBuilder();
        return sax.build(file);
    }

    @SuppressWarnings("unused")
    public static void validarDocumento(Document doc, String rutaXsd) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(rutaXsd));
        javax.xml.validation.Validator validator = schema.newValidator();

        File temp = File.createTempFile("jdom", ".xml");
        try (FileOutputStream fos = new FileOutputStream(temp)) {
            new XMLOutputter(Format.getPrettyFormat()).output(doc, fos);
        }
        validator.validate(new StreamSource(temp));
        temp.delete();
    }

    public static void guardarDocumento(Document doc, String rutaXml) throws Exception {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        try (FileOutputStream fos = new FileOutputStream(rutaXml)) {
            out.output(doc, fos);
        }
    }

    public static int generarNuevoId(java.util.List<Element> nodos, String idAttr) {
        return nodos.stream()
                .mapToInt(e -> Integer.parseInt(e.getAttributeValue(idAttr)))
                .max().orElse(0) + 1;
    }

    public static void reordenarNodos(Document doc, String tag, java.util.Comparator<Element> comp) {
        Element root = doc.getRootElement();
        java.util.List<Element> elems = new java.util.ArrayList<>(root.getChildren(tag));
        elems.sort(comp);
        root.removeChildren(tag);
        elems.forEach(root::addContent);
    }
}
