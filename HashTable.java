import java.io.*;                    // for I/O
import java.util.*;                  // for Stack class
import java.lang.Integer;            // for parseInt()
///////////////////////////////////////////////////////////////////////////////////////////////
class DataItem
   { 
                                   
   public int iData;// data item (key)
                    
//--------------------------------------------------------------

   public DataItem(int ii)// constructor           
      { 
         iData = ii; 
      }
      
//--------------------------------------------------------------
      
   public int getKey()
      {
         return iData;
      }
      
//--------------------------------------------------------------

   }  
   
///////////////////////////////////////////////////////////////////////////////////////////////

class HashTable
   {
      private DataItem[] hashArray;//holds table
      private DataItem nonItem;//for deleted item
       
      private int arraySize;//size of table     
      private int divisor;//for folding hash
      private int num_items;
     
// -------------------------------------------------------------
      
      public HashTable(int size)//constructor
      {
         arraySize = size;     
         hashArray = new DataItem[arraySize];  
             
         nonItem = new DataItem(-1);//deleted item at key is -1    
         divisor = 1;
         
         while(size > 0)//Calculate the necessary divisor
         {
            size /= 10;
            divisor *= 10;
         }    
      }
      
// -------------------------------------------------------------

      public void displayTable()//prints the table
      {
         System.out.print("Table: ");
         
         for(int j=0; j<arraySize; j++)//for loop
            {
               if(hashArray[j] != null)
                  {
                     System.out.print(hashArray[j].iData+ " ");
                  }
               else
                  {
                     System.out.print("** ");
                  }
            }
            
         System.out.println("");
         
      }
      
// -------------------------------------------------------------
      
      public int hashFunc(int key)//hash function
      {  
         int hashVal = 0;
         
         while(key > 0)//while there ara stil numbers in the key
         {
            hashVal += key%divisor;//add group to hash  
            key /= divisor;
         }
         
         return hashVal % arraySize;//divide out the group from yhe key
      }
      
// -------------------------------------------------------------
                                        
      public void insert( DataItem item)// insert a DataItem
      {
         int key = item.getKey();
         int hashVal = hashFunc(key);// hash the key
                                        
         while(hashArray[hashVal] != null && hashArray[hashVal].iData != -1)// until empty cell or -1
            {
               ++hashVal;// add the step
               hashVal %= arraySize;// for wraparound
            }
         hashArray[hashVal] = item;// insert item
      } 
         
// -------------------------------------------------------------
      
         public DataItem delete(int key)// delete a DataItem
         {
            int hashVal = hashFunc(key);// hash the key
              
            while(hashArray[hashVal] != null)//until empty cell,
               {                              
                  if(hashArray[hashVal].iData == key)
                     {
                        num_items--;
                        DataItem temp = hashArray[hashVal];// save item
                        
                        hashArray[hashVal] = nonItem;// delete item
                        return temp;// return item
                     }
                     
                  ++hashVal;// add the step
                  hashVal %= arraySize;// for wraparound
               }
               
            return null; // can't find item
         }  
         
   // -------------------------------------------------------------
   
      public DataItem find(int key)// find item with key
         {
         int hashVal = hashFunc(key);     
   
         while(hashArray[hashVal] != null)// until empty cell,
            { 
                                         
            if(hashArray[hashVal].iData == key)
               {
                  return hashArray[hashVal];// yes, return item
               }
               
            ++hashVal;// add the step
            hashVal %= arraySize;// for wraparound
            
            }
            
         return null;// can't find item
         }
         
// -------------------------------------------------------------

///////////////////////////////////////////////////////////////////////////////////////////////
      
      public static void main(String args[])
      {
         HashTable table = new HashTable(23);//new hash table and data
         
         Random rand = new Random();
         
         int size = rand.nextInt(20) + 1;
         
         int nums[] = new int[size];
              
          for(int j = 0; j < size; j++)//for loop
          {
				nums[j] = rand.nextInt(10000);
          }
         
         for(int i : nums)
         {
            System.out.println("Inserting data item: " + i);       
            table.insert(new DataItem(i));
         }
         
         System.out.println("\nThe table: ");
         table.displayTable();//prints out the table
      
      }
   }
///////////////////////////////////////////////////////////////////////////////////////////////

