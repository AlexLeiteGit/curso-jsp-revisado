<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Curso JSP Revisado</title>

<style type="text/css">
form {
	position: absolute;
	top: 42%;
	left: 33%;
	right: 33%
}

h1 {
	position: absolute;
	top: 25%;
	left: 30%;
	right: 27%;
}

h3 {
	position: absolute;
	top: 70%;
	left: 37%;
	right: 27%;
	font-size: 20px;
	color: red;
}

.mgs {
	position: absolute;
	top: 70%;
	left: 37%;
	right: 27%;
	font-size: 60px;
	color: red;
}
</style>


</head>
<body>

	<h1>BEM-VINDO AO ESTUDO REVISADO DE JSP</h1>

	<form action="<%= request.getContextPath()%>/ServletLogin" method="post"
		class="row g-3 needs-validation"novalidate">

		<input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">

		<!--<div class="col-md-6">-->
		<div class="md-3">
			<label class="form-label">LOGIN: </label> <input class="form-control"
				name="login" type="text" required>
			<div class="valid-feedback">Looks good!</div>
			<div class="invalid-feedback">Do it Again!</div>
		</div>

		<!--<div class="col-md-6">-->
		<div class="md-3">
			<label class="form-label">SENHA: </label> <input class="form-control"
				name="senha" type="password" required>
			<div class="valid-feedback">Looks good!</div>
			<div class="invalid-feedback">Do it Again!</div>
		</div>

		<input class="btn btn-primary" type="submit" value="ENVIAR">


	</form>

	<h3 class="msg">${msg}</h3>

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

<script>
//Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
</script>
</body>
</html>