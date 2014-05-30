package beans;


public class Administrateur extends Utilisateur implements Comparable<Administrateur>
{
	@Override
	public int compareTo(Administrateur administrateur2) 
	{
		int compId = this.getId().compareTo(administrateur2.getId());
        if(compId != 0) return compId;
        return 0;
	}

}
