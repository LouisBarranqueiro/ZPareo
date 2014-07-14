<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="creation-administrateur" action="http://localhost:8080/ZPareo/ai/administrateur/creation" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Ajout d'un administrateur</h3>
    </div>
  	<div class="modal__mod--lg">
	    <label>NOM</label>
	    <input type="text" name="nom" class="form--control" value="<c:out value='${ administrateur.nom }'/>" pattern="[A-Za-z ]{2,50}"  x-moz-errormessage="Veuillez entrer un nom de 2 à 50 caractères" required/>
	    <span class="form__error">${ form.erreurs['nom'] }</span>
	    <label>PRENOM</label>
	    <input type="text" name="prenom" class="form--control" value="<c:out value='${ administrateur.prenom }'/>" pattern="[A-Za-z ]{2,50}" x-moz-errormessage="Veuillez entrer un prenom de 2 à 50 caractères"required/>
	    <span class="form__error">${ form.erreurs['prenom'] }</span>
	    <label>ADRESSE MAIL</label>
	    <input type="text" name="adresseMail" class="form--control" value="<c:out value='${ administrateur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}"  x-moz-errormessage="Veuillez entrer une adresse mail correcte" required/>
	    <span class="form__error">${ form.erreurs['adresseMail'] }</span>
	    <label>MOT DE PASSE</label>
	    <input type="password" name="motDePasse" class="form--control" pattern=".{8,}"  x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
	    <span class="form__error">${ form.erreurs['motDePasse'] }</span>
	    <label>CONFIRMATION</label>
	    <input type="password" name="confirmation" class="form--control" pattern=".{8,}"  x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum" required/>
	    <span class="form__error">${ form.erreurs['motDePasse'] }</span>
	    <span class="form__error">${ form.erreurs['administrateur'] }</span>
	</div>          
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>