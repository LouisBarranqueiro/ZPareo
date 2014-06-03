/**
 * Initialize le plugin Table Sorter
 */ 
var initTableSorter = function()
{
	$('table.style-1').tablesorter(); 
};

/**
 * Anime les colonnes de tri
 */
var animColonne = function() 
{
	$('th.sortable').click(function() 
	{
		if($(this).hasClass('active'))
		{
			$(this).removeClass('active');
			$(this).addClass('activee');
		}
		else
		{
			$('.sortable').removeClass('activee');
			$('.sortable').removeClass('active');
			$(this).addClass('active');
		}
	});
};

/**
 * Centre la fenetre Modale
 * 
 * @return true
 */
var centrerFenetreModale = function(largFenetre)
{
	var fenetreModale = $('.fenetre-modale');
    var popMargTop = (fenetreModale.height()) / 2;
    var popMargLeft = (fenetreModale.width()) / 2;
    
    fenetreModale.css({
        'width'   : largFenetre,
        'position': 'fixed',
        'top'     : '50%',
        'left'    : '50%',
        'margin-top' : -popMargTop,
        'margin-left' : -popMargLeft
    });
    
    return true;
};

/**
 * Affiche un masque sur le site
 */ 
var affMasque = function() 
{
	$('body').append('<div id="masque"></div>');
	$('#masque').css({
		'filter': 'alpha(opacity=80)'
	}).fadeIn();
};

/**
 * Affiche la fenetre modale 
 */ 
var affFenetreModale = function() 
{
	$('.fenetre-modale').show();
};

/**
 * Supprime le masque
 */
var supprMasque = function() 
{
	$('#masque').remove();
};

/**
 * Cache les fenetres modales et supprime le masque
 */ 
var cacheFenetresModales = function() 
{
	$('.fenetre-modale').hide();
	remMasque();
};

/**
 * Supprime une fenetre modale
 */
var supprFenetresModales = function() 
{
	$('.fenetre-modale').remove();
	$('#masque').remove();
};

/**
 * Initialise la fenetre modale
 */
var initFenetreModale = function(largeurFenetre) 
{
	centrerFenetreModale(largeurFenetre);
	supprMasque();
	affMasque();
	affFenetreModale();
};

/**
 * Recharge les fonctions AJAX de création
 */
var rechFonctCreat = function()
{
	creerGroupe();
	creerEtudiant();
	creerMatiere();
	creerProfesseur();
	creerAdministrateur();
};

/**
 * Recharge les fonctions AJAX d'edition
 */
var rechFonctEdit = function()
{
	editerGroupe();
	editerMatiere();
	editerEtudiant();
	editerProfesseur();
	editerAdministrateur();
};

/**
 * Affiche le formulaire de création d'un objet
 * 
 * @param object
 * @param tailleFenetre
 */
var affFormCreation = function(url, tailleFenetre) 
{
	$.ajax({ 
		type: "GET", 
		url: "http://localhost:8080/ZPareo/" + url + "/creation",  
	    error: function() 
	    { 
	    	alert("erreur !"); 
	    },
	    success: function( data ) 
	    {
	    	$('main').append( data );
	    	initFenetreModale( tailleFenetre );
	    	rechFonctCreat();
	    } 
	});
};

/**
 * Affiche le formulaire d'edition d'un objet
 * 
 * @param objet
 * @param id
 * @param tailleFenetre
 */
var affFormEdition = function(url, id, tailleFenetre) 
{
	$.ajax({ 
		type: "GET", 
	    url: "http://localhost:8080/ZPareo/" + url + "/edition?id=" + id, 
	    data: 
	    {
	    }, 
	    error: function() 
	    { 
	    	alert("erreur !"); 
	    },
	    success: function( data ) 
	    {
	    	$('main').append( data );
	    	alert(data);
	    	initFenetreModale( tailleFenetre );
	    	rechFonctEdit();
	    } 
	});
};

/**
 * Affiche le details d'un objet
 * 
 * @param objet
 * @param id
 * @param tailleFenetre
 */
var affFormDetails = function(url, id, tailleFenetre) 
{
	$.ajax({ 
		type: "GET", 
	    url: "http://localhost:8080/ZPareo/" + url + "/details?id=" + id, 
	    data: 
	    {
	    	id: id
	    }, 
	    error: function() 
	    { 
	    	alert("erreur !"); 
	    },
	    success: function( data ) 
	    {
	    	$('main').append( data );
	    	initFenetreModale( tailleFenetre );
	    } 
	});
};

/**
 * Creer une matiere dans la base de donn�es
 */ 
var creerMatiere = function()  
{
	$('#creation-matiere').submit(function(event)
	{
		event.preventDefault();
		var nom = $('#creation-matiere input[name=nom]').val();
		
		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/matiere/creation", 
		    data: 
		    {
		    	nom: nom
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith( vue );
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctCreat();
		    	}
		    } 
		});
	});
};


/**
 * Edite une matiere dans la base de donn�es
 */
var editerMatiere = function()  
{
	$('#edition-matiere').submit(function(event)
	{
		event.preventDefault();
		var id = $('#edition-matiere input[name=id]').val();
		var nom = $('#edition-matiere input[name=nom]').val();
		
		$.ajax({ 
			type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/matiere/edition", 
		    data: 
		    {
		    	id: id,
		    	nom: nom
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr( data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith( vue );
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};

/**
 * Creer un groupe dans la base de donn�es
 */ 
var creerGroupe = function()  
{
	$('#creation-groupe').submit(function(event)
	{
		event.preventDefault();
		var nom = $('#creation-groupe input[name=nom]').val();
		
		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/groupe/creation", 
		    data: 
		    {
		    	nom: nom
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctCreat();
		    	}
		    } 
		});
	});
};

/**
 * Edite une matiere dans la base de donn�es
 */
var editerGroupe = function()  
{
	$('#edition-groupe').submit( function( event )
	{
		event.preventDefault();
		var id = $('#edition-groupe input[name=id]').val();
		var nom = $('#edition-groupe input[name=nom]').val();
		
		$.ajax({ 
			type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/groupe/edition", 
		    data: 
		    {
		    	id: id,
		    	nom: nom
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};

/**
 * Creer un etudiant dans la base de donn�es
 */ 
var creerEtudiant = function()  
{
	$('#creation-etudiant').submit(function(event)
	{
		event.preventDefault();
		var nom = $('#creation-etudiant input[name=nom]').val();
		var prenom = $('#creation-etudiant input[name=prenom]').val();
		var adresseMail = $('#creation-etudiant input[name=adresseMail]').val();
		var groupe = $('#creation-etudiant select[name=groupe]').val();
		var vue = "";

		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/etudiant/creation", 
		    data: 
		    {
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	groupe: groupe
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctCreat();
		    	}
		    } 
		});
	});
};

/**
 * Edite une �tudiant dans la base de donn�es
 */
var editerEtudiant = function()  
{
	$('#edition-etudiant').submit(function(event)
	{
		event.preventDefault();
		var id = $('#edition-etudiant input[name=id]').val();
		var nom = $('#edition-etudiant input[name=nom]').val();
		var prenom = $('#edition-etudiant input[name=prenom]').val();
		var adresseMail = $('#edition-etudiant input[name=adresseMail]').val();
		var groupe = $('#edition-etudiant select[name=groupe]').val();
		var vue = "";

		$.ajax({ 
			type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/etudiant/edition", 
		    data: 
		    {
		    	id: id,
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	groupe: groupe
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};

/**
 * Creer un professeur dans la base de données
 */ 
var creerProfesseur = function()  
{
	$('#creation-professeur').submit( function( event )
	{
		event.preventDefault();
		var nom = $('#creation-professeur input[name=nom]').val();
		var prenom = $('#creation-professeur input[name=prenom]').val();
		var adresseMail = $('#creation-professeur input[name=adresseMail]').val();
		var motDePasse = $('#creation-professeur input[name=motDePasse]').val();
		var confirmation = $('#creation-professeur input[name=confirmation]').val();
		var groupes = $('#creation-professeur select[name=groupes]').val();
		var matieres = $('#creation-professeur select[name=matieres]').val();
		var vue = "";
		
		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/professeur/creation", 
		    data: 
		    {
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	motDePasse: motDePasse,
		    	confirmation: confirmation,
		    	groupes: groupes,
		    	matieres: matieres
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith( vue );
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(600);
		    		rechFonctCreat();
		    	}
		    } 
		});
	});
};

/**
 * Editer un professeur dans la base de données
 */ 
var editerProfesseur = function()  
{
	$('#edition-professeur').submit(function(event)
	{
		event.preventDefault();
		var id = $('#edition-professeur input[name=id]').val();
		var nom = $('#edition-professeur input[name=nom]').val();
		var prenom = $('#edition-professeur input[name=prenom]').val();
		var adresseMail = $('#edition-professeur input[name=adresseMail]').val();
		var motDePasse = $('#edition-professeur input[name=motDePasse]').val();
		var confirmation = $('#edition-professeur input[name=confirmation]').val();
		var groupes = $('#edition-professeur select[name=groupes]').val();
		var matieres = $('#edition-professeur select[name=matieres]').val();
		var vue = "";

		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/professeur/edition", 
		    data: 
		    {
		    	id: id,
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	motDePasse: motDePasse,
		    	confirmation: confirmation,
		    	groupes: groupes,
		    	matieres: matieres
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr( data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(600);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};


/**
 * Creer un professeur dans la base de données
 */ 
var creerAdministrateur = function()  
{
	$('#creation-administrateur').submit( function( event )
	{
		event.preventDefault();
		var nom = $('#creation-administrateur input[name=nom]').val();
		var prenom = $('#creation-administrateur input[name=prenom]').val();
		var adresseMail = $('#creation-administrateur input[name=adresseMail]').val();
		var motDePasse = $('#creation-administrateur input[name=motDePasse]').val();
		var confirmation = $('#creation-administrateur input[name=confirmation]').val();
		var vue = "";
		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/administrateur/creation", 
		    data: 
		    {
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	motDePasse: motDePasse,
		    	confirmation: confirmation
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctCreat();
		    	}
		    } 
		});
	});
};

/**
 * Creer un professeur dans la base de données
 */ 
var editerAdministrateur = function()  
{
	$('#edition-administrateur').submit(function(event)
	{
		event.preventDefault();
		var id = $('#edition-administrateur input[name=id]').val();
		var nom = $('#edition-administrateur input[name=nom]').val();
		var prenom = $('#edition-administrateur input[name=prenom]').val();
		var adresseMail = $('#edition-administrateur input[name=adresseMail]').val();
		var motDePasse = $('#edition-administrateur input[name=motDePasse]').val();
		var confirmation = $('#edition-administrateur input[name=confirmation]').val();

		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/administrateur/edition", 
		    data: 
		    {
		    	id: id,
		    	nom: nom,
		    	prenom: prenom,
		    	adresseMail: adresseMail,
		    	motDePasse: motDePasse,
		    	confirmation: confirmation
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};

/**
 * Creer un examen dans la base de données
 */ 
var creerExamen = function()  
{
	$('#creation-examen').submit(function(event)
	{
		event.preventDefault();
		var nom = $('#creation-examen input[name=nom]').val();
		var matiere = $('#creation-examen input[name=matiere]').val();
		var groupe = $('#creation-examen input[name=groupe]').val();

		$.ajax({ 
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/examen/creation", 
		    data: 
		    {
		    	nom: nom,
		    	matiere: matiere,
		    	groupe: groupe
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(data) 
		    { 
		    	if(data.match('<tbody>')) 
		    	{
		    		supprFenetresModales();
		    		vue = data.substr(data.search("<div id='module-conteneur'>"), data.search("</main>"));
		    		$('#module-conteneur').replaceWith(vue);
		    	}
		    	else 
		    	{
		    		$('.fenetre-modale').replaceWith(data);
		    		initFenetreModale(300);
		    		rechFonctEdit();
		    	}
		    } 
		});
	});
};


/**
 * Creer un etudiant dans la base de donn�es
 */ 
var verifIdentifiant = function()  
{
	$('#authentification').submit(function(event)
	{
		event.preventDefault();
		var adresseMail = $('#connexion input[name=adresseMail]').val();
		var motDePasse = $('#connexion input[name=motDePasse]').val();
		var vue = "";
		
		// Animation 
		$('.module-form').fadeOut(300);
		$('#connexion-loader-conteneur').delay(300).fadeIn(1500, function()
		{
			$.ajax({ 
			    type: "POST", 
			    url: "http://localhost:8080/ZPareo/connexion", 
			    data: 
			    {
			    	adresseMail: adresseMail,
			    	motDePasse: motDePasse
			    }, 
			    error: function() 
			    { 
			    	alert("erreur !"); 
			    },
			    success: function(data, status, xhr) 
			    { 
			    	if (data.match('<tbody>')) 
			    	{
			    		$('#connexion').delay(1300).animate({
			    			left: '-=2000'
			    		},1000, function()
			    		{
				    		vue = data.substr( data.search("<div id=\"site-conteneur\">"), data.search("<!-- Fin site-conteneur -->"));
				    		$('body').hide();
				    		$('header').remove();
				    		$('#site-conteneur').replaceWith(vue);
				    		$('body').fadeIn();
				    		if (vue.search("Liste des administrateurs") > 0) 
				    		{
				    			history.pushState({ 
				    				path: this.path 
				    			}, 
				    				'', 
				    				'http://localhost:8080/ZPareo/ai/administrateur'
				    			);
				    			document.title = "ZPareo - Liste des administrateurs";
				    		}
				    		else if (vue.search("Liste de vos examens") > 0) 
				    		{
				    			history.pushState({ 
				    				path: this.path 
				    			}, 
				    				'', 
				    				'http://localhost:8080/ZPareo/pi/examen'
				    			);
				    			document.title = "ZPareo - Liste de vos examens";
				    		}
				    		else if (vue.search("Mes notes") > 0) 
				    		{
				    			history.pushState({ 
				    				path: this.path 
				    			},
				    				'', 
				    				'http://localhost:8080/ZPareo/ei/note'
				    			);
				    			document.title = "ZPareo - Mes notes";
				    		}
			    		});
			    	}
			    	else 
			    	{
			    		vue = data.substr( data.search("<div id=\"site-conteneur\">"), data.search("<!-- Fin site-conteneur -->"));
			    		$('#site-conteneur').replaceWith(vue);
			    		$('#connexion').animate({
			    			left:"+=5"
			    		},60).animate({
			    			left:"-=10"
			    		},60).animate({
			    			left:"+=10"
			    		},60).animate({
			    			left:"-=10"
			    		},60).animate({
			    			left:"+=5"
			    		},60);
			    	}
			    } 
			});
		});
	});
};

/**
 * Fonction main
 */
$(function() {
	initTableSorter();
	animColonne();
	verifIdentifiant();
});
