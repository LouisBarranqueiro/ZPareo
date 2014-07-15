<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="create-teacher" action="<c:url value="ai/professeur/creation"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Ajout d'un professeur</h3>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <label>PRENOM</label>
        <input type="text" name="firstName" class="form--control" size="39" value="<c:out value='${ professeur.prenom }'/>" pattern="[a-zA-Z ]{2,50}" x-moz-error-message="Veuillez entrez un prenom correct composer de 2 à 50 caractères" required/>
        <span class="form__error">${ form.erreurs['"firstName"'] }</span>
        <label>NOM</label>
        <input type="text" name="lastName" class="form--control" size="39" value="<c:out value='${ professeur.nom }'/>" pattern="[a-zA-Z ]{2,50}" x-moz-error-message="Veuillez entrez un nom correct composer de 2 à 50 caractères" required/>
        <span class="form__error">${ form.erreurs['lastName'] }</span>
        <label>ADRESSE MAIL</label>
        <input type="text" name="emailAddress" class="form--control" size="39" value="<c:out value='${ professeur.adresseMail }'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" x-moz-error-message="Veuillez entrez une adresse mail correcte" required/>
        <span class="form__error">${ form.erreurs['professeur'] }</span>
        <span class="form__error">${ form.erreurs['emailAddress'] }</span>
        <label>MOT DE PASSE</label>
        <input type="password" name="password" class="form--control" size="39" pattern=".{8,}" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères avec au moins une minucule, une majuscule, un chiffre et un caractère spéciale" required/>
        <span class="form__error">${ form.erreurs['password'] }</span>
        <label>CONFIRMATION</label>
        <input type="password" name="confirmation" class="form--control" size="39" pattern=".{8,}"  x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères avec au moins une minucule, une majuscule, un chiffre et un caractère spéciale" required/>
        <span class="form__error">${ form.erreurs['password'] }</span>
        <label>GROUPE(S)</label>
        <select multiple="multiple" class="form--control" size="7" name="groups" class="form-control input-sm">
            <option disabled="disabled">Sélectionnez un ou plusieurs groupes</option>
            <c:forEach items="${ listeGroupes }" var="groupe">
                <option value="${ groupe.id }"
                    <c:forEach items="${ professeur.listeGroupes }" var="professeurGroupe">
                    <c:if test="${ professeurGroupe.id == groupe.id }"><c:out value="selected=selected"/></c:if>                                  
                    </c:forEach>
                >
                    <c:out value="${ groupe.nom }"/>
                </option>
             </c:forEach>
         </select>
         <span class="form__error">${ form.erreurs['groups'] }</span>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <label>MATIERE(S) ENSEIGNEE(S)</label>
        <select multiple="multiple" class="form--control" size="23" name="matters" class="form-control input-sm">
            <option disabled="disabled">Sélectionnez une ou plusieurs matières</option>
            <c:forEach items="${ listeMatieres }" var="matiere">
                <option value="${ matiere.id }"
                    <c:forEach items="${ professeur.listeMatieres }" var="professeurMatiere">
                        <c:if test="${ professeurMatiere.id == matiere.id }"><c:out value="selected=selected"/></c:if>                                
                    </c:forEach>
                >
                    <c:out value="${ matiere.nom }"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${ form.erreurs['matters'] }</span>
    </div>              
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>