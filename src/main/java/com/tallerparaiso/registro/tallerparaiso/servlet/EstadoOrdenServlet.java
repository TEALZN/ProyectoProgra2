/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.servlet;

import com.tallerparaiso.registro.tallerparaiso.Domain.EstadoOrden;
import com.tallerparaiso.registro.tallerparaiso.Domain.OrdenTrabajo;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlOrdenTrabajoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Jose Solano
 */
@WebServlet("/ordenes/estado")
public class EstadoOrdenServlet extends HttpServlet {

    private XmlOrdenTrabajoDAO xmlOrdenTrabajo;

    @Override
    public void init() throws ServletException {
        super.init();
        String xml = getServletContext().getRealPath("/WEB-INF/ordenes.xml");
        String bkup = getServletContext().getRealPath("/WEB-INF/backups/ordenes");
        xmlOrdenTrabajo = new XmlOrdenTrabajoDAO(xml, bkup);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            EstadoOrden nuevo = EstadoOrden.valueOf(req.getParameter("nuevoEstado"));
            Optional<OrdenTrabajo> opt = xmlOrdenTrabajo.leerPorId(id);
            if (opt.isPresent()) {
                OrdenTrabajo orden = opt.get();
                orden.cambiarEstado(nuevo);
                xmlOrdenTrabajo.actualizar(orden);
            }
            resp.sendRedirect(req.getContextPath() + "/ordenes?id=" + id);
        } catch (Exception e) {
            throw new ServletException("Error cambiando estado", e);
        }
    }
}
