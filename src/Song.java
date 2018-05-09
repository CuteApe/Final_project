import java.io.*;
import java.util.*;
import javax.sound.*;
import javax.sound.sampled.*;
import org.tritonus.share.sampled.file.TAudioFileFormat;
public class Song 
{
	String path;
	String fileName;
	String songName;
	String artist;
	String album;
	float length; //Where 
	
	public Song(File file) throws UnsupportedAudioFileException, IOException
	{
		path = file.getAbsolutePath();
		System.out.println(path);
		fileName = setFileName();
		splitName();
		length = fileDuration(file);
		//System.out.println(length);
	}
	
	private void splitName()
	{
		for(int i = 0; i < fileName.length() - 1; i++)
		{
			String c = String.valueOf(fileName.charAt(i));
			if(c.equals("-"))
			{
				artist = fileName.substring(0, i - 1);
				songName = fileName.substring(i + 2, fileName.length() - 4);
			}
		}
	}
	
	private float fileDuration(File file) throws UnsupportedAudioFileException, IOException
	{
		try
		{
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			if(fileFormat instanceof TAudioFileFormat)
			{
				Map<?, ?> props = ((TAudioFileFormat)fileFormat).properties();
				long microSec = (Long) props.get("duration");
				float sec = microSec/1000000;
				int tempMin = (int)sec/60;
				sec -= tempMin*60;
				System.out.println(sec /= 100);
				return sec;
			}
			
			else
				throw new UnsupportedAudioFileException();
		}
		
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		return 0;
	}
	
	private String setFileName()
	{
		for(int i = path.length(); i >= 0; i--)
		{
			String c = String.valueOf(path.charAt(i - 1));
			if(c.equals("\\"))
			{
				return fileName = path.substring(i);
			}
		}
		return null;
	}
	
	public float getDuration()
	{
		return length;
	}
	
	public String getSongName()
	{
		return songName;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	public String getAlbum()
	{
		if(album == null || album == "")
			return songName;
		
		return album;
	}
	
}
