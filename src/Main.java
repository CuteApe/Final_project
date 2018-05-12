import java.io.*;
import java.lang.reflect.Array;

import javax.swing.*;
import java.util.*;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Main 
{
	static ArrayList<Song> songs = new ArrayList<Song>();
	static HashTabel songsTabel = new HashTabel();
	
	public static void main(String[] args) throws Exception
	{
		readFiles();
		
		for(Song song: songs)
		{
			System.out.println(song.getSongName() + " from album " + song.getAlbum() + " By: " + song.getArtist() + " duration " + song.getDuration());
			songsTabel.insert(song);
			System.out.println(Arrays.toString(songsTabel.array));
			
			System.out.println(songsTabel.find(song));
			
			System.out.println(songsTabel.find("These Days feat Jess Glynne Macklemore  Dan Caplen Official Video"));
			
			for (MyTreeSet tree : songsTabel.array)
				tree.inOrder(tree.root);
		}
		
	}
	
	
	
	
	
	
	public static void createFolder()
	{
		String foldPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Music";
		File folder = new File(foldPath);
		
		if(!folder.exists())
			new File(foldPath).mkdir();
	}
	
	public static void readFiles()throws Exception
	{
		String foldPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Music";
		
		File folder = new File(foldPath);
		File[] folderFiles = folder.listFiles();
		
		
		for(File file: folderFiles)
		{
			songs.add(new Song(file));
		}
	}
}
