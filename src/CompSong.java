import java.util.Comparator;
public class CompSong implements Comparator<Song>
{
	public int compare(Song c1, Song c2)
	{
		String name1 = c1.getSongName();
		String name2= c2.getSongName();
		
		if(name1.compareTo(name2) > 0)
			return 1;
		
		else if(name1.compareTo(name2) < 0)
			return -1;
		
		else 
			return 0;
	}
}