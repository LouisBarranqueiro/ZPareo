<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="delete-group" action="<c:url value="/ai/groupe/suppression"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Suppression du groupe n°<c:out value="${group.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
    	<p>Êtes-vous sûr de vouloir supprimer le groupe : <c:out value="${group.name}"/>?</p>
    	<input type="hidden" name="id" value="<c:out value='${group.id}'/>" size="30" pattern="[0-9]{1,11}" readonly required/>
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--danger">SUPPRIMER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>