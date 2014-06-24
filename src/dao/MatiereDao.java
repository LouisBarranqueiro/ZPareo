package dao;

import java.util.Set;
import beans.Matiere;

public interface MatiereDao 
{
	/**
     * Ajoute une matière dans la base de données
     * 
     * @param matiere
     * @throws DAOException
     */
	void ajouter(Matiere matiere) throws DAOException;
	
	/**
     * Recherche une ou des mati�re(s) dans la base de données
     * 
     * @param matiere
     * @throws DAOException
     */
	Set<Matiere> rechercher(Matiere matiere) throws DAOException;
	
	/**
	 * Edite une matiere dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	void editer(Matiere matiere) throws DAOException;
	
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
	 * Supprime une matière dans la base de données
	 * 
	 * @param matiere
	 * @throws DAOException
	 */
	void supprimer(Matiere matiere) throws DAOException;	
}
