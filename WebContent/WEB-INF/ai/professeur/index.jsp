<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ZPareo - Liste des professeurs</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/assets/scss/style.css"/>"/>
    </head>
    <body>
        <div id="site-wrap">
            <div id="aside-wrap">
                <div class="aside__user-info">
                    <span class="aside__user-info__picture"></span>
                    <span class="aside__user-info__name"><c:out value="${fn:toUpperCase(sessionScope.sessionAdministrateur.nom)}"></c:out><br/><c:out value="${fn:toUpperCase(sessionScope.sessionAdministrateur.prenom)}"></c:out></span>
                </div>
                <nav class="aside__nav">
                    <a href="<c:url value="/ai/administrateur"/>">
                        <span class="icon-profile"></span>
                        <span>ADMINISTRATEURS</span>
                    </a>
                    <a href="<c:url value="/ai/etudiant"/>">
                        <span class="icon-graduate"></span>
                        <span>ETUDIANTS</span>
                    </a>
                    <a href="<c:url value="/ai/groupe"/>">
                        <span  class="icon-addressbook"></span>
                        <span>GROUPES</span>
                    </a>
                    <a href="<c:url value="/ai/matiere"/>">
                        <span class="icon-presentation"></span>
                        <span>MATIERES</span>
                    </a>
                    <a href="<c:url value="/ai/professeur"/>">
                        <span class="icon-suitcase3"></span>
                        <span>PROFESSEURS</span>
                    </a>
                    <a href="<c:url value="/deconnexion"/>">
                        <span class="icon-switch2"></span>
                        <span>DECONNEXION</span>
                    </a>
                </nav>
            </div>
            <div id="main-wrap" class="main">
                <div class="main__head">
                    <h1 class="main__head__title">Liste des professeurs</h1>
                    <p class="main__head__desc"><c:out value="${ nbProfesseurs }"/> professeur(s) enregistré(s)</p>
                    <button type="button" class="btn btn--success main__head__control" onclick="displayRespModal('ai/professeur/creation',600)">AJOUTER UN PROFESSEUR</button>
                </div>
                <div class="main__content">
                    <div class="mod mod--lg">
                        <form action="http://localhost:8080/ZPareo/ai/professeur" method="GET" class="form--inline">
                            <table>
                                <thead>
                                    <tr class="tr--ref">
                                        <th class="sortable">REFERENCE</th>
                                        <th class="sortable">NOM</th>
                                        <th class="sortable">PRENOM</th>
                                        <th class="sortable">ADRESSE MAIL</th>
                                        <th>ACTION</th>
                                    </tr>
                                    <tr class="tr--search">
                                        <th><input name="id" size="10" pattern="[0-9]{1,11}" placeholder="Référence" x-moz-error-message="Veuillez entrez une référence correct"/></th>
                                        <th><input name="nom" size="20" pattern="[a-zA-Z ]{3,50}" placeholder="Nom" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères"/></th>
                                        <th><input name="prenom" size="20" pattern="[a-zA-Z ]{3,50}" placeholder="Prenom" x-moz-error-message="Veuillez entrez un prenom correct composer de 3 à 50 caractères"/></th>
                                        <th><input name="adresseMail" size="25" pattern="[a-zA-Z.]{3,50}" placeholder="Adresse mail" x-moz-error-message="Veuillez entrez une adresse mail correct"/></th>
                                        <th><button type="submit" class="btn btn--primary">RECHERCHER</button></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ listeProfesseurs }" var="professeur" >
                                        <tr class="tr--md">
                                            <td><c:out value="${ professeur.id }"/></td>
                                            <td><c:out value="${ professeur.nom }"/></td>
                                            <td><c:out value="${ professeur.prenom }"/></td>
                                            <td><c:out value="${ professeur.adresseMail }"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('ai/professeur/details?id=<c:out value="${ professeur.id }"/>','auto')"><span class="icon-list2" ></span></button>
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('ai/professeur/edition?id=<c:out value="${ professeur.id }"/>',600)"><span class="icon-edit"></span></button>
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('ai/professeur/suppression?id=<c:out value="${ professeur.id }"/>','auto')"><span class="icon-trashcan"></span></button>
                                                </div>
                                            </td>
                                        </tr>
                                     </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="modal" class="modal"></div>
        <script type="text/javascript" src="<c:url value="/assets/js/jquery.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery-ui.custom.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.tablesorter.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.ui.datepicker-fr.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/script.js"/>"></script> 
    </body>
</html>