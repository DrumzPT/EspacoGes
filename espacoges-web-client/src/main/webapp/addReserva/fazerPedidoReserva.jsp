<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.NewReservationModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css"> 
<title>EspacoGes : Adicionar reserva</title>
</head>
<body>
<h2>Adicionar Pedido de Reserva</h2>
<form action="criarReserva" method="post">

	<div class="mandatory_field">
   		<label for="nutente">Número Utente:</label> 
   		<input type="text" name="nutente" value="${model.nutente}"/>
   </div>
   
   <div class="optional_field">
   		<label for="telefone">Descrição da Atividade:</label> 
   		<input type="text" name="atividade" value="${model.atividade}"/>
   </div>
   <div class="mandatory_field">
		<label for="modalidade">Modalidade:</label>
		<select name="modalidade">  
			<c:forEach var="modalidade" items="${model.modalities}">
				<option selected = "selected" value="${modalidade.designation}">${modalidade.designation}</option>
			</c:forEach> 
		</select>
   </div>
   
   <div class="mandatory_field">
		<label for="horaInicio">Data e Hora Inicio:</label> 
		<input type="text" name="horaInicio" value="${model.horaInicio}"/> <br/>
    </div>
    
    <div class="mandatory_field">
		<label for="horaFim">Data e Hora Fim:</label> 
		<input type="text" name="horaFim" value="${model.horaFim}"/> <br/>
    </div>
    
    <div class="mandatory_field">
		<label for="nParticipants">Numero de participantes:</label> 
		<input type="text" name="nparticipants" value="${model.nparticipants}"/> <br/>
    </div>
    
   <div class="button" align="right">
   		<input type="submit" value="Fazer Pedido">
   </div>
</form>
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