import static org.junit.Assert.*;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class HashTabelTest {

	HashTabel tabel;
	Song one;
	Song two;
	Song three;
	
	@Before
	public void setUp() throws Exception
	{
		tabel = new HashTabel();
		one = new Song(new File("MGK - Swing life away.wav"));
		two = new Song(new File("Pewdiepie - Outro.wav"));
		three = new Song(new File("Rudimental - These Days feat Jess Glynne Macklemore  Dan Caplen Official Video.wav"));
	}
	
	
	@Test
	public void hashTabelConstructor() {
		assertEquals("Checks if new hashtabel has a size of 10", 10, tabel.arraySize);
		
		tabel = new HashTabel(30);
		
		assertEquals("Checks if new user defiend size works", 30, tabel.arraySize);
	}
	
	@Test
	public void testInsertandFind()
	{
		tabel.insert(one);
		assertEquals("Checks if insert function actually inserts the song and that it can be found and returns the path", "C:\\Users\\Simon Nordqvist\\Desktop\\CuteApeWorkspace\\CuteApeProject\\Final_project\\MGK - Swing life away.wav", tabel.find(one));
	}

}
