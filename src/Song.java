import java.io.*;
import java.net.*;
import java.util.*;
import javax.sound.*;
import javax.sound.sampled.*;
import org.tritonus.share.sampled.file.TAudioFileFormat;
public class Song 
{
	private String path;
	private String fileName;
	private String songName;
	private String artist;
	private String album;
	private double length; //Where decimals represents whole seconds and not fraction of minutes
	private Clip clip;
	
	public Song(File file) throws Exception
	{
		path = file.getAbsolutePath();
		fileName = setFileName();
		splitName();
		clip = createClip(file);
		length = wavDuration();
	}
	
	private void splitName()
	{
		for(int i = 0; i < fileName.length() - 1; i++)
		{
			String c = String.valueOf(fileName.charAt(i));
			if(c.equals("-"))
			{
				if(artist == null)
				{
					artist = fileName.substring(0, i - 1);
					songName = fileName.substring(i + 2, fileName.length() - 4);
				}
				else
				{
					album = fileName.substring(artist.length() + 3, i - 1);
					songName = fileName.substring(i + 2, fileName.length() - 4);
				}
			}
		}
	}
	
	//
	/*private double mp3Duration(File file) throws UnsupportedAudioFileException, IOException
	{
		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
		if(fileFormat instanceof TAudioFileFormat)
		{
			Map<?, ?> props = ((TAudioFileFormat)fileFormat).properties();
			long microSec = (Long) props.get("duration");
			double sec = microSec/1000000;
			int min = (int)sec/60;
			sec -= min*60;
			sec /= 100;
			return min + sec;
		}
		
		else
			throw new UnsupportedAudioFileException();
	}*/
	
	private double wavDuration()
	{
		double sec = (int)(clip.getBufferSize() / (clip.getFormat().getFrameSize() * clip.getFormat().getFrameRate()));
		int min = (int) sec/60;
		sec -= min*60;
		sec /= 100;
		return min + sec ;
	}
	
	private Clip createClip(File song) throws Exception
	{
		AudioInputStream stream = AudioSystem.getAudioInputStream(song);
		AudioFormat format = stream.getFormat();
		if(format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
		{
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, 
									 format.getChannels(), format.getFrameSize() * 2, format.getFrameRate(), true);
			stream = AudioSystem.getAudioInputStream(format, stream); 
		}
		
		DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(), 
												(int)stream.getFrameLength() * format.getFrameSize());
		
		
		Clip clip = (Clip)AudioSystem.getLine(info);
		clip.close();	
		return clip;
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
	
	public double getDuration()
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
	
	public int compareTo(String s)
	{
		return songName.compareTo(s);
	}
	
	
	public Clip getClip()
	{
		return clip;
	}
	
}
