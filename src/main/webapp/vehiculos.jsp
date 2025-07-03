<%-- 
    Document   : Vehiculos
    Created on : 2 jul 2025, 7:19:47 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Vehículos</title></head>
<body>
  <h1>Vehículos</h1>
  <a href="vehiculos?nuevo=true">Nuevo Vehículo</a>
  <table border="1">
    <tr>
      <th>ID</th><th>Placa</th><th>Marca</th><th>Color</th><th>Dueño (ID)</th><th>Acciones</th>
    </tr>
    <c:forEach var="v" items="${vehiculos}">
      <tr>
        <td>${v.id}</td>
        <td>${v.placa}</td>
        <td>${v.marca}</td>
        <td>${v.color}</td>
        <td>${v.clienteId}</td>
        <td><a href="vehiculos?id=${v.id}">Editar</a></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>