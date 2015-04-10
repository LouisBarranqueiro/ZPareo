<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal__mod">
	<div class="modal__mod__head">
		<h3 class="modal__mod__head__title text-center">Informations génerales</h3>
	</div>
	<div class="modal__mod--lg">
        <dl>
            <dt>NOM</dt>
            <dd><c:out value='${teacher.lastName}'/></dd>
            <dt>PRENOM</dt>
            <dd><c:out value='${teacher.firstName}'/></dd>
            <dt>ADRESSE MAIL</dt>
            <dd><c:out value='${teacher.emailAddress}'/></dd>
            <dt>GROUPE(S)</dt>
            <dd>
                <c:forEach items="${groups}" var="group">
                    <c:forEach items="${teacher.groups}" var="teacherGroup">
                        <c:if test="${teacherGroup.id == group.id}"><c:out value="${group.name}"/>, </c:if>                                  
                    </c:forEach>
                </c:forEach>
            </dd>
            <dt>MATIERE(S) ENSEIGNEE(S)</dt>
            <dd>
                <c:forEach items="${matters}" var="matter">
                    <c:forEach items="${teacher.matters}" var="teacherMatter">
                        <c:if test="${teacherMatter.id == matter.id}"><c:out value="${matter.name}, "/></c:if>                               
                    </c:forEach>
                </c:forEach>
            </dd>
        </dl>
	    <div class="form__control modal__mod__control">
	            <button type="submit" class="btn btn--primary" onclick="displayRespModal('ai/professeur/edition?id=<c:out value="${teacher.id}"/>',600)">EDITER</button>
	            <button type="button" class="btn btn--default" onclick="removeModalWindow()">FERMER</button>
	    </div>
    </div>
</div>