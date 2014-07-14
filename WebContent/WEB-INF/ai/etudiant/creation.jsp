<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="creation-etudiant" action="http://localhost:8080/ZPareo/ai/etudiant/creation" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Ajout d'un étudiant</h3>
    </div>
    <div class="modal__mod--lg">
	    <label>NOM</label>
	    <input type="text" name="nom" class="form--control" value="<c:out value='${ etudiant.nom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
	    <span class="form_error">${ form.erreurs['nom'] }</span>
	    <label>PRENOM</label>
	    <input type="text" name="prenom" class="form--control" value="<c:out value='${ etudiant.prenom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Prenom" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères" required/>
	    <span class="form_error">${ form.erreurs['prenom'] }</span>
	    <label>ADRESSE MAIL</label>
	    <input type="text" name="adresseMail" class="form--control" value="<c:out value='${ etudiant.adresseMail }'/>" size="30"  pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" placeholder="Adresse mail" x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
	    <span class="form_error">${ form.erreurs['adresseMail'] }</span>
	    <label>GROUPE</label>
	    <select name="groupe" class="form--control" required>
	       <option disabled="disabled" selected="selected">Sélectionnez un groupe</option>
	       <c:forEach items="${ listeGroupes }" var="groupe">
	           <option value="${ groupe.id }"<c:if test="${ etudiant.groupe.id == groupe.id }"><c:out value="selected='selected'"/></c:if>><c:out value="${ groupe.nom }"/></option>
	       </c:forEach>
	    </select>
	    <span class="form_error">${ form.erreurs['groupe'] }</span>
	    <span class="form_error">${ form.erreurs['etudiant'] }</span> 
	</div>          
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>