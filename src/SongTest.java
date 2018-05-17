import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;

public class SongTest 
{
	Song songTest;
	@Before
	public void setUp() throws Exception, FileNotFoundException
	{
		//For this test we'll use MANS NOT HOT by Big Shaq
		//Sets path to Document/TestMusic
		String foldPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Music";
		File folder = new File(foldPath);
		
		if(!folder.exists())
			new File(foldPath).mkdir();
		File[] folderFiles = folder.listFiles();
		
		//Creates a song of the first file in the folder Document/Music to use in the testing.
		songTest = new Song(folderFiles[0]);
	}

	@Test
	public void testSetFileName() 
	{
		songTest.setFileName();
		assertTrue("Checks that artist name has been correctly named",songTest.getArtist().equals("Big Shaq"));
		assertTrue("Checks that song name has been correctly named",songTest.getSongName().equals("MANS NOT HOT"));
		
	}

	@Test
	public void testGetDuration() 
	{
		assertEquals( "Calls on the getDuration method which returns the duration calculated earlier by wavDuration", 3.05, songTest.getDuration(),0.0);
	}

	@Test
	public void testGetClip() 
	{
		assertEquals("Makes sure that the encoding of the clip is of format PCM_SIGNED", AudioFormat.Encoding.PCM_SIGNED, songTest.getClip().getFormat().getEncoding());
	}

}
