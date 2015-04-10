<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-group" action="<c:url value="/ai/groupe/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition du groupe n°<c:out value="${group.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
    	<input type="hidden" name="id" class="form--control" size="30" value="${group.id}" readonly required/>
    	<input type="text" name="name" class="form--control" size="30" value="${group.name}" pattern="(G|g)[A-Za-z]" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 2 caractères commencant par G" required/>
    	<span class="form__error">${groupForm.errors['name']}</span>
    	<span class="form__error">${groupForm.errors['group']}</span>
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary" onclick="editGroup()">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>
