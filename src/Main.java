import java.awt.EventQueue;
import java.io.*;
import javax.swing.*;
import java.util.*;
public class Main 
{
	public static ArrayList<Song> songs = new ArrayList<Song>();
	static HashTabel songsTabel = new HashTabel();
	public static void main(String[] args) throws Exception
	{
		createFolder();
		readFiles();
		for(Song song: songs)
		{
			if(song.getPath().substring(song.getPath().length()-4, song.getPath().length()-1).equals("wav"))
				songsTabel.insert(song);
		}
		
		for (int i = 0; i < songsTabel.array.length; i++)
		{
			System.out.println(i);
			songsTabel.array[i].inOrder(songsTabel.array[i].root);
		}
		System.out.println();
		System.out.println(songsTabel.find(songs.get(0)) + " " + songs.get(0).getSongName());
		System.out.println(songsTabel.find(songs.get(1)) + " " + songs.get(1).getSongName());
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI_Test window = new GUI_Test();
					window.frmSpooderfi.setVisible(true);
				} 
				catch (Exception e) 
				{
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
		
		System.out.println(foldPath);
		for(File file: folderFiles)
		{
			songs.add(new Song(file));
		}
	}
}
