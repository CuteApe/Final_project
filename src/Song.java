import java.io.*;
import java.text.DecimalFormat;
import javax.sound.sampled.*;

public class Song 
{
	private String 
		path,
		fileName,
		songName,
		artist;
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
	*Splites the filename into the name of the song and name of the artist.
	*Requires the filename to be in a specific format, Artist - Song name.
	*Where '-' is used as a divider between the different names.
	*/
	private void splitName()
	{
		for(int i = 0; i < fileName.length() - 1; i++)
		{
			String c = String.valueOf(fileName.charAt(i));
			if(c.equals("-"))
			{
				int x = 0;
				int y = 0;
				for(int j = i; j > 0; j--)
				{
					String a = String.valueOf(fileName.charAt(j));
					if(!a.equals(" ") && !a.equals("-"))
					{
						x = j;
						break;
					}
				}
				
				for(int j = i; j < fileName.length(); j++)
				{
					String a = String.valueOf(fileName.charAt(j));
					if(!a.equals(" ") && !a.equals("-"))
					{
						y = j;
						break;
					}
				}
				
				artist = fileName.substring(0, x + 1);
				songName = fileName.substring(y, fileName.length() - 4);
			}
		}
	}
	
	/**
	 * Makes use of Clip information bufferSize, frameSize and frameRate to calculate the duration of the clip.
	 * @return the duration of the .wav clip; song, where the decimals represent whole seconds and not fractions of a minute.
	 * If a clip doesn't exist of the song it returns -1.
	 */
	private double wavDuration()
	{
		if(clip == null)
			return -1;
		
		double sec = (int)(clip.getBufferSize() / (clip.getFormat().getFrameSize() * clip.getFormat().getFrameRate()));
		int min = (int) sec/60;
		sec -= min*60;
		sec /= 100;
		return min + sec;
	}
	
	/**
	 * Creates a clip with the encoding format PCM_SINGED 
	 * @param songFile - file of the song which you want to create a clip of.
	 * @return clip with PCM_SIGNED encoding
	 */
	private Clip createClip(File songFile) throws Exception
	{
		AudioInputStream stream = AudioSystem.getAudioInputStream(songFile);
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
	 
	/**
	 * Creates a string with song name, artist and the duration o the sing, where duration has the DecimalFormat "0.00" and '.' is replaced with ':'
	 * @return a string in the format "Song - Artist - X:YY"
	 */
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		return songName + " - " + artist + " - " + df.format(getDuration()).replace(',', ':');
	}
	
	/**
	 * Creates a string 
	 * @return 
	 */
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
	
	/**
	 * @return
	 */
	public double getDuration()
	{
		return length;
	}
	
	/**
	 * @return
	 */
	public int getSeconds()
	{
		int min = (int)length;
		double sec = length - min;
		sec *= 100;
		return min*60 + (int)sec;
	}
	
	/**
	 * @return
	 */
	public String getSongName()
	{
		return songName;
	}
	
	/**
	 * @return
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * @return
	 */
	public int compareTo(String s)
	{
		return toString().compareTo(s);
	}
	
	/**
	 * @return
	 */
	public String getPath()
	{
		return path;
	}
	
	/**
	 * @return
	 */
	public Clip getClip()
	{
		return clip;
	}
	
}
