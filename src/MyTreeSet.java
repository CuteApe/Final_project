import java.util.Iterator;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class MyTreeSet {

	Node root;
	private int maxNum = 0;
	private String maxName = null;
	private int size = 0;
	
	public MyTreeSet()
	{
		 root = null;
		 size = 0;
		 maxName = null;
		 maxNum = 0;
	}
	
	public void insert(Song name)
	{
		Node newNode = new Node(name);
		
		//If there is no root node set this one as it
		if (root==null)
		{
			size++;
			root = newNode;
		}
		
		else
		{
			//If the node already exists increase the count
			if(findNode(name.getSongName()) != null)
				findNode(name.getSongName()).count++;

			else
			{		
				Node focus = root;
				
				//Future parent node for our new node
				Node parent;

				//Traverse the tree
				while(true)
				{
					parent = focus;
					
					//Check if the what side of the tree the new node should go
					if (name.compareTo(focus.song.getSongName()) < 0)
					{
						focus = focus.left; //Switch focus to the left child
						
						//If this child has no children set the newNode to it's child
						if (focus==null)
						{
							parent.left = newNode;
							size++;
							return;
						}
					}
					
					//Same as above but for the right child
					else
					{
						focus = focus.right;
						
						if(focus==null)
						{
							parent.right = newNode;
							size++;
							return;
						}
					}
					
				}
			}
		}
	}
	
	public void makeEmpty()
	{
		size = 0;
		root = null;
	}
	
	
	
	private Node findNode(String sName)
	{	
		 Node focus = root;
		 
		 while(focus.song.getSongName() != sName)
		 {
			 if(focus.song.getSongName().compareTo(sName) < 0)
				 focus = focus.right;
			 else
				 focus = focus.left;
			 
			 if(focus == null)
				 return null;
		 }
	 
		return focus;
	}

	
	
	public static void main(String[] args) {
		
		MyTreeSet tree = new MyTreeSet();
		
		
	}

}
