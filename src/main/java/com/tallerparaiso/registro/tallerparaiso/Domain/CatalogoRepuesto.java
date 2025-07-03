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
import java.util.Objects;
import org.jdom2.Element;

public class CatalogoRepuesto {

    private int id;
    private String nombre;
    private BigDecimal precioUnitarioBase;
    private int stockActual;
    private String proveedor;
    private boolean activo;

    public CatalogoRepuesto() {
    }

    public CatalogoRepuesto(int id, String nombre, BigDecimal precioUnitarioBase, int stockActual, String proveedor, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitarioBase = precioUnitarioBase;
        this.stockActual = stockActual;
        this.proveedor = proveedor;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioUnitarioBase() {
        return precioUnitarioBase;
    }

    public void setPrecioUnitarioBase(BigDecimal precio) {
        this.precioUnitarioBase = precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean hayStock(int cantidad) {
        return activo && stockActual >= cantidad;
    }

    public void reducirStock(int cantidad) {
        if (!hayStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente");
        }
        stockActual -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("Cantidad negativa");
        }
        stockActual += cantidad;
    }

    @Override
    public String toString() {
        return nombre + " (Stock: " + stockActual + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogoRepuesto)) {
            return false;
        }
        CatalogoRepuesto that = (CatalogoRepuesto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Deserializa un Repuesto desde XML.
     */
    public static CatalogoRepuesto fromElement(Element e) {
        CatalogoRepuesto r = new CatalogoRepuesto();
        r.setId(Integer.parseInt(e.getAttributeValue("id")));
        r.setNombre(e.getChildText("nombre"));
        r.setPrecioUnitarioBase(new BigDecimal(e.getChildText("precioUnitarioBase")));
        r.setStockActual(Integer.parseInt(e.getChildText("stockActual")));
        r.setProveedor(e.getChildText("proveedor"));
        r.setActivo(Boolean.parseBoolean(e.getChildText("activo")));
        return r;
    }
}
