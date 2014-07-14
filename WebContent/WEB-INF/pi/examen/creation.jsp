<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="creation-examen" action="http://localhost:8080/ZPareo/pi/examen/creation" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Ajout d'un examen</h3>
    </div>
    <div class="modal__mod--lg">
        <input type="hidden" name="professeur" class="form--control" value="<c:out value='${ sessionScope.sessionProfesseur.id }'/>" pattern=".{5,55}" readonly="readonly" required="required"/>
        <label>FORMAT</label>
        <select name="format" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner un format</option>
            <option value="1">Oral</option>
            <option value="2">Ecrit</option>
        </select>
        <span class="form__error">${ form.erreurs['format'] }</span>
        <label>DATE</label>
        <input type="text" name="date" class="form--control input-sm datepicker" pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\d\d" placeholder="Date de l'examen JJ/MM/AAAA" x-moz-errormessage="Veuillez entrer une date correct" required/>
        <span class="form__error">${ form.erreurs['date'] }</span>
        <label>INTITULE</label>
        <input type="text" name="nom" class="form--control" pattern=".{5,55}"x-moz-errormessage="Veuillez entrer un nom correct" required/>
        <span class="form__error">${ form.erreurs['nom'] }</span>
        <label>COEFFICIENT</label>
        <input type="text" name="coefficient" class="form--control" pattern="[0-9,.]{1,3}"  x-moz-errormessage="Veuillez entrer un nombre" required/>
        <span class="form__error">${ form.erreurs['coefficient'] }</span>
        <label>GROUPE</label>
        <select name="groupe" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner un groupe</option>
            <c:forEach items="${ sessionScope.sessionProfesseur.listeGroupes }" var="groupe">
                <option value="${ groupe.id }">
                     <c:out value="${ groupe.nom }"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${ form.erreurs['groupe'] }</span>
        <label>MATIERE</label>
        <select name="matiere" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner une matière</option>
            <c:forEach items="${ sessionScope.sessionProfesseur.listeMatieres }" var="matiere">
                <option value="${ matiere.id }">
                     <c:out value="${ matiere.nom }"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${ form.erreurs['matiere'] }</span>
    </div>              
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">AJOUTER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>