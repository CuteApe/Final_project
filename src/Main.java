import java.awt.EventQueue;
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
		}
		
		for (int i = 0; i < songsTabel.array.length; i++)
		{
			System.out.println(i + " " + songsTabel.array[i].root);
		}
		
		System.out.println(songsTabel.find(songs.get(0)) + " " + songs.get(0).getSongName());
		System.out.println(songsTabel.find(songs.get(1)) + " " + songs.get(1).getSongName());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Test window = new GUI_Test();
					window.frmSpooderfi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
