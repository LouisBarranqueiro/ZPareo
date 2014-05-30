package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtilitaire.*;

import java.util.Set;
import java.util.TreeSet;

import beans.Matiere;

public class MatiereDaoImpl implements MatiereDao 
{
	private DAOFactory daoFactory;
	private static final String SQL_COUNT_TOUS           = "SELECT COUNT(id) FROM gnw_matiere WHERE date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_PAR_NOM = "SELECT COUNT(id) FROM gnw_matiere WHERE date_suppr IS NULL AND nom = ?";
	private static final String SQL_SELECT_TOUS          = "SELECT id, nom FROM gnw_matiere WHERE date_suppr IS NULL";
	private static final String SQL_SELECT_PAR_ID        = "SELECT id, nom FROM gnw_matiere WHERE id = ? AND date_suppr IS NULL ORDER BY id ASC";
	private static final String SQL_INSERT               = "INSERT INTO gnw_matiere (nom) VALUES (?)";
	private static final String SQL_UPDATE               = "UPDATE gnw_matiere SET nom = ?, date_modif = now() WHERE id = ?"; 
	private static final String SQL_UPDATE_SUPPR         = "UPDATE gnw_matiere SET date_suppr = now() WHERE id = ?";
	
	/**
	 * Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	MatiereDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
    
	/**
     * Ajoute une matière dans la base de données
     * 
     * @param matiere
     * @throws DAOException
     */
	@Override 
	public void creer(Matiere matiere) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, matiere.getNom());
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
     * Recherche une ou des matière(s) dans la base de données
     * 
     * @param matiere
     * @return listeMatiere
     * @throws DAOException
     */
	@Override
	public Set<Matiere> rechercher(Matiere matiere) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Set<Matiere> listeMatiere = new TreeSet<Matiere>();
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (matiere.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_matiere.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_matiere.id IS NOT ?";	
			}
			
			if (matiere.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_matiere.nom LIKE ?";
				matiere.setNom("%" + matiere.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_matiere.nom IS NOT ?";	
			}
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, matiere.getId(), matiere.getNom());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				matiere = map(resultSet);
				listeMatiere.add(matiere);
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
		
		return listeMatiere;
	}
	
	/**
	 * Renvoie le nombre de matière de la base de données
	 * 
	 * @return nbMatiere
	 * @throws DAOException
	 */
	@Override
	public int compterTous() throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int nbMatiere;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_TOUS, true);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			nbMatiere = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return nbMatiere;
	}

	/**
	 * Edite une matiere dans la base de données
	 * 
	 * @param matiere
	 * @return matiere
	 * @throws DAOException
	 */
	public Matiere editer(Matiere matiere)  throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, matiere.getNom(), matiere.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException (e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return matiere;
	}
	
	/**
	 * Verifie l'existance d'une matière dans la base de données
	 * 
	 * @param matiere
	 * @return statut
	 */
	public int verifExistance(Matiere matiere) throws DAOException 
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_COUNT_PAR_NOM;
		int statut;
		
		try 
		{
			
			connexion = daoFactory.getConnection();
			
			if (matiere.getId() == null) 
			{
				sqlSelectRecherche += " AND id IS NOT ?";
			}
			else
			{
				sqlSelectRecherche += " AND id != ?";
			}

			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, matiere.getNom(), matiere.getId());
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
	 * Cherche une matiere dans la base de données
	 * 
	 * @param matiere
	 * @return matiere
	 * @throws DAOException
	 */
	public Matiere trouver(Matiere matiere) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, true, matiere.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) 
			{
				matiere = map(resultSet);
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
		return matiere;
	}
	
	/**
	 * Supprime une matiere dans la base de données
	 * 
	 * @param matiere
	 * @return statut
	 * @throws DAOException
	 */
	public int supprimer(Matiere matiere) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_SUPPR, true, matiere.getId());
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
	 * Transfère les données du resultSet vers un objet
	 * 
	 * @param resultSet
	 * @return matiere
	 * @throws SQLException
	 */
	private static Matiere map(ResultSet resultSet) throws SQLException 
	{
		Matiere matiere = new Matiere();
		
		matiere.setId(resultSet.getLong("id"));
		matiere.setNom(resultSet.getString("nom"));
		
		return matiere;
	}
}
