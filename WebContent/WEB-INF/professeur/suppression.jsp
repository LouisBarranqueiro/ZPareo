<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de suppression d'un professeur -->
<div id="suppression-professeur" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Suppression du professeur n°<c:out value="${professeur.id}"/></h1>
		</div>
		<form method="POST" class="form-horizontal">
			<div class="module-form">
			<br/>
			<p>Êtes-vous sûr de vouloir supprimer le professeur :  <c:out value="${professeur.prenom}"/> <c:out value="${professeur.nom}"/>?</p>
				<input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ professeur.id }'/>" size="30" pattern="[0-9]{1,11}" readonly disabled required/>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-danger">SUPPRIMER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>