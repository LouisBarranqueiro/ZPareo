package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.TreeSet;
import java.util.Set;

import beans.Administrateur;

public class AdministrateurDaoImpl implements AdministrateurDao
{
	private DAOFactory daoFactory;
	private static final String SQL_COUNT_TOUS                    = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 2 AND date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_PAR_ADRESSE_MAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE adresse_mail = ?";
	private static final String SQL_SELECT_TOUS                   = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 2 AND date_suppr IS NULL";
	private static final String SQL_SELECT_PAR_ID                 = "SELECT id, nom, prenom, adresse_mail FROM gnw_utilisateur WHERE id = ? AND date_suppr IS NULL";;
	private static final String SQL_SELECT_AUTH                   = "SELECT id, nom, prenom, adresse_mail  FROM gnw_utilisateur WHERE profil = 2 AND date_suppr IS NULL AND adresse_mail = ? AND mot_de_passe = ?";
	private static final String SQL_INSERT_PROFESSEUR             = "INSERT INTO gnw_utilisateur ( nom, prenom, adresse_mail, mot_de_passe, profil ) VALUES ( ?, ?, ?, ?, 2 )";
	private static final String SQL_UPDATE_PROFESSEUR             = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ? WHERE id = ?";
	private static final String SQL_UPDATE_MOT_DE_PASSE           = "UPDATE gnw_utilisateur SET mot_de_passe = ? WHERE id = ?"; 
	private static final String SQL_UPDATE_SUPPR                  = "UPDATE gnw_utilisateur SET date_suppr = now() WHERE id = ?";

	/**
	 * Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	AdministrateurDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
	
	/**
     * Ajoute un administrateur dans la base de données
     * 
     * @param administrateur
     * @throws DAOException
     */
	public void creer(Administrateur administrateur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_PROFESSEUR, true, administrateur.getNom(), administrateur.getPrenom(), administrateur.getAdresseMail(), administrateur.getMotDePasse());
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
     * Recherche un ou des administrateur(s) dans la base de données
     * 
     * @param administrateur
     * @return listeAdministrateurs
     * @throws DAOException
     */
	public Set<Administrateur> rechercher(Administrateur administrateur) throws DAOException 
	{
		Set<Administrateur> listeAdministrateurs = new TreeSet<Administrateur>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (administrateur.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";	
			}
			
			if (administrateur.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom LIKE ?";
				administrateur.setNom("%" + administrateur.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom IS NOT ?";	
			}
			
			if (administrateur.getPrenom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom LIKE ?";
				administrateur.setPrenom("%" + administrateur.getPrenom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom IS NOT ?";	
			}
			
			if (administrateur.getAdresseMail() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail LIKE ?";
				administrateur.setAdresseMail("%" + administrateur.getAdresseMail() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail IS NOT ?";	
			}
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, administrateur.getId(), administrateur.getNom(), administrateur.getPrenom(), administrateur.getAdresseMail());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				administrateur = map(resultSet);
				listeAdministrateurs.add(administrateur);
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
		
		return listeAdministrateurs;
	}
	
	/**
	 * Compte tous les administrateurs de la base de données
	 * 
	 * @return nbAdministrateurs
	 */
	public int compterTous() throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int nbAdministrateurs;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			nbAdministrateurs = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return nbAdministrateurs;
	}
	
	/**
	 * Edite un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @return administrateur
	 * @throws DAOException
	 */
	public Administrateur editer(Administrateur administrateur) throws DAOException
	{
		// Edite les informations générales du professeur
		editerInformations(administrateur);
		
		// Edite le mot de passe du professeur
		if (administrateur.getMotDePasse() != null)
		{
			editerMotDePasse(administrateur);			
		}
			
		return administrateur;
	}
	
	/**
	 * Edite les informations principales d'un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @return administrateur
	 * @throws DAOException
	 */
	public Administrateur editerInformations(Administrateur administrateur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PROFESSEUR, true, administrateur.getNom(), administrateur.getPrenom(), administrateur.getAdresseMail(), administrateur.getId());
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
		
		return administrateur;
	}
	
	/**
	 * Edite le mot de passe d'un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @return administrateur
	 * @throws DAOException
	 */
	public Administrateur editerMotDePasse(Administrateur administrateur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_MOT_DE_PASSE, true, administrateur.getMotDePasse(), administrateur.getId());
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
		
		return administrateur;
	}
	
	/**
	 * Vérifie l'existance d'un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @return statut
	 * @throws DAOException
	 */
	public int verifExistance(Administrateur administrateur) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_COUNT_PAR_ADRESSE_MAIL;
		int statut;
		
		try 
		{
			
			connexion = daoFactory.getConnection();
			
			if (administrateur.getId() == null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id != ?";
			}

			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, administrateur.getAdresseMail(), administrateur.getId());
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
	 * Cherche un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @return administrateur
	 * @throws DAOException
	 */
	public Administrateur trouver(Administrateur administrateur) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, administrateur.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				administrateur = map(resultSet);
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
		
		return administrateur;
	}
	
	/**
	 * Supprime un administrateur dans la base de donn�es
	 * 
	 * @param administrateur
	 * @return statut
	 * @throws DAOException
	 */
	public int supprimer(Administrateur administrateur) 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, administrateur.getId());
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
	 * Vérifie les identifiants d'un etudiant dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	public Administrateur verifIdentifiant(Administrateur administrateur) throws DAOException
	{
		Administrateur administrateur2 = new Administrateur();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_AUTH, true, administrateur.getAdresseMail(), administrateur.getMotDePasse());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				administrateur2 = map(resultSet);
				administrateur2.setMotDePasse(administrateur.getMotDePasse());
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
		
		return administrateur2;
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
	private static Administrateur map(ResultSet resultSet) throws SQLException 
	{
		Administrateur administrateur = new Administrateur();
		
		administrateur.setId(resultSet.getLong("id"));
		administrateur.setNom(resultSet.getString("nom"));
		administrateur.setPrenom(resultSet.getString("prenom"));
		administrateur.setAdresseMail(resultSet.getString("adresse_mail"));
		
		return administrateur;
	}
	
}
