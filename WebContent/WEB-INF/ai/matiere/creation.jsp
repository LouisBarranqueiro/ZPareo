<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="creation-matiere" action="http://localhost:8080/ZPareo/matiere/creation" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Ajout d'une matière</h3>
    </div>
    <div class="modal__mod--lg">
	    <input type="text" name="nom" class="form--control" value="<c:out value='${matiere.nom}'/>" size="30" pattern="[a-zA-Z0-9.-\' ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct" required/>
	    <span class="form__error">${form.erreurs['nom']}</span>
	    <span class="form__error">${ form.erreurs['matiere'] }</span>
	</div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>