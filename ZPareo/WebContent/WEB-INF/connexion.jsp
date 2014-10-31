<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ZPareo - Authentification</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/assets/scss/style.css"/>"/>
	</head>
	<body>
		<div id="site-wrap" class="bg-white">
			<div class="vertical-align-middle"></div>
			<div id="mod-connection" class="mod mod--xs mod--center">
				<h1 class="mod__head text-center">ZPareo</h1>
				<form id="check-login" action="<c:url value='/connexion'/>" method="POST" class="form--horizontal">					
					<input type="text" name="emailAddress" class="form--control" value="<c:out value='${utilisateur.adresseMail }'></c:out>" placeholder="Adresse mail" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}" x-moz-errormessage="Veuillez entrez un identifiant" required/>
					<input type="password" name="password" class="form--control" placeholder="Mot de passe" pattern=".{8,}" x-moz-errormessage="Veuillez entrez un mot de passe" required/>
					<span class="form__erreur">${form.erreurs['connexion'] }</span>
					<button type="submit" class="btn btn--100 btn--primary" onclick="checkLogin()">CONNEXION</button>
				</form>
				<div class="icon-load-wrap icon-load-wrap--center">
					<span class="icon-load"></span>
				</div>
			</div>
		</div><!-- End site-wrap -->
		<div id="modal" class="modal"></div>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery-ui.custom.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.tablesorter.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.ui.datepicker-fr.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/script.js"/>"></script>
	</body>
</html>