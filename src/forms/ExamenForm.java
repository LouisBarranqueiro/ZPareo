package forms;

import dao.ExamenDao;

public class ExamenForm 
{
	
    private ExamenDao examenDao;
    
	/**
     * Récupère l'objet : examenDao
     * 
     * @param examenDao
     */
    public ExamenForm(ExamenDao examenDao) 
    {
    	this.examenDao = examenDao;
    }
    
}
