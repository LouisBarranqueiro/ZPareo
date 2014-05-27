<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ZPareo - Liste de vos examens</title>
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/ZPareo/ressources/css/bootstrap.css" />
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/ZPareo/ressources/css/main.css" />
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/ZPareo/ressources/css/style.css" />
    </head>
    <body>
            <div id="site-conteneur">
                <main>
                    <section id="menu-principal">
						<a href="">Portail ZPareo</a>
						<div id="info-utilisateur">
							<span></span>
							<span><c:out value="${ sessionScope.util.nom }"></c:out><br/><c:out value="${ sessionScope.util.prenom }"></c:out></span>
						</div>
						<nav>
							<h2>MENU PRINCIPAL</h2>
							<a href="http://localhost:8080/ZPareo/examen">
					    		<span class="icon-stats"></span>
					    		<span>Examens</span>
					    	</a>
					    </nav>
					    <a href="">Deconnexion</a>
					</section>
                    <!--  Conteneur de module -->
                    <div id='module-conteneur'>
                        <!-- Interface de gestion des administrateurs -->
                        <section class="module module-fixe">
                            <div class="module-barre">
                                <h1>Liste de vos examens</h1>
                                <p><c:out value="${ nbExamens }"/> examens enregistrés</p>
                                <button type="button" class="bouton bouton-success" onclick="affFormCreation('administrateur',300)">AJOUTER UN EXAMEN</button>
                            </div>
                            <!-- formulaire de recherche de administrateurs -->
                            <form action="http://localhost:8080/ZPareo/examen" method="GET" class="form-inline">
                                <!-- Tableau principal -->
                                <table class="style-1">
                                    <thead>
                                        <tr>
                                            <th><input type="text" name="id" class="form-control input-sm" size="10" pattern="[0-9]+" placeholder="Reference" x-moz-errormessage="Veuillez entrez une référence correcte"/></th>
                                            <th><input type="text" name="nom" class="form-control input-sm" size="30" pattern=".{5,55}" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct"/></th>
                                            <th>
                                            	
                                            </th>
                                            <th>
                                            
                                            </th>
                                            <th><button type="submit" class="bouton bouton-primary">RECHERCHER</button></th>
                                        </tr>
                                        <tr>
                                            <th class="sortable">Référence</th>
                                            <th class="sortable">Nom</th>
                                            <th class="sortable">Matiere</th>
                                            <th class="sortable">Groupe</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${ listeAdministrateurs }" var="administrateur" >
                                            <tr>
                                                <td><c:out value="${ examen.id }"/></td>
                                                <td><c:out value="${ examen.nom }"/></td>
                                                <td><c:out value="${ examen.matiere }"/></td>
                                                <td><c:out value="${ examen.Groupe }"/></td>
                                                <td>
                                                    <div class="btn-group">
                                                        <button type="button" class="bouton bouton-action" onclick="affFormEdition('administrateur',<c:out value="${ administrateur.id }"/>,300)"><span class="icon-edit"></span></button>
                                                        <a class="unstyled" href="http://localhost:8080/ZPareo/ai/administrateur/suppression?id=<c:out value="${ administrateur.id }"/>"><button type="button" class="bouton bouton-action" onclick="return(confirm('Etes vous sur de vouloir supprimer l\'administrateur : <c:out value="${ administrateur.prenom }"/> <c:out value="${ administrateur.nom }"/> ?'));"><span class="icon-trashcan"></span></button></a>
                                                    </div>
                                                </td>
                                            </tr>
                                         </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </section>
                    </div>
                </main>
         </div><!-- Fin site-conteneur -->
    <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.js"></script>
	<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.tablesorter.min.js"></script> 
	<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/script.js"></script> 
    </body>  
</html>