<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de creation d'un professeur -->
<div id="creation-professeur" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Ajout d'un professeur</h1>
		</div>
		<form action="http://localhost:8080/ZPareo/professeur/creation" method="POST" class="form-horizontal">
			<div class="module-form">
 				<div class="form-group-inline-moy">
				<input type="text" name="nom" class="form-control input-sm" size="39" value="<c:out value='${ professeur.nom }'/>" pattern="[a-zA-Z ]{2,50}" placeholder="Nom" x-moz-error-message="Veuillez entrez un nom correct composer de 2 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['nom'] }</span>
				<input type="text" name="prenom" class="form-control input-sm" size="39" value="<c:out value='${ professeur.prenom }'/>" pattern="[a-zA-Z ]{2,50}" placeholder="Prenom" x-moz-error-message="Veuillez entrez un prenom correct composer de 2 à 50 caractères" required/>
				<span class="erreur">${ form.erreurs['prenom'] }</span>
				<input type="text" name="adresseMail" class="form-control input-sm" size="39" value="<c:out value='${ professeur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" placeholder="Adresse Mail" x-moz-error-message="Veuillez entrez une adresse mail correcte" required/>
				<span class="erreur">${ form.erreurs['professeur'] }</span>
				<span class="erreur">${ form.erreurs['adresseMail'] }</span>
				<input type="password" name="motDePasse" class="form-control input-sm" size="39" pattern=".{8,}" placeholder="Mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères avec au moins une minucule, une majuscule, un chiffre et un caractère spéciale" required/>
				<span class="erreur">${ form.erreurs['motDePasse'] }</span>
				<input type="password" name="confirmation" class="form-control input-sm" size="39" pattern=".{8,}" placeholder="Confirmation du mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères avec au moins une minucule, une majuscule, un chiffre et un caractère spéciale" required/>
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
				</div>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">AJOUTER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>