<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale d'edition d'un groupe -->
<div id="edition-groupe" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition du groupe n°<c:out value="${ groupe.id }"/></h1>
		</div>
		<form action="http://localhost:8080/ZPareo/groupe/edition" method="POST" class="form-horizontal">
			<div class="module-form">
					<input type="hidden" name="id" class="form-control input-sm" size="30" value="${ groupe.id }" />
					<input type="text" name="nom" class="form-control input-sm" size="30" value="${ groupe.nom }" pattern="(G|g)[A-Za-z]" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 caractères commencant par G"/>
					<span class="erreur">${ form.erreurs['nom'] }</span>
					<span class="erreur">${ form.erreurs['groupe'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">ENREGISTRER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>
