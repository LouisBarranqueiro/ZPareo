package dao;

import java.util.Set;
import beans.Etudiant;

public interface EtudiantDao 
{
	/**
     * Ajoute un étudiant dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	void creer(Etudiant etudiant) throws DAOException;
	
	/**
     * Recherche une ou des étudiant(s) dans la base de données
     * 
     * @param etudiant
     * @return Set<Etudiant>
     * @throws DAOException
     */
	Set<Etudiant> rechercher(Etudiant etudiant) throws DAOException;
	
	/**
	 * Edite les informations d'un étudiant dans la base de données
	 * 
	 * @param etudiant
	 */
	void editer(Etudiant etudiant);
	
	/**
	 * Edite le mot de passe d'un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	void editerMotDePasse(Etudiant etudiant) throws DAOException;
	
	/**
	 * Trouve un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return Etudiant
	 * @throws DAOException
	 */
	Etudiant trouver(Etudiant etudiant) throws DAOException;

	/**
	 * Vérifie l'éxistance d'un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return int
	 * @throws DAOException
	 */
	int verifExistance(Etudiant etudiant) throws DAOException;

	/**
	 * Supprime un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	void supprimer(Etudiant etudiant) throws DAOException;
	
	/**
	 * Vérifie les identifiants d'un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return Etudiant
	 * @throws DAOException
	 */
	Etudiant verifIdentifiant(Etudiant etudiant) throws DAOException;

	/**
	 * Récupère toutes les informations et le bulletin de l'étudiant
	 * 
	 * @param etudiant
	 * @return Etudiant
	 * @throws DAOException
	 */
	Etudiant recupTout(Etudiant etudiant) throws DAOException;
}
