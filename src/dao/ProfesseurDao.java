package dao;

import java.util.Set;
import beans.Professeur;


public interface ProfesseurDao 
{
	/**
     * Ajoute un professeur dans la base de données
     * 
     * @param professeur
     */
	void creer(Professeur professeur);
	
	/**
     * Recherche un ou des professeur(s) dans la base de données
     * 
     * @param professeu
     * @return Set<Professeur>
     * @throws DAOException
     */
	Set<Professeur> rechercher(Professeur professeur) throws DAOException;
	
	/**
	 * Edite un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	void editer(Professeur professeur) throws DAOException;
	
	/**
	 * Vérifie l'existance d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return int
	 * @throws DAOException
	 */
	int verifExistance(Professeur professeur) throws DAOException;

	/**
	 * Cherche un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return Professeur
	 * @throws DAOException
	 */
	Professeur trouver(Professeur professeur) throws DAOException;
	
	/**
	 * Supprime un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	void supprimer(Professeur professeur) throws DAOException;
	
	/**
	 * Vérifie les identifiants d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @return Professeur
	 * @throws DAOException
	 */
	Professeur verifIdentifiant(Professeur professeur) throws DAOException;
}
