<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="delete-teacher" action="<c:url value="/ai/professeur/suppression"/>" method="POST" class="form--horizontal">
	<div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Suppression du professeur n°<c:out value="${teacher.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
    <p>Êtes-vous sûr de vouloir supprimer le professeur :  <c:out value="${teacher.firstName}"/> <c:out value="${teacher.lastName}"/>?</p>
    <input type="hidden" name="id" value="<c:out value='${teacher.id}'/>" size="30" pattern="[0-9]{1,11}" readonly  required/>
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--danger" onclick="deleteTeacher()">SUPPRIMER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>