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
public class Cliente {

    private int id;
    private String numeroIdentificacion;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefonoFijo;
    private String telefonoCelular;
    private String direccion;
    private String email;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    public Cliente() {
    }

    public Cliente(int id, String numeroIdentificacion, String nombre, String primerApellido, String segundoApellido, String telefonoFijo, String telefonoCelular, String direccion, String email) {
        this.id = id;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.direccion = direccion;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return String.join(" ", nombre, primerApellido, segundoApellido);
    }

    public boolean validarEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validarTelefono() {
        return (telefonoFijo != null && TELEFONO_PATTERN.matcher(telefonoFijo).matches())
                || (telefonoCelular != null && TELEFONO_PATTERN.matcher(telefonoCelular).matches());
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s)", getNombreCompleto(), numeroIdentificacion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente c = (Cliente) o;
        return id == c.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
