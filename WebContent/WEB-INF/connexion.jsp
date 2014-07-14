<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ZPareo - Authentification</title>
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/ZPareo/ressources/scss/style.css" />
	</head>
	<body>
		<div id="site-wrap bg-white">
			<div class="vertical-align-middle"></div>
			<div id="mod-connection" class="mod mod--xs mod--center">
				<h1 class="mod__head text-center">ZPareo</h1>
				<form id="check-credentials" action="<c:url value='/connexion'/>" method="POST" class="form--horizontal">					
					<input type="text" name="adresseMail" class="form--control" value="<c:out value='${ utilisateur.adresseMail }'></c:out>" placeholder="Adresse mail" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}" x-moz-errormessage="Veuillez entrez un identifiant" required/>
					<input type="password" name="motDePasse" class="form--control" placeholder="Mot de passe" pattern=".{8,}" x-moz-errormessage="Veuillez entrez un mot de passe" required/>
					<span class="form__erreur">${ form.erreurs['connexion'] }</span>
					<button type="submit" class="btn btn--100 btn--primary">CONNEXION</button>
				</form>
				<div class="icon-load-wrap icon-load-wrap--center">
					<span class="icon-load"></span>
				</div>
			</div>
		</div><!-- Fin site-conteneur -->
		<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.js"></script>
		<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/highcharts.js"></script> 
    	<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery-ui.custom.min.js"></script>
		<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.tablesorter.min.js"></script>
		<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.ui.datepicker-fr.js"></script> 
		<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/script.js"></script> 
	</body>
</html>