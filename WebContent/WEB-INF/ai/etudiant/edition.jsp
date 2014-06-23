<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="edition-etudiant" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition de l'etudiant <c:out value="${ etudiant.id }"/></h1>
		</div>
		<!-- formulaire d'ajout d'un etudiant -->
		<form action="http://localhost:8080/ZPareo/etudiant/edition" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ etudiant.id }'/>" size="30" pattern="[0-9]+" placeholder="id"/>
				<input type="text" name="nom" class="form-control input-sm" value="<c:out value='${ etudiant.nom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['nom'] }</span>
				<input type="text" name="prenom" class="form-control input-sm" value="<c:out value='${ etudiant.prenom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Prenom" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['prenom'] }</span>
				<input type="text" name="adresseMail" class="form-control input-sm" value="<c:out value='${ etudiant.adresseMail }'/>" size="30" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}" placeholder="Adresse mail" x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
			    <span class="erreur">${ form.erreurs['adresseMail'] }</span>
				<select name="groupe" class="form-control input-sm" required>
					<option disabled="disabled" >Sélectionnez un groupe</option>
					<c:forEach items="${ listeGroupes }" var="groupe">
						<option value="${ groupe.id }"<c:if test="${ etudiant.groupe.id == groupe.id }"><c:out value="selected='selected'"/></c:if>><c:out value="${ groupe.nom }"/></option>
					</c:forEach>
				</select>
				<span class="erreur">${ form.erreurs['groupe'] }</span>
				<span class="erreur">${ form.erreurs['etudiant'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">ENREGISTRER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>
