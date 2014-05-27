<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de creation d'un groupe -->
<div id="creation-matiere" class="fenetre-modale">
	<!-- Interface de gestion des eleves -->
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Ajout d'une matière</h1>
		</div>
		<!-- formulaire d'ajout d'une matière -->
		<form action="http://localhost:8080/ZPareo/matiere/creation" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="text" name="nom" class="form-control input-sm" value="<c:out value='${matiere.nom}'/>" size="30" pattern="[a-zA-Z0-9.-\' ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct" required/>
				<span class="erreur">${form.erreurs['nom']}</span>
				<span class="erreur">${ form.erreurs['matiere'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">AJOUTER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>