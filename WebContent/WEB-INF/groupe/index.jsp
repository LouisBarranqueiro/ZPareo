<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ZPareo - Liste des groupes</title>
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
					    <a href="">Deconnexion</a>
					</section>
					<!--  Conteneur de module -->
					<div id='module-conteneur'>
					    <section class="module module-fixe">
					        <div class="module-barre">
					            <h1>Liste des groupes</h1>
					            <p><c:out value="${ nbGroupes }"/> groupes enregistrés</p>
					            <button type="button" class="bouton bouton-success" onclick="affFormCreation('groupe',300)">AJOUTER UN GROUPE</button>
					        </div>
					        <!-- formulaire de recherche de groupes -->
					        <form action="http://localhost:8080/ZPareo/ai/groupe" method="GET" class="form-inline">
					            <!-- Tableau principal -->
					            <table class="style-1">
					                <thead>
					                	<tr>
					                        <th><input type="text" name="id" class="form-control input-sm" size="10" pattern="[0-9]+" placeholder="Reference" x-moz-errormessage="Veuillez entrez une référence correcte"/></th>
					                        <th><input type="text" name="nom" class="form-control input-sm" size="30" pattern="(G|g)[A-Za-z]" placeholder="Nom" x-moz-errormessage="Veuillez entrer un nom de 1 à 2 caractères commencant par G"/></th>
					                        <th><button type="submit" class="bouton bouton-primary">RECHERCHER</button></th>
					                    </tr>
					                    <tr>
					                        <th class="sortable">Référence</th>
					                        <th class="sortable">Nom</th>
					                        <th>Action</th>
					                    </tr>
					                </thead>
					                <tbody>
					                	<c:forEach items="${ listeGroupes }" var="groupe" >
					                        <tr>
					                            <td><c:out value="${ groupe.id }"/></td>
					                            <td><c:out value="${ groupe.nom }"/></td>
					                            <td>
													<div class="btn-group">
													  	<button type="button" class="bouton bouton-action" onclick="affFormEdition('groupe',<c:out value="${ groupe.id }"/>,300)"><span class="icon-edit"></span></button>
													  	<a class="unstyled" href="http://localhost:8080/ZPareo/ai/groupe/suppression?id=<c:out value="${ groupe.id }"/>"><button type="button" class="bouton bouton-action" onclick="return(confirm('Etes-vous sur de vouloir supprimer la groupe : <c:out value="${ groupe.nom }"/>?'));"><span class="icon-trashcan"></span></button></a>
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