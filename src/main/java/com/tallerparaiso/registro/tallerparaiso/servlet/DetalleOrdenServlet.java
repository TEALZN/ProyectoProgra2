/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tallerparaiso.registro.tallerparaiso.servlet;

import com.tallerparaiso.registro.tallerparaiso.Domain.DetalleOrden;
import com.tallerparaiso.registro.tallerparaiso.Domain.RepuestoDetalle;
import com.tallerparaiso.registro.tallerparaiso.Domain.ServicioDetalle;
import com.tallerparaiso.registro.tallerparaiso.dao.XmlDetalleOrdenDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Jose Solano
 */
@WebServlet("/ordenes/detalles")
public class DetalleOrdenServlet extends HttpServlet {

    private XmlDetalleOrdenDAO xmlDetalleOrden;

    @Override
    public void init() throws ServletException {
        super.init();
        String xml = getServletContext().getRealPath("/WEB-INF/detalles.xml");
        String bkup = getServletContext().getRealPath("/WEB-INF/backups/detalles");
        xmlDetalleOrden = new XmlDetalleOrdenDAO(xml, bkup);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        int ordenId = Integer.parseInt(req.getParameter("ordenId"));
        try {
            List<DetalleOrden> list = xmlDetalleOrden.leerTodos().stream().filter(d -> d.getOrdenId() == ordenId).toList();
            req.setAttribute("detalles", list);
            req.setAttribute("ordenId", ordenId);
            req.getRequestDispatcher("/detalles.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error en DetalleOrdenServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        try {
            String tipo = req.getParameter("tipo");
            DetalleOrden detalle;
            if ("SERVICIO".equals(tipo)) {
                detalle = new ServicioDetalle();
                ((ServicioDetalle) detalle).setServicioId(Integer.parseInt(req.getParameter("servicioId")));
                ((ServicioDetalle) detalle).setDescripcionServicio(req.getParameter("descripcionServicio"));
                ((ServicioDetalle) detalle).setCostoManoObra(new BigDecimal(req.getParameter("costoManoObra")));
            } else {
                detalle = new RepuestoDetalle();
                ((RepuestoDetalle) detalle).setRepuestoId(Integer.parseInt(req.getParameter("repuestoId")));
                ((RepuestoDetalle) detalle).setNombreRepuesto(req.getParameter("nombreRepuesto"));
                ((RepuestoDetalle) detalle).setPrecioUnitario(new BigDecimal(req.getParameter("precioUnitario")));
                ((RepuestoDetalle) detalle).setPedido(Boolean.parseBoolean(req.getParameter("pedido")));
            }

            String id = req.getParameter("id");
            if (id != null && !id.isBlank()) {
                detalle.setId(Integer.parseInt(id));
            }
            detalle.setOrdenId(Integer.parseInt(req.getParameter("ordenId")));
            detalle.setCantidad(Integer.parseInt(req.getParameter("cantidad")));
            detalle.setObservaciones(req.getParameter("observaciones"));

            if (id == null || id.isBlank()) {
                xmlDetalleOrden.crear(detalle);
            } else {
                xmlDetalleOrden.actualizar(detalle);
            }

            resp.sendRedirect(req.getContextPath() + "/ordenes/detalles?ordenId=" + detalle.getOrdenId());
        } catch (Exception e) {
            throw new ServletException("Error guardando detalle", e);
        }
    }
}
