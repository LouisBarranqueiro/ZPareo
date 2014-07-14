<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ZPareo - Liste de vos examens</title>
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/ZPareo/ressources/scss/style.css" />
    </head>
    <body>
        <div id="site-wrap">
            <div id="aside-wrap">
                <div class="aside__user-info">
                    <span class="aside__user-info__picture"></span>
                    <span class="aside__user-info__name"><c:out value="${fn:toUpperCase(sessionScope.sessionEtudiant.nom)}"></c:out><br/><c:out value="${fn:toUpperCase(sessionScope.sessionEtudiant.prenom)}"></c:out></span>
                </div>
                <nav class="aside__nav">
                    <a href="http://localhost:8080/ZPareo/pi/examen">
                        <span class="icon-stats"></span>
                        <span>BULLETIN</span>
                    </a>
                    <a href="http://localhost:8080/ZPareo/deconnexion">
                        <span class="icon-switch2"></span>
                        <span>DECONNEXION</span>
                    </a>
                </nav>
            </div>
            <div id="main-wrap" class="main">
                <div class="main__head">
                        <h1 class="main__head__title">Bulletin de notes</h1>
                        <p class="main__head__desc"><c:out value="${sessionScope.sessionEtudiant.prenom}"></c:out> <c:out value="${sessionScope.sessionEtudiant.nom}"></c:out> - Groupe : <c:out value="${sessionScope.sessionEtudiant.groupe.nom}"></c:out> - Moyenne générale : <fmt:formatNumber type="number" maxFractionDigits="1" value="${etudiant.bulletin.moyenne}"/></p>
                    </div>
                    <div class="main__content">
                        <div class="mod mod--lg">  
                            <div id="graph-etudiant-moyennes">
                                <c:forEach items="${etudiant.bulletin.listeMatiereNote}" var="matiereNote">
                                    <span class="matieres" data-matiere="<c:out value="${matiereNote.matiere.nom}"/>"></span>
                                    <span class="moyennes" data-moyenne="<fmt:formatNumber type="number" maxFractionDigits="1" value="${matiereNote.moyenne}"/>"></span>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="mod mod--xs">  
                            <h3 class="mod__head">Liste des moyennes</h3>
                            <table>
                                <thead>
                                    <tr class="tr--ref">
                                        <th class="sortable">MATIERE</th>
                                        <th class="sortable">MOYENNE</th>
                                    </tr>
                                </thead>
                                <tbody class="tr--lg">
                                <c:forEach items="${etudiant.bulletin.listeMatiereNote}" var="matiereNote">
                                    <tr>
                                        <td><c:out value="${matiereNote.matiere.nom}"/></td>
                                        <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${matiereNote.moyenne}"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="mod mod--md">  
                            <h3 class="mod__head">Liste des examens</h3>
                            <table>
                                <thead>
                                    <tr class="tr--ref">
                                        <th class="sortable">FORMAT</th>
                                        <th class="sortable">INTITULE</th>
                                        <th class="sortable">DATE</th>
                                        <th class="sortable">COEFFICIENT</th>
                                        <th class="sortable">NOTE</th>
                                    </tr>
                                </thead>
                                <tbody class="tr--lg">
                                <c:forEach items="${etudiant.bulletin.listeMatiereNote}" var="matiereNote">
                                <c:forEach items="${matiereNote.listeExamens}" var="examen">
                                    <tr>
                                        <td><c:out value="${examen.format.nom}"/></td>
                                        <td><c:out value="${examen.nom}"/></td>
                                        <td><c:out value="${examen.date}"/></td>
                                        <td><c:out value="${examen.coefficient}"/></td>
                                        <c:forEach items="${examen.listeNotes}" var="note">
                                            <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${note.note}"/></td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="modal" class="modal"></div>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.js"></script>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/highcharts.js"></script>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.tablesorter.min.js"></script>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.ui.datepicker-fr.js"></script>
        <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/script.js"></script> 
    </body>  
</html>