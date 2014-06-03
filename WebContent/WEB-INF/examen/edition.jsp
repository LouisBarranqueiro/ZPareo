<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale d'edition d'un examen -->
<div id="edition-examen" class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition de l'examen n°<c:out value="${ examen.id }"/></h1>
		</div>
		<form action="http://localhost:8080/ZPareo/pi/examen/edition" method="POST" class="form-horizontal">
			<div class="module-form">
				<input type="hidden" name="professeur" class="form-control input-sm" value="<c:out value='${ sessionScope.sessionProfesseur.id }'/>" pattern=".{1,55}" readonly="readonly" required="required"/>
 				<select name="format" class="form-control input-sm">
                	<option disabled="disabled" selected="selected">Sélectionner un type</option>
                	<option <c:if test="${ examen.format == \"Oral\"}"><c:out value="selected=\"selected\""/></c:if> value="1">Oral</option>
                	<option <c:if test="${ examen.format == \"Ecrit\"}"><c:out value="selected=\"selected\""/></c:if>value="2">Ecrit</option>
			    </select>
			    <span class="erreur">${ form.erreurs['format'] }</span>
                <input type="text" name="nom" class="form-control input-sm" value="<c:out value="${ examen.nom }"/>" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct"/>
                <span class="erreur">${ form.erreurs['nom'] }</span>
                <input type="date" name="date" class="form-control input-sm" value="<c:out value="${ examen.date }"/>" placeholder="Date de l'examen" x-moz-errormessage="Veuillez entrer une date correct"/>
                <span class="erreur">${ form.erreurs['date'] }</span>
                <select name="groupe" class="form-control input-sm">
                	<option disabled="disabled" selected="selected">Sélectionner un groupe</option>
			        <c:forEach items="${ sessionScope.sessionProfesseur.listeGroupes }" var="groupe">
			        	<option value="${ groupe.id }"
			                	<c:if test="${ examen.groupe.nom == groupe.nom }"><c:out value="selected=selected"/></c:if>			                      
			        	>
			            	 <c:out value="${ groupe.nom }"/>
			            </option>
			        </c:forEach>
			    </select>
			    <span class="erreur">${ form.erreurs['groupe'] }</span>
                <select name="matiere" class="form-control input-sm">
                	<option disabled="disabled" selected="selected">Sélectionner une matière</option>
			        <c:forEach items="${ sessionScope.sessionProfesseur.listeMatieres }" var="matiere">
			        	<option value="${ matiere.id }"
			        		<c:if test="${ examen.matiere.nom == matiere.nom }"><c:out value="selected=selected"/></c:if>
			        	>
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