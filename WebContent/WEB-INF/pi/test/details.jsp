<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Informations générales de l'examen n°<c:out value="${test.id}"/></h3>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <dl>
            <dt>REFERENCE</dt>
            <dd><c:out value="${test.id}"/></dd>
        
            <dt>INTITULE</dt>
            <dd><c:out value="${test.title}"/></dd>
        
            <dt>FORMAT</dt>
            <dd><c:out value="${test.format.name}"/></dd>
        
            <dt>DATE</dt>
            <dd><c:out value="${test.date}"/></dd>
        
            <dt>GROUPE</dt>
            <dd><c:out value="${test.group.name}"/></dd>
        
            <dt>MATIERE</dt>
            <dd><c:out value="${test.matter.name}"/></dd>
        
            <dt>COEFFICIENT</dt>
            <dd><c:out value="${test.coefficient}"/></dd>
        
            <dt>MOYENNE</dt>
            <dd><fmt:formatNumber type="number" maxFractionDigits="1" value="${test.average}"/></dd>
        </dl>
    </div>
    <div id="examen-notes" class="modal__mod--sm modal__mod--vertical modal__mod--scrollable">
        <table id="examen__notes">
            <thead>
                <tr class="tr--ref tr--blue">
                    <th>PRENOM</th>
                    <th>NOM</th>
                    <th>NOTE</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach  items="${test.scores}" var="score">
                    <tr class="tr--lg">
                        <td><c:out value="${score.student.firstName}"/></td>
                        <td><c:out value="${score.student.lastName}"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${score.score}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="form__control modal__mod__control">
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">RETOUR</button>
    </div>