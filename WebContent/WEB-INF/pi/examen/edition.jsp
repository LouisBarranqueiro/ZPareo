<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edition-examen" action="http://localhost:8080/ZPareo/pi/examen/edition" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Informations générales de l'examen n°<c:out value="${ examen.id }"/></h3>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <input type="hidden" name="id" class="form--control" value="<c:out value="${ examen.id }"/>" pattern="[0-9]{1,11}" readonly="readonly" disabled="disabled" required/>
        <input type="hidden" name="professeur" class="form--control" value="<c:out value='${ sessionScope.sessionProfesseur.id }'/>" pattern=".{1,55}" readonly="readonly" required/>
        <label>FORMAT</label>
        <select name="format" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner un type</option>
            <option <c:if test="${ examen.format.nom == \"Oral\"}"><c:out value="selected=\"selected\""/></c:if> value="2">Oral</option>
            <option <c:if test="${ examen.format.nom == \"Ecrit\"}"><c:out value="selected=\"selected\""/></c:if> value="1">Ecrit</option>
        </select>
        <span class="form__error">${ form.erreurs['format'] }</span>
        <label>DATE</label>
        <input type="text" name="date" class="form--control datepicker" value="<c:out value="${ examen.date }"/>" placeholder="Date de l'examen" pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\d\d" x-moz-errormessage="Veuillez entrer une date correct" required/>
        <span class="form__error">${ form.erreurs['date'] }</span>
        <label>INTITULE</label>
        <input type="text" name="nom" class="form--control" value="<c:out value="${ examen.nom }"/>" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct" required/>
        <span class="form__error">${ form.erreurs['nom'] }</span>
        <label>COEFFICIENT</label>
        <input type="text" name="coefficient" class="form--control" value="<c:out value="${ examen.coefficient }"/>" placeholder="Coefficient" x-moz-errormessage="Veuillez entrer un nombre" required/>
        <span class="form__error">${ form.erreurs['coefficient'] }</span>
        <label>GROUPE</label>
        <input type="text" name="groupe" class="form--control" value="<c:out value="${ examen.groupe.nom}"/>" pattern="G[a-zA-Z]{1}" disabled readonly required/>
        <span class="form__error">${ form.erreurs['groupe'] }</span>
        <label>MATIERE</label>
        <select name="matiere" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner une matière</option>
            <c:forEach items="${ sessionScope.sessionProfesseur.listeMatieres }" var="matiere">
                <option value="${ matiere.id }"
                    <c:if test="${ examen.matiere.nom == matiere.nom }"><c:out value="selected=selected"/></c:if>
                >
                     <c:out value="${ matiere.nom }"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${ form.erreurs['matiere'] }</span>
    </div>
    <div class="modal__mod--sm modal__mod--vertical modal__mod--scrollable">
        <table>
            <thead>
                <tr class="tr--ref tr--blue">
                    <th>PRENOM</th>
                    <th>NOM</th>
                    <th>NOTE</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${examen.listeNotes}" var="note">
                <tr class="tr--md">
                    <td><c:out value="${note.etudiant.prenom}"/></td>
                    <td><c:out value="${note.etudiant.nom}"/></td>
                    <td>
                        <input type="hidden" name="etudiants[]" value="<c:out value="${note.etudiant.id}"/>" readonly required/>
                        <input type="text" name="notes[]" value="<fmt:formatNumber type="number" maxFractionDigits="1" value="${note.note}" />" placeholder="Note" pattern="[0-9,.]{1,5}" x-moz-errormessage="Veuillez entrer un nombre avec ou sans virgule">        
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <span class="form__error">${ form.erreurs['notes'] }</span>
    </div>          
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>