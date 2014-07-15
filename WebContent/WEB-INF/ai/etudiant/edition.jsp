<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-student" action="<c:url value="ai/etudiant/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition de l'étudiant n°<c:out value="${ etudiant.id }"/></h3>
    </div>
    <div class="modal__mod--lg">
	    <input type="hidden" name="id" class="form--control" value="<c:out value='${ etudiant.id }'/>" size="30" pattern="[0-9]+" placeholder="id"/>
	    <label>NOM</label>
	    <input type="text" name="lastName" class="form--control" value="<c:out value='${ etudiant.nom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
	    <span class="form__error">${ form.erreurs['lastName'] }</span>
	    <label>PRENOM</label>
	    <input type="text" name="firstName" class="form--control" value="<c:out value='${ etudiant.prenom }'/>" size="30" pattern="[A-Za-z ]{2,50}" placeholder="Prenom" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères" required/>
	    <span class="form__error">${ form.erreurs['firstName'] }</span>
	    <label>ADRESSE MAIL</label>
	    <input type="text" name="emailAddress" class="form--control" value="<c:out value='${ etudiant.adresseMail }'/>" size="30" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" placeholder="Adresse mail" x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
	    <span class="form__error">${ form.erreurs['"emailAddress"'] }</span>
	    <label>GROUPE</label>
	    <select name="group" class="form--control" required>
	        <option disabled="disabled" >Sélectionnez un groupe</option>
	        <c:forEach items="${ listeGroupes }" var="groupe">
	            <option value="${ groupe.id }"<c:if test="${ etudiant.groupe.id == groupe.id }"><c:out value="selected=selected"/></c:if>><c:out value="${ groupe.nom }"/></option>
	        </c:forEach>
	    </select>
	    <span class="form__error">${ form.erreurs['group'] }</span>
	    <span class="form__error">${ form.erreurs['etudiant'] }</span>   
	</div>        
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>
