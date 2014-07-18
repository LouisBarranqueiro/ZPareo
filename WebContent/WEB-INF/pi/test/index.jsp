<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ZPareo - Liste de vos examens</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/assets/scss/style.css"/>"/>
    </head>
    <body>
        <div id="site-wrap">
            <div id="aside-wrap">
                <div class="aside__user-info">
                    <span class="aside__user-info__picture"></span>
                    <span class="aside__user-info__name"><c:out value="${fn:toUpperCase(sessionScope.teacherSession.lastName)}"></c:out><br/><c:out value="${fn:toUpperCase(sessionScope.teacherSession.firstName)}"></c:out></span>
                </div>
                <nav class="aside__nav">
                    <a href="<c:url value="/pi/examen"/>">
                        <span class="icon-stats"></span>
                        <span>EXAMENS</span>
                    </a>
                    <a href="<c:url value="/deconnexion"/>">
                        <span class="icon-switch2"></span>
                        <span>DECONNEXION</span>
                    </a>
                </nav>
            </div>
            <div id="main-wrap" class="main">
                <div class="main__head">
                    <h1 class="main__head__title">Liste de vos examens</h1>
                    <p class="main__head__desc"><c:out value="${numbTests}"/> examens enregistrés</p>
                    <button type="button" class="btn btn--success main__head__control" onclick="displayRespModal('pi/examen/creation',300)">AJOUTER UN EXAMEN</button>
                </div>
                <div class="main__content">
                    <div class="mod mod--lg">
                        <form action="http://localhost:8080/ZPareo/pi/examen" method="GET" class="form--inline">
                            <table>
                                <thead>
                                    <tr class="tr--ref">
                                        <th class="sortable">REFERENCE</th>
                                        <th class="sortable">DATE</th>
                                        <th class="sortable">FORMAT</th>
                                        <th class="sortable">INTITULE</th>
                                        <th class="sortable">GROUPE</th>
                                        <th class="sortable">MATIERE</th>
                                        <th class="sortable">MOYENNE</th>
                                        <th>ACTION</th>
                                    </tr>
                                    <tr class="tr--search">
                                        <th><input type="text" name="id" size="8" pattern="[0-9]+" placeholder="Reference" x-moz-errormessage="Veuillez entrez une référence correcte"/></th>
                                        <th><input type="text" name="date" class="form-control input-sm datepicker" size="10" placeholder="Date" pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\d\d" x-moz-errormessage="Veuillez entrer une date correct"/></th>
                                        <th>
                                            <select name="format">
                                                <option disabled="disabled" value="">Format</option>
                                                <option value="">Tous</option>
                                                <option value="1">Ecrit</option>
                                                <option value="2">Oral</option>
                                            </select>
                                        </th>
                                        <th><input type="text" name="title" size="25" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct"/></th>
                                        <th>
                                            <select name="groupe">
                                            <option disabled="disabled" value="">Groupe</option>
                                                <option value="">Tous</option>
                                                <c:forEach items="${sessionScope.teacherSession.groups}" var="teacherGroup">
                                                    <option value="${teacherGroup.id}">
                                                        <c:out value="${teacherGroup.name}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </th>
                                        <th>
                                            <select name="matter">
                                                <option disabled="disabled" value="">Matière</option>
                                                <option value="">Tous</option>
                                                <c:forEach items="${sessionScope.teacherSession.matters}" var="matter">
                                                    <option value="${matter.id}">
                                                        <c:out value="${matter.name}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </th>
                                        <th></th>
                                        <th><button type="submit" class="btn btn--primary">RECHERCHER</button></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${tests}" var="test">
                                        <tr class="tr--md">
                                            <td><c:out value="${test.id}"/></td>
                                            <td><c:out value="${test.date}"/></td>
                                            <td><c:out value="${test.format.name}"/></td>
                                            <td><c:out value="${test.title}"/></td>
                                            <td><c:out value="${test.group.name}"/></td>
                                            <td><c:out value="${test.matter.name}"/></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${test.average}"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('pi/examen/details?id=<c:out value="${test.id}"/>',550)"><span class="icon-list2"></span></button>
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('pi/examen/edition?id=<c:out value="${test.id}"/>',600)"><span class="icon-edit"></span></button>
                                                    <button type="button" class="btn btn--icon" onclick="displayRespModal('pi/examen/suppression?id=<c:out value="${test.id}"/>','auto')"><span class="icon-trashcan"></span></button>
                                                </div>
                                            </td>
                                        </tr>
                                     </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div><!-- End main-wrap -->
        </div><!-- End site-wrap -->
        <div id="modal" class="modal"></div>
        <script type="text/javascript" src="<c:url value="/assets/js/jquery.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/highcharts.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery-ui.custom.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.tablesorter.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/jquery.ui.datepicker-fr.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/assets/js/script.js"/>"></script> 
    </body>  
</html>