import java.util.Comparator;
public class CompDuration implements Comparator<Song>
{
	public int compare(Song s1, Song s2)
	{
		double pop1 = s1.getDuration();
		double pop2 = s2.getDuration();
		
		if(pop1 > pop2)
			return -1;
		
		else if(pop1 < pop2)
			return 1;
		
		else
			return 0;
	}
}