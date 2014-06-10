<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de suppression d'un administrateur -->
<div id="suppression-administrateur" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Suppression de l'administrateur n°<c:out value="${ administrateur.id }"/></h1>
		</div>
		<form method="POST" class="form-horizontal">
			<div class="module-form">
			<br/>
			<p>Êtes-vous sûr de vouloir supprimer la l'étudiant : <c:out value="${administrateur.prenom}"/> <c:out value="${administrateur.nom}"/>?</p>
				<input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ administrateur.id }'/>" size="30" pattern="[0-9]{1,11}" readonly disabled required/>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-danger">SUPPRIMER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>