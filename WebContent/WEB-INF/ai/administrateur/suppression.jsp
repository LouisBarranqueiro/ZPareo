<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="delete-administrator" action="<c:url value="ai/administrateur/suppression"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Suppression de l'administrateur n°<c:out value="${ administrateur.id }"/></h3>
    </div>
    <div class="modal__mod--lg">
	    <p>Êtes-vous sûr de vouloir supprimer l'administrateur : <c:out value="${administrateur.prenom}"/> <c:out value="${administrateur.nom}"/>?</p>
	    <input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ administrateur.id }'/>" size="30" pattern="[0-9]{1,11}" readonly required/>           
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--danger">SUPPRIMER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>