package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import beans.Professeur;
import beans.Groupe;
import beans.Matiere;

public class ProfesseurDaoImpl implements ProfesseurDao
{
	private DAOFactory daoFactory;
	private static final String SQL_COUNT_TOUS                    = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 1 AND date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_PAR_ADRESSE_MAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 1 AND adresse_mail = ?";
	private static final String SQL_SELECT_PAR_ADRESSE_MAIL       = "SELECT id FROM gnw_utilisateur WHERE profil = 1 AND adresse_mail = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_TOUS                   = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 1 AND date_suppr IS NULL";
	private static final String SQL_SELECT_MATIERES               = "SELECT gnw_professeur_matiere.fk_matiere as matiereId, gnw_matiere.nom as matiereNom FROM gnw_professeur_matiere, gnw_matiere WHERE gnw_professeur_matiere.date_suppr IS NULL AND gnw_professeur_matiere.fk_professeur = ? AND gnw_professeur_matiere.fk_matiere = gnw_matiere.id";
	private static final String SQL_SELECT_GROUPES                = "SELECT gnw_professeur_groupe.fk_groupe as groupeId, gnw_groupe.nom as groupeNom FROM gnw_professeur_groupe, gnw_groupe WHERE gnw_professeur_groupe.date_suppr IS NULL AND gnw_professeur_groupe.fk_professeur = ? AND gnw_professeur_groupe.fk_groupe = gnw_groupe.id";
	private static final String SQL_SELECT_PAR_ID                 = "SELECT id, nom, prenom, adresse_mail FROM gnw_utilisateur WHERE id = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_MATIERE          = "SELECT COUNT(id) FROM gnw_professeur_matiere WHERE fk_professeur = ? AND fk_groupe = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_GROUPE           = "SELECT COUNT(id) FROM gnw_professeur_groupe WHERE fk_professeur = ? AND fk_matiere = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_AUTH                   = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail FROM gnw_utilisateur WHERE gnw_utilisateur.profil = 1 AND gnw_utilisateur.adresse_mail = ? AND gnw_utilisateur.mot_de_passe = ?";
	private static final String SQL_INSERT_PROFESSEUR             = "INSERT INTO gnw_utilisateur ( nom, prenom, adresse_mail, mot_de_passe, profil ) VALUES ( ?, ?, ?, ?, 1 )";
	private static final String SQL_INSERT_MATIERE                = "INSERT INTO gnw_professeur_matiere ( gnw_professeur_matiere.fk_professeur, gnw_professeur_matiere.fk_matiere ) VALUES ( ?, ? )";
	private static final String SQL_INSERT_GROUPE                 = "INSERT INTO gnw_professeur_groupe ( gnw_professeur_groupe.fk_professeur, gnw_professeur_groupe.fk_groupe ) VALUES ( ?, ? )";
	private static final String SQL_UPDATE_PROFESSEUR             = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ? WHERE id = ?";
	private static final String SQL_UPDATE_MOT_DE_PASSE           = "UPDATE gnw_utilisateur SET mot_de_passe = ? WHERE id = ?"; 
	private static final String SQL_UPDATE_MATIERE                = "UPDATE gnw_professeur_matiere SET date_suppr = now() WHERE fk_professeur = ?"; 
	private static final String SQL_UPDATE_GROUPE                 = "UPDATE gnw_professeur_groupe SET date_suppr = now() WHERE fk_professeur = ?"; 
	private static final String SQL_UPDATE_SUPPR                  = "UPDATE gnw_utilisateur SET date_suppr = now() WHERE id = ?";

	/**
	 * Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	ProfesseurDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
	
	/**
     * Ajoute un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	public void creer(Professeur professeur) throws DAOException 
	{
		ajouterProfesseur(professeur);
		ajouterMatieres(professeur);
		ajouterGroupes(professeur);
	}

	/**
     * Ajoute un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	public void ajouterProfesseur(Professeur professeur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Professeur professeur1 = new Professeur();
		ResultSet resultSet;
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			// Insertion du professeur dans la base
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_PROFESSEUR, true, professeur.getNom(), professeur.getPrenom(), professeur.getAdresseMail(), professeur.getMotDePasse());
			preparedStatement.executeUpdate();
			
			// Recherche de l'id du professeur
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ADRESSE_MAIL, true, professeur.getAdresseMail());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			professeur.setId(resultSet.getLong("id"));

		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
     * Ajoute les matieres d'un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	public void ajouterMatieres(Professeur professeur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			Object[] matieres = professeur.getListeMatieres().toArray();
			
			for(Object m : matieres)
			{
				Matiere matiere = (Matiere) m;
				preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_MATIERE, true, professeur.getId(), matiere.getId());
				preparedStatement.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Ajoute une matiere à un professeur dans la base de données
	 * 
	 * @param professeur
	 * @param matiere
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur ajouterMatiere(Professeur professeur, Matiere matiere) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_MATIERE, true, professeur.getId(), matiere.getId() );
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
     * Ajoute les groupes d'un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	public void ajouterGroupes(Professeur professeur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			Object[] groupes = professeur.getListeGroupes().toArray();
			
			for(Object g : groupes)
			{
				Groupe groupe = (Groupe) g;
				preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_GROUPE, true, professeur.getId(), groupe.getId());
				preparedStatement.executeUpdate();
			}
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Ajoute un groupe a un professeur dans la base de données
	 * 
	 * @param professeur
	 * @param groupe
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur ajouterGroupe(Professeur professeur, Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_GROUPE, true, professeur.getId(), groupe.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
     * Recherche un ou des professeur(s) dans la base de donn�es
     * 
     * @param professeur
     * @return listeProfesseurs
     * @throws DAOException
     */
	public Set<Professeur> rechercher(Professeur professeur) throws DAOException 
	{
		Set<Professeur> listeProfesseurs = new TreeSet<Professeur>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (professeur.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";	
			}
			
			if (professeur.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom LIKE ?";
				professeur.setNom("%" + professeur.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom IS NOT ?";	
			}
			
			if (professeur.getPrenom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom LIKE ?";
				professeur.setPrenom("%" + professeur.getPrenom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom IS NOT ?";	
			}
			
			if (professeur.getAdresseMail() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail LIKE ?";
				professeur.setAdresseMail("%" + professeur.getAdresseMail() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail IS NOT ?";	
			}
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, professeur.getId(), professeur.getNom(), professeur.getPrenom(), professeur.getAdresseMail());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				professeur = mapProfesseur(resultSet);
				listeProfesseurs.add(professeur);
	        }
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return listeProfesseurs;
	}
	
	/**
	 * Compte tous les professeurs de la base de donn�es
	 * 
	 * @return nbProfesseurs
	 */
	public int compterTous() throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int nbProfesseurs;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			nbProfesseurs = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return nbProfesseurs;
	}
	
	/**
	 * Edite un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur editer(Professeur professeur) throws DAOException
	{
		// Edite les informations générales du professeur
		editerInformations(professeur);
		// Edite le mot de passe du professeur
		if (professeur.getMotDePasse() != null)
		{
			editerMotDePasse(professeur);			
		}
		
		// Supprime les matieres et les groupes du professeur
		supprimerMatieres(professeur);
		supprimerGroupes(professeur);
		
		// Ajoute les matieres et les groupes du professeur
		if (professeur.getListeMatieres() != null)
		{
			ajouterMatieres( professeur );
		}
		if (professeur.getListeGroupes() != null)
		{
			ajouterGroupes( professeur);
		}
			
		return professeur;
	}
	
	/**
	 * Edite les informations principales d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur editerInformations(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PROFESSEUR, true, professeur.getNom(), professeur.getPrenom(), professeur.getAdresseMail(), professeur.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
	 * Edite le mot de passe d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur editerMotDePasse(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_MOT_DE_PASSE, true, professeur.getMotDePasse(), professeur.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
	 * Supprime les groupes d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public void supprimerGroupes(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_GROUPE, true, professeur.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Supprime les matieres d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur supprimerMatieres(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_MATIERE, true, professeur.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
	 * Vérifie les identifiants d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	public Professeur verifIdentifiant(Professeur professeur) throws DAOException
	{
		Professeur professeur2 = new Professeur();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_AUTH, true, professeur.getAdresseMail(), professeur.getMotDePasse());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				professeur2 = mapProfesseur(resultSet);
				professeur2.setMotDePasse(professeur.getMotDePasse());
			}
			
			// Récupère les groupes du professeur
			professeur2.setListeGroupes(trouverGroupes(professeur2));
						
			// Récupère les matieres du professeur
			professeur2.setListeMatieres(trouverMatieres(professeur2));
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur2;
	}
	
	/**
	 * Vérifie l'existance d'une matiere d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @param matiere
	 * @return statut
	 * @throws DAOException
	 */
	public int verifExistanceMatiere(Professeur professeur, Matiere matiere) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int statut;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COUNT_MATIERE, true, professeur.getId(), matiere.getId());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			statut = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return statut;
	}
	
	/**
	 * Vérifie l'existance d'un groupe d'un professeur dans la base de donn�es
	 * 
	 * @param professeur
	 * @return
	 * @throws DAOException
	 */
	public int verifExistanceGroupe(Professeur professeur, Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int statut;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COUNT_GROUPE, true, professeur.getId(), groupe.getId());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			statut = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return statut;
	}

	/**
	 * Vérifie l'existance d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return statut
	 * @throws DAOException
	 */
	public int verifExistance(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_COUNT_PAR_ADRESSE_MAIL;
		int statut;
		
		try 
		{
			
			connexion = daoFactory.getConnection();
			
			if (professeur.getId() == null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id != ?";
			}

			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, professeur.getAdresseMail(), professeur.getId());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			statut = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return statut;
	}

	/**
	 * Cherche un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur trouver(Professeur professeur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			// Récupére le professeur
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, professeur.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				professeur = mapProfesseur(resultSet);
	        }
			
			// Récupère les groupes du professeur
			professeur.setListeGroupes(trouverGroupes(professeur));
			
			// Récupère les matieres du professeur
			professeur.setListeMatieres(trouverMatieres(professeur));
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return professeur;
	}
	
	/**
	 * Trouve les matières d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return listeMatieres
	 * @throws DAOException
	 */
	public Set<Matiere> trouverMatieres(Professeur professeur) throws DAOException
	{
		Set<Matiere> listeMatieres = new TreeSet<Matiere>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_MATIERES, true, professeur.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				Matiere matiere = new Matiere();
				matiere = mapMatiere(resultSet);
				listeMatieres.add(matiere);
	        }
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		return listeMatieres;
	}
	
	/**
	 * Trouve les groupes d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return listeGroupes
	 * @throws DAOException
	 */
	public Set<Groupe> trouverGroupes( Professeur professeur ) throws DAOException
	{
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_GROUPES, true, professeur.getId());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) 
			{
				Groupe groupe = new Groupe();
				groupe = mapGroupe(resultSet);
				listeGroupes.add(groupe);
	        }
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}

		
		return listeGroupes;
	}
	
	/**
	 * Supprime un professeur dans la base de donn�es
	 * 
	 * @param groupe
	 * @return statut
	 * @throws DAOException
	 */
	public int supprimer(Professeur professeur) 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, professeur.getId());
			statut = preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException( e );
		} 
		finally 
		{
			fermeturesSilencieuses( preparedStatement, connexion );
		}
		
		return statut;
	}
	
	/**
	 * Prépare une requête SQL sur mesure
	 * 
	 * @param connexion
	 * @param sql
	 * @param returnGeneratedKeys
	 * @param objets
	 * @return preparedStatement
	 * @throws SQLException
	 */
	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException 
	{
		PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ?Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		for (int i = 0; i < objets.length; i++) 
		{
			preparedStatement.setObject(i + 1, objets[i]);
		}
		
		return preparedStatement;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet Professeur
	 * 
	 * @param resultSet
	 * @return professeur
	 * @throws SQLException
	 */
	private static Professeur mapProfesseur(ResultSet resultSet) throws SQLException 
	{
		Professeur professeur = new Professeur();
		
		professeur.setId(resultSet.getLong("id"));
		professeur.setNom(resultSet.getString("nom"));
		professeur.setPrenom(resultSet.getString("prenom"));
		professeur.setAdresseMail(resultSet.getString("adresse_mail"));
		
		return professeur;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet Matiere
	 * 
	 * @param resultSet
	 * @return matiere
	 * @throws SQLException
	 */
	private static Matiere mapMatiere(ResultSet resultSet) throws SQLException 
	{
		Matiere matiere = new Matiere();
		
		matiere.setId(resultSet.getLong("matiereId"));
		matiere.setNom(resultSet.getString("matiereNom"));
		
		return matiere;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet Groupe
	 * 
	 * @param resultSet
	 * @return groupe
	 * @throws SQLException
	 */
	private static Groupe mapGroupe(ResultSet resultSet) throws SQLException 
	{
		Groupe groupe = new Groupe();
		
		groupe.setId(resultSet.getLong("groupeId"));
		groupe.setNom(resultSet.getString("groupeNom"));
		
		return groupe;
	}
	
}
