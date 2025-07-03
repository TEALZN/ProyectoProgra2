/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.servlet;

import com.tallerparaiso.registro.tallerparaiso.dao.XmlClienteDAO;
import com.tallerparaiso.registro.tallerparaiso.Domain.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Zailyn Tencio
 */
@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private XmlClienteDAO xmlCliente;

    @Override
    public void init() throws ServletException {
        super.init();
        String xml = getServletContext().getRealPath("/WEB-INF/clientes.xml");
        String bkup = getServletContext().getRealPath("/WEB-INF/backups/clientes");
        xmlCliente = new XmlClienteDAO(xml, bkup);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String id = req.getParameter("id");
        String nuevo = req.getParameter("nuevo");

        try {
            if (nuevo != null) {
                req.getRequestDispatcher("/clienteForm.jsp").forward(req, resp);
            } else if (id != null) {
                Optional<Cliente> c = xmlCliente.leerPorId(Integer.parseInt(id));
                req.setAttribute("cliente", c.orElse(null));
                req.getRequestDispatcher("/clienteForm.jsp").forward(req, resp);
            } else {
                List<Cliente> clientes = xmlCliente.leerTodos();
                req.setAttribute("clientes", clientes);
                req.getRequestDispatcher("/clientes.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error en ClienteServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Cliente cliente = new Cliente();
            String id = req.getParameter("id");
            if (id != null && !id.isBlank()) {
                cliente.setId(Integer.parseInt(id));
            }
            cliente.setNumeroIdentificacion(req.getParameter("numeroIdentificacion"));
            cliente.setNombre(req.getParameter("nombre"));
            cliente.setPrimerApellido(req.getParameter("primerApellido"));
            cliente.setSegundoApellido(req.getParameter("segundoApellido"));
            cliente.setTelefonoFijo(req.getParameter("telefonoFijo"));
            cliente.setTelefonoCelular(req.getParameter("telefonoCelular"));
            cliente.setDireccion(req.getParameter("direccion"));
            cliente.setEmail(req.getParameter("email"));

            if (id == null || id.isBlank()) {
                xmlCliente.crear(cliente);
            } else {
                xmlCliente.actualizar(cliente);
            }
            resp.sendRedirect(req.getContextPath() + "/clientes");
        } catch (Exception e) {
            throw new ServletException("Error guardando cliente", e);
        }
    }
}
