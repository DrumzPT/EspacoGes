<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.NewVisualizeOccupationModel"
	scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>EspacoGes : Visualizar Ocupações</title>
</head>
<body>
	<h2>Visualizar Ocupações</h2>
	<form action="processarVisualizarOcupacoes" method="post">


		<div class="mandatory_field">
			<label for="installation">Instalao:</label> <select
				name="installation">
				<c:forEach var="inst" items="${model.installations}">
					<option selected="selected" value="${inst.name}">${inst.name}</option>
				</c:forEach>
			</select>
		</div>


		<div class="mandatory_field">
				<label for="dataInicio">Data e Hora de Inicio:</label> <input
					type="text" name="dataInicio" value="${model.dataInicio}" /> <br />
			</div>

			<div class="mandatory_field">
				<label for="dataFim">Data e Hora de Fim:</label> <input type="text"
					name="dataFim" value="${model.dataFim}" /> <br />
			</div>

			<div class="button" align="right">
				<input type="submit" value="Visualizar Ocupação">
			</div>
	</form>
	
	<c:if test="${model.hasLogs}">
		<p>Registo de Atividade:</p>
		<table  border="1" cellspacing="0" cellpadding= "5">
			<tr>
				<th>ID Reserva</th>
				<th>DataInicio</th>
				<th>DataFim</th>
			</tr>
				<c:forEach var="log" items="${model.logs}">
					<tr>
					<td><c:out value="${log.reservationID}" /> </td>
					<td><c:out value="${log.inicio}}" />	 </td>
					<td><c:out value="${log.fim}" />	 </td>
					</tr>
				</c:forEach>
		</table>		
	</c:if>
	
	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>