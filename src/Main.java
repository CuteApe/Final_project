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
			String path = song.getPath();
			if(path.substring(path.length() - 4, path.length()).equals(".wav"))
				songsTabel.insert(song);
		}
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI window = new GUI();
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

		for(File file: folderFiles)
		{
			songs.add(new Song(file));
		}
	}
}
