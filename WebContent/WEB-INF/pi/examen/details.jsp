<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fenetre modale d'edition d'un examen -->
<div class="fenetre-modale">
	<section class="module">
		<div class="module-barre">
			<h1 class="centre">Edition de l'examen n°<c:out value="${ examen.id }"/></h1>
		</div>
		<form id="edition-examen" action="http://localhost:8080/ZPareo/pi/examen/edition" method="POST" class="form-horizontal">
			<div id="form-examen" class="module-form">
	
				<input type="hidden" name="professeur" class="form-control input-sm" value="<c:out value='${ sessionScope.sessionProfesseur.id }'/>" pattern=".{1,55}" readonly="readonly" required/>
 				<select name="format" class="form-control input-sm" required>
                	<option disabled="disabled" selected="selected">Sélectionner un type</option>
                	<option <c:if test="${ examen.format.nom == \"Oral\"}"><c:out value="selected=\"selected\""/></c:if> value="2">Oral</option>
                	<option <c:if test="${ examen.format.nom == \"Ecrit\"}"><c:out value="selected=\"selected\""/></c:if> value="1">Ecrit</option>
			    </select>
			    <span class="erreur">${ form.erreurs['format'] }</span>
                <input type="text" name="date" class="form-control input-sm datepicker" value="<c:out value="${ examen.date }"/>" placeholder="Date de l'examen" pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\d\d" x-moz-errormessage="Veuillez entrer une date correct" required/>
                <span class="erreur">${ form.erreurs['date'] }</span>
                <input type="text" name="nom" class="form-control input-sm" value="<c:out value="${ examen.nom }"/>" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct" required/>
                <span class="erreur">${ form.erreurs['nom'] }</span>
                <input type="text" name="coefficient" class="form-control input-sm" value="<c:out value="${ examen.coefficient }"/>" placeholder="Coefficient" x-moz-errormessage="Veuillez entrer un nombre" required/>
                <span class="erreur">${ form.erreurs['coefficient'] }</span>
                <select name="groupe" class="form-control input-sm" readonly="readonly"  disabled="disabled" required>
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
                <select name="matiere" class="form-control input-sm" required>
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
			
			<div id="form-notes">
				<c:forEach  items="${examen.listeNotes}" var="note">
  				<div class="input-group input-group-sm form-note">
  					<span class="input-group-addon form-addon"><c:out value="${note.etudiant.prenom}"/> <c:out value="${note.etudiant.nom}"/></span>
 					<input type="hidden" name="etudiants[]" value="<c:out value="${note.etudiant.id}"/>" class="form-control" readonly="readonly"/>
 					<input type="text" name="notes[]" value="<c:out value="${note.note}"/>" class="form-control" placeholder="Note" pattern="[0-9,.]{1,5}" x-moz-errormessage="Veuillez entrer un nombre avec ou sans virgule">		
				</div>
  				</c:forEach>
  				<span class="erreur">${ form.erreurs['notes'] }</span>
			</div>			
			<div class="module-control-bas">
            	<button type="button" class="bouton bouton-default" onclick="supprFenetresModales()">RETOUR</button>
            </div>
		</form>
	</section>
</div>