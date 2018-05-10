import java.io.*;
import java.util.*;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Main 
{
	static ArrayList<Song> songs = new ArrayList<Song>();
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, Exception
	{
		String foldPath = System.getProperty("user.home") + "/Desktop/Music";
		String testPath = "C:\\Users\\Grron\\Desktop\\Music";
		
		File folder = new File(testPath);
		File[] folderFiles = folder.listFiles();
		
		for(File file: folderFiles)
			songs.add(new Song(file));
		
		for(Song song: songs)
			System.out.println(song.length);
	}
}
