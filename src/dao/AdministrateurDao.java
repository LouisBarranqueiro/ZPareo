package dao;

import java.util.Set;

import beans.Administrateur;

public interface AdministrateurDao
{
	/**
     * Ajoute un administrateur dans la base de données
     * 
     * @param administrateur
     * @throws DAOException
     */
	void creer(Administrateur administrateur) throws DAOException;
	
	/**
     * Recherche une ou des administrateur(s) dans la base de données
     * 
     * @param administrateur
     * @throws DAOException
     */
	Set<Administrateur> rechercher(Administrateur administrateur) throws DAOException;
	
	/**
	 * Edite un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur editer(Administrateur administrateur) throws DAOException;
	
	/**
	 * Trouve un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur trouver(Administrateur administrateur) throws DAOException;

	/**
	 * Vérifie l'existance d'un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	int verifExistance(Administrateur administrateur) throws DAOException;

	/**
	 * Supprime un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	void supprimer(Administrateur administrateur) throws DAOException;
	
	/**
	 * Vérifie les identifiants d'un administrateur dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur verifIdentifiant(Administrateur administrateur) throws DAOException;
}
