/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.dao;

/**
 *
 * @author Zailyn Tencio
 */

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    List<T> leerTodos() throws Exception;
    Optional<T> leerPorId(ID id) throws Exception;
    void crear(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(ID id) throws Exception;
}