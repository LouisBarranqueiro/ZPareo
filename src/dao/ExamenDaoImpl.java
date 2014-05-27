package dao;

import java.util.HashSet;
import java.util.Set;

import models.Examen;

public class ExamenDaoImpl implements ExamenDao 
{

	private DAOFactory daoFactory;
	
	/**
	 * R�Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	ExamenDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
	
	/**
     * Ajoute un examen dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	public void creer(Examen examen) throws DAOException
	{
		
	}
	
	/**
     * Recherche une ou des examen(s) dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	public Set<Examen> rechercher(Examen examen) throws DAOException
	{
		return new HashSet<Examen>();
	}
	
	/**
	 * Edite un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public Examen editer(Examen examen) throws DAOException
	{
		return new Examen();
	}
	
	/**
	 * Trouve un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public Examen trouver(Examen examen) throws DAOException
	{
		return new Examen();
	}

	/**
	 * Compte le nombre d'examens de la base de données
	 * 
	 * @throws DAOException
	 */
	public int compterTous() throws DAOException
	{
		return 1;
	}
	
	/**
	 * Supprime un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public int supprimer(Examen examen) throws DAOException
	{
		return 1;
	}
}
