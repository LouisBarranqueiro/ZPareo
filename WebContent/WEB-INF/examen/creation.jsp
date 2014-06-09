<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale de creation d'un examen -->
<div id="creation-examen" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Ajout d'un examen</h1>
		</div>
		<form action="http://localhost:8080/ZPareo/pi/examen/creation" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="hidden" name="professeur" class="form-control input-sm" value="<c:out value='${ sessionScope.sessionProfesseur.id }'/>" pattern=".{5,55}" readonly="readonly" required="required"/>
 				<select name="format" class="form-control input-sm" required>
                	<option disabled="disabled" selected="selected">Sélectionner un type</option>
                	<option value="1">Oral</option>
                	<option value="2">Ecrit</option>
			    </select>
			    <span class="erreur">${ form.erreurs['format'] }</span>
			    <input type="date" name="date" class="form-control input-sm" pattern="(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\d\d" placeholder="Date de l'examen (JJ/MM/AAAA)" x-moz-errormessage="Veuillez entrer une date correct" required/>
                <span class="erreur">${ form.erreurs['date'] }</span>
                <input type="text" name="nom" class="form-control input-sm" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct" required/>
                <span class="erreur">${ form.erreurs['nom'] }</span>
                <input type="text" name="coefficient" class="form-control input-sm" pattern="[0-9,.]{1,3}" placeholder="Coefficient" x-moz-errormessage="Veuillez entrer un nombre" required/>
                <span class="erreur">${ form.erreurs['coefficient'] }</span>
                <select name="groupe" class="form-control input-sm" required>
                	<option disabled="disabled" selected="selected">Sélectionner un groupe</option>
			        <c:forEach items="${ sessionScope.sessionProfesseur.listeGroupes }" var="groupe">
			        	<option value="${ groupe.id }">
			            	 <c:out value="${ groupe.nom }"/>
			            </option>
			        </c:forEach>
			    </select>
			    <span class="erreur">${ form.erreurs['groupe'] }</span>
                <select name="matiere" class="form-control input-sm" required>
                	<option disabled="disabled" selected="selected">Sélectionner une matière</option>
			        <c:forEach items="${ sessionScope.sessionProfesseur.listeMatieres }" var="matiere">
			        	<option value="${ matiere.id }">
			            	 <c:out value="${ matiere.nom }"/>
			            </option>
			        </c:forEach>
			   	</select>
			   	<span class="erreur">${ form.erreurs['matiere'] }</span>
			</div>				
			<div class="module-control-bas">
				<button type="submit" class="bouton bouton-primary">AJOUTER</button>
				<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">ANNULER</button>
			</div>
		</form>
	</section>
</div>