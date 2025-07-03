/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

/**
 *
 * @author Zailyn Tencio
 */
import com.tallerparaiso.registro.tallerparaiso.util.BackupManager;
import com.tallerparaiso.registro.tallerparaiso.util.LockManager;
import com.tallerparaiso.registro.tallerparaiso.util.XmlUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class XmlGenericDAO<T, ID> implements GenericDAO<T, ID> {

    protected final String rutaXml;
    protected final BackupManager backupManager;

    public XmlGenericDAO(String rutaXml, String backupDir) {
        this.rutaXml = rutaXml;
        this.backupManager = new BackupManager(backupDir);
        initializeFile();
    }

    private void initializeFile() {
        try {
            Document doc = XmlUtil.loadDocument(rutaXml);
            if (doc.getRootElement() == null) {
                doc.setRootElement(new Element(getRootTag()));
                XmlUtil.guardarDocumento(doc, rutaXml);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando XML: " + rutaXml, e);
        }
    }

    protected abstract String getRootTag();

    protected abstract String getItemTag();

    protected abstract Element toElement(T entidad, Document doc);

    protected abstract T toEntity(Element elem);

    protected abstract ID extractId(Element elem);

    protected abstract Function<T, ID> idFunction();

    protected abstract void setEntityId(T entidad, int id);

    @Override
    public List<T> leerTodos() throws Exception {
        return LockManager.executeWithLock(rutaXml, (Callable<List<T>>) () -> {
            Document doc = XmlUtil.cargarDocumento(rutaXml);
            List<Element> elems = doc.getRootElement().getChildren(getItemTag());
            return elems.stream().map(this::toEntity).collect(Collectors.toList());
        });
    }

    @Override
    public Optional<T> leerPorId(ID id) throws Exception {
        return leerTodos().stream().filter(e -> idFunction().apply(e).equals(id)).findFirst();
    }

    @Override
    public void crear(T entidad) throws Exception {
        LockManager.runWithLock(rutaXml, () -> {
            try {
                backupManager.backupFile(rutaXml);
                Document doc = XmlUtil.cargarDocumento(rutaXml);
                Element root = doc.getRootElement();
                int nextId = XmlUtil.generarNuevoId(root.getChildren(getItemTag()), "id");
                setEntityId(entidad, nextId);
                root.addContent(toElement(entidad, doc));
                XmlUtil.guardarDocumento(doc, rutaXml);
            } catch (Exception e) {
                throw new RuntimeException("Error en crear entidad", e);
            }
        });
    }

    @Override
    public void actualizar(T entidad) throws Exception {
        LockManager.runWithLock(rutaXml, () -> {
            try {
                backupManager.backupFile(rutaXml);
                Document doc = XmlUtil.cargarDocumento(rutaXml);
                Element root = doc.getRootElement();
                root.getChildren(getItemTag()).removeIf(e -> extractId(e).equals(idFunction().apply(entidad)));
                root.addContent(toElement(entidad, doc));
                XmlUtil.guardarDocumento(doc, rutaXml);
            } catch (Exception e) {
                throw new RuntimeException("Error en actualizar entidad", e);
            }
        });
    }

    @Override
    public void eliminar(ID id) throws Exception {
        LockManager.runWithLock(rutaXml, () -> {
            try {
                backupManager.backupFile(rutaXml);
                Document doc = XmlUtil.cargarDocumento(rutaXml);
                Element root = doc.getRootElement();
                root.getChildren(getItemTag()).removeIf(e -> extractId(e).equals(id));
                XmlUtil.guardarDocumento(doc, rutaXml);
            } catch (Exception e) {
                throw new RuntimeException("Error en eliminar entidad", e);
            }
        });
    }
}
