<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edition-matiere" action="http://localhost:8080/ZPareo/matiere/edition" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition de la matière n°<c:out value="${ matiere.id }"/></h3>
    </div>
    <div class="modal__mod--lg">
	    <input type="hidden" name="id" class="form--control" size="30" value="${ matiere.id }" readonly required/>
	    <input type="text" name="nom" class="form--control" value="<c:out value='${matiere.nom}'/>" size="30" pattern="[a-zA-Z0-9.-\' ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct" required/>
	    <span class="form__error">${form.erreurs['nom']}</span>
	    <span class="form__error">${ form.erreurs['matiere'] }</span>
	</div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>