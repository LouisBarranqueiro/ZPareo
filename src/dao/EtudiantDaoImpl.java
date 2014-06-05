package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtilitaire.*;

import java.util.TreeSet;
import java.util.Set;

import beans.Etudiant;
import beans.Groupe;

public class EtudiantDaoImpl implements EtudiantDao 
{
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_COUNT_PAR_ADRESSE_MAIL = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 0 AND gnw_utilisateur.adresse_mail = ?";
	private static final String SQL_INSERT_ETUDIANT               = "INSERT INTO gnw_utilisateur (nom, prenom, adresse_mail, mot_de_passe, profil) VALUES (?, ?, ?, ?, 0);";
	private static final String SQL_INSERT_GROUPE	     		  = "INSERT INTO gnw_etudiant_groupe (fk_etudiant, fk_groupe) VALUES ( ?, ? );";
	private static final String SQL_SELECT_TOUS                   = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe";
	private static final String SQL_SELECT_PAR_ID                 = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe AND gnw_utilisateur.id = ?";
	private static final String SQL_SELECT_AUTH                   = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom FROM gnw_utilisateur, gnw_etudiant_groupe, gnw_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_groupe.id = gnw_etudiant_groupe.fk_groupe AND gnw_utilisateur.adresse_mail = ? AND gnw_utilisateur.mot_de_passe = ?";
	private static final String SQL_SELECT_ID_PAR_ADRESSE_MAIL    = "SELECT id FROM gnw_utilisateur WHERE profil = 0 AND date_suppr IS NULL AND adresse_mail = ?";
	private static final String SQL_COUNT_TOUS                    = "SELECT COUNT(id) FROM gnw_utilisateur WHERE profil = 0 AND date_suppr IS NULL";
	private static final String SQL_UPDATE_ETUDIANT               = "UPDATE gnw_utilisateur SET nom = ?, prenom = ?, adresse_mail = ? WHERE id = ?";
	private static final String SQL_UPDATE_GROUPE                 = "UPDATE gnw_etudiant_groupe SET fk_groupe = ? WHERE fk_etudiant = ?";
	private static final String SQL_UPDATE_SUPPR                  = "UPDATE gnw_utilisateur SET date_suppr = now() WHERE id = ?";
	
	
	/**
	 * Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	EtudiantDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
    
	/**
     * Ajoute un etudiant dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	@Override 
	public void creer(Etudiant etudiant) throws DAOException 
	{
		Etudiant etudiant1 = new Etudiant();
		
		ajouterEtudiant(etudiant);
		etudiant1 = retrouver(etudiant);
		etudiant.setId(etudiant1.getId());
		ajouterGroupe(etudiant);
	}
	
	/**
     * Ajoute un etudiant dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	public void ajouterEtudiant(Etudiant etudiant) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_ETUDIANT, true, etudiant.getNom(), etudiant.getPrenom(), etudiant.getAdresseMail(), etudiant.getMotDePasse());
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
     * Ajoute la relation entre l'étudian et son groupe dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	public void ajouterGroupe(Etudiant etudiant) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Groupe groupe = new Groupe(etudiant.getGroupe());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_GROUPE, true, etudiant.getId(), groupe.getId());
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
	 * Vérifie les identifiants d'un etudiant dans la base de données
	 * 
	 * @return etudiant
	 * @throws DAOException
	 */
	public Etudiant verifIdentifiant(Etudiant etudiant) throws DAOException 
	{
		Etudiant etudiant2 = new Etudiant();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_AUTH, true, etudiant.getAdresseMail(), etudiant.getMotDePasse());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				etudiant2 = map(resultSet);
				etudiant2.setMotDePasse(etudiant.getMotDePasse());
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
		
		return etudiant2;
	}
	
	/**
	 * Sélectionne tous les étudiants de la base de données
	 * 
	 * @return listeEtudiants
	 * @throws DAOException
	 */
	public Set<Etudiant> selectTous() throws DAOException 
	{
		Set<Etudiant> listeEtudiants = new TreeSet<Etudiant>();
		Etudiant etudiant = new Etudiant();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				etudiant = map(resultSet);
				listeEtudiants.add(etudiant);
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
		
		return listeEtudiants;
	}
	
	/**
	 * Recherche un ou des étudiant(s) dans la base de données
	 * 
	 * @param etudiant
	 * @return listeEtudiants
	 */
	public Set<Etudiant> rechercher(Etudiant etudiant) throws DAOException 
	{
		Set<Etudiant> listeEtudiants = new TreeSet<Etudiant>();
		Groupe groupe = new Groupe(etudiant.getGroupe());
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_TOUS;

		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (etudiant.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";	
			}
			
			if (etudiant.getNom() != null )
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom LIKE ?";
				etudiant.setNom("%" + etudiant.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.nom IS NOT ?";	
			}
			
			if(etudiant.getPrenom() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom LIKE ?";
				etudiant.setPrenom("%" + etudiant.getPrenom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.prenom IS NOT ?";	
			}
			
			if(etudiant.getAdresseMail() != null)
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail LIKE ?";
				etudiant.setAdresseMail("%" + etudiant.getAdresseMail() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.adresse_mail IS NOT ?";	
			}
			
			if (groupe.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_groupe.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_groupe.id IS NOT ?";	
			}
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, etudiant.getId(), etudiant.getNom(), etudiant.getPrenom(), etudiant.getAdresseMail(), groupe.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				etudiant = map(resultSet);
				listeEtudiants.add(etudiant);
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
		
		return listeEtudiants;
	}
	
	/**
	 * Edite un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return etudiant
	 * @throws DAOException
	 */
	public Etudiant editer(Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Groupe groupe = new Groupe(etudiant.getGroupe());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_ETUDIANT, true, etudiant.getNom(), etudiant.getPrenom(), etudiant.getAdresseMail(), etudiant.getId());
			preparedStatement.executeUpdate();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_GROUPE, true, groupe.getId(), etudiant.getId());
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
		
		return etudiant;
	}
	
	/**
	 * Cherche un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return etudiant
	 * @throws DAOException
	 */
	public Etudiant trouver(Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, etudiant.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				etudiant = map(resultSet);
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
		
		return etudiant;
	}
	
	/**
	 * Cherche l'id d'un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return etudiant
	 * @throws DAOException
	 */
	public Etudiant retrouver(Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ID_PAR_ADRESSE_MAIL, true, etudiant.getAdresseMail());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				etudiant.setId(resultSet.getLong("id"));
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
		
		return etudiant;
	}
	
	
	/**
	 * Compte tous les étudiants de la base de données
	 * 
	 * @return nbEtudiants
	 * @throws DAOException
	 */
	public int compterTous() throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int nbEtudiants;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			nbEtudiants = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return nbEtudiants;
	}
	
	/**
	 * Verifie l'existance d'un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return statut
	 * @throws DAOException
	 */
	public int verifExistance(Etudiant etudiant) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_COUNT_PAR_ADRESSE_MAIL;
		int statut;
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			if (etudiant.getId() == null) 
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id IS NOT ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_utilisateur.id != ?";
			}

			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, etudiant.getAdresseMail(), etudiant.getId());
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
	 * Supprime un groupe dans la base de données
	 * 
	 * @param etudiant
	 * @return statut
	 * @throws DAOException
	 */
	public int supprimer(Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, etudiant.getId());
			statut = preparedStatement.executeUpdate();
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
	 * Prépare une requete SQL sur mesure
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
	 * Transfère les données du resultSet vers un objet
	 * 
	 * @param resultSet
	 * @return etudiant
	 * @throws SQLException
	 */
	private static Etudiant map(ResultSet resultSet) throws SQLException 
	{
		Etudiant etudiant = new Etudiant();
		Groupe groupe = new Groupe();
		
		etudiant.setId(resultSet.getLong("id"));
		etudiant.setNom(resultSet.getString("nom"));
		etudiant.setPrenom(resultSet.getString("prenom"));
		etudiant.setAdresseMail(resultSet.getString("adresse_mail"));
		groupe.setId(resultSet.getLong("groupeId"));
		groupe.setNom(resultSet.getString("groupeNom"));
		etudiant.setGroupe(groupe);
		
		return etudiant;
	}
}
