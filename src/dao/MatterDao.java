package dao;

import java.util.Set;
import beans.Matter;

public interface MatterDao 
{
	/**
     * Creates a matter into database
     * 
     * @param matter
     * @throws DAOException
     */
	void create(Matter matter) throws DAOException;
	
	/**
     * Searches one or more matters into database
     * 
     * @param matter
     * @throws DAOException
     */
	Set<Matter> search(Matter matter) throws DAOException;
	
	/**
	 * Edits a matter into database
	 * 
	 * @param matter
	 * @throws DAOException
	 */
	void edit(Matter matter) throws DAOException;
	
	/**
	 * Checks the existance of a matter into database
	 * 
	 * @param matter
	 * @throws DAOException
	 */
	int check(Matter matter) throws DAOException;
	
	/**
	 * Returns a matter into database
	 * 
	 * @param matter
	 * @throws DAOException
	 */
	Matter get(Matter matter) throws DAOException;
	
	/**
	 * Deletes a matter into database
	 * 
	 * @param matter
	 * @throws DAOException
	 */
	void delete(Matter matter) throws DAOException;	
}
