
public class MyTreeSet {

	Node root;
	
	public MyTreeSet()
	{
		 root = null;
	}
	
	public void insert(Song name)
	{
		Node newNode = new Node(name);
		
		//If there is no root node set this one as it
		if (root==null)
		{
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
							return;
						}
					}
					
				}
			}
		}
	}
	

	
	public Node findNode(String sName)
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
	
	public void inOrder(Node node)
	{
		if (node != null)
		{
			inOrder(node.left);
			
			System.out.print(node);
			
			inOrder(node.right);
		}
	}
}
