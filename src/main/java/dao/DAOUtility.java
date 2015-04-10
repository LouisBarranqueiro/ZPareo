package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtility 
{

	/**
	 * Close a resultSet
	 * 
	 * @param resultSet
	 */
	public static void silentClosure(ResultSet resultSet) 
	{
		if (resultSet != null) 
		{
			try 
			{
				resultSet.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}
	
	/**
	 * Close a statement
	 * 
	 * @param statement
	 */
	public static void silentClosure(Statement statement) 
	{
		if (statement != null) 
		{
			try 
			{
				statement.close();
			}
			catch (SQLException e) 
			{
				System.out.println("Échec de la fermeture du Statement: " + e.getMessage() );
			}
		}
	}
	
	/**
	 * Close a connexion
	 * 
	 * @param connexion
	 */
	public static void silentClosure(Connection connexion) 
	{
		if (connexion != null) 
		{
			try 
			{
				connexion.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}
	
	/**
	 * Close a statement and a connexion
	 * 
	 * @param statement
	 * @param connexion
	 */
	public static void silentClosures(Statement statement, Connection connexion) 
	{
		silentClosure(statement);
		silentClosure(connexion);
	}
	
	/**
	 *  Close a resultSet, a statement and a connexion
	 *  
	 * @param resultSet
	 * @param statement
	 * @param connexion
	 */
	public static void silentClosures(ResultSet resultSet, Statement statement, Connection connexion) 
	{
		silentClosure(resultSet);
		silentClosure(statement);
		silentClosure(connexion);
	}
}
