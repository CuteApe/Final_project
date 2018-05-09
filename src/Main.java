import java.io.*;
import java.util.*;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Main 
{
	static ArrayList<Song> songs = new ArrayList<Song>();
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException
	{
		File folder = new File("C:\\Users\\gabriel.nilsson1\\Desktop\\Music");
		File[] folderFiles = folder.listFiles();
		
		for(File file: folderFiles)
			songs.add(new Song(file));
	}
}
