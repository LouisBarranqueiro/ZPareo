package dao;

import java.util.Set;

import beans.Examen;

public interface ExamenDao 
{
	/**
     * Ajoute un examen dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	void creer(Examen examen) throws DAOException;
	
	/**
     * Recherche une ou des examen(s) dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	Set<Examen> rechercher(Examen examen) throws DAOException;
	
	/**
	 * Edite un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	Examen editer(Examen examen) throws DAOException;
	
	/**
	 * Trouve un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	Examen trouver(Examen examen) throws DAOException;

	/**
	 * Supprime un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	int supprimer(Examen examen) throws DAOException;
}
