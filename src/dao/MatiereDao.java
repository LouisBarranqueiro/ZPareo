package dao;

import java.util.Set;

import beans.Administrateur;
import beans.Matiere;

public interface MatiereDao 
{
	/**
     * Ajoute une mati�re dans la base de donn�es
     * 
     * @param matiere
     * @throws DAOException
     */
	void creer(Administrateur createur, Matiere matiere) throws DAOException;
	
	/**
     * Recherche une ou des mati�re(s) dans la base de donn�es
     * 
     * @param matiere
     * @throws DAOException
     */
	Set<Matiere> rechercher(Matiere matiere) throws DAOException;
	
	/**
	 * Compte le nombre de mati�re de la base de donn�es
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * Edite une matiere dans la base de donn�es
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	Matiere editer (Administrateur editeur, Matiere matiere) throws DAOException;
	
	/**
	 * V�rifie l'existance d'une mati�re dans la base de donn�es
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	int verifExistance(Matiere matiere) throws DAOException;
	
	/**
	 * Cherche une matiere dans la base de donn�es
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	Matiere trouver(Matiere matiere) throws DAOException;
	
	/**
	 * Supprime une matiere dans la base de donn�es
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	int supprimer(Administrateur editeur, Matiere matiere) throws DAOException;	
}
