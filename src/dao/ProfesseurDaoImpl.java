package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;
import beans.Administrateur;
import beans.Professeur;
import beans.Groupe;
import beans.Matiere;

public class ProfesseurDaoImpl implements ProfesseurDao
{
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_COUNT_PAR_ADRESSE_MAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 1 AND adresse_mail = ?";
    private static final String SQL_SELECT_TOUS                   = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 1 AND date_suppr IS NULL";
	private static final String SQL_SELECT_MATIERES               = "SELECT gnw_professeur_matiere.fk_matiere as matiereId, gnw_matiere.nom as matiereNom FROM gnw_professeur_matiere, gnw_matiere WHERE gnw_professeur_matiere.date_suppr IS NULL AND gnw_professeur_matiere.fk_professeur = ? AND gnw_professeur_matiere.fk_matiere = gnw_matiere.id";
	private static final String SQL_SELECT_GROUPES                = "SELECT gnw_professeur_groupe.fk_groupe as groupeId, gnw_groupe.nom as groupeNom FROM gnw_professeur_groupe, gnw_groupe WHERE gnw_professeur_groupe.date_suppr IS NULL AND gnw_professeur_groupe.fk_professeur = ? AND gnw_professeur_groupe.fk_groupe = gnw_groupe.id";
	private static final String SQL_SELECT_PAR_ID                 = "SELECT id, nom, prenom, adresse_mail FROM gnw_utilisateur WHERE id = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_AUTH                   = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail FROM gnw_utilisateur WHERE gnw_utilisateur.profil = 1 AND gnw_utilisateur.adresse_mail = ? AND gnw_utilisateur.mot_de_passe = ?";
	private static final String SQL_INSERT_PROFESSEUR             = "INSERT INTO gnw_utilisateur ( nom, prenom, adresse_mail, mot_de_passe, profil, fk_utilisateur ) VALUES (?, ?, ?, ?, 1, ?)";
	private static final String SQL_INSERT_MATIERE                = "INSERT INTO gnw_professeur_matiere ( fk_professeur, fk_matiere, fk_utilisateur ) VALUES (?, ?, ?)";
	private static final String SQL_INSERT_GROUPE                 = "INSERT INTO gnw_professeur_groupe ( fk_professeur, fk_groupe, fk_utilisateur ) VALUES (?, ?, ?)";
	private static final String SQL_UPDATE_PROFESSEUR             = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ?, fk_utilisateur = ? WHERE id = ?";
	private static final String SQL_UPDATE_MOT_DE_PASSE           = "UPDATE gnw_utilisateur SET mot_de_passe = ?, fk_utilisateur = ? WHERE id = ?"; 
	private static final String SQL_UPDATE_MATIERE                = "UPDATE gnw_professeur_matiere SET date_suppr = now(), fk_utilisateur = ? WHERE fk_professeur = ?"; 
	private static final String SQL_UPDATE_GROUPE                 = "UPDATE gnw_professeur_groupe SET date_suppr = now(), fk_utilisateur = ? WHERE fk_professeur = ?"; 
	private static final String SQL_UPDATE_SUPPR                  = "UPDATE gnw_utilisateur SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";

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
     * Ajoute un professeur, ses matières et ses gorupes dans la base de données
     * 
     * @param professeur
     */
	public void creer(Professeur professeur)
	{
		ajouterProfesseur(professeur);
		ajouterMatieres(professeur, true);
		ajouterGroupes(professeur, true);
	}

	/**
     * Ajoute un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	private void ajouterProfesseur(Professeur professeur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		Administrateur createur = new Administrateur(professeur.getCreateur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_PROFESSEUR, true, professeur.getNom(), professeur.getPrenom(), professeur.getAdresseMail(), professeur.getMotDePasse(), createur.getId());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
			if(resultSet.next())professeur.setId(resultSet.getLong(1));
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
	private void ajouterMatieres(Professeur professeur, boolean creation) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur administrateur = null;
		Object[] matieres = null;
		
		if(creation) administrateur = new Administrateur(professeur.getCreateur());
		else  administrateur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			matieres = professeur.getListeMatieres().toArray();

			for (Object m : matieres)
			{
				Matiere matiere = (Matiere) m;
				preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_MATIERE, true, professeur.getId(), matiere.getId(), administrateur.getId());
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
     * Ajoute les groupes d'un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	private void ajouterGroupes(Professeur professeur, boolean creation) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur administrateur = null;
		Object[] groupes = null;
		
		if(creation) administrateur = new Administrateur(professeur.getCreateur());
		else  administrateur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			groupes = professeur.getListeGroupes().toArray();
			
			for(Object g : groupes)
			{
				Groupe groupe = (Groupe) g;
				preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_GROUPE, true, professeur.getId(), groupe.getId(), administrateur.getId());
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
     * Recherche un ou des professeur(s) dans la base de données
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
			
			if (professeur.getId() != null) sqlSelectRecherche += " AND gnw_utilisateur.id = ?";
			else sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";	
		
			if (professeur.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom LIKE ?";
				professeur.setNom("%" + professeur.getNom() + "%");
			}
			else sqlSelectRecherche += " AND gnw_utilisateur.nom IS NOT ?";	
			
			
			if (professeur.getPrenom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom LIKE ?";
				professeur.setPrenom("%" + professeur.getPrenom() + "%");
			}
			else sqlSelectRecherche += " AND gnw_utilisateur.prenom IS NOT ?";	
			
			
			if (professeur.getAdresseMail() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail LIKE ?";
				professeur.setAdresseMail("%" + professeur.getAdresseMail() + "%");
			}
			else sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail IS NOT ?";	
			
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
	 * Edite un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	public Professeur editer(Professeur professeur)
	{
		// Edite les informations générales du professeur
		editerInformations(professeur);
		
		// Edite le mot de passe du professeur
		if (professeur.getMotDePasse() != null) editerMotDePasse(professeur);			
		
		// Supprime les matieres et les groupes du professeur
		supprimerMatieres(professeur);
		supprimerGroupes(professeur);
		
		// Ajoute les matieres et les groupes du professeur
		if (professeur.getListeMatieres() != null) ajouterMatieres(professeur, false);
		
		if (professeur.getListeGroupes() != null) ajouterGroupes(professeur, false);
	
		return professeur;
	}
	
	/**
	 * Edite les informations principales d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return professeur
	 * @throws DAOException
	 */
	private Professeur editerInformations(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PROFESSEUR, true, professeur.getNom(), professeur.getPrenom(), professeur.getAdresseMail(), editeur.getId(), professeur.getId());
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
	private Professeur editerMotDePasse(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_MOT_DE_PASSE, true, professeur.getMotDePasse(), editeur.getId(), professeur.getId());
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
	private void supprimerGroupes(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_GROUPE, true, editeur.getId(), professeur.getId());
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
	private Professeur supprimerMatieres(Professeur professeur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_MATIERE, true, editeur.getId(), professeur.getId());
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
	 * @return professeur2
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
			
			if (professeur.getId() == null) sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";
			else sqlSelectRecherche += " AND gnw_utilisateur.id != ?";
		
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
			connexion = daoFactory.getConnection();
			
			// Récupére le professeur
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, professeur.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) professeur = mapProfesseur(resultSet);
	        
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
	private Set<Matiere> trouverMatieres(Professeur professeur) throws DAOException
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
	private Set<Groupe> trouverGroupes(Professeur professeur) throws DAOException
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
	 * Supprime un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	public void supprimer(Professeur professeur) 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(professeur.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, editeur.getId(), professeur.getId());
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