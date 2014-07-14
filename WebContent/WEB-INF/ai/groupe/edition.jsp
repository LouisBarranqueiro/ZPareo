<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edition-groupe" action="http://localhost:8080/ZPareo/groupe/edition" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition du groupe n°<c:out value="${ groupe.id }"/></h3>
    </div>
    <div class="modal__mod--lg">
    	<input type="hidden" name="id" class="form--control" size="30" value="${ groupe.id }" readonly required/>
    	<input type="text" name="nom" class="form--control" size="30" value="${ groupe.nom }" pattern="(G|g)[A-Za-z]" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 caractères commencant par G" required/>
    	<span class="form__error">${ form.erreurs['nom'] }</span>
    	<span class="form__error">${ form.erreurs['groupe'] }</span>
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>
