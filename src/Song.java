import java.io.*;
import javax.sound.sampled.*;
public class Song 
{
	private String 
		path,
		fileName,
		songName,
		artist,
		album;
	private double length;
	private Clip clip;
	
	public Song(File file) throws Exception
	{
		path = file.getAbsolutePath();
		fileName = setFileName();
		splitName();
		clip = createClip(file);
		length = wavDuration();
	}
	/**
	*Splites the filename into the name of the song, name of the artist and album if album name exists in the filename.
	*Requires the filename to be in a specific format, Artist - Album - Song name.
	*Where "-" is used as a divider between the different names.
	*/
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
	
	/**
	 * Makes use of Clip to calculate the duration of the song with
	 * @return the duration of the .wav clip; duration of the song, where the decimals represent whole seconds and not fractions of a minute.
	 * If a clip doesn't exist of the song it returns -1.
	 */
	private double wavDuration()
	{
		if(clip == null)
			return -1;
		
		double sec = (int)(clip.getBufferSize() / (clip.getFormat().getFrameSize() * clip.getFormat().getFrameRate()));
		int min = (int) sec/60;
		sec -= min*60;
		min *= 100;
		double duration = Math.round(min + sec);
		return duration /100;
	}
	
	/**
	 * 
	 * @param 
	 * @return
	 */
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
	
	public String toString()
	{
		return songName + " " + getAlbum() + " " + artist;
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
			return "Unknown";
		
		return album;
	}
	
	public int compareTo(String s)
	{
		return songName.compareTo(s);
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Clip getClip()
	{
		return clip;
	}
	
}
