package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static dao.DAOUtilitaire.*;
import java.util.TreeSet;
import java.util.Set;

import beans.Groupe;

public class GroupeDaoImpl implements GroupeDao 
{
	private DAOFactory daoFactory;
	private static final String SQL_COUNT_TOUS           = "SELECT COUNT(id) FROM gnw_groupe WHERE date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_PAR_NOM = "SELECT COUNT(id) FROM gnw_groupe WHERE date_suppr IS NULL AND nom = ?";
	private static final String SQL_SELECT_TOUS          = "SELECT id, nom FROM gnw_groupe WHERE date_suppr IS NULL";
	private static final String SQL_SELECT_PAR_ID        = "SELECT id, nom FROM gnw_groupe WHERE id = ? AND date_suppr IS NULL";
	private static final String SQL_INSERT               = "INSERT INTO gnw_groupe (nom) VALUES (?)";
	private static final String SQL_UPDATE               = "UPDATE gnw_groupe SET nom = ?, date_modif = now() WHERE id = ?"; 
	private static final String SQL_UPDATE_SUPPR         = "UPDATE gnw_groupe SET date_suppr = now() WHERE id = ?";
	
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
     * Ajoute un groupe dans la base de donn�es
     * 
     * @param groupe
     * @throws DAOException
     */
	@Override 
	public void creer(Groupe groupe) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, groupe.getNom());
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
     * Recherche une ou des groupe(s) dans la base de donn�es
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
			
			if (groupe.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_groupe.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_groupe.id IS NOT ?";	
			}
			
			if (groupe.getNom() != null )
			{
				sqlSelectRecherche += " AND gnw_groupe.nom LIKE ?";
				groupe.setNom("%" + groupe.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_groupe.nom IS NOT ?";	
			}
			
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
	 * Renvoie le nombre de groupes de la base de donn�es
	 * 
	 * @return nbGroupes
	 * @throws DAOException
	 */
	@Override
	public int compterTous() throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int nbGroupes;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			nbGroupes = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return nbGroupes;
	}

	/**
	 * Edite un groupe dans la base de donn�es
	 * 
	 * @param groupe
	 * @return groupe
	 * @throws DAOException
	 */
	public Groupe editer(Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, groupe.getNom(), groupe.getId());
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
	 * Verifie l'existance d'un groupe dans la base de donn�es
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
		int statut;
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			if (groupe.getId() == null) 
			{
				sqlSelectRecherche += " AND id IS NOT ?";
			}
			else
			{
				sqlSelectRecherche += " AND id != ?";
			}

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
	 * Cherche un groupe dans la base de donn�es
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
			
			if (resultSet.next()) 
			{
				groupe = map(resultSet);
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
		
		return groupe;
	}
	
	/**
	 * Supprime un groupe dans la base de donn�es
	 * 
	 * @param groupe
	 * @return statut
	 * @throws DAOException
	 */
	public int supprimer(Groupe groupe) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, groupe.getId());
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
	 * Pr�pare une r�quete SQL sur mesure
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
		
		for ( int i = 0; i < objets.length; i++ ) 
		{
			preparedStatement.setObject( i + 1, objets[i] );
		}
		
		return preparedStatement;
	}
	
	/**
	 * Transf�re les donn�es du resultSet vers un objet
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
