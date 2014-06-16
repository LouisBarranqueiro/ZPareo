<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de creation d'un groupe -->
<div id="creation-groupe" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Ajout d'un groupe</h1>
		</div>
		<form action="http://localhost:8080/ZPareo/ai/groupe/creation" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="text" name="nom" class="form-control input-sm" value="<c:out value='${ groupe.nom }'/>" size="30" pattern="(G|g)[A-Za-z]" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 caractères commencant par G" required/>
				<span class="erreur">${ form.erreurs['nom'] }</span>
				<span class="erreur">${ form.erreurs['groupe'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">AJOUTER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>