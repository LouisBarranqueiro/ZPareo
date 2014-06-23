<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="reinitmdp-etudiant" class="fenetre-modale">
	<section class="module">
		<form method="POST" class="form-horizontal">
			<div class="module-form">
			<br/>
			<p>Êtes-vous sûr de vouloir réinitialiser le mot de passe de l'étudiant : <c:out value="${etudiant.prenom}"/> <c:out value="${etudiant.nom}"/>?</p>
				<input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ etudiant.id }'/>" size="30" pattern="[0-9]{1,11}" readonly disabled required/>
			</div>
			<div class="loader-conteneur">
				<img class="loader"src="<c:url value="/ressources/img/ajax-loader.gif"/>"/>
			</div>			
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-danger">REINITIALISER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>