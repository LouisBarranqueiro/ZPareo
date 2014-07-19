<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="create-administrator" action="<c:url value="/ai/administrateur/creation"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Ajout d'un administrateur</h3>
    </div>
  	<div class="modal__mod--lg">
	    <label>NOM</label>
	    <input type="text" name="lastName" class="form--control" value="<c:out value='${administrator.lastName}'/>" pattern="[A-Za-z ]{2,50}"  x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
	    <span class="form__error">${administratorForm.errors['lastName']}</span>
	    <label>PRENOM</label>
	    <input type="text" name="firstName" class="form--control" value="<c:out value='${administrator.firstName}'/>" pattern="[A-Za-z ]{2,50}" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères"required/>
	    <span class="form__error">${administratorForm.errors['firstName']}</span>
	    <label>ADRESSE MAIL</label>
	    <input type="text" name="emailAddress" class="form--control" value="<c:out value='${administrator.emailAddress}'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}"  x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
	    <span class="form__error">${administratorForm.errors['emailAddress']}</span>
	    <label>MOT DE PASSE</label>
	    <input type="password" name="password" class="form--control" pattern=".{8,}"  x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
	    <span class="form__error">${administratorForm.errors['password']}</span>
	    <label>CONFIRMATION</label>
	    <input type="password" name="confirmation" class="form--control" pattern=".{8,}"  x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
	    <span class="form__error">${administratorForm.errors['password']}</span>
	    <span class="form__error">${administratorForm.errors['administrator']}</span>
	</div>          
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary" onclick="createAdmin()">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>