<%-- 
    Document   : clientes
    Created on : 2 jul 2025, 7:18:17 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Clientes</title></head>
<body>
  <h1>Clientes</h1>
  <a href="clientes?nuevo=true">Nuevo Cliente</a>
  <table border="1">
    <tr>
      <th>ID</th><th>Identificación</th><th>Nombre Completo</th><th>Teléfonos</th><th>Email</th><th>Acciones</th>
    </tr>
    <c:forEach var="c" items="${clientes}">
      <tr>
        <td>${c.id}</td>
        <td>${c.numeroIdentificacion}</td>
        <td>${c.nombre} ${c.primerApellido} ${c.segundoApellido}</td>
        <td>${c.telefonoFijo} / ${c.telefonoCelular}</td>
        <td>${c.email}</td>
        <td>
          <a href="clientes?id=${c.id}">Editar</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>
