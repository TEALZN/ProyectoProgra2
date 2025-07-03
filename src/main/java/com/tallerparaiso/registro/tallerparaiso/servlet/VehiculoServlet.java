/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlVehiculoDAO;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlClienteDAO;
import com.tallerparaiso.registro.tallerparaiso.Domain.Vehiculo;
import com.tallerparaiso.registro.tallerparaiso.Domain.Cliente;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Zailyn Tencio
 */
@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {

    private XmlVehiculoDAO xmlVehiculo;

    @Override
    public void init() throws ServletException {
        super.init();
        String xml = getServletContext().getRealPath("/WEB-INF/vehiculos.xml");
        String bkup = getServletContext().getRealPath("/WEB-INF/backups/vehiculos");
        xmlVehiculo = new XmlVehiculoDAO(xml, bkup);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String nuevo = req.getParameter("nuevo");

        try {
            if (nuevo != null) {
                req.getRequestDispatcher("/vehiculoForm.jsp").forward(req, resp);
            } else if (id != null) {
                Optional<Vehiculo> opt = xmlVehiculo.leerPorId(Integer.parseInt(id));
                req.setAttribute("vehiculo", opt.orElse(null));
                req.getRequestDispatcher("/vehiculoForm.jsp").forward(req, resp);
            } else {
                List<Vehiculo> vehs = xmlVehiculo.leerTodos();
                req.setAttribute("vehiculos", vehs);
                req.getRequestDispatcher("/vehiculos.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error en VehiculoServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Vehiculo vehiculo = new Vehiculo();
            String id = req.getParameter("id");
            if (id != null && !id.isBlank()) {
                vehiculo.setId(Integer.parseInt(id));
            }
            vehiculo.setPlaca(req.getParameter("placa"));
            vehiculo.setColor(req.getParameter("color"));
            vehiculo.setMarca(req.getParameter("marca"));
            vehiculo.setEstilo(req.getParameter("estilo"));
            vehiculo.setAnio(Integer.parseInt(req.getParameter("anio")));
            vehiculo.setVin(req.getParameter("vin"));
            vehiculo.setCilindraje(req.getParameter("cilindraje"));
            vehiculo.setClienteId(Integer.parseInt(req.getParameter("clienteId")));

            if (id == null || id.isBlank()) {
                xmlVehiculo.crear(vehiculo);
            } else {
                xmlVehiculo.actualizar(vehiculo);
            }
            resp.sendRedirect(req.getContextPath() + "/vehiculos");
        } catch (Exception e) {
            throw new ServletException("Error guardando vehículo", e);
        }
    }
}
