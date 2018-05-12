import java.io.*;
import java.util.*;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Main 
{
	static ArrayList<Song> songs = new ArrayList<Song>();
	
	public static void main(String[] args) throws Exception
	{
		readFiles();
		for(Song song: songs)
			System.out.println(song.getSongName() + " from album " + song.getAlbum() + " by: " + song.getArtist() + " duration " + song.getDuration());
	}
	
	public static void readFiles()throws Exception
	{
		String foldPath = System.getProperty("user.home") + "/Desktop/Music";
		
		File folder = new File(foldPath);
		File[] folderFiles = folder.listFiles();
		
		for(File file: folderFiles)
			songs.add(new Song(file));
	}
}
