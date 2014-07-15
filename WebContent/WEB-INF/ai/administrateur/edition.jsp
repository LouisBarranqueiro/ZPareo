<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-administrator" action="<c:url value="ai/administrateur/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    <h3 class="modal__mod__head__title text-center">Edition de l'administrateur n°<c:out value="${ administrateur.id }"/></h3>
    </div>
    <div class="modal__mod--lg">
	   	<input type="hidden" name="id" class="form--control" value="<c:out value='${ administrateur.id }'/>" size="30" pattern="[0-9]+" placeholder="id"/>
	    <label>NOM</label>
	    <input type="text" name="lastName" class="form--control" value="<c:out value='${ administrateur.nom }'/>" pattern="[A-Za-z ]{2,50}"  x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
	    <span class="form__erreur">${ form.erreurs['nom'] }</span>
	    <label>PRENOM</label>
	    <input type="text" name="firstName" class="form--control" value="<c:out value='${ administrateur.prenom }'/>" pattern="[A-Za-z ]{2,50}"  x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères"required/>
	    <span class="form__erreur">${ form.erreurs['prenom'] }</span>
	    <label>ADRESSE MAIL</label>
	    <input type="text" name="emailAddress" class="form--control" value="<c:out value='${ administrateur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
	    <span class="form__erreur">${ form.erreurs['adresseMail'] }</span>
	    <label>MOT DE PASSE</label>
	    <input type="password" name="password" class="form--control" pattern=".{8,}" placeholder="Nouveau mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
	    <span class="form__erreur">${ form.erreurs['motDePasse'] }</span>
	    <label>CONFIRMATION</label>
	    <input type="password" name="confirmation" class="form--control" pattern=".{8,}" placeholder="Confirmation du nouveau mot de passe" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
	    <span class="form__erreur">${ form.erreurs['motDePasse'] }</span>
	    <span class="form__erreur">${ form.erreurs['administrateur'] }</span>
	</div>           
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>
