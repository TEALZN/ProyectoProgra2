<%-- 
    Document   : Detalles
    Created on : 2 jul 2025, 7:21:27 p. m.
    Author     : Jose Solano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Detalles de Orden ${ordenId}</title></head>
<body>
  <h1>Detalles Orden #${ordenId}</h1>
  <table border="1">
    <tr><th>ID</th><th>Tipo</th><th>Cantidad</th><th>Subtotal</th><th>Acciones</th></tr>
    <c:forEach var="d" items="${detalles}">
      <tr>
        <td>${d.id}</td>
        <td>${d.tipo}</td>
        <td>${d.cantidad}</td>
        <td>${d.subTotal}</td>
        <td>
          <!-- aquí podrías enlazar a edición de detalle -->
        </td>
      </tr>
    </c:forEach>
  </table>
  <h2>Agregar Detalle</h2>
  <form action="ordenes/detalles" method="post">
    <input type="hidden" name="ordenId" value="${ordenId}" />
    <label>Tipo:
      <select name="tipo">
        <option value="SERVICIO">Servicio</option>
        <option value="REPUESTO">Repuesto</option>
      </select>
    </label><br/>
    <label>Cantidad:<input type="number" name="cantidad"/></label><br/>
    <label>Observaciones:<input name="observaciones"/></label><br/>
    <!-- Campos específicos según tipo -->
    <div id="servicioFields" style="display:none;">
      <label>Servicio ID:<input name="servicioId"/></label><br/>
      <label>Descripción Servicio:<input name="descripcionServicio"/></label><br/>
      <label>Costo Mano Obra:<input name="costoManoObra"/></label><br/>
    </div>
    <div id="repuestoFields" style="display:none;">
      <label>Repuesto ID:<input name="repuestoId"/></label><br/>
      <label>Nombre Repuesto:<input name="nombreRepuesto"/></label><br/>
      <label>Precio Unitario:<input name="precioUnitario"/></label><br/>
      <label>Pedido:<select name="pedido"><option value="true">Sí</option><option value="false">No</option></select></label><br/>
    </div>
    <button type="submit">Agregar</button>
  </form>
  <script>
    const tipoSelect = document.querySelector('select[name=tipo]');
    const serv = document.getElementById('servicioFields');
    const rep  = document.getElementById('repuestoFields');
    tipoSelect.addEventListener('change', () => {
      if (tipoSelect.value==='SERVICIO') {
        serv.style.display='block'; rep.style.display='none';
      } else {
        serv.style.display='none';  rep.style.display='block';
      }
    });
  </script>
  <a href="ordenes?id=${ordenId}">Volver a Orden</a>
</body>
</html>