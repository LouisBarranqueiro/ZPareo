package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;
import beans.Bulletin;
import beans.Etudiant;
import beans.Examen;
import beans.FormatExamen;
import beans.Groupe;
import beans.Matiere;
import beans.MatiereNote;
import beans.Note;
import beans.Professeur;

public class ExamenDaoImpl implements ExamenDao 
{
	private static final String SQL_INSERT_NOTE                 = "INSERT INTO gnw_examen_note(fk_examen, fk_etudiant, note, fk_utilisateur) VALUES (?, ?, ?, ?)";
    private static final String SQL_INSERT_EXAMEN               = "INSERT INTO gnw_examen(fk_format, nom, date, coefficient, fk_professeur, fk_groupe, fk_matiere, fk_utilisateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_TOUS                 = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen WHERE gnw_examen.date_suppr Is NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_professeur = ?";
	private static final String SQL_SELECT_EXAMEN_PAR_ID        = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen WHERE gnw_examen.date_suppr IS NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.id = ?";
	private static final String SQL_SELECT_NOTE                 = "SELECT id, note FROM gnw_examen_note WHERE fk_examen = ? AND fk_etudiant = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_COUNT_NOTE           = "SELECT COUNT(id) FROM gnw_examen_note WHERE fk_examen = ? AND fk_etudiant = ? AND date_suppr IS NULL";
	private static final String SQL_SELECT_ETUDIANTS_PAR_GROUPE = "SELECT gnw_utilisateur.id, gnw_utilisateur.nom, gnw_utilisateur.prenom, gnw_utilisateur.adresse_mail FROM gnw_utilisateur, gnw_etudiant_groupe WHERE profil = 0 AND gnw_utilisateur.date_suppr IS NULL AND gnw_utilisateur.id = gnw_etudiant_groupe.fk_etudiant AND gnw_etudiant_groupe.fk_groupe = ?";
	private static final String SQL_EXAMEN_MATIERE_GROUPE       = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.fk_professeur, gnw_examen.fk_format as formatId, gnw_formatexamen.nom as formatNom, gnw_groupe.id as groupeId, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, AVG(gnw_examen_note.note) as moyenne, gnw_examen.coefficient FROM gnw_examen, gnw_matiere, gnw_groupe, gnw_formatexamen, gnw_examen_note WHERE gnw_examen.date_suppr IS NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_format = gnw_formatexamen.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.fk_matiere = ? AND gnw_examen.fk_groupe = ? GROUP BY gnw_examen.id";
	private static final String SQL_MATIERES_ETUDIANT           = "SELECT DISTINCT gnw_examen.fk_matiere FROM gnw_examen, gnw_examen_note WHERE gnw_examen.date_suppr IS NULL AND gnw_examen_note.date_suppr IS NULL AND gnw_examen.id = gnw_examen_note.fk_examen AND gnw_examen_note.fk_etudiant = ?";
	private static final String SQL_MOYENNE_MATIERE             = "SELECT SUM(gnw_examen_note.note * gnw_examen.coefficient) / SUM(gnw_examen.coefficient) as moyenne FROM gnw_examen, gnw_examen_note WHERE gnw_examen_note.fk_etudiant = ? AND gnw_examen.fk_matiere = ? AND gnw_examen_note.fk_examen = gnw_examen.id";
	private static final String SQL_UPDATE_NOTE                 = "UPDATE gnw_examen_note SET note = ?, fk_utilisateur = ? WHERE fk_examen = ? AND fk_etudiant = ?";
	private static final String SQL_UPDATE_EXAMEN               = "UPDATE gnw_examen SET fk_professeur = ?, fk_format = ?, nom = ?, date = ?, coefficient = ?, fk_matiere = ?, fk_utilisateur = ? WHERE id = ?";
	private static final String SQL_DELETE_EXAMEN               = "UPDATE gnw_examen SET date_suppr = now(), fk_utilisateur = ? WHERE id = ? AND fk_professeur = ?";
	private static final String SQL_DELETE_NOTES                = "UPDATE gnw_examen_note SET date_suppr = now(), fk_utilisateur = ? WHERE fk_examen = ?;";
	private static final String SQL_SELECT_MOY_EXAMEN           = "SELECT AVG(gnw_examen_note.note) as moyenne FROM gnw_examen_note WHERE fk_examen = ?";
	private DAOFactory daoFactory;
	
	/**
	 * Récupère la daoFactory
	 * 
	 * @param daoFactory
	 */
	ExamenDaoImpl(DAOFactory daoFactory) 
	{
        this.daoFactory = daoFactory;
    }
	
	/**
     * Ajoute un examen dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	public void ajouter(Examen examen)
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Groupe groupe = new Groupe(examen.getGroupe());
		Matiere matiere = new Matiere(examen.getMatiere());
		Professeur professeur = new Professeur(examen.getProfesseur());
		FormatExamen format = new FormatExamen(examen.getFormat());
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
		
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_EXAMEN, true, format.getId(), examen.getNom(), examen.getDate(), examen.getCoefficient(), professeur.getId(), groupe.getId(), matiere.getId(), professeur.getId());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) examen.setId(resultSet.getLong(1));

		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Ajoute une note à un étudiant dans la base de données
	 * 
	 * @param examen
	 * @param note
	 * @throws DAOException
	 */
	private void ajouterNote(Examen examen, Note note) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Etudiant etudiant = new Etudiant(note.getEtudiant());
		Professeur professeur = new Professeur(examen.getProfesseur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_NOTE, true, examen.getId(), etudiant.getId(), note.getNote(), professeur.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Récupère un examen et ses notes dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public Examen trouver(Examen examen)
	{
		examen = recupExamen(examen);
		recupListeEtudiants(examen);
		verifListeNotes(examen);
		recupListeNotes(examen);
		recupMoyenne(examen);
		
		return examen;
	}
	
	/**
	 * Récupère la liste des étudiants d'un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	private void recupListeEtudiants(Examen examen) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Etudiant etudiant = new Etudiant();
		Groupe groupe = new Groupe(examen.getGroupe());
		Set<Note> listeNotes = new TreeSet<Note>();
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ETUDIANTS_PAR_GROUPE, true, groupe.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Note note = new Note();
				etudiant = mapEtudiant(resultSet);
				note.setEtudiant(etudiant);
				listeNotes.add(note);
			}
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		examen.setListeNotes(listeNotes);
	}
	
	/**
	 * Récupère la liste des notes d'un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	private void recupListeNotes(Examen examen) throws DAOException
	{
		Set<Note> listeNotes = new TreeSet<Note>(examen.getListeNotes());
		Object[] notes = listeNotes.toArray();
		
		for (Object n : notes)
		{
			Note note = (Note) n;
			recupNote(examen, note);
			listeNotes.add(note);
		}
		
		examen.setListeNotes(listeNotes);
	}
	
	/**
	 * Récupère la moyenne d'un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	private void recupMoyenne(Examen examen) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_MOY_EXAMEN, true, examen.getId());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) examen.setMoyenne(resultSet.getFloat("moyenne"));
			
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Récupère une note d'un étudiant dans la base de données
	 * 
	 * @param examen
	 * @param note
	 * @throws DAOException
	 */
	private void recupNote(Examen examen, Note note) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Etudiant etudiant = new Etudiant(note.getEtudiant());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_NOTE, true, examen.getId(), etudiant.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				note.setId(resultSet.getLong("id"));
				note.setNote(resultSet.getFloat("note"));
			}
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Récupère une note d'un étudiant dans la base de données
	 * 
	 * @param examen
	 * @param etudiant
	 * @throws DAOException
	 */
	private Examen recupNote(Examen examen, Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Set<Note> listeNotes = new TreeSet<Note>();
		Note note = new Note(etudiant);
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_NOTE, true, examen.getId(), etudiant.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				note.setId(resultSet.getLong("id"));
				note.setNote(resultSet.getFloat("note"));
				listeNotes.add(note);
				examen.setListeNotes(listeNotes);
			}
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return examen;
	}

	/**
	 * Récupère un examen dans la base de données
	 * 
	 * @param examen
	 * @return examen
	 * @throws DAOException
	 */
	private Examen recupExamen(Examen examen) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_EXAMEN_PAR_ID, true, examen.getId());
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) examen = mapExamen(resultSet);
	        
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return examen;
	}
	
	/* Récupère la liste d'examen d'un étudiant dans une matière dans la base de données
	 * 
	 * @param matiereNote
	 * @param etudaint
	 * @return matiereNote
	 * @throws DAOException
	 */
	private MatiereNote recupListeExamen(MatiereNote matiereNote, Etudiant etudiant)
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Matiere matiere = new Matiere(matiereNote.getMatiere());
		Groupe groupe = new Groupe(etudiant.getGroupe());
		Set<Examen> listeExamens = new TreeSet<Examen>();
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_EXAMEN_MATIERE_GROUPE, true, matiere.getId(), groupe.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				Examen examen = new Examen();
				examen = mapExamen(resultSet);
				examen = recupNote(examen, etudiant);
				listeExamens.add(examen);
			}
			
			matiereNote.setListeExamens(listeExamens);

		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return matiereNote;
	}
	
	/**
	 * Récupère la liste des informations de chaque matières d'un étudiant dans la base de données
	 * 
	 * @param etudiant
	 * @return listeMatiereNotes
	 * @throws DAOException
	 */
	public Set<MatiereNote> recupNotesMatieres(Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Set<MatiereNote> listeMatiereNotes = new TreeSet<MatiereNote>();
		MatiereDaoImpl matiereDao = new MatiereDaoImpl(daoFactory);
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_MATIERES_ETUDIANT, true, etudiant.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
			{
				MatiereNote matiereNote = new MatiereNote();
				matiereNote.setId(resultSet.getLong("fk_matiere"));
				Matiere matiere = new Matiere(matiereNote.getId());
				matiere = matiereDao.trouver(matiere);
				matiereNote.setMatiere(matiere);
				matiereNote = recupMoyenneMatiere(matiereNote, etudiant);
				matiereNote = recupListeExamen(matiereNote, etudiant);
				listeMatiereNotes.add(matiereNote);
			}

		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return listeMatiereNotes;
	}
	
	/**
	 * Récupère la moyenne d'un étudiant dans une matière
	 * 
	 * @param matiereNote
	 * @param etudiant
	 * @return matiereNote
	 * @throws DAOException
	 */
	public MatiereNote recupMoyenneMatiere(MatiereNote matiereNote, Etudiant etudiant) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_MOYENNE_MATIERE, true, etudiant.getId(), matiereNote.getId());
			resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
			matiereNote.setMoyenne(resultSet.getFloat("moyenne"));

		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		return matiereNote;
	}
	
	/**
	 * Retourne le buletin d'un étudiant
	 * 
	 * @param etudiant
	 * @return bulletin
	 */
	public Bulletin recupBulletin(Etudiant etudiant)
	{
		Bulletin bulletin = new Bulletin();
		
		bulletin.setListeMatiereNote(recupNotesMatieres(etudiant));
		
		return bulletin;
	}
	
	/**
     * Recherche un ou des examen(s) dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	public Set<Examen> rechercher(Examen examen) throws DAOException
	{
		Set<Examen> listeExamens = new TreeSet<Examen>();
		Groupe groupe = new Groupe(examen.getGroupe());
		Matiere matiere = new Matiere(examen.getMatiere());
		Professeur professeur = new Professeur(examen.getProfesseur());
		FormatExamen format = new FormatExamen(examen.getFormat());
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try
		{	
			connexion = daoFactory.getConnection();
			
			if (examen.getId() != null) sqlSelectRecherche += " AND gnw_examen.id = ?";
			else sqlSelectRecherche += " AND gnw_examen.id IS NOT ?";	
			
			
			if (examen.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_examen.nom LIKE ?";
				examen.setNom("%" + examen.getNom() + "%");
			}
			else sqlSelectRecherche += " AND gnw_examen.nom IS NOT ?";	
			
			
			if (examen.getDate() != null) sqlSelectRecherche += " AND gnw_examen.date = ?";
			else sqlSelectRecherche += " AND gnw_examen.date IS NOT ?";	
			
			
			if (format.getId() != null) sqlSelectRecherche += " AND gnw_examen.fk_format = ?";
			else sqlSelectRecherche += " AND gnw_examen.fk_format IS NOT ?";	
			
			
			if (groupe.getId() != null) sqlSelectRecherche += " AND gnw_examen.fk_groupe = ?";
			else sqlSelectRecherche += " AND gnw_examen.fk_groupe IS NOT ?";	
			
			
			if (matiere.getId() != null) sqlSelectRecherche += " AND gnw_examen.fk_matiere = ?";
			else sqlSelectRecherche += " AND gnw_examen.fk_matiere IS NOT ?";	
			
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, professeur.getId(), examen.getId(), examen.getNom(), examen.getDate(), format.getId(), groupe.getId(), matiere.getId());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				examen = mapExamen(resultSet);
				recupMoyenne(examen);
				listeExamens.add(examen);
	        }
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
		return listeExamens;
	}
	
	/**
	 * Edite un examen dans la base de données
	 * 
	 * @param examen
	 * @return examen
	 * @throws DAOException
	 */
	public Examen editer(Examen examen)
	{
		editerExamen(examen);
		editerListeNotes(examen);
		
		return examen;
	}
	
	/**
	 *  Edite les informations générales d'un examen dans la base de données
	 *  
	 * @param examen
	 * @throws DAOException
	 */
	private void editerExamen(Examen examen) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Professeur professeur = new Professeur(examen.getProfesseur());
		FormatExamen format = new FormatExamen(examen.getFormat());
		Matiere matiere = new Matiere(examen.getMatiere());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_EXAMEN, true, professeur.getId(), format.getId(), examen.getNom(), examen.getDate(), examen.getCoefficient(), matiere.getId(), professeur.getId(), examen.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Edite la liste des notes d'un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	private void editerListeNotes(Examen examen) throws DAOException
	{
		Object[] notes = examen.getListeNotes().toArray();
		
		for (Object n : notes)
		{
			Note note = (Note) n;
			editerNote(examen, note);
		}
	}

	/**
	 *  Met à jour la liste des notes dans la base de données
	 *  
	 * @param examen
	 */
	private void verifListeNotes(Examen examen) 
	{
		Object[] notes = examen.getListeNotes().toArray();
		
		for (Object n : notes)
		{
			Note note = (Note) n;
			
			if (verifierNote(examen, note))
			{
				ajouterNote(examen, note);
			}
		}
	}
	
	/**
	 * Vérifie l'existance d'une note dans la base de données
	 * 
	 * @param examen
	 * @param note
	 * @return boolean
	 * @throws DAOException
	 */
	private boolean verifierNote(Examen examen, Note note) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Etudiant etudiant = new Etudiant(note.getEtudiant());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COUNT_NOTE, true, examen.getId(), etudiant.getId());
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			if (resultSet.getInt(1) == 1) return false;
			
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
		return true;
	}
	
	/**
	 * Edite la note d'un étudiant dans la base de données
	 * 
	 * @param examen
	 * @param note
	 * @throws DAOException
	 */
	private void editerNote(Examen examen, Note note) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Etudiant etudiant = new Etudiant(note.getEtudiant());
		Professeur professeur = new Professeur(examen.getProfesseur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_NOTE, true, note.getNote(), professeur.getId(), examen.getId(), etudiant.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Supprime un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public void supprimer(Examen examen) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Professeur professeur = new Professeur(examen.getProfesseur());
		
		try 
		{
			connexion = daoFactory.getConnection();
			
			// Suppression de l'examen
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_EXAMEN, true, professeur.getId(), examen.getId(), professeur.getId());
			preparedStatement.executeUpdate();
			
			// Supprssion des notes de l'examen
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_NOTES, true, professeur.getId(), examen.getId());
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new DAOException(e);
		} 
		finally 
		{
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	/**
	 * Prépare une requête SQL sur mesure
	 * 
	 * @param connexion
	 * @param sql
	 * @param returnGeneratedKeys
	 * @param objets
	 * @return preparedStatement
	 * @throws SQLException
	 */
	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException 
	{
		PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ?Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		
		for (int i = 0; i < objets.length; i++) 
		{
			preparedStatement.setObject(i + 1, objets[i]);
		}
		
		return preparedStatement;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet Examen
	 * 
	 * @param resultSet
	 * @return examen
	 * @throws SQLException
	 */
	private static Examen mapExamen(ResultSet resultSet) throws SQLException 
	{
		Examen examen = new Examen();
		Matiere matiere = new Matiere();
		Groupe groupe = new Groupe();
		FormatExamen format = new FormatExamen();
		Professeur professeur = new Professeur();
		
		examen.setId(resultSet.getLong("id"));
		examen.setNom(resultSet.getString("nom"));
		examen.setCoefficient(resultSet.getFloat("coefficient"));
		examen.setDate(convertirDateToString(resultSet.getDate("date")));
		professeur.setId(resultSet.getLong("fk_professeur"));
		examen.setProfesseur(professeur);
		format.setId(resultSet.getLong("formatId"));
		format.setNom(resultSet.getString("formatNom"));
		examen.setFormat(format);
		groupe.setId(resultSet.getLong("groupeId"));
		groupe.setNom(resultSet.getString("groupeNom"));
		matiere.setNom(resultSet.getString("matiereNom"));
		examen.setGroupe(groupe);
		examen.setMatiere(matiere);
		
		return examen;
	}
	
	/**
	 * Transfère les données du resultSet vers un objet
	 * 
	 * @param resultSet
	 * @return etudiant
	 * @throws SQLException
	 */
	private static Etudiant mapEtudiant(ResultSet resultSet) throws SQLException 
	{
		Etudiant etudiant = new Etudiant();
		
		etudiant.setId(resultSet.getLong("id"));
		etudiant.setNom(resultSet.getString("nom"));
		etudiant.setPrenom(resultSet.getString("prenom"));
		etudiant.setAdresseMail(resultSet.getString("adresse_mail"));
		
		return etudiant;
	}
	
	/**
	 * Converti une Date en chaine de caractères
	 * 
	 * @param date
	 * @return 
	 */
	private static String convertirDateToString(java.sql.Date date)
	{
		DateFormat APP_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		
		return APP_FORMAT.format(date);
	}
}
