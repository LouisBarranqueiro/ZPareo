package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtilitaire.*;

import java.util.TreeSet;
import java.util.Set;

import beans.Administrateur;
import beans.Groupe;

public class GroupeDaoImpl implements GroupeDao 
{
	private DAOFactory daoFactory;
	private static final String SQL_SELECT_COUNT_PAR_NOM = "SELECT COUNT(id) FROM gnw_groupe WHERE date_suppr IS NULL AND nom = ?";
	private static final String SQL_SELECT_TOUS          = "SELECT id, nom FROM gnw_groupe WHERE date_suppr IS NULL";
	private static final String SQL_SELECT_PAR_ID        = "SELECT id, nom FROM gnw_groupe WHERE id = ? AND date_suppr IS NULL";
	private static final String SQL_INSERT               = "INSERT INTO gnw_groupe (nom, fk_utilisateur) VALUES (?, ?)";
	private static final String SQL_UPDATE               = "UPDATE gnw_groupe SET nom = ?, date_modif = now(), fk_utilisateur = ? WHERE id = ?"; 
	private static final String SQL_UPDATE_SUPPR         = "UPDATE gnw_groupe SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";
	
	/**
	 * R�cup�re la daoFactory
	 * 
	 * @param daoFactory
	 */
	GroupeDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
    
	/**
     * Ajoute un groupe dans la base de données
     * 
     * @param groupe
     * @throws DAOException
     */
	@Override 
	public void ajouter(Groupe groupe) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur createur = new Administrateur(groupe.getCreateur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, groupe.getNom(), createur.getId());
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
     * Recherche une ou des groupe(s) dans la base de données
     * 
     * @param groupe
     * @return listeGroupes
     * @throws DAOException
     */
	@Override
	public Set<Groupe> rechercher(Groupe groupe) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Set<Groupe> listeGroupes = new TreeSet<Groupe>();
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (groupe.getId() != null) sqlSelectRecherche += " AND gnw_groupe.id = ?";
			else sqlSelectRecherche += " AND gnw_groupe.id IS NOT ?";	
			
			if (groupe.getNom() != null )
			{
				sqlSelectRecherche += " AND gnw_groupe.nom LIKE ?";
				groupe.setNom("%" + groupe.getNom() + "%");
			}
			else sqlSelectRecherche += " AND gnw_groupe.nom IS NOT ?";	
			
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, groupe.getId(), groupe.getNom());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				groupe = map(resultSet);
				listeGroupes.add(groupe);
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
		
		return listeGroupes;
	}

	/**
	 * Edite un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return groupe
	 * @throws DAOException
	 */
	public Groupe editer(Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(groupe.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, groupe.getNom(), editeur.getId(), groupe.getId());
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
		
		return groupe;
	}
	
	/**
	 * Vérifie l'existance d'un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return statut
	 * @throws DAOException
	 */
	public int verifExistance(Groupe groupe) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_COUNT_PAR_NOM;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			if (groupe.getId() == null) sqlSelectRecherche += " AND id IS NOT ?";
			else sqlSelectRecherche += " AND id != ?";
		
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, groupe.getNom(), groupe.getId());
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
	 * Cherche un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return groupe
	 * @throws DAOException
	 */
	public Groupe trouver(Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, groupe.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) groupe = map(resultSet);
	        
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return groupe;
	}
	
	/**
	 * Supprime un groupe dans la base de données
	 * 
	 * @param groupe 
	 * @throws DAOException
	 */
	public void supprimer(Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Administrateur editeur = new Administrateur(groupe.getEditeur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, editeur.getId(), groupe.getId());
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
		PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		for (int i = 0; i < objets.length; i++) 
		{
			preparedStatement.setObject( i + 1, objets[i] );
		}
		
		return preparedStatement;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet Groupe
	 * 
	 * @param resultSet
	 * @return groupe
	 * @throws SQLException
	 */
	private static Groupe map(ResultSet resultSet) throws SQLException 
	{
		Groupe groupe = new Groupe();
		
		groupe.setId(resultSet.getLong("id"));
		groupe.setNom(resultSet.getString("nom"));
		
		return groupe;
	}
}
