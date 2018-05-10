import com.sun.org.apache.xml.internal.security.Init;

public class HashTabel 
{
	String[] array;
	int aSize;
	int itmInArray;
	float loadFactor;
	
	
	//Constructor for the hashtable
	public HashTabel()
	{
		array = new String[10];
		aSize = 0;
		itmInArray = 0;
		loadFactor = 0.75f;
	}
	
	public HashTabel(int intialCapacity)
	{
		array = new String[intialCapacity];
		aSize = 0;
		itmInArray = 0;
		loadFactor = 0.75f;
	}
	
	public HashTabel(int intialCapacity, float loadFactor)
	{
		array = new String[intialCapacity];
		aSize = 0;
		itmInArray = 0;
		this.loadFactor = loadFactor;
	}
/*
 * ----------------------------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------
 * Public functions
 */
	
	
	
	
	
	
	
	
	
	
	
	
/*
 * ----------------------------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------
 * Private functions
 */
	private void hasFunc(String[] stringsToInput, String[] theArray)
	{
		
	}
	public static void main(String[] args) 
	{

		
		

	}

}
