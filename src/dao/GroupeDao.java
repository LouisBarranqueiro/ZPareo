package dao;

import java.util.Set;

import beans.Administrateur;
import beans.Groupe;

public interface GroupeDao 
{
	/**
     * Ajoute un groupe dans la base de donn�es
     * 
     * @param groupe
     * @throws DAOException
     */
	void creer(Administrateur createur, Groupe groupe) throws DAOException;
	
	/**
     * Recherche une ou des groupe(s) dans la base de donn�es
     * 
     * @param groupe
     * @throws DAOException
     */
	Set<Groupe> rechercher(Groupe groupe) throws DAOException;
	
	/**
	 * Compte le nombre de groupes de la base de donn�es
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * Edite une matiere dans la base de donn�es
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	Groupe editer(Administrateur editeur, Groupe groupe) throws DAOException;
	
	/**
	 * V�rifie l'existance d'un groupe dans la base de donn�es
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	int verifExistance(Groupe groupe) throws DAOException;

	/**
	 * Cherche un groupe dans la base de donn�es
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	Groupe trouver(Groupe groupe) throws DAOException;
	
	/**
	 * Supprime un groupe dans la base de donn�es
	 * 
	 * @param groupe
	 * @return
	 */
	int supprimer(Administrateur editeur, Groupe groupe) throws DAOException;
}
