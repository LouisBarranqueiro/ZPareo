package dao;

import java.util.Set;
import java.util.TreeSet;

import models.Groupe;
import models.Matiere;

public interface MatiereDao 
{
	/**
     * Ajoute une matière dans la base de données
     * 
     * @param matiere
     * @throws DAOException
     */
	void creer(Matiere matiere) throws DAOException;
	
	/**
     * Recherche une ou des matière(s) dans la base de données
     * 
     * @param matiere
     * @throws DAOException
     */
	Set<Matiere> rechercher(Matiere matiere) throws DAOException;
	
	/**
	 * Compte le nombre de matière de la base de données
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * Edite une matiere dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	Matiere editer (Matiere matiere) throws DAOException;
	
	/**
	 * Vérifie l'existance d'une matière dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	int verifExistance(Matiere matiere) throws DAOException;
	
	/**
	 * Cherche une matiere dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	Matiere trouver(Matiere matiere) throws DAOException;
	
	/**
	 * Supprime une matiere dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	int supprimer(Matiere matiere) throws DAOException;	
}
