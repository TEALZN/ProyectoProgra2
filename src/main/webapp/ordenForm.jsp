<%-- 
    Document   : OrdenForm
    Created on : 2 jul 2025, 7:20:52 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Formulario Orden</title></head>
<body>
  <h1><c:out value="${orden != null ? 'Editar Orden' : 'Nueva Orden'}"/></h1>
  <form action="ordenes" method="post">
    <c:if test="${orden != null}">
      <input type="hidden" name="id" value="${orden.id}" />
    </c:if>
    <label>Vehículo:
      <select name="vehiculoPlaca">
        <c:forEach var="v" items="${vehiculos}">
          <option value="${v.placa}"
            <c:if test="${orden != null && orden.vehiculoPlaca == v.placa}">selected</c:if>>
            ${v.placa} - ${v.marca}
          </option>
        </c:forEach>
      </select>
    </label><br/>
    <label>Descripción:<textarea name="descripcionSolicitud">${orden.descripcionSolicitud}</textarea></label><br/>
    <label>Fecha Ingreso:<input type="date" name="fechaIngreso" value="${orden.fechaIngreso}"/></label><br/>
    <label>Fecha Devolución Aproximada:<input type="date" name="fechaDevolucionAproximada" value="${orden.fechaDevolucionAproximada}"/></label><br/>
    <label>Estado:
      <select name="estado">
        <c:forEach var="e" items="${['DIAGNOSTICO','EN_REPARACION','LISTO_ENTREGA','ENTREGADO','CANCELADO']}">
          <option value="${e}"
            <c:if test="${orden != null && orden.estado.name() == e}">selected</c:if>>${e}</option>
        </c:forEach>
      </select>
    </label><br/>
    <label>Observaciones:<textarea name="observaciones">${orden.observaciones}</textarea></label><br/>
    <button type="submit">Guardar Orden</button>
  </form>
  <a href="ordenes">Volver a lista</a>
  <c:if test="${orden != null}">
    <hr/>
    <h2>Detalles</h2>
    <a href="ordenes/detalles?ordenId=${orden.id}">Ver Detalles</a>
  </c:if>
</body>
</html>
