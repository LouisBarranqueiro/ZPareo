<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-subject" action="<c:url value="/ai/matiere/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition de la matière n°<c:out value="${subject.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
	    <input type="hidden" name="id" class="form--control" size="30" value="${subject.id}" readonly required/>
	    <input type="text" name="name" class="form--control" value="<c:out value='${subject.name}'/>" size="30" pattern="[a-zA-Z0-9.-\' ]{2,50}" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct" required/>
	    <span class="form__error">${matterForm.errors['name']}</span>
	    <span class="form__error">${matterForm.errors['subject']}</span>
	</div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary" onclick="editMatter()">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>