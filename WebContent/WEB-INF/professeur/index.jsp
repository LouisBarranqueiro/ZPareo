<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ZPareo - Liste des professeurs</title>
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
						<!-- Interface de gestion des professeurs -->
					    <section class="module module-fixe">
					        <div class="module-barre">
					            <h1>Liste des professeurs</h1>
					            <p><c:out value="${ nbProfesseurs }"/> professeurs enregistrées</p>
					            <button type="button" class="bouton bouton-success" onclick="affFormCreation('ai/professeur',600)">AJOUTER UN PROFESSEUR</button>
					        </div>
					        <!-- formulaire de recherche de professeurs -->
					        <form action="http://localhost:8080/ZPareo/ai/professeur" method="GET" class="form-inline">
					            <!-- Tableau principal -->
					            <table class="style-1">
					                <thead>
					                	<tr>
					                        <th><input name="id" class="form-control input-sm" size="10" pattern="[0-9]{1,11}" placeholder="Référence" x-moz-error-message="Veuillez entrez une référence correct"/></th>
					                        <th><input name="nom" class="form-control input-sm" size="20" pattern="[a-zA-Z ]{3,50}" placeholder="Nom" x-moz-error-message="Veuillez entrez un nom correct composer de 3 à 50 caractères"/></th>
					                        <th><input name="prenom" class="form-control input-sm" size="20" pattern="[a-zA-Z ]{3,50}" placeholder="Prenom" x-moz-error-message="Veuillez entrez un prenom correct composer de 3 à 50 caractères"/></th>
					                        <th><input name="adresseMail" class="form-control input-sm" size="35" pattern="[a-zA-Z.]{3,50}" placeholder="Adresse mail" x-moz-error-message="Veuillez entrez une adresse mail correct"/></th>
					                        <th><button type="submit" class="bouton bouton-primary">RECHERCHER</button></th>
					                    </tr>
					                    <tr>
					                        <th class="sortable">Référence</th>
					                        <th class="sortable">Nom</th>
					                        <th class="sortable">Prenom</th>
					                        <th class="sortable">Adresse mail</th>
					                        <th>Action</th>
					                    </tr>
					                </thead>
					                <tbody>
					                	<c:forEach items="${ listeProfesseurs }" var="professeur" >
					                        <tr>
					                            <td><c:out value="${ professeur.id }"/></td>
					                            <td><c:out value="${ professeur.nom }"/></td>
					                            <td><c:out value="${ professeur.prenom }"/></td>
					                            <td><c:out value="${ professeur.adresseMail }"/></td>
					                            <td>
													<div class="btn-group">
														<button type="button" class="bouton bouton-action" onclick="affFormDetails('ai/professeur',<c:out value="${ professeur.id }"/>,400)"><span class="icon-list2" ></span></button>
													  	<button type="button" class="bouton bouton-action" onclick="affFormEdition('ai/professeur',<c:out value="${ professeur.id }"/>,600)"><span class="icon-edit"></span></button>
													  	<button type="button" class="bouton bouton-action" onclick="affFormSuppr('ai/professeur',<c:out value="${ professeur.id }"/>,'auto')"><span class="icon-trashcan"></span></button>
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