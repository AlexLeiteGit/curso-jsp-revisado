<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TELA QUE MOSTRA OS ERROS</title>
</head>
<body>

<h1>Mensagem de Erro, entre em contato com a aquipe de suporte do sistema</h1>

<%

out.println(request.getAttribute("msg"));

%>

</body>
</html>