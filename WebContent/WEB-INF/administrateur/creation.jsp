<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id='creation-administrateur' class='fenetre-modale'>
	<!-- Interface de gestion des administrateur -->
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Ajout d'un administrateur</h1>
		</div>
		<!-- formulaire d'ajout d'un administrateur -->
		<form action="http://localhost:8080/ZPareo/ai/administrateur/creation" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="text" name="nom" class="form-control input-sm" value="<c:out value='${ administrateur.nom }'/>" pattern="[A-Za-z ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['nom'] }</span>
				<input type="text" name="prenom" class="form-control input-sm" value="<c:out value='${ administrateur.prenom }'/>" pattern="[A-Za-z ]{2,50}" placeholder="Prenom" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères"required/>
				<span class="erreur">${ form.erreurs['prenom'] }</span>
				<input type="text" name="adresseMail" class="form-control input-sm" value="<c:out value='${ administrateur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}" placeholder="Adresse mail" x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
				<span class="erreur">${ form.erreurs['adresseMail'] }</span>
				<input type="password" name="motDePasse" class="form-control input-sm" pattern=".{8,}" placeholder="Mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
				<span class="erreur">${ form.erreurs['motDePasse'] }</span>
				<input type="password" name="confirmation" class="form-control input-sm"  pattern=".{8,}" placeholder="Confirmation du mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
				<span class="erreur">${ form.erreurs['motDePasse'] }</span>
				<span class="erreur">${ form.erreurs['administrateur'] }</span>
			</div>	            
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">AJOUTER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
	 	</form>
	</section>
</div>