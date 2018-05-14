import java.util.Comparator;
public class CompArtist implements Comparator<Song>
{
	public int compare(Song s1, Song s2)
	{
		String name1 = s1.getArtist();
		String name2= s2.getArtist();
		
		if(name1.compareTo(name2) > 0)
			return 1;
		
		else if(name1.compareTo(name2) < 0)
			return -1;
		
		else 
			return 0;
	}
}