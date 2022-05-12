<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Curso JSP Revisado</title>
</head>
<body>

<h1>BEM-VINDO AO ESTUDO REVISADO DE JSP</h1>

<form action="ServletLogin" method="post">

<input type="hidden" value="<%= request.getParameter("url") %>" name="url">

<table>
<tr>
<td>
<label>LOGIN: </label>
</td>
<td>
<input name="login" type="text">
</td>
</tr>

<tr>
<td>
<label>SENHA: </label>
</td>
<td>
<input name="senha" type="password">
</td>
</tr>

<tr>
<td>
</td>
<td>
<input type="submit" value="ENVIAR">
</td>
</tr>
</table>

</form>

<h4>${msg}</h4>

</body>
</html>