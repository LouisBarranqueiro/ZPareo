<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="create-group" action="<c:url value="/ai/groupe/creation"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Ajout d'un groupe</h3>
    </div>
    <div class="modal__mod--lg">
	    <label>NOM</label>
	    <input type="text" name="name" class="form--control" value="<c:out value='${group.name}'/>" size="30" pattern="(G|g)[A-Za-z]" x-moz-errormessage="Veuillez entrer un nom de 2 caractères commencant par G" required/>
	    <span class="form__error">${groupForm.errors['name']}</span>
	    <span class="form__error">${groupForm.errors['groupe']}</span>
	</div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>