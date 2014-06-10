package dao;

import java.util.Set;

import beans.Administrateur;
import beans.Professeur;


public interface ProfesseurDao 
{
	/**
     * Ajoute un professeur dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	void creer(Administrateur createur, Professeur professeur) throws DAOException;
	
	/**
     * Recherche un ou des professeur(s) dans la base de données
     * 
     * @param professeur
     * @throws DAOException
     */
	Set<Professeur> rechercher(Professeur professeur) throws DAOException;
	
	/**
	 * Compte le nombre de professeurs de la base de données
	 * 
	 * @throws DAOException
	 */
	int compterTous() throws DAOException;
	
	/**
	 * Edite un professeur dans la base de donn�es
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	Professeur editer(Administrateur editeur, Professeur professeur) throws DAOException;
	
	/**
	 * Vérifie l'existance d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	int verifExistance(Professeur professeur) throws DAOException;

	/**
	 * Cherche un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	Professeur trouver(Professeur professeur) throws DAOException;
	
	/**
	 * Supprime un professeur dans la base de données
	 * 
	 * @param groupe
	 * @return
	 */
	int supprimer(Administrateur editeur, Professeur professeur);
	
	/**
	 * Vérifie les identifiants d'un professeur dans la base de données
	 * 
	 * @param professeur
	 * @throws DAOException
	 */
	Professeur verifIdentifiant(Professeur professeur) throws DAOException;
}
