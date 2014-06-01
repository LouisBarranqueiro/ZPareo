package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import beans.Examen;
import beans.Groupe;
import beans.Matiere;
import beans.Professeur;

public class ExamenDaoImpl implements ExamenDao 
{
	private static final String SQL_FORMAT = "yyyy-mm-dd";
	private static final String APP_FORMAT = "dd-mm-yyyy";
	public static final String SQL_SELECT_TOUS = "SELECT gnw_examen.id, gnw_examen.nom, gnw_examen.date, gnw_examen.format, gnw_groupe.nom as groupeNom, gnw_matiere.nom as matiereNom, gnw_examen.moyenne_generale FROM gnw_examen, gnw_matiere, gnw_groupe WHERE gnw_examen.date_suppr Is NULL AND gnw_examen.fk_groupe = gnw_groupe.id AND gnw_examen.fk_matiere = gnw_matiere.id AND gnw_examen.fk_professeur = ?";
	private DAOFactory daoFactory;
	
	/**
	 * R�Récupère la daoFactory
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
	public void creer(Examen examen) throws DAOException
	{
		
	}
	
	/**
     * Recherche un ou des examen(s) dans la base de données
     * 
     * @param examen
     * @throws DAOException
     */
	public TreeSet<Examen> rechercher(Examen examen) throws DAOException
	{
		TreeSet<Examen> listeExamens = new TreeSet<Examen>();
		Groupe groupe = new Groupe(examen.getGroupe());
		Matiere matiere = new Matiere(examen.getMatiere());
		Professeur professeur = new Professeur(examen.getProfesseur());
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sqlSelectRecherche = SQL_SELECT_TOUS;
		
		try
		{	
			connexion = daoFactory.getConnection();
			
			if(examen.getId() != null) 
			{
				sqlSelectRecherche += " AND gnw_examen.id = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.id IS NOT ?";	
			}
			
			if (examen.getNom() != null)
			{
				sqlSelectRecherche += " AND gnw_examen.nom LIKE ?";
				examen.setNom("%" + examen.getNom() + "%");
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.nom IS NOT ?";	
			}
			
			if (examen.getDate() != null)
			{
				sqlSelectRecherche += " AND gnw_examen.date = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.date IS NOT ?";	
			}
			
			if (examen.getFormat() == "Ecrit")
			{
				sqlSelectRecherche += " AND gnw_examen.format = 2";
			}
			else if (examen.getFormat() == "Oral")
			{
				sqlSelectRecherche += " AND gnw_examen.format = 1";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.format IS NOT NULL";	
			}
			
			if(groupe.getId() != null)
			{
				sqlSelectRecherche += " AND gnw_examen.fk_groupe = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.fk_groupe IS NOT ?";	
			}
			
			if(matiere.getId() != null)
			{
				sqlSelectRecherche += " AND gnw_examen.fk_matiere = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.fk_matiere IS NOT ?";	
			}
			
			if (examen.getMoyenneGenerale() != null) 
			{
				sqlSelectRecherche += " AND gnw_examen.moyenne_generale = ?";
			}
			else
			{
				sqlSelectRecherche += " AND gnw_examen.moyenne_generale IS NOT ?";	
			}
			
			preparedStatement = initialisationRequetePreparee(connexion, sqlSelectRecherche, true, professeur.getId(), examen.getId(), examen.getNom(), examen.getDate(), groupe.getId(), matiere.getId(), examen.getMoyenneGenerale());
			System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) 
			{
				examen = mapExamen(resultSet);
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
	 * @throws DAOException
	 */
	public Examen editer(Examen examen) throws DAOException
	{
		return new Examen();
	}
	
	/**
	 * Trouve un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public Examen trouver(Examen examen) throws DAOException
	{
		return new Examen();
	}

	/**
	 * Compte le nombre d'examens de la base de données
	 * 
	 * @throws DAOException
	 */
	public int compterTous() throws DAOException
	{
		return 1;
	}
	
	/**
	 * Supprime un examen dans la base de données
	 * 
	 * @param examen
	 * @throws DAOException
	 */
	public int supprimer(Examen examen) throws DAOException
	{
		return 1;
	}
	
	/**
	 * Pr�pare une r�quete SQL sur mesure
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
	 * Transfère les données du resultSet vers un objet
	 * 
	 * @param resultSet
	 * @return groupe
	 * @throws SQLException
	 */
	private static Examen mapExamen(ResultSet resultSet) throws SQLException 
	{
		Examen examen = new Examen();
		Matiere matiere = new Matiere();
		Groupe groupe = new Groupe();
		
		examen.setId(resultSet.getLong("id"));
		examen.setNom(resultSet.getString("nom"));
		examen.setDate(resultSet.getDate("date"));
		
		if (resultSet.getLong("format") == 1)
		{
			examen.setFormat("Oral");
		}
		else if (resultSet.getLong("format") == 2)
		{
			examen.setFormat("Ecrit");
		}
		
		examen.setMoyenneGenerale(resultSet.getFloat("moyenne_generale"));
		groupe.setNom(resultSet.getString("groupeNom"));
		matiere.setNom(resultSet.getString("matiereNom"));
		examen.setGroupe(groupe);
		examen.setMatiere(matiere);
		
		return examen;
	}
}
