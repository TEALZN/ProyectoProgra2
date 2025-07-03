<%-- 
    Document   : Ordenes
    Created on : 2 jul 2025, 7:20:34 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Órdenes de Trabajo</title></head>
<body>
  <h1>Órdenes</h1>
  <a href="ordenes?nuevo=true">Nueva Orden</a>
  <table border="1">
    <tr>
      <th>ID</th><th>Vehículo</th><th>Ingreso</th><th>Estado</th><th>Acciones</th>
    </tr>
    <c:forEach var="o" items="${ordenes}">
      <tr>
        <td>${o.id}</td>
        <td>${o.vehiculoPlaca}</td>
        <td>${o.fechaIngreso}</td>
        <td>${o.estado}</td>
        <td><a href="ordenes?id=${o.id}">Detalles / Editar</a></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>