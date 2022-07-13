import java.io.*;
import java.util.*;                  
import java.lang.Integer;           

/////////////////////////////////////////////////////////////////////////////////////

	class DataItem
	{
		public long dData; //one data item
      
//------------------------------------------------------------------------------------	
		
		public DataItem(long dd)
		{
         dData = dd; 
      }
      
//------------------------------------------------------------------------------------		
	
		public void displayItem()
		{ 
         System.out.print("/"+dData);//print data
      }
	}
	
/////////////////////////////////////////////////////////////////////////////////////

	class Node234
	{
		private static final int ORDER = 4;
		private int numItems;
      
		private Node234 parent;
		private Node234 childArray[] = new Node234[ORDER];
		private DataItem itemArray[] = new DataItem[ORDER-1];
      
//------------------------------------------------------------------------------------	

		public void connectChild(int childNum, Node234 child)//add child
		{
			childArray[childNum] = child;
         
			if(child != null)
         {
				child.parent = this;
         }
		}
//------------------------------------------------------------------------------------		
	
		public Node234 disconnectChild(int childNum)//remuves child
		{
			Node234 tempNode = childArray[childNum];
			childArray[childNum] = null;
			return tempNode;
		}
//------------------------------------------------------------------------------------		
	
		public Node234 getChild(int childNum)
		{ 
         return childArray[childNum]; //return child  j
      }
      
//------------------------------------------------------------------------------------	
		
		public Node234 getParent()
		{ 
         return parent; //return perent
      }
      
//------------------------------------------------------------------------------------		
	
		public boolean isLeaf()
		{ 
         return (childArray[0]==null) ? true : false; //return if leaf
      }
      
//------------------------------------------------------------------------------------	
		
		public int getNumItems()
		{ 
         return numItems; //get numer of data
      }
      
//------------------------------------------------------------------------------------	
		
		public DataItem getItem(int index)
		{ 
         return itemArray[index]; //get data
      }
      
//------------------------------------------------------------------------------------		
	
		public boolean isFull()
		{ 
         return (numItems==ORDER-1) ? true : false; //retuns if full
      }
      
//------------------------------------------------------------------------------------			
		
		public int insertItem(DataItem newItem)
		{
			//assumes node is not full
			numItems++;
			long newKey = newItem.dData;
			
			for(int j=ORDER-2; j>=0; j--)
			{
				if(itemArray[j] == null)
            {
					continue;
            }
				else
				{
					long itsKey = itemArray[j].dData;
               
					if(newKey < itsKey)
               {
						itemArray[j+1] = itemArray[j];
               }
					else
					{
						itemArray[j+1] = newItem;
						return j+1;
					}
				}//end else (not null)
			}//end for
         
			itemArray[0] = newItem;
         
			return 0;
		}
      
//------------------------------------------------------------------------------------		
	
		public DataItem removeItem()
		{
			//assumes node not empty
			DataItem temp = itemArray[numItems-1];
         
			itemArray[numItems-1] = null;
			numItems--;
         
			return temp;
		}
      
//------------------------------------------------------------------------------------		
	
		public void displayNode()	//format "/24/56/74/"
		{
			for(int j = 0; j<numItems; j++)
         {
				itemArray[j].displayItem();
         }
			System.out.println("/");
		}
	} //end class Node
	
/////////////////////////////////////////////////////////////////////////////////////

	class Tree234
	{
		private Node234 root = new Node234();
      
//------------------------------------------------------------------------------------		
					
			public void sortTraverse(long[] theArray)//start of sort
			{
				int i = 0;
				recSortTraverse(root, theArray, i);
			}
         
//------------------------------------------------------------------------------------		
		
			private int recSortTraverse(Node234 curNode, long[] theArray, int i)//sort fucton
			{
				if(curNode.isLeaf())//if node is a leaf
				{
					for(int j = 0; j<curNode.getNumItems(); j++)//for loop
					{
						theArray[i] = curNode.getItem(j).dData;
						i++;
					}
					return i;//return index
				}
				else//if node is not a leaf
				{
					for(int j = 0; j < curNode.getNumItems()+1; j++)//for loop
					{
						i = recSortTraverse(curNode.getChild(j), theArray, i);
						if(j < curNode.getNumItems())
						{
							theArray[i] = curNode.getItem(j).dData;
							i++;
						}
					}
					return i;//return index
				}
			}
         
//------------------------------------------------------------------------------------		
	
		public void insert(long dValue)
		{
			Node234 curNode = root;
			DataItem tempItem = new DataItem(dValue);
			
			while(true)
			{
				if (curNode.isFull() )//if node full,
				{
					split(curNode);//split it
					curNode = curNode.getParent();//back up
											
					curNode = getNextChild(curNode, dValue);
				}
				
				else if (curNode.isLeaf() )//if node is leaf
            {
					break;	
            }							
				else
            {
					curNode = getNextChild(curNode, dValue);
            }
			}
			
			curNode.insertItem(tempItem);	//insert new DataItem
		}
      
//------------------------------------------------------------------------------------		
		
		public void split(Node234 thisNode)
		{
			DataItem itemB, itemC;
			Node234 parent, child2, child3;
			int itemIndex;
			
			itemC = thisNode.removeItem();//remove rightmost item
			itemB = thisNode.removeItem();//remove next item
         
			child2 = thisNode.disconnectChild(2);//remove children
			child3 = thisNode.disconnectChild(3);
			
			Node234 newRight = new Node234();
			
			if(thisNode==root)//if this is the root
			{
				root = new Node234();//make a new root
				parent = root;	//and connect thisNode to it
				root.connectChild(0, thisNode);
			}
			else
         {
				parent = thisNode.getParent();
         }
			
			itemIndex = parent.insertItem(itemB);//insert old middle item to parent
			int n = parent.getNumItems();			
			
			for(int j = n-1; j>itemIndex; j--)//move parent's connections
			{
				Node234 temp = parent.disconnectChild(j);
				parent.connectChild(j+1, temp);//one child to the right
			}
			
			
			parent.connectChild(itemIndex+1, newRight);
			
			newRight.insertItem(itemC);//item C to newRight
			newRight.connectChild(0, child2);//connect to 0 and 1
			newRight.connectChild(1, child3);//on newRight
		}
	
//------------------------------------------------------------------------------------	

		public Node234 getNextChild(Node234 theNode, long theValue)
		{
			int j;
			int numItems = theNode.getNumItems();
         
			for(j=0; j<numItems; j++)//for each item in node
			{							
				if( theValue < theNode.getItem(j).dData)
            {
					return theNode.getChild(j);//return left child
             }
			} 
			return theNode.getChild(j);//return right child
		}
      
	} //end class

/////////////////////////////////////////////////////////////////////////////////////	

	class Tree234App
	{
		public static void sort(long[] theArray, int counter)//sort functon
		{
			Tree234 sortingTree = new Tree234();//build the tree

			for(int j = 0; j < counter; j++)
         {
				sortingTree.insert(theArray[j]);//insert array into tree
         }
			sortingTree.sortTraverse(theArray);//get array from tree
		}
      
//------------------------------------------------------------------------------------	
			
		public static void main(String[] args) throws IOException
		{
         
         Random rand = new Random();
			
         long[] arrayToSort = new long[20];
               
          for(int j = 0; j < 20; j++)//for loop
          {
				arrayToSort[j] = rand.nextInt(100);//20 random numbers
          }
               
			System.out.println("\nBefore sorting...");
         
			for(int j = 0; j < 20; j++)//print array
         {
				System.out.print(arrayToSort[j] + " ");
         }
                  
			sort(arrayToSort, 20);
               
			System.out.println("\n\nAfter sorting...");
         
			for(int j = 0; j < 20; j++)//print sorted array
         {
				System.out.print(arrayToSort[j] + " ");
         }
                  


			}//end main()		
	} //end class Tree234App
	
/////////////////////////////////////////////////////////////////////////////////////