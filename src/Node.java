public class Node {

	int count;
	Song song;
	
	Node left;
	Node right;
	
	public Node(Song song)
	{
		this.song = song;
		count++;
	}
	
	public String toString()
	{
		return song + " appears " + count + " times";
	}
}
