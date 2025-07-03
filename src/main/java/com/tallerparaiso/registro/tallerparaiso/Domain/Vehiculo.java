/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.Domain;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author Jose Solano
 */
public class Vehiculo {

    private int id;
    private String placa;
    private String color;
    private String marca;
    private String estilo;
    private int anio;
    private String vin;
    private String cilindraje;
    private int clienteId;

    private static final Pattern PLACA_PATTERN = Pattern.compile("^[A-Z0-9\\-]{1,10}$");

    public Vehiculo() {
    }

    public Vehiculo(int id, String placa, String color, String marca, String estilo, int anio, String vin, String cilindraje, int clienteId) {
        this.id = id;
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.estilo = estilo;
        this.anio = anio;
        this.vin = vin;
        this.cilindraje = cilindraje;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public boolean validarPlaca() {
        return placa != null && PLACA_PATTERN.matcher(placa).matches();
    }

    public String getDescripcionCorta() {
        return String.format("%s %s (%d)", marca, estilo, anio);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", placa, getDescripcionCorta());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehiculo)) {
            return false;
        }
        Vehiculo v = (Vehiculo) o;
        return id == v.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
