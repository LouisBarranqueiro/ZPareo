<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale d'édition d'un professeur -->
<div id="edition-professeur" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition du professeur n°<c:out value='${ professeur.id }'/></h1>
		</div>
		<form action="http://localhost:8080/ZPareo/professeur/edition" method="POST" class="form-horizontal">
			<div class="module-form">
 				<div class="form-group-inline-moy">
 				<input type="hidden" name="id" class="form-control input-sm" size="39" value="<c:out value='${ professeur.id }'/>" size="20" pattern="[0-9]{1,11}" placeholder="id" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères" required/>
				<input type="text" name="nom" class="form-control input-sm" size="39" value="<c:out value='${ professeur.nom }'/>" size="20" pattern="[a-zA-Z ]{2,50}" placeholder="Nom" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['nom'] }</span>
				<input type="text" name="prenom" class="form-control input-sm" size="39" value="<c:out value='${ professeur.prenom }'/>" size="20" pattern="[a-zA-Z ]{2,50}" placeholder="Prenom" x-moz-error-message="Veuillez entrez un prenom correct composer de 3 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['prenom'] }</span>
				<input type="text" name="adresseMail" class="form-control input-sm" size="39" value="<c:out value='${ professeur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z]{2,20}.[a-zA-Z]{2,3}"  placeholder="Adresse Mail" x-moz-error-message="Veuillez entrez une adresse mail correcte" required/>
				<span class="erreur">${ form.erreurs['adresseMail'] }</span>
				<input type="password" name="motDePasse" class="form-control input-sm" size="39" placeholder="Nouveau mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
				<span class="erreur">${ form.erreurs['motDePasse'] }</span>
				<input type="password" name="confirmation" class="form-control input-sm" size="39"  placeholder="Confirmation du nouveau mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
				<span class="erreur">${ form.erreurs['motDePasse'] }</span>
				<select multiple="multiple" size="10" name="groupes" class="form-control input-sm">
			         <option disabled="disabled">Sélectionnez un ou plusieurs groupe(s)</option>
			         <c:forEach items="${ listeGroupes }" var="groupe">
			             <option value="${ groupe.id }"
			             	<c:forEach items="${ professeur.listeGroupes }" var="professeurGroupe">
			                	<c:if test="${ professeurGroupe.id == groupe.id }"><c:out value="selected=selected"/></c:if>			                      
			                </c:forEach>
			             >
			            	 <c:out value="${ groupe.nom }"/>
			             </option>
			         </c:forEach>
			     </select>
			     <span class="erreur">${ form.erreurs['groupes'] }</span>
			     </div>
			     <div class="form-group-inline-moy">
			     <select multiple="multiple" size="23" name="matieres" class="form-control input-sm">
			         <option disabled="disabled">Sélectionnez une ou plusieurs matiere(s)</option>
			         <c:forEach items="${ listeMatieres }" var="matiere">
			             <option value="${ matiere.id }"
			             	<c:forEach items="${ professeur.listeMatieres }" var="professeurMatiere">
			                	<c:if test="${ professeurMatiere.id == matiere.id }"><c:out value="selected=selected"/></c:if>			                      
			                </c:forEach>
			             >
			             	<c:out value="${ matiere.nom }"/>
			             </option>
			         </c:forEach>
			     </select>
				<span class="erreur">${ form.erreurs['matieres'] }</span>
				<span class="erreur">${ form.erreurs['professeur'] }</span>
				</div>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">ENREGISTRER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>