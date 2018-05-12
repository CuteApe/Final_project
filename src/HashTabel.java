
public class HashTabel 
{
	MyTreeSet[] array;
	int arraySize;
	int itmInArray;
	
	
	//Constructors for the hash table, with the possibility to define size of the array
	public HashTabel()
	{
		array = new MyTreeSet[10];
		arraySize = 10;
		itmInArray = 0;
	}
	
	public HashTabel(int intialCapacity)
	{
		array = new MyTreeSet[intialCapacity];
		arraySize = intialCapacity;
		itmInArray = 0;
		
		for (int i = 0; i < array.length; i++)
			array[i] = new MyTreeSet();
	}
	
/*
 * ----------------------------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------
 * Public functions
 */
	//Insert function to insert a new song into the table
	public void insert(Song newSong)
	{
		 String artist = newSong.getArtist();
		 
		 int hashVal = hashFunc(artist);
		 
		 array[hashVal].insert(newSong);
	}
	
	
	
	
	
	
	
	
	
	
	
/*
 * ----------------------------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------
 * Private functions
 */
	
	//Hashin function for the Artists name to get a index in the array
	private int hashFunc(String s)
	{
		int hashKeyVal = 0;	//Variable to carry over the result from the previous letter in the string
		
		for (int i = 0; i < s.length(); i++)
		{
			int charCode = s.charAt(i);
			int temp = hashKeyVal;
			
			hashKeyVal = (hashKeyVal * 29 + charCode) % arraySize; //Hashing function
			
            System.out.println("Hash Key Value " + temp + " * 27 + Character Code " + charCode + " % arraySize " + arraySize + " = " + hashKeyVal);
		}
		
		return hashKeyVal;
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) 
	{

		
		

	}

}
