<%-- 
    Document   : VehiculoForm
    Created on : 2 jul 2025, 7:20:11 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Formulario Vehículo</title></head>
<body>
  <h1><c:out value="${vehiculo != null ? 'Editar Vehículo' : 'Nuevo Vehículo'}"/></h1>
  <form action="vehiculos" method="post">
    <c:if test="${vehiculo != null}">
      <input type="hidden" name="id" value="${vehiculo.id}" />
    </c:if>
    <label>Placa:<input name="placa" value="${vehiculo.placa}"/></label><br/>
    <label>Color:<input name="color" value="${vehiculo.color}"/></label><br/>
    <label>Marca:<input name="marca" value="${vehiculo.marca}"/></label><br/>
    <label>Estilo:<input name="estilo" value="${vehiculo.estilo}"/></label><br/>
    <label>Año:<input name="anio" type="number" value="${vehiculo.anio}"/></label><br/>
    <label>VIN:<input name="vin" value="${vehiculo.vin}"/></label><br/>
    <label>Cilindraje:<input name="cilindraje" value="${vehiculo.cilindraje}"/></label><br/>
    <label>Cliente ID:<input name="clienteId" type="number" value="${vehiculo.clienteId}"/></label><br/>
    <button type="submit">Guardar</button>
  </form>
  <a href="vehiculos">Volver a lista</a>
</body>
</html>
