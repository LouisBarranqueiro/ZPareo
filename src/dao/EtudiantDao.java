package dao;

import java.util.Set;

import beans.Administrateur;
import beans.Etudiant;

public interface EtudiantDao 
{
	/**
     * Ajoute un etudiant dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	void creer(Administrateur utilisateur, Etudiant etudiant) throws DAOException;
	
	/**
     * Recherche une ou des etudiant(s) dans la base de données
     * 
     * @param etudiant
     * @throws DAOException
     */
	Set<Etudiant> rechercher(Etudiant etudiant) throws DAOException;
	
	/**
	 * Edite un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	Etudiant editer(Administrateur utilisateur, Etudiant etudiant) throws DAOException;
	
	/**
	 * Trouve un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	Etudiant trouver(Etudiant etudiant) throws DAOException;

	/**
	 * Vérifie l'existance d'un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	int verifExistance(Etudiant etudiant) throws DAOException;

	/**
	 * Supprime un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	int supprimer(Administrateur utilisateur, Etudiant etudiant) throws DAOException;
	
	/**
	 * Vérifie les identifiants d'un etudiant dans la base de données
	 * 
	 * @param etudiant
	 * @throws DAOException
	 */
	Etudiant verifIdentifiant(Etudiant etudiant) throws DAOException;

	/**
	 * Récupère toutes les informations et le bulletin de l'etudiant
	 * 
	 * @param etudiant
	 * @return Etudiant
	 * @throws DAOException
	 */
	Etudiant recupTout(Etudiant etudiant) throws DAOException;
}
