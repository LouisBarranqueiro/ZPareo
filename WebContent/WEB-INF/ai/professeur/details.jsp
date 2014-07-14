<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal__mod">
	<div class="modal__mod__head">
		<h3 class="modal__mod__head__title text-center">Informations génerales</h3>
	</div>
	<div class="modal__mod--lg">
        <dl>
            <dt>NOM</dt>
            <dd><c:out value='${ professeur.nom }'/></dd>
            <dt>PRENOM</dt>
            <dd><c:out value='${ professeur.prenom }'/></dd>
            <dt>ADRESSE MAIL</dt>
            <dd><c:out value='${ professeur.adresseMail }'/></dd>
            <dt>GROUPE(S)</dt>
            <dd>
                <c:forEach items="${ listeGroupes }" var="groupe">
                    <c:forEach items="${ professeur.listeGroupes }" var="professeurGroupe">
                        <c:if test="${ professeurGroupe.id == groupe.id }"><c:out value="${ groupe.nom }"/>, </c:if>                                  
                    </c:forEach>
                </c:forEach>
            </dd>
            <dt>MATIERE(S) ENSEIGNEE(S)</dt>
            <dd>
                <c:forEach items="${ listeMatieres }" var="matiere">
                    <c:forEach items="${ professeur.listeMatieres }" var="professeurMatiere">
                        <c:if test="${ professeurMatiere.id == matiere.id }"><c:out value="${ matiere.nom }, "/></c:if>                               
                    </c:forEach>
                </c:forEach>
            </dd>
        </dl>
	    <div class="form__control modal__mod__control">
	            <button type="submit" class="btn btn--primary" onclick="affFormEdition('/ai/professeur',<c:out value="${ professeur.id }"/>,600)" >EDITER</button>
	            <button type="button" class="btn btn--default" onclick="supprFenetresModales()">FERMER</button>
	    </div>
    </div>
</div>