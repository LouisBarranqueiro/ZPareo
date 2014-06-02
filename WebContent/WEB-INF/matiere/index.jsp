<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ZPareo - Liste des matières</title>
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
						<!-- Interface de gestion des matières -->
					    <section class="module module-fixe">
					        <div class="module-barre">
					            <h1>Liste des matières</h1>
					            <p><c:out value="${ nbMatieres }"/> matières enregistrées</p>
					            <button type="button" class="bouton bouton-success" onclick="affFormCreation('ai/matiere',300)">AJOUTER UNE MATIERE</button>
					        </div>
					        <!-- formulaire de recherche de groupes -->
					        <form action="http://localhost:8080/ZPareo/ai/matiere" method="GET" class="form-inline">
					            <!-- Tableau principal -->
					            <table class="style-1">
					                <thead>
					                	<tr>
					                        <th><input type="text" name="id" class="form-control input-sm" size="10" pattern="[0-9]+" placeholder="Reference" x-moz-errormessage="Veuillez entrez une référence correcte"/></th>
					                        <th><input type="text" name="nom" class="form-control input-sm" size="30" placeholder="Nom" x-moz-errormessage="Veuillez entrez un nom correct"/></th>
					                        <th><button type="submit" class="bouton bouton-primary">RECHERCHER</button></th>
					                    </tr>
					                    <tr>
					                        <th class="sortable">Référence</th>
					                        <th class="sortable">Nom</th>
					                        <th>Action</th>
					                    </tr>
					                </thead>
					                <tbody>
					                	<c:forEach items="${ listeMatieres }" var="matiere" >
					                        <tr>
					                            <td><c:out value="${ matiere.id }"/></td>
					                            <td><c:out value="${ matiere.nom }"/></td>
					                            <td>
													<div class="btn-group">
													  	<button type="button" class="bouton bouton-action" onclick="affFormEdition('ai/matiere',<c:out value="${ matiere.id }"/>,300)"><span class="icon-edit"></span></button>
													  	<a class="unstyled" href="http://localhost:8080/ZPareo/ai/matiere/suppression?id=<c:out value="${ matiere.id }"/>"><button type="button" class="bouton bouton-action" onclick="return(confirm('Etes-vous sur de vouloir supprimer la matiere : <c:out value="${ matiere.nom }"/>?'));"><span class="icon-trashcan"></span></button></a>
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