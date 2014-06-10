<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ZPareo - Liste des étudiants</title>
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
							<span><c:out value="${ sessionScope.sessionAdministrateur.nom }"></c:out><br/><c:out value="${ sessionScope.sessionAdministrateur.prenom }"></c:out></span>
						</div>
						<nav>
							<h2>MENU PRINCIPAL</h2>
							<a href="http://localhost:8080/ZPareo/ai/groupe">
								<span class="icon-addressbook"></span>
								<span>Groupes</span>
							</a>
							<a href="http://localhost:8080/ZPareo/ai/matiere">
								<span class="icon-presentation"></span>
								<span>Matières</span>
							</a>
							<a href="http://localhost:8080/ZPareo/ai/etudiant">
								<span  class="icon-graduate"></span>
								<span>Etudiants</span>
							</a>
							<a href="http://localhost:8080/ZPareo/ai/professeur">
								<span class="icon-suitcase3"></span>
								<span>Professeurs</span>
							</a>
							<a href="http://localhost:8080/ZPareo/ai/administrateur">
								<span class="icon-profile"></span>
								<span>Administrateurs</span>
							</a>
					    </nav>
					    <a href="http://localhost:8080/ZPareo/deconnexion">Deconnexion</a>
					</section>
                    <!--  Conteneur de module -->
                    <div id='module-conteneur'>
                        <!-- Interface de gestion des etudiants -->
                        <section class="module module-fixe">
                            <div class="module-barre">
                                <h1>Liste des étudiants</h1>
                                <p><c:out value="${ nbEtudiants }"/> étudiants enregistrés</p>
                                <button type="button" class="bouton bouton-success" onclick="affFormCreation('ai/etudiant',300)">AJOUTER UN ETUDIANT</button>
                            </div>
                            <!-- formulaire de recherche de etudiant -->
                            <form action="http://localhost:8080/ZPareo/ai/etudiant" method="GET" class="form-inline">
                                <!-- Tableau principal -->
                                <table class="style-1">
                                    <thead>
                                        <tr>
                                            <th><input type="text" name="id" class="form-control input-sm" size="10" pattern="[0-9]+" placeholder="Reference" x-moz-errormessage="Veuillez entrez une référence correcte"/></th>
                                            <th><input type="text" name="nom" class="form-control input-sm" size="30" pattern="[A-Za-z ]+" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom correct"/></th>
                                            <th><input type="text" name="prenom" class="form-control input-sm" size="30" pattern="[A-Za-z ]+" placeholder="Prenom" x-moz-errormessage="Veuillez entrer un prenom correct"/></th>
                                            <th><input type="text" name="adresseMail" class="form-control input-sm" size="35" pattern="[A-Za-z0-9@.-_]+" placeholder="Adresse mail" x-moz-errormessage="Veuillez entrer une adresse mail correct"/></th>
                                            <th>
                                                <select name="groupe" class="form-control input-sm" >
                                                    <option value="" selected="selected">Tous</option>
                                                    <c:forEach items="${ listeGroupes }" var="groupe">
                                                        <option value="${ groupe.id }"><c:out value="${ groupe.nom }"/></option>
                                                    </c:forEach>
                                                </select>
                                            </th>
                                            <th><button type="submit" class="bouton bouton-primary">RECHERCHER</button></th>
                                        </tr>
                                        <tr>
                                            <th class="sortable">Référence</th>
                                            <th class="sortable">Nom</th>
                                            <th class="sortable">Prenom</th>
                                            <th class="sortable">Adresse mail</th>
                                            <th class="sortable">Groupe</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${ listeEtudiants }" var="etudiant" >
                                            <tr>
                                                <td><c:out value="${ etudiant.id }"/></td>
                                                <td><c:out value="${ etudiant.nom }"/></td>
                                                <td><c:out value="${ etudiant.prenom }"/></td>
                                                <td><c:out value="${ etudiant.adresseMail }"/></td>
                                                <td><c:out value="${ etudiant.groupe.nom }"/></td>
                                                <td>
                                                    <div class="btn-group">
                                                        <button type="button" class="bouton bouton-action" onclick="affFormEdition('ai/etudiant',<c:out value="${ etudiant.id }"/>,500)"><span class="icon-edit"></span></button>
                                                        <button type="button" class="bouton bouton-action" onclick="affFormSuppr('ai/etudiant',<c:out value="${ etudiant.id }"/>,'auto')"><span class="icon-trashcan"></span></button>
                                                    </div>
                                                </td>
                                            </tr>
                                         </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </section>
                    </div>
                </main><!-- Fin main -->
         </div><!-- Fin site-conteneur -->
    <script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.js"></script>
	<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/jquery.tablesorter.min.js"></script> 
	<script type="text/javascript" src="http://localhost:8080/ZPareo/ressources/js/script.js"></script> 
    </body>  
</html>