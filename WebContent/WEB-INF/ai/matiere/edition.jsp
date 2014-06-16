<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="edition-matiere" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition de la matière n°<c:out value="${ matiere.id }"/></h1>
		</div>
		<!-- formulaire d'edition d'un groupe -->
		<form action="http://localhost:8080/ZPareo/matiere/edition" method="POST" class="form-horizontal">
			<div class="module-form">
					<input type="hidden" name="id" class="form-control input-sm" size="30" value="${ matiere.id }" />
					<input type="text" name="nom" class="form-control input-sm" value="<c:out value='${matiere.nom}'/>" size="30" pattern="[a-zA-Z0-9.-\' ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct" required/>
					<span class="erreur">${form.erreurs['nom']}</span>
					<span class="erreur">${ form.erreurs['matiere'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">ENREGISTRER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>
