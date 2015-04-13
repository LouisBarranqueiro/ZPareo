<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="edit-teacher" action="<c:url value="/ai/professeur/edition"/>" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
    	<h3 class="modal__mod__head__title text-center">Edition du professeur n°<c:out value='${teacher.id}'/></h3>
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
        <input type="hidden" name="id" class="form--control"  size="39" value="<c:out value='${teacher.id}'/>" size="20" pattern="[0-9]{1,11}" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères" required/>
        <label>PRENOM</label>
        <input type="text" name="firstName" class="form--control"  size="39" value="<c:out value='${teacher.firstName}'/>" size="20" pattern="[a-zA-Z ]{2,50}" x-moz-error-message="Veuillez entrez un prenom correct composer de 3 à 50 caractères" required/>
        <span class="form__error">${teacherForm.errors['firstName']}</span>
        <label>NOM</label>
        <input type="text" name="lastName" class="form--control"  size="39" value="<c:out value='${teacher.lastName}'/>" size="20" pattern="[a-zA-Z ]{2,50}" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères" required/>
        <span class="form__error">${teacherForm.errors['lastName']}</span>
        <label>ADRESSE MAIL</label>
        <input type="text" name="emailAddress" class="form--control"  size="39" value="<c:out value='${teacher.emailAddress}'/>" pattern="[a-zA-Z0-9@.-_]+@[a-zA-Z.]{2,20}.[a-zA-Z]{2,3}" x-moz-error-message="Veuillez entrez une adresse mail correcte" required/>
        <span class="form__error">${teacherForm.errors['emailAddress']}</span>
        <label>NOUVEAU MOT DE PASSE</label>
        <input type="password" name="password" class="form--control"  size="39" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
        <span class="form__error">${teacherForm.errors['password']}</span>
        <label>CONFIRMATION</label>
        <input type="password" name="confirmation" class="form--control"  size="39" x-moz-error-message="Veuillez entrez un mot de passe composer de 8 caractères minimum"/>
        <span class="form__error">${teacherForm.errors['password']}</span>
        
    </div>
    <div class="modal__mod--sm modal__mod--vertical">
    	<label>GROUPE(S)</label>
        <select multiple="multiple" size="7" name="groups" class="form--control select2">
            <option disabled="disabled">Sélectionnez un ou plusieurs groupe(s)</option>
            <c:forEach items="${groups}" var="group">
                <option value="${group.id}"
                   <c:forEach items="${teacher.groups}" var="teacherGroup">
                       <c:if test="${teacherGroup.id == group.id}"><c:out value="selected=selected"/></c:if>                                  
                   </c:forEach>
                >
                    <c:out value="${group.name}"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${teacherForm.errors['"groups"']}</span>
        <label>MATIERE(S) ENSEIGNEE(S)</label>
        <select multiple="multiple" size="23" name="subjects" class="form--control select2">
            <option disabled="disabled">Sélectionnez une ou plusieurs matières</option>
            <c:forEach items="${subjects}" var="subject">
                <option value="${subject.id}"
                   <c:forEach items="${teacher.subjects}" var="teacherMatter">
                       <c:if test="${teacherMatter.id == subject.id}"><c:out value="selected=selected"/></c:if>
                   </c:forEach>
                >
                   <c:out value="${subject.name}"/>
                </option>
            </c:forEach>
        </select>
        <span class="form__error">${teacherForm.errors['subjects']}</span>
        <span class="form__error">${teacherForm.errors['teacher']}</span>
    </div>
    <div class="form__control modal__mod__control">
    	<button type="submit" class="btn btn--primary" onclick="editTeacher()">ENREGISTRER</button>
        <button type="button" class="btn btn--default" onclick="removeModalWindow()">ANNULER</button>
   	</div>
</form>