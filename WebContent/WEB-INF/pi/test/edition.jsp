<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-test" action="<c:url value="pi/examen/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Informations générales de l'examen n°<c:out value="${test.id}"/></h3>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <input type="hidden" name="id" class="form--control" value="<c:out value="${test.id}"/>" pattern="[0-9]{1,11}" readonly="readonly" disabled="disabled" required/>
        <input type="hidden" name="teacher" class="form--control" value="<c:out value='${sessionScope.teacherSession.id}'/>" pattern=".{1,55}" readonly="readonly" required/>
        <label>FORMAT</label>
        <select name="format" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner un type</option>
            <option <c:if test="${test.format.name == \"Oral\"}"><c:out value="selected=\"selected\""/></c:if> value="2">Oral</option>
            <option <c:if test="${test.format.name == \"Ecrit\"}"><c:out value="selected=\"selected\""/></c:if> value="1">Ecrit</option>
        </select>
        <span class="form__error">${testForm.errors['format']}</span>
        <label>DATE</label>
        <input type="text" name="date" class="form--control datepicker" value="<c:out value="${test.date}"/>" placeholder="Date de l'examen" pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\d\d" x-moz-errormessage="Veuillez entrer une date correct" required/>
        <span class="form__error">${testForm.errors['date']}</span>
        <label>INTITULE</label>
        <input type="text" name="title" class="form--control" value="<c:out value="${test.title}"/>" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct" required/>
        <span class="form__error">${testForm.errors['title']}</span>
        <label>COEFFICIENT</label>
        <input type="text" name="coefficient" class="form--control" value="<c:out value="${test.coefficient}"/>" placeholder="Coefficient" x-moz-errormessage="Veuillez entrer un nombre" required/>
        <span class="form__error">${testForm.errors['coefficient']}</span>
        <label>GROUPE</label>
        <input type="text" name="group" class="form--control" value="<c:out value="${test.group.name}"/>" pattern="G[a-zA-Z]{1}" disabled readonly required/>
        <span class="form__error">${testForm.errors['group']}</span>
        <label>MATIERE</label>
        <select name="matter" class="form--control" required>
            <option disabled="disabled" selected="selected">Sélectionner une matière</option>
            <c:forEach items="${sessionScope.teacherSession.matters}" var="matter">
                <option value="${matter.id}"
                    <c:if test="${test.matter.name == matter.name}"><c:out value="selected=selected"/></c:if>
                >
                     <c:out value="${matter.name}"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${testForm.errors['matter']}</span>
    </div>
    <div class="modal__mod--sm modal__mod--vertical modal__mod--scrollable">
        <table class="table">
            <thead>
                <tr class="tr--ref tr--blue">
                    <th>PRENOM</th>
                    <th>NOM</th>
                    <th>NOTE</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${test.scores}" var="score">
                <tr class="tr--md">
                    <td><c:out value="${score.student.firstName}"/></td>
                    <td><c:out value="${score.student.lastName}"/></td>
                    <td>
                        <input type="hidden" name="students[]" value="<c:out value="${score.student.id}"/>" readonly required/>
                        <input type="text" name="scores[]" value="<fmt:formatNumber type="number" maxFractionDigits="1" value="${score.score}"/>" placeholder="Note" pattern="[0-9,.]{1,5}" x-moz-errormessage="Veuillez entrer un nombre avec ou sans virgule">        
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <span class="form__error">${testForm.errors['scores']}</span>
    </div>          
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--primary">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
    </div>
</form>