package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DAOUtility.*;

import java.util.Set;
import java.util.TreeSet;

import beans.Administrator;
import beans.Matter;

public class MatterDaoImpl implements MatterDao 
{
	private DAOFactory daoFactory;
	private static final String SELECT_COUNT_BY_NAME = "SELECT COUNT(id) FROM gnw_matiere WHERE date_suppr IS NULL AND nom = ?";
	private static final String SELECT_ALL           = "SELECT id, nom FROM gnw_matiere WHERE date_suppr IS NULL";
	private static final String SELECT_BY_ID         = "SELECT id, nom FROM gnw_matiere WHERE id = ? AND date_suppr IS NULL ORDER BY id ASC";
	private static final String INSERT               = "INSERT INTO gnw_matiere (nom, fk_utilisateur) VALUES (?, ?)";
	private static final String UPDATE               = "UPDATE gnw_matiere SET nom = ?, fk_utilisateur = ? WHERE id = ?"; 
	private static final String DELETE               = "UPDATE gnw_matiere SET date_suppr = now(), fk_utilisateur = ? WHERE id = ?";
	
	/**
	 * Returns daoFactory
	 * 
	 * @param daoFactory
	 */
	MatterDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
    
	/**
     * Creates a matter into database
     * 
     * @param matter
     * @throws DAOException
     */
	@Override 
	public void create(Matter matter) throws DAOException 
	{
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		Administrator creator               = new Administrator(matter.getCreator());
		
		try 
		{
			connexion         = daoFactory.getConnection();
			preparedStatement = initPreparedQuery(connexion, INSERT, true, matter.getName(), creator.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			silentClosures(preparedStatement, connexion);
		}
	}
	
    /**
     * Searches one or more matter into database
     * 
     * @param matter
     * @return matters
     * @throws DAOException
     */
	@Override
	public Set<Matter> search(Matter matter) throws DAOException 
	{
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet                 = null;
		Set<Matter> matters                 = new TreeSet<Matter>();
		String SQLQuery                     = SELECT_ALL;
		
		try 
		{	
			connexion = daoFactory.getConnection();
			
			if (matter.getId() != null) SQLQuery += " AND gnw_matiere.id = ?";
			else SQLQuery += " AND gnw_matiere.id IS NOT ?";	
			
			
			if (matter.getName() != null)
			{
				SQLQuery += " AND gnw_matiere.nom LIKE ?";
				matter.setName("%" + matter.getName() + "%");
			}
			else SQLQuery += " AND gnw_matiere.nom IS NOT ?";	
			
			preparedStatement = initPreparedQuery(connexion, SQLQuery, true, matter.getId(), matter.getName());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				matter = map(resultSet);
				matters.add(matter);
	        }
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			silentClosures(resultSet, preparedStatement, connexion);
		}
		
		return matters;
	}
	
	/**
	 * Edits a matter into database
	 * 
	 * @param matter
	 * @throws DAOException
	 */
	public void edit(Matter matter)  throws DAOException
	{
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		Administrator editor                = new Administrator(matter.getEditor());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedQuery(connexion, UPDATE, true, matter.getName(), editor.getId(), matter.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException (e);
		} 
		finally 
		{
			silentClosures(preparedStatement, connexion);
		}
	}
	
	/**
	 * Checks the existance of a matter into database
	 * 
	 * @param matter
	 * @return status
	 */
	public int check(Matter matter) throws DAOException 
	{
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet                 = null;
		String SQLQuery                     = SELECT_COUNT_BY_NAME;
		int status                          = 0;
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			if (matter.getId() == null)  SQLQuery += " AND id IS NOT ?";
			else SQLQuery += " AND id != ?";
		
			preparedStatement = initPreparedQuery(connexion, SQLQuery, true, matter.getName(), matter.getId());
			resultSet         = preparedStatement.executeQuery();
			resultSet.next();
			status = resultSet.getInt(1);
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			silentClosures(preparedStatement, connexion);
		}
		
		return status;
	}
	
	/**
	 * Returns a matter into database
	 * 
	 * @param matter
	 * @return matter
	 * @throws DAOException
	 */
	public Matter get(Matter matter) throws DAOException
	{ 
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet                 = null;
		
		try 
		{
			connexion         = daoFactory.getConnection();
			preparedStatement = initPreparedQuery(connexion, SELECT_BY_ID, true, matter.getId());
			resultSet         = preparedStatement.executeQuery();
			
			if (resultSet.next()) matter = map(resultSet);
	        
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			silentClosures(preparedStatement, connexion);
		}
		
		return matter;
	}
	
	/**
	 * Deletes a matter into database
	 * 
	 * @param matter
	 * @return status
	 * @throws DAOException
	 */
	public void delete(Matter matter) throws DAOException
	{
		Connection connexion                = null;
		PreparedStatement preparedStatement = null;
		Administrator editor                = new Administrator(matter.getEditor());
		
		try 
		{
			connexion         = daoFactory.getConnection();
			preparedStatement = initPreparedQuery(connexion, DELETE, true, editor.getId(), matter.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			silentClosures(preparedStatement, connexion);
		}
	}
	
	/**
	 * Prepares a SQL query
	 * 
	 * @param connexion
	 * @param sql
	 * @param returnGeneratedKeys
	 * @param objets
	 * @return preparedStatement
	 * @throws SQLException
	 */
	public static PreparedStatement initPreparedQuery(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException 
	{
		PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ?Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		for (int i = 0; i < objets.length; i++) 
		{
			preparedStatement.setObject(i + 1, objets[i]);
		}
		
		return preparedStatement;
	}

	/**
	 * Maps a Matter
	 * 
	 * @param resultSet
	 * @return matter
	 * @throws SQLException
	 */
	private static Matter map(ResultSet resultSet) throws SQLException 
	{
		Matter matter = new Matter();
		
		matter.setId(resultSet.getLong("id"));
		matter.setName(resultSet.getString("nom"));
		
		return matter;
	}
}
