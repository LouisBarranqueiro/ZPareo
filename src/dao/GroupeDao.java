package dao;

import java.util.Set;
import beans.Groupe;

public interface GroupeDao 
{
	/**
     * Ajoute un groupe dans la base de données
     * 
     * @param groupe
     * @throws DAOException
     */
	void ajouter(Groupe groupe) throws DAOException;
	
	/**
     * Recherche une ou des groupe(s) dans la base de données
     * 
     * @param groupe
     * @return Set<Groupe> 
     * @throws DAOException
     */
	Set<Groupe> rechercher(Groupe groupe) throws DAOException;
	
	/**
	 * Edite une matiere dans la base de données
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	void editer(Groupe groupe) throws DAOException;
	
	/**
	 * Vérifie l'existance d'un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return int
	 * @throws DAOException
	 */
	int verifExistance(Groupe groupe) throws DAOException;

	/**
	 * Cherche un groupe dans la base de données
	 * 
	 * @param groupe
	 * @return Groupe
	 * @throws DAOException
	 */
	Groupe trouver(Groupe groupe) throws DAOException;
	
	/**
	 * Supprime un groupe dans la base de données
	 * 
	 * @param groupe
	 * @throws DAOException
	 */
	void supprimer(Groupe groupe) throws DAOException;
}
