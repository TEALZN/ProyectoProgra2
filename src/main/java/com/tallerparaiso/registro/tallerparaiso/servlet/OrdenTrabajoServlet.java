/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.servlet;

import com.tallerparaiso.registro.tallerparaiso.Domain.EstadoOrden;
import com.tallerparaiso.registro.tallerparaiso.Domain.OrdenTrabajo;
import com.tallerparaiso.registro.tallerparaiso.Domain.Vehiculo;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlOrdenTrabajoDAO;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlVehiculoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jose Solano
 */
@WebServlet("/ordenes")
public class OrdenTrabajoServlet extends HttpServlet {

    private XmlOrdenTrabajoDAO xmlOrdenTrabajo;
    private XmlVehiculoDAO xmlVehiculo;

    @Override
    public void init() throws ServletException {
        super.init();
        String xmlO = getServletContext().getRealPath("/WEB-INF/ordenes.xml");
        String bkupO = getServletContext().getRealPath("/WEB-INF/backups/ordenes");
        xmlOrdenTrabajo = new XmlOrdenTrabajoDAO(xmlO, bkupO);

        String xmlV = getServletContext().getRealPath("/WEB-INF/vehiculos.xml");
        String bkupV = getServletContext().getRealPath("/WEB-INF/backups/vehiculos");
        xmlVehiculo = new XmlVehiculoDAO(xmlV, bkupV);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String nuevo = req.getParameter("nuevo");

        try {
            if (nuevo != null) {
                List<Vehiculo> vehs = xmlVehiculo.leerTodos();
                req.setAttribute("vehiculos", vehs);
                req.getRequestDispatcher("/ordenForm.jsp").forward(req, resp);
            } else if (id != null) {
                Optional<OrdenTrabajo> opt = xmlOrdenTrabajo.leerPorId(Integer.parseInt(id));
                req.setAttribute("orden", opt.orElse(null));
                req.setAttribute("vehiculos", xmlVehiculo.leerTodos());
                req.getRequestDispatcher("/ordenForm.jsp").forward(req, resp);
            } else {
                List<OrdenTrabajo> list = xmlOrdenTrabajo.leerTodos();
                req.setAttribute("ordenes", list);
                req.getRequestDispatcher("/ordenes.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error en OrdenTrabajoServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OrdenTrabajo orden = new OrdenTrabajo();
            String id = req.getParameter("id");
            if (id != null && !id.isBlank()) {
                orden.setId(Integer.parseInt(id));
            }
            orden.setVehiculoPlaca(req.getParameter("vehiculoPlaca"));
            orden.setDescripcionSolicitud(req.getParameter("descripcionSolicitud"));
            orden.setFechaIngreso(LocalDate.parse(req.getParameter("fechaIngreso")));
            orden.setFechaDevolucionAproximada(LocalDate.parse(req.getParameter("fechaDevolucionAproximada")));
            orden.setEstado(EstadoOrden.valueOf(req.getParameter("estado")));
            orden.setObservaciones(req.getParameter("observaciones"));

            if (id == null || id.isBlank()) {
                xmlOrdenTrabajo.crear(orden);
            } else {
                xmlOrdenTrabajo.actualizar(orden);
            }
            resp.sendRedirect(req.getContextPath() + "/ordenes");
        } catch (Exception e) {
            throw new ServletException("Error guardando orden", e);
        }
    }
}
