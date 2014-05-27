package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtilitaire 
{

	/**
	 * Ferme un resultSet
	 * 
	 * @param resultSet
	 */
	public static void fermetureSilencieuse(ResultSet resultSet) 
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
	 * Ferme un statement
	 * 
	 * @param statement
	 */
	public static void fermetureSilencieuse(Statement statement) 
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
	 * Ferme la connexion
	 * 
	 * @param connexion
	 */
	public static void fermetureSilencieuse(Connection connexion) 
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
	 * Ferme un statement et une connexion
	 * 
	 * @param statement
	 * @param connexion
	 */
	public static void fermeturesSilencieuses(Statement statement, Connection connexion) 
	{
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}
	
	/**
	 *  Ferme un resultSet, un statement et une connexion
	 *  
	 * @param resultSet
	 * @param statement
	 * @param connexion
	 */
	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) 
	{
		fermetureSilencieuse(resultSet);
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}
}
