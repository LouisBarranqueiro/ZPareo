<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="reinit-pass-student" action="<c:url value="/ai/etudiant/reinit-mot-de-passe"/>" method="POST" class="form--horizontal">
   	<div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Réinitialisation du mot de passe de l'étudiant n°<c:out value="${student.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
	    <p>Êtes-vous sûr de vouloir réinitialiser le mot de passe de l'étudiant : <c:out value="${student.firstName}"/> <c:out value="${student.lastName}"/> ?</p>
	    <input type="hidden" name="id" value="<c:out value='${student.id}'/>" size="30" pattern="[0-9]{1,11}" readonly disabled required/>         
    </div>
    <div class="form__control">
        <button type="submit" class="btn btn--danger" onclick="resetPassStudent()">REINITIALISER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>