package dao;

import java.util.Set;

import models.Administrateur;
import models.Etudiant;

public interface AdministrateurDao
{
	/**
     * Ajoute un administrateur dans la base de donn�es
     * 
     * @param administrateur
     * @throws DAOException
     */
	void creer(Administrateur administrateur) throws DAOException;
	
	/**
     * Recherche une ou des administrateur(s) dans la base de donn�es
     * 
     * @param administrateur
     * @throws DAOException
     */
	Set<Administrateur> rechercher(Administrateur etudiant) throws DAOException;
	
	/**
	 * Edite un administrateur dans la base de donn�es
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur editer(Administrateur administrateur) throws DAOException;
	
	/**
	 * Trouve un administrateur dans la base de donn�es
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur trouver(Administrateur administrateur) throws DAOException;

	/**
	 * Compte le nombre d'administrateurs de la base de donn�es
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * V�rifie l'existance d'un administrateur dans la base de donn�es
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	int verifExistance(Administrateur administrateur) throws DAOException;

	/**
	 * Supprime un administrateur dans la base de donn�es
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	int supprimer(Administrateur administrateur) throws DAOException;
	
	/**
	 * Vérifie les identifiants d'un etudiant dans la base de données
	 * 
	 * @param administrateur
	 * @throws DAOException
	 */
	Administrateur verifIdentifiant(Administrateur administrateur) throws DAOException;
}
