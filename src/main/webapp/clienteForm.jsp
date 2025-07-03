<%-- 
    Document   : ClienteForm
    Created on : 2 jul 2025, 7:19:04 p. m.
    Author     : Jose Solano
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Formulario Cliente</title></head>
<body>
  <h1><c:out value="${cliente != null ? 'Editar Cliente' : 'Nuevo Cliente'}"/></h1>
  <form action="clientes" method="post">
    <c:if test="${cliente != null}">
      <input type="hidden" name="id" value="${cliente.id}" />
    </c:if>
    <label>Número Identificación:<input name="numeroIdentificacion" value="${cliente.numeroIdentificacion}"/></label><br/>
    <label>Nombre:<input name="nombre" value="${cliente.nombre}"/></label><br/>
    <label>Primer Apellido:<input name="primerApellido" value="${cliente.primerApellido}"/></label><br/>
    <label>Segundo Apellido:<input name="segundoApellido" value="${cliente.segundoApellido}"/></label><br/>
    <label>Teléfono Fijo:<input name="telefonoFijo" value="${cliente.telefonoFijo}"/></label><br/>
    <label>Teléfono Celular:<input name="telefonoCelular" value="${cliente.telefonoCelular}"/></label><br/>
    <label>Dirección:<input name="direccion" value="${cliente.direccion}"/></label><br/>
    <label>Email:<input name="email" value="${cliente.email}"/></label><br/>
    <button type="submit">Guardar</button>
  </form>
  <a href="clientes">Volver a lista</a>
</body>
</html>
