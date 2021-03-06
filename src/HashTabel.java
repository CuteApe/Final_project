public class HashTabel 
{
	MyTreeSet[] array;
	int arraySize;
	int itmInArray;

	
/*
* ----------------------------------------------------------------------------------------------
* ----------------------------------------------------------------------------------------------
* Constructors
*/
	
	//Constructors for the hash table, with the possibility to define size of the array
	public HashTabel()
	{
		array = new MyTreeSet[10];
		arraySize = 10;
		itmInArray = 0;
		
		for (int i = 0; i < array.length; i++)
			array[i] = new MyTreeSet();
	}
	
	//With user defined size
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
	/**
	 * Insert function to insert a new song into the table
	 * @param newSong, Song object containing the path, artist and song name
	 * @see Song
	 */
	public void insert(Song newSong)
	{
		 String artist = newSong.getArtist(); //Hash the artist name to
		 int hashVal = hashFunc(artist);	  //a place in the array
		 
		 array[hashVal].insert(newSong);
	}
	
	/**
	 * Find function to find and be able to play a song
	 * @param The song you wish to find
	 * @see Song
	 */
	public String find(Song s)
	{
		 String artist = s.getArtist(); //Hash the artist name to
		 int hashVal = hashFunc(artist);
		 
		 String path = array[hashVal].findNode(s.getSongName()).song.getPath();
		 
		if (path != null)
			return path;
		
		return null;
	}
	
		
/*
 * ----------------------------------------------------------------------------------------------
 * ----------------------------------------------------------------------------------------------
 * Private functions
 */
	
	/**
	 * Hashing function for the Artists name to get a index in the array
	 * @param string to be hashed
	 * @return the hashed value of the string
	 */
	private int hashFunc(String s)
	{
		int hashKeyVal = 0;	//Variable to carry over the result from the previous letter in the string
		
		for (int i = 0; i < s.length(); i++)
		{
			int charCode = s.charAt(i);
			
			hashKeyVal = (hashKeyVal * 29 + charCode) % arraySize; //Hashing function
		}
		
		return hashKeyVal;
	}
}
