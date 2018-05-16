import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.swing.JFileChooser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SongTest 
{
	Song songTest;
	@BeforeEach
	void setUp() throws Exception, FileNotFoundException
	{
		//For this test we'll use Shots by Imagine Dragons
		//Sets path to Document/TestMusic
		String foldPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/TestMusic";
		File folder = new File(foldPath);
		
		if(!folder.exists())
			new File(foldPath).mkdir();
		File[] folderFiles = folder.listFiles();
		
		//Creates a song of the first file in the folder Document/TestMusic to use in the testing.
		songTest = new Song(folderFiles[0]);
	}

	@Test
	void testSetFileName() 
	{
		songTest.setFileName();
		assertTrue(songTest.getArtist().equals("Imagine Dragons"),"Checks that artist name has been correctly named");
		assertTrue(songTest.getSongName().equals("Shots"),"Checks that song name has been correctly named");
		
	}

	@Test
	void testGetDuration() 
	{
		assertEquals(4.01, songTest.getDuration(), "Calls on the getDuration method which returns the duration calculated earlier by wavDuration");
	}

	@Test
	void testGetClip() 
	{
		assertEquals(AudioFormat.Encoding.PCM_SIGNED, songTest.getClip().getFormat().getEncoding(), "Makes sure that the encoding of the clip is of format PCM_SIGNED");
	}

}
