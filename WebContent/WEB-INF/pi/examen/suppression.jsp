<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<form id="suppression-examen" action="" method="POST" class="form--horizontal">
    <div class="modal__mod__head">
        <h3 class="modal__mod__head__title text-center">Suppression de l'examen n°<c:out value="${examen.id}"/></h3>
    </div>
    <div class="modal__mod--lg">
        <p>Êtes-vous sûr de vouloir supprimer l'examen : <c:out value="${examen.nom}"/> en <c:out value="${examen.matiere.nom}"/>?</p>
        <input type="hidden" name="id" class="form-control input-sm" value="<c:out value='${ examen.id }'/>" size="30" pattern="[0-9]{1,11}" readonly disabled required/>
    </div>
    <div class="form__control modal__mod__control">
        <button type="submit" class="btn btn--danger">SUPPRIMER</button>
        <button type="button" class="btn btn--default" onclick="supprFenetresModales()">ANNULER</button>
    </div>
</form>
