package dao;

import java.util.Set;
import java.util.TreeSet;

import models.Etudiant;
import models.Groupe;

public interface GroupeDao 
{
	/**
     * Ajoute un groupe dans la base de données
     * 
     * @param groupe
     * @throws DAOException
     */
	void creer(Groupe groupe) throws DAOException;
	
	/**
     * Recherche une ou des groupe(s) dans la base de données
     * 
     * @param groupe
     * @throws DAOException
     */
	Set<Groupe> rechercher(Groupe groupe) throws DAOException;
	
	/**
	 * Compte le nombre de groupes de la base de données
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * Edite une matiere dans la base de données
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	Groupe editer(Groupe groupe) throws DAOException;
	
	/**
	 * Vérifie l'existance d'un groupe dans la base de données
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	int verifExistance(Groupe groupe) throws DAOException;

	/**
	 * Cherche un groupe dans la base de données
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	Groupe trouver(Groupe groupe) throws DAOException;
	
	/**
	 * Supprime un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return
	 */
	int supprimer(Groupe groupe) throws DAOException;
}
